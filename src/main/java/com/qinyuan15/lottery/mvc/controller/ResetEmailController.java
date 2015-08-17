package com.qinyuan15.lottery.mvc.controller;

import com.qinyuan15.lottery.mvc.dao.ResetEmailRequestDao;
import com.qinyuan15.lottery.mvc.dao.User;
import com.qinyuan15.lottery.mvc.dao.UserDao;
import com.qinyuan15.lottery.mvc.mail.ResetEmailMailSender;
import com.qinyuan15.utils.database.hibernate.HibernateUtils;
import com.qinyuan15.utils.mail.MailSerialKey;
import com.qinyuan15.utils.mvc.UrlUtils;
import com.qinyuan15.utils.mvc.controller.ImageController;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class ResetEmailController extends ImageController {

    @RequestMapping("/reset-email")
    public String index(@RequestParam(value = "serial", required = true) String serial) {
        if (StringUtils.hasText(serial)) {
            String newEmail = getNewEmail(serial);
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

    private String getNewEmail(String serial) {
        serial = UrlUtils.decode(serial);
        int endIndex = serial.indexOf(ResetEmailMailSender.PREFIX_END);
        return endIndex > 0 ? serial.substring(0, endIndex) : null;
    }
}
