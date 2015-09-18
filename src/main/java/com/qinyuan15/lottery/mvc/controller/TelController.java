package com.qinyuan15.lottery.mvc.controller;

import com.qinyuan.lib.database.hibernate.HibernateUtils;
import com.qinyuan.lib.mvc.controller.BaseController;
import com.qinyuan15.lottery.mvc.account.DatabaseTelValidator;
import com.qinyuan15.lottery.mvc.dao.User;
import com.qinyuan15.lottery.mvc.dao.UserDao;
import org.apache.commons.lang3.tuple.Pair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class TelController extends BaseController {
    private final static Logger LOGGER = LoggerFactory.getLogger(TelController.class);

    @RequestMapping("/tel-validate.json")
    @ResponseBody
    public String validateTel(@RequestParam(value = "tel", required = true) String tel) {
        User user = new UserDao().getInstance(securitySearcher.getUserId());
        if (user == null) {
            return fail("请重新登录");
        }

        return validateTelWithoutLogin(tel);
    }

    @RequestMapping("/validate-tel-without-login.json")
    @ResponseBody
    public String validateTelWithoutLogin(@RequestParam(value = "tel", required = true) String tel) {
        Pair<Boolean, String> telValidation = new DatabaseTelValidator().validate(tel);
        if (telValidation.getLeft()) {
            return success();
        } else {
            return fail(telValidation.getRight());
        }
    }

    @RequestMapping("/update-tel.json")
    @ResponseBody
    public String updateTel(@RequestParam(value = "tel", required = true) String tel) {
        User user = new UserDao().getInstance(securitySearcher.getUserId());
        return updateTel(user, tel);
    }

    @RequestMapping("/admin-update-tel.json")
    @ResponseBody
    public String updateTel(@RequestParam(value = "id", required = true) Integer userId,
                            @RequestParam(value = "tel", required = true) String tel) {
        User user = new UserDao().getInstance(userId);
        return updateTel(user, tel);
    }

    public String updateTel(User user, String tel) {
        if (user == null) {
            return failByInvalidParam();
        }

        Pair<Boolean, String> telValidation = new DatabaseTelValidator().validate(tel);
        if (!telValidation.getLeft()) {
            return fail(telValidation.getRight());
        }

        try {
            user.setTel(tel);
            HibernateUtils.update(user);
            return success();
        } catch (Exception e) {
            LOGGER.error("Fail to update tel, tel: {}, info {}", tel, e);
            return failByDatabaseError();
        }
    }
}
