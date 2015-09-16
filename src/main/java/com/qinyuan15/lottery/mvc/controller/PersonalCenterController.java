package com.qinyuan15.lottery.mvc.controller;

import com.qinyuan.lib.lang.IntegerUtils;
import com.qinyuan15.lottery.mvc.dao.User;
import com.qinyuan15.lottery.mvc.dao.UserDao;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class PersonalCenterController extends AbstractUserController {

    @RequestMapping("/personal-center")
    public String index() {
        return super.index(getUser(), "个人中心");
    }

    @RequestMapping("/personal-center-update-additional-info")
    public String updateAdditionalInfo(@RequestParam(value = "gender", required = true) String gender,
                                       @RequestParam(value = "birthdayYear", required = false) Integer birthdayYear,
                                       @RequestParam(value = "birthdayMonth", required = false) Integer birthdayMonth,
                                       @RequestParam(value = "birthdayDay", required = false) Integer birthdayDay,
                                       @RequestParam(value = "constellation", required = true) String constellation,
                                       @RequestParam(value = "hometown", required = true) String hometown,
                                       @RequestParam(value = "residence", required = true) String residence,
                                       @RequestParam(value = "lunarBirthday", required = false) String lunarBirthdayString) {
        final String index = "personal-center";
        Integer userId = securitySearcher.getUserId();
        if (!IntegerUtils.isPositive(userId)) {
            return redirect(index, "用户未登录");
        }

        return super.updateAdditionalInfo(userId, index, gender, birthdayYear, birthdayMonth, birthdayDay,
                constellation, hometown, residence, lunarBirthdayString);
    }

    @RequestMapping("/personal-center-update-real-name.json")
    @ResponseBody
    public String updateRealName(@RequestParam(value = "realName") String realName) {
        return super.updateRealName(getUser(), realName);
    }

    @RequestMapping("/personal-center-update-password.json")
    @ResponseBody
    public String updatePassword(@RequestParam(value = "oldPassword", required = true) String oldPassword,
                                 @RequestParam(value = "newPassword", required = true) String newPassword) {
        return super.updatePassword(getUser(), oldPassword, newPassword);
    }

    @RequestMapping("/personal-center-update-email.json")
    @ResponseBody
    public String updateEmail(@RequestParam(value = "email", required = true) String email) {
        return super.updateEmail(securitySearcher.getUserId(), email);
    }

    private User getUser() {
        return new UserDao().getInstance(securitySearcher.getUserId());
    }
}
