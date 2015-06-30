package com.qinyuan15.lottery.mvc.controller;

import com.qinyuan15.lottery.mvc.dao.UserDao;
import com.qinyuan15.utils.mail.MailAddressValidator;
import com.qinyuan15.utils.mvc.controller.BaseController;
import com.qinyuan15.utils.mvc.controller.IdentityCodeController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;

/**
 * Controller about register
 * Created by qinyuan on 15-6-29.
 */
@Controller
public class RegisterController extends BaseController {
    private final static Logger LOGGER = LoggerFactory.getLogger(RegisterController.class);

    @Autowired
    private HttpSession session;

    private UserDao userDao = new UserDao();

    @RequestMapping(value = "register-submit.json", method = RequestMethod.POST)
    @ResponseBody
    public String submit(@RequestParam(value = "email", required = true) String email,
                         @RequestParam(value = "username", required = true) String username,
                         @RequestParam(value = "password", required = true) String password,
                         @RequestParam(value = "password2", required = true) String password2,
                         @RequestParam(value = "identityCode", required = true) String identityCode) {
        if (!validate(identityCode)) {
            return fail("验证码输入错误！");
        }

        if (!new MailAddressValidator().validate(email)) {
            return fail("邮箱格式错误！");
        }

        if (userDao.hasEmail(email)) {
            return fail("该邮箱已经被注册！");
        }

        if (!StringUtils.hasText(username)) {
            return fail("用户名不能为空！");
        }

        if (userDao.hasUsername(username)) {
            return fail("该用户名已经存在！");
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
            userDao.addNormal(username, password, email, "");
            return success();
        } catch (Exception e) {
            LOGGER.error("fail to add user, username: {}, password: {}, email {}, info {}",
                    username, password, email, e);
            return fail("数据库操作失败！");
        }
    }

    private boolean validate(String identityCode) {
        if (!StringUtils.hasText(identityCode)) {
            return false;
        }

        String identityCodeInSession = (String) session.getAttribute(IdentityCodeController.IDENTITY_CODE_SESSION_KEY);

        return StringUtils.hasText(identityCodeInSession) &&
                identityCode.toLowerCase().equals(identityCodeInSession.toLowerCase());
    }
}
