package com.qinyuan15.lottery.mvc.controller;

import com.qinyuan.lib.contact.mail.MailAddressValidator;
import com.qinyuan.lib.lang.IntegerUtils;
import com.qinyuan.lib.mvc.controller.ImageController;
import com.qinyuan.lib.mvc.security.PasswordValidator;
import com.qinyuan15.lottery.mvc.AppConfig;
import com.qinyuan15.lottery.mvc.account.NewUserValidator;
import com.qinyuan15.lottery.mvc.account.PreUserSerialKeyBuilder;
import com.qinyuan15.lottery.mvc.dao.PreUser;
import com.qinyuan15.lottery.mvc.dao.PreUserDao;
import com.qinyuan15.lottery.mvc.dao.User;
import com.qinyuan15.lottery.mvc.dao.UserDao;
import com.qinyuan15.lottery.mvc.mail.RegisterMailSender;
import org.apache.commons.lang3.BooleanUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.tuple.Pair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.Map;

/**
 * Controller about register
 * Created by qinyuan on 15-6-29.
 */
@Controller
public class RegisterController extends ImageController {
    private final static Logger LOGGER = LoggerFactory.getLogger(RegisterController.class);

    private UserDao userDao = new UserDao();

    @RequestMapping("/register")
    public String index(@RequestParam("serial") String serial) {
        IndexHeaderUtils.setHeaderParameters(this);

        if (StringUtils.isNotBlank(serial)) {
            PreUser preUser = new PreUserDao().getInstanceBySerialKey(serial);
            setAttribute("preUser", preUser);
            addJavaScriptData("telValidateDescriptionPage", AppConfig.getTelValidateDescriptionPage());

            String websiteintroductionlink = AppConfig.getWebsiteIntroductionLink();
            if (StringUtils.isBlank(websiteintroductionlink)) {
                websiteintroductionlink = "javascript:void(0)";
            }
            setAttribute("websiteIntroductionLink", websiteintroductionlink);

            if (preUser != null && new UserDao().hasEmail(preUser.getEmail())) {
                setAttribute("userInfoCompleted", true);
            }
        }

        setAttribute("registerHeaderLeftLogo", pathToUrl(AppConfig.getRegisterHeaderLeftLogo()));
        setAttribute("registerHeaderRightLogo", pathToUrl(AppConfig.getRegisterHeaderRightLogo()));
        setTitle("完善个人信息");
        addCssAndJs("register");
        setAttribute("noFooter", true);
        return "register";
    }

    @RequestMapping("/register-by-qq")
    public String registerByQQ() {
        setAttributeAndJavaScriptData("byQQ", true);
        return index(null);
    }

    @RequestMapping("/register-complete-user-info.json")
    @ResponseBody
    public String completeUserInfo(@RequestParam("serialKey") String serialKey,
                                   @RequestParam("username") String username,
                                   @RequestParam("password") String password) {
        // get email from serial key
        if (StringUtils.isBlank(serialKey)) {
            return failByInvalidParam();
        }
        PreUser preUser = new PreUserDao().getInstanceBySerialKey(serialKey);
        if (preUser == null) {
            return failByInvalidParam();
        }
        String email = preUser.getEmail();

        Pair<Boolean, String> userInfoValidation = validateUserInfo(email, username, password);
        if (!userInfoValidation.getLeft()) {
            return fail(userInfoValidation.getRight());
        }

        try {
            Integer spreadUserId = preUser.getSpreadUserId();
            String spreadWay = preUser.getSpreadWay();
            Integer userId;
            if (IntegerUtils.isPositive(spreadUserId) && StringUtils.isNotBlank(spreadWay)) {
                userId = userDao.addNormal(username, password, email, spreadUserId, spreadWay);
                LivenessAdder.addLiveness(userId, false, spreadUserId, spreadWay, preUser.getActivityId());
            } else {
                userId = userDao.addNormal(username, password, email);
            }
            User user = new UserDao().getInstance(userId);
            login(user.getUsername(), user.getPassword());
            return success();
        } catch (Exception e) {
            LOGGER.error("fail to add user, username: {}, password: {}, email {}, info {}",
                    username, password, email, e);
            return failByDatabaseError();
        }
    }

    @RequestMapping("/register-complete-user-info-by-qq.json")
    @ResponseBody
    public String completeUserInfoByQQ(@RequestParam("qqOpenId") String qqOpenId,
                                       @RequestParam("email") String email,
                                       @RequestParam("username") String username,
                                       @RequestParam("password") String password) {
        Pair<Boolean, String> userInfoValidation = validateUserInfo(email, username, password);
        if (!userInfoValidation.getLeft()) {
            return fail(userInfoValidation.getRight());
        }

        try {
            LivenessAdder livenessAdder = new LivenessAdder(session);
            Integer spreadUserId = livenessAdder.getSpreadUserId();
            String spreadWay = livenessAdder.getSpreadWay();
            Integer userId;
            if (IntegerUtils.isPositive(spreadUserId) && StringUtils.isNotBlank(spreadWay)) {
                userId = userDao.addNormal(username, password, email, spreadUserId, spreadWay, qqOpenId);
                LivenessAdder.addLiveness(userId, false, spreadUserId, spreadWay, livenessAdder.getActivityId());
            } else {
                userId = userDao.addNormal(username, password, email, qqOpenId);
            }
            User user = new UserDao().getInstance(userId);
            login(user.getUsername(), user.getPassword());
            return success();
        } catch (Exception e) {
            LOGGER.error("fail to add user, username: {}, password: {}, email {}, info {}",
                    username, password, email, e);
            return failByDatabaseError();
        }
    }

    private Pair<Boolean, String> validateUserInfo(String email, String username, String password) {
        UserDao userDao = new UserDao();
        if (userDao.hasEmail(email)) {
            Pair.of(false, "该邮箱已经被注册！");
        }

        Pair<Boolean, String> usernameValidation = new NewUserValidator().validateUsername(username);
        if (!usernameValidation.getLeft()) {
            return usernameValidation;
        }

        Pair<Boolean, String> passwordValidation = new PasswordValidator().validate(password);
        if (!passwordValidation.getLeft()) {
            return passwordValidation;
        }

        return Pair.of(true, null);
    }

    @RequestMapping(value = "register-submit.json", method = RequestMethod.POST)
    @ResponseBody
    public String submit(@RequestParam(value = "email", required = true) String email,
                         @RequestParam(value = "identityCode", required = true) String identityCode) {

        if (!validateIdentityCode(identityCode)) {
            return fail("验证码输入错误！");
        }

        return sendRegisterMail(email);
    }

    @RequestMapping(value = "resend-register-email.json", method = RequestMethod.GET)
    @ResponseBody
    public String resendValidateEmail(@RequestParam(value = "email", required = true) String email) {
        return sendRegisterMail(email);
    }

    private String sendRegisterMail(String email) {
        if (!new MailAddressValidator().validate(email)) {
            return fail("邮箱格式错误！");
        }

        if (userDao.hasEmail(email)) {
            return fail("该邮箱已经被注册！");
        }

        try {
            String serialKey;
            PreUserDao dao = new PreUserDao();
            PreUser preUser = dao.getInstanceByEmail(email);
            if (preUser == null) {
                // add pre user instance
                LivenessAdder livenessAdder = new LivenessAdder(session);

                serialKey = new PreUserSerialKeyBuilder().build();
                dao.add(email, livenessAdder.getSpreadUserId(), livenessAdder.getSpreadWay(),
                        livenessAdder.getActivityId(), serialKey);
            } else {
                // get serial key from pre use directly
                serialKey = preUser.getSerialKey();
            }
            try {
                new RegisterMailSender().send(email, serialKey);
            } catch (Exception e) {
                LOGGER.error("Fail to send email, info: {}", e);
                return fail("邮件发送失败");
            }
            return success();
        } catch (Exception e) {
            LOGGER.error("fail to register email: {}, info {}", email, e);
            return failByDatabaseError();
        }
    }

    private final static String TO_LOGIN_HTML = "<a href='javascript:void(0)' onclick='switchToLogin()'>立即登录</a>";

    @RequestMapping(value = "validate-email.json", method = RequestMethod.GET)
    @ResponseBody
    public String validateEmail(@RequestParam(value = "email", required = true) String email) {
        if (!new MailAddressValidator().validate(email)) {
            return fail("请输入有效的邮箱格式");
        }

        if (userDao.hasEmail(email)) {
            return fail("邮箱已被注册，" + TO_LOGIN_HTML);
        } else {
            return success();
        }
    }

    @RequestMapping(value = "validate-username.json", method = RequestMethod.GET)
    @ResponseBody
    public String validateUsername(@RequestParam(value = "username", required = true) String username,
                                   @RequestParam(value = "withoutLink", required = false) Boolean withoutLink) {
        Pair<Boolean, String> validation = new NewUserValidator().validateUsername(username);
        if (validation.getLeft()) {
            return success();
        } else {
            String reason = validation.getRight();
            if (reason.equals(NewUserValidator.REGISTERED) && !BooleanUtils.toBoolean(withoutLink)) {
                return fail(reason + TO_LOGIN_HTML);
            }
            return fail(reason);
        }
    }

    @RequestMapping(value = "try-to-login-by-qq-open-id.json", method = RequestMethod.GET)
    @ResponseBody
    public String hasQqOpenId(@RequestParam("qqOpenId") String qqOpenId) {
        Map<String, Boolean> map = new HashMap<>();
        try {
            User user = new UserDao().getInstanceByQqOpenId(qqOpenId);
            map.put("result", user != null);
            if (user != null) {
                login(user.getUsername(), user.getPassword());
            }
        } catch (Exception e) {
            map.put("result", false);
            LOGGER.error("fail to validate qqOpenId, qqOpenId: {}, info: {}", qqOpenId, e);
        }
        return toJson(map);
    }
}
