package com.qinyuan15.lottery.mvc.controller;

import com.qinyuan15.lottery.mvc.dao.ActivateRequest;
import com.qinyuan15.lottery.mvc.dao.ActivateRequestDao;
import com.qinyuan15.lottery.mvc.dao.User;
import com.qinyuan15.lottery.mvc.dao.UserDao;
import com.qinyuan15.utils.mvc.controller.ImageController;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class ActivateAccountController extends ImageController {

    @RequestMapping("/activate-account")
    public String index(@RequestParam(value = "serial", required = true) String serial) {
        if (StringUtils.hasText(serial)) {
            ActivateRequestDao requestDao = new ActivateRequestDao();
            ActivateRequest activateRequest = requestDao.getInstanceBySerialKey(serial);
            if (activateRequest != null) {
                requestDao.response(activateRequest.getId());

                UserDao userDao = new UserDao();
                userDao.activate(activateRequest.getUserId());
                User user = userDao.getInstance(activateRequest.getUserId());
                setAttribute("email", user.getEmail());
            }
        }

        IndexHeaderUtils.setHeaderParameters(this);
        setTitle("邮箱验证成功");
        addCss("activate-account");
        return "activate-account";
    }
}
