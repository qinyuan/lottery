package com.qinyuan15.lottery.mvc.controller;

import com.qinyuan.lib.contact.mail.MailSerialKey;
import com.qinyuan.lib.database.hibernate.HibernateUtils;
import com.qinyuan.lib.mvc.controller.ImageController;
import com.qinyuan15.lottery.mvc.dao.ResetEmailRequestDao;
import com.qinyuan15.lottery.mvc.dao.User;
import com.qinyuan15.lottery.mvc.dao.UserDao;
import com.qinyuan15.lottery.mvc.mail.ResetEmailMailSender;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class ResetEmailController extends ImageController {

    @RequestMapping("/reset-email")
    public String index(@RequestParam(value = "serial", required = true) String serial) {
        if (StringUtils.hasText(serial)) {
            String newEmail = ResetEmailMailSender.parseNewEmail(serial);
            if (newEmail != null) {
                ResetEmailRequestDao requestDao = new ResetEmailRequestDao();
                MailSerialKey mailSerialKey = requestDao.getInstanceBySerialKey(serial);
                if (mailSerialKey != null) {
                    requestDao.response(mailSerialKey.getId());

                    UserDao userDao = new UserDao();
                    userDao.activate(mailSerialKey.getUserId());
                    User user = userDao.getInstance(mailSerialKey.getUserId());
                    user.setEmail(newEmail);
                    HibernateUtils.update(user);
                    setAttribute("newEmail", newEmail);
                }
            }
        }

        IndexHeaderUtils.setHeaderParameters(this);
        setTitle("邮箱修改成功");
        addCss("activate-account");
        return "reset-email";
    }
}
