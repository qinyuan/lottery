package com.qinyuan15.lottery.mvc.controller;

import com.qinyuan15.lottery.mvc.dao.ResetPasswordRequestDao;
import com.qinyuan15.lottery.mvc.dao.UserDao;
import com.qinyuan15.utils.mail.MailSerialKey;
import com.qinyuan15.utils.mvc.controller.ImageController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class ResetPasswordController extends ImageController {
    private final static Logger LOGGER = LoggerFactory.getLogger(ResetPasswordController.class);

    @RequestMapping("/reset-password")
    public String index(@RequestParam(value = "serial", required = true) String serial) {
        if (StringUtils.hasText(serial) && new ResetPasswordRequestDao().hasSerialKey(serial)) {
            setAttribute("serialKey", serial);
        }

        setAttribute("noFooter", true);
        IndexHeaderUtils.setHeaderParameters(this);
        setTitle("输入新密码");
        addCssAndJs("reset-password");
        return "reset-password";
    }

    @RequestMapping("/update-password-submit.json")
    @ResponseBody
    public String json(@RequestParam(value = "serialKey", required = true) String serialKey,
                       @RequestParam(value = "password", required = true) String password) {
        if (!StringUtils.hasText(serialKey) || !StringUtils.hasText(password)) {
            return failByInvalidParam();
        }

        MailSerialKey mailSerialKey = new ResetPasswordRequestDao().getInstanceBySerialKey(serialKey);
        if (mailSerialKey == null) {
            return fail("链接已过期！");
        } else {
            try {
                new UserDao().updatePassword(mailSerialKey.getUserId(), password);
                return success();
            } catch (Exception e) {
                LOGGER.error("Fail to update password, serialKey: {}, password: {}, info: {}",
                        serialKey, password, e);
                return failByDatabaseError();
            }
        }
    }
}
