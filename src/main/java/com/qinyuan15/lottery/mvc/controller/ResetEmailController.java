package com.qinyuan15.lottery.mvc.controller;

import com.qinyuan.lib.contact.mail.MailSerialKey;
import com.qinyuan.lib.mvc.controller.ImageController;
import com.qinyuan15.lottery.mvc.dao.ResetEmailRequestDao;
import com.qinyuan15.lottery.mvc.dao.UserDao;
import com.qinyuan15.lottery.mvc.mail.ResetEmailMailSender;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class ResetEmailController extends ImageController {
    private final static Logger LOGGER = LoggerFactory.getLogger(ResetEmailController.class);

    @RequestMapping("/reset-email")
    public String index(@RequestParam(value = "serial", required = true) String serial) {
        String title = "邮箱修改失败";
        if (StringUtils.hasText(serial)) {
            String newEmail = ResetEmailMailSender.parseNewEmail(serial);
            if (newEmail != null) {
                try {
                    ResetEmailRequestDao requestDao = new ResetEmailRequestDao();
                    MailSerialKey mailSerialKey = requestDao.getInstanceBySerialKey(serial);
                    if (mailSerialKey != null) {
                        requestDao.response(mailSerialKey.getId());

                        UserDao userDao = new UserDao();
                        userDao.activate(mailSerialKey.getUserId());
                        userDao.updateEmail(mailSerialKey.getUserId(), newEmail);
                        setAttribute("newEmail", newEmail);
                        title = "邮箱修改成功";
                    }
                } catch (Exception e) {
                    LOGGER.error("fail to change email, of serial: {}, info: {}", serial, e);
                }
            }
        }

        IndexHeaderUtils.setHeaderParameters(this);
        setTitle(title);
        addCss("activate-account");
        return "reset-email";
    }
}
