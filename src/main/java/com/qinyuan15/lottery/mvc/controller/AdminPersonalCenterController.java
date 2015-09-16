package com.qinyuan15.lottery.mvc.controller;

import com.qinyuan15.lottery.mvc.dao.User;
import com.qinyuan15.lottery.mvc.dao.UserDao;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class AdminPersonalCenterController extends AbstractUserController {

    @RequestMapping("/admin-personal-center")
    public String index(@RequestParam(value = "id", required = true) Integer userId) {
        return super.index(getUser(userId), "修改用户信息", "admin-personal-center");
    }

    @RequestMapping("/admin-personal-center-update-additional-info")
    public String updateAdditionalInfo(@RequestParam(value = "id", required = true) Integer userId,
                                       @RequestParam(value = "gender", required = true) String gender,
                                       @RequestParam(value = "birthdayYear", required = false) Integer birthdayYear,
                                       @RequestParam(value = "birthdayMonth", required = false) Integer birthdayMonth,
                                       @RequestParam(value = "birthdayDay", required = false) Integer birthdayDay,
                                       @RequestParam(value = "constellation", required = true) String constellation,
                                       @RequestParam(value = "hometown", required = true) String hometown,
                                       @RequestParam(value = "residence", required = true) String residence,
                                       @RequestParam(value = "lunarBirthday", required = false) String lunarBirthdayString) {
        final String index = "admin-personal-center";
        return super.updateAdditionalInfo(userId, index, gender, birthdayYear, birthdayMonth, birthdayDay,
                constellation, hometown, residence, lunarBirthdayString);
    }

    @RequestMapping("/admin-personal-center-update-real-name.json")
    @ResponseBody
    public String updateRealName(@RequestParam(value = "id", required = true) Integer userId,
                                 @RequestParam(value = "realName") String realName) {
        return super.updateRealName(getUser(userId), realName);
    }

    @RequestMapping("/admin-personal-center-update-password.json")
    @ResponseBody
    public String updatePassword(@RequestParam(value = "id", required = true) Integer userId,
                                 @RequestParam(value = "oldPassword", required = true) String oldPassword,
                                 @RequestParam(value = "newPassword", required = true) String newPassword) {
        return super.updatePassword(getUser(userId), oldPassword, newPassword);
    }

    @RequestMapping("/admin-personal-center-update-email.json")
    @ResponseBody
    public String updateEmail(@RequestParam(value = "id", required = true) Integer userId,
                              @RequestParam(value = "email", required = true) String email) {
        return super.updateEmail(userId, email);
    }

    private User getUser(Integer userId) {
        return new UserDao().getInstance(userId);
    }

    /*
    @RequestMapping("/hello-world.json")
    @ResponseBody
    public String json(){
        return success();
    }
    */
}
