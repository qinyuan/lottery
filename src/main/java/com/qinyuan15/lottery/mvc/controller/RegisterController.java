package com.qinyuan15.lottery.mvc.controller;

import com.qinyuan.lib.contact.mail.MailAddressValidator;
import com.qinyuan.lib.mvc.controller.ImageController;
import com.qinyuan15.lottery.mvc.account.NewUserValidator;
import com.qinyuan15.lottery.mvc.dao.PreUser;
import com.qinyuan15.lottery.mvc.dao.PreUserDao;
import com.qinyuan15.lottery.mvc.dao.UserDao;
import com.qinyuan15.lottery.mvc.mail.RegisterMailSender;
import org.apache.commons.lang3.RandomStringUtils;
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
            setAttribute("preUser", new PreUserDao().getInstanceBySerialKey(serial));
        }

        setTitle("完善个人信息");
        addCss("personal-center-frame");
        addCss("personal-center");
        addCssAndJs("register");
        return "register";
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
                // create a new serial key
                do {
                    serialKey = RandomStringUtils.randomAlphanumeric(100);
                } while (dao.hasSerialKey(serialKey));

                // add pre user instance
                LivenessAdder livenessAdder = new LivenessAdder(session);
                dao.add(email, livenessAdder.getSpreadUserId(), livenessAdder.getSpreadWay(), livenessAdder.getActivityId(), serialKey);
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
    public String validateUsername(@RequestParam(value = "username", required = true) String username) {
        Pair<Boolean, String> validation = new NewUserValidator().validateUsername(username);
        if (validation.getLeft()) {
            return success();
        } else {
            String reason = validation.getRight();
            if (reason.equals(NewUserValidator.REGISTERED)) {
                return reason + TO_LOGIN_HTML;
            }
            return fail(reason);
        }
    }
}
