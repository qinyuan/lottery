package com.qinyuan15.lottery.mvc.controller;

import com.qinyuan.lib.contact.tel.TelValidator;
import com.qinyuan.lib.database.hibernate.HibernateUtils;
import com.qinyuan.lib.mvc.controller.BaseController;
import com.qinyuan15.lottery.mvc.dao.User;
import com.qinyuan15.lottery.mvc.dao.UserDao;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
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
            //return failByInvalidParam();
        }

        if (!StringUtils.hasText(tel)) {
            return fail("电话号码不能为空");
        }

        if (!new TelValidator().validate(tel)) {
            return fail("电话号码必须为11位数字");
        }

        try {
            if (new UserDao().hasTel(tel)) {
                return fail("号码已被使用");
            } else {
                return success();
            }
        } catch (Exception e) {
            LOGGER.error("Fail to validate tel, tel: {}, info: {}", tel, e);
            return failByDatabaseError();
        }
    }

    @RequestMapping("/update-tel.json")
    @ResponseBody
    public String updateTel(@RequestParam(value = "tel", required = true) String tel) {
        User user = new UserDao().getInstance(securitySearcher.getUserId());
        if (user == null) {
            return failByInvalidParam();
        }

        if (!StringUtils.hasText(tel)) {
            return fail("电话号码不能为空");
        }

        if (!new TelValidator().validate(tel)) {
            return fail("电话号码必须为11位数字");
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
