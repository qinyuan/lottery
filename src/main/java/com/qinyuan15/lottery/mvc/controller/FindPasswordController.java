package com.qinyuan15.lottery.mvc.controller;

import com.qinyuan15.lottery.mvc.mail.ResetPasswordMailSender;
import com.qinyuan15.lottery.mvc.dao.User;
import com.qinyuan15.lottery.mvc.dao.UserDao;
import com.qinyuan15.utils.mvc.controller.ImageController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.Map;

@Controller
public class FindPasswordController extends ImageController {
    private final static Logger LOGGER = LoggerFactory.getLogger(FindPasswordController.class);

    @RequestMapping("/find-password")
    public String index() {
        setTitle("找回密码");
        IndexHeaderUtils.setHeaderParameters(this);
        addCssAndJs("find-password");
        setAttribute("noFooter", true);
        return "find-password";
    }

    @RequestMapping("/send-reset-password-mail.json")
    @ResponseBody
    public String sendResetPasswordMail(@RequestParam(value = "resetPasswordUsername", required = true) String username,
                                        @RequestParam(value = "identityCode", required = true) String identityCode) {
        if (!validateIdentityCode(identityCode)) {
            return fail("验证码输入错误！");
        }

        if (!StringUtils.hasText(username)) {
            return fail("帐户名不能为空！");
        }

        User user = new UserDao().getInstanceByName(username);
        if (user == null) {
            return fail("没有此用户，请检查帐户名是否正确！");
        }

        try {
            new ResetPasswordMailSender().send(user.getId());
            Map<String, Object> result = new HashMap<>();
            result.put("success", true);
            result.put("mail", user.getEmail());
            return toJson(result);
        } catch (Exception e) {
            LOGGER.error("Fail to reset password！");
            return fail("重置密码邮件发送失败！");
        }
    }

    @RequestMapping("/resend-reset-password-mail.json")
    @ResponseBody
    public String resendResetPasswordMail(@RequestParam(value = "email", required = true) String email) {
        if (!StringUtils.hasText(email)) {
            return fail("邮箱地址不能为空！");
        }

        User user = new UserDao().getInstanceByEmail(email);
        if (user == null) {
            return fail("用户不存在！");
        }

        try {
            new ResetPasswordMailSender().send(user.getId());
            return success();
        } catch (Exception e) {
            LOGGER.error("Fail to resend email to {}, info: {}", email, e);
            return fail("邮件发送失败！");
        }
    }
}
