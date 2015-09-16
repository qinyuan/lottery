package com.qinyuan15.lottery.mvc.controller;

import com.qinyuan.lib.contact.mail.MailAddressValidator;
import com.qinyuan.lib.mvc.controller.BaseController;
import com.qinyuan15.lottery.mvc.account.NewUserValidator;
import com.qinyuan15.lottery.mvc.dao.User;
import com.qinyuan15.lottery.mvc.dao.UserDao;
import com.qinyuan15.lottery.mvc.mail.ActivateMailSender;
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
public class RegisterController extends BaseController {
    private final static Logger LOGGER = LoggerFactory.getLogger(RegisterController.class);

    private UserDao userDao = new UserDao();

    @RequestMapping(value = "register-submit.json", method = RequestMethod.POST)
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
    }

    @RequestMapping(value = "resend-activate-email.json", method = RequestMethod.GET)
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
