package com.qinyuan15.lottery.mvc.controller;

import com.qinyuan.lib.database.hibernate.HibernateUtils;
import com.qinyuan.lib.mvc.controller.BaseController;
import com.qinyuan15.lottery.mvc.dao.User;
import com.qinyuan15.lottery.mvc.dao.UserDao;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class UserController extends BaseController {
    private final static Logger LOGGER = LoggerFactory.getLogger(UserController.class);

    @RequestMapping("/update-receive-mail.json")
    @ResponseBody
    public String updateReceiveMail(@RequestParam(value = "receiveMail", required = true) Boolean receiveMail) {
        User user = new UserDao().getInstance(securitySearcher.getUserId());
        if (user == null) {
            return fail("请重新登录");
        }

        user.setReceiveMail(receiveMail);
        try {
            HibernateUtils.update(user);
            return success();
        } catch (Exception e) {
            LOGGER.error("fail to update receive mail, info: {}", e);
            return failByDatabaseError();
        }
    }
}
