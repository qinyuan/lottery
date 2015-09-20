package com.qinyuan15.lottery.mvc.controller;

import com.qinyuan.lib.contact.mail.MailAddressValidator;
import com.qinyuan.lib.database.hibernate.HibernateUtils;
import com.qinyuan.lib.lang.DateUtils;
import com.qinyuan.lib.lang.IntegerUtils;
import com.qinyuan.lib.mvc.controller.ImageController;
import com.qinyuan.lib.mvc.security.PasswordValidator;
import com.qinyuan15.lottery.mvc.AppConfig;
import com.qinyuan15.lottery.mvc.account.DatabaseTelValidator;
import com.qinyuan15.lottery.mvc.account.NewUserValidator;
import com.qinyuan15.lottery.mvc.account.PreUserSerialKeyBuilder;
import com.qinyuan15.lottery.mvc.dao.PreUser;
import com.qinyuan15.lottery.mvc.dao.PreUserDao;
import com.qinyuan15.lottery.mvc.dao.User;
import com.qinyuan15.lottery.mvc.dao.UserDao;
import com.qinyuan15.lottery.mvc.mail.RegisterMailSender;
import org.apache.commons.lang3.BooleanUtils;
import org.apache.commons.lang3.tuple.Pair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Controller about register
 * Created by qinyuan on 15-6-29.
 */
@Controller
public class RegisterController extends ImageController {
    private final static Logger LOGGER = LoggerFactory.getLogger(RegisterController.class);

    private UserDao userDao = new UserDao();

    @RequestMapping("/register")
    public String index(@RequestParam(value = "serial", required = true) String serial) {
        IndexHeaderUtils.setHeaderParameters(this);

        if (StringUtils.hasText(serial)) {
            PreUser preUser = new PreUserDao().getInstanceBySerialKey(serial);
            setAttribute("preUser", preUser);
            addJavaScriptData("telValidateDescriptionPage", AppConfig.getTelValidateDescriptionPage());
            if (preUser != null && new UserDao().hasEmail(preUser.getEmail())) {
                setAttribute("userInfoCompleted", true);
            }
        }

        setTitle("完善个人信息");
        addCss("personal-center-frame");
        addCss("personal-center");
        addJs("personal-center-birthday");
        addCssAndJs("register");
        return "register";
    }

    @RequestMapping("/register-complete-user-info.json")
    @ResponseBody
    public String completeUserInfo(@RequestParam(value = "serialKey", required = true) String serialKey,
                                   @RequestParam(value = "username", required = true) String username,
                                   @RequestParam(value = "password", required = true) String password,
                                   @RequestParam(value = "password2", required = true) String password2,
                                   @RequestParam(value = "realName", required = true) String realName,
                                   @RequestParam(value = "tel", required = true) String tel,
                                   @RequestParam(value = "gender", required = true) String gender,
                                   @RequestParam(value = "birthdayYear", required = false) Integer birthdayYear,
                                   @RequestParam(value = "birthdayMonth", required = false) Integer birthdayMonth,
                                   @RequestParam(value = "birthdayDay", required = false) Integer birthdayDay,
                                   @RequestParam(value = "constellation", required = true) String constellation,
                                   @RequestParam(value = "hometown", required = true) String hometown,
                                   @RequestParam(value = "residence", required = true) String residence,
                                   @RequestParam(value = "lunarBirthday", required = false) String lunarBirthdayString) {

        if (!StringUtils.hasText(serialKey)) {
            return failByInvalidParam();
        }

        PreUser preUser = new PreUserDao().getInstanceBySerialKey(serialKey);
        if (preUser == null) {
            return failByInvalidParam();
        }

        UserDao userDao = new UserDao();
        String email = preUser.getEmail();
        if (userDao.hasEmail(email)) {
            return fail("该邮箱已经被注册！");
        }

        Pair<Boolean, String> usernameValidation = new NewUserValidator().validateUsername(username);
        if (!usernameValidation.getLeft()) {
            return fail(usernameValidation.getRight());
        }

        Pair<Boolean, String> passwordValidation = new PasswordValidator().validate(password);
        if (!passwordValidation.getLeft()) {
            return fail(passwordValidation.getRight());
        }

        if (!password.equals(password2)) {
            return fail("两次输入的密码需要一致！");
        }

        if (StringUtils.hasText(tel)) {
            Pair<Boolean, String> telValidation = new DatabaseTelValidator().validate(tel);
            if (!telValidation.getLeft()) {
                return fail(telValidation.getRight());
            }
        } else {
            tel = null;
        }

        try {
            Integer spreadUserId = preUser.getSpreadUserId();
            String spreadWay = preUser.getSpreadWay();
            Integer userId;
            if (IntegerUtils.isPositive(spreadUserId) && StringUtils.hasText(spreadWay)) {
                userId = userDao.addNormal(username, password, email, spreadUserId, spreadWay);
                LivenessAdder.addLiveness(userId, false, spreadUserId, spreadWay, preUser.getActivityId());
            } else {
                userId = userDao.addNormal(username, password, email);
            }

            User user = userDao.getInstance(userId);
            user.setRealName(StringUtils.hasText(realName) ? realName : null);
            user.setTel(tel);
            user.setGender(StringUtils.hasText(gender) ? gender : null);
            user.setBirthday(DateUtils.buildDateString(birthdayYear, birthdayMonth, birthdayDay));
            user.setConstellation(StringUtils.hasText(constellation) ? constellation : null);
            user.setHometown(StringUtils.hasText(hometown) ? hometown : null);
            user.setResidence(StringUtils.hasText(residence) ? residence : null);
            user.setLunarBirthday(StringUtils.hasText(lunarBirthdayString));
            HibernateUtils.update(user);

            return success();
        } catch (Exception e) {
            LOGGER.error("fail to add user, username: {}, password: {}, email {}, info {}",
                    username, password, email, e);
            return failByDatabaseError();
        }

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
            new RegisterMailSender().send(email, serialKey);
            return success();
        } catch (Exception e) {
            LOGGER.error("fail to register email: {}, info {}", email, e);
            return failByDatabaseError();
        }
    }

    /*@RequestMapping(value = "register-submit.json", method = RequestMethod.POST)
    @ResponseBody
    public String submit(@RequestParam(value = "email", required = true) String email,
                         @RequestParam(value = "username", required = true) String username,
                         @RequestParam(value = "password", required = true) String password,
                         @RequestParam(value = "password2", required = true) String password2,
                         @RequestParam(value = "identityCode", required = true) String identityCode) {

        if (!validateIdentityCode(identityCode)) {
            return fail("验证码输入错误！");
        }

        if (!new MailAddressValidator().validate(email)) {
            return fail("邮箱格式错误！");
        }

        if (userDao.hasEmail(email)) {
            return fail("该邮箱已经被注册！");
        }

        Pair<Boolean, String> userValidation = new NewUserValidator().validateUsername(username);
        if (!userValidation.getLeft()) {
            return fail(userValidation.getRight());
        }

        if (!StringUtils.hasText(password)) {
            return fail("密码不能为空！");
        }

        if (password.length() < 6) {
            return fail("密码至少需要6个字符");
        }

        if (password.length() > 20) {
            return fail("密码不得超过20个字符");
        }

        if (!password.equals(password2)) {
            return fail("两次输入的密码不一致");
        }

        try {
            LivenessAdder livenessAdder = new LivenessAdder(session);
            Integer userId = userDao.addNormal(username, password, email, livenessAdder.getSpreadUserId(),
                    livenessAdder.getSpreadWay());
            livenessAdder.addLiveness(userId, false);
            new ActivateMailSender().send(userId);
            return success();
        } catch (Exception e) {
            LOGGER.error("fail to add user, username: {}, password: {}, email {}, info {}",
                    username, password, email, e);
            return failByDatabaseError();
        }
    }*/



    /*@RequestMapping(value = "resend-activate-email.json", method = RequestMethod.GET)
    @ResponseBody
    public String resendValidateEmail(@RequestParam(value = "email", required = true) String email) {
        User user = userDao.getInstanceByEmail(email);
        if (user == null) {
            return fail("用户不存在！");
        }

        try {
            new ActivateMailSender().send(user.getId());
            return success();
        } catch (Exception e) {
            LOGGER.error("Fail to resend email to {}, info: {}", email, e);
            return fail("邮件发送失败");
        }
    }*/

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
}
