package com.qinyuan15.lottery.mvc.controller;

import com.qinyuan.lib.contact.mail.MailAddressValidator;
import com.qinyuan.lib.contact.tel.TelValidator;
import com.qinyuan.lib.database.hibernate.HibernateUtils;
import com.qinyuan.lib.lang.IntegerUtils;
import com.qinyuan.lib.mvc.controller.ImageController;
import com.qinyuan.lib.mvc.security.LoginRecord;
import com.qinyuan.lib.mvc.security.LoginRecordDao;
import com.qinyuan.lib.mvc.security.SecuritySearcher;
import com.qinyuan15.lottery.mvc.dao.LotteryLivenessDao;
import com.qinyuan15.lottery.mvc.dao.User;
import com.qinyuan15.lottery.mvc.dao.UserDao;
import com.qinyuan15.lottery.mvc.mail.ResetEmailMailSender;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
public class PersonalCenterController extends ImageController {
    private final static Logger LOGGER = LoggerFactory.getLogger(PersonalCenterController.class);
    private final static int LOGIN_RECORD_SIZE = 20;

    @Autowired
    private SecuritySearcher securitySearcher;

    @RequestMapping("/personal-center")
    public String index() {
        IndexHeaderUtils.setHeaderParameters(this);

        User user = getUser();
        setAttribute("user", user);
        setAttribute("liveness", new LotteryLivenessDao().getLiveness(user.getId()));

        List<LoginRecord> loginRecords = LoginRecordDao.factory().setUserId(user.getId())
                .setLimitSize(LOGIN_RECORD_SIZE).getInstances();
        for (LoginRecord loginRecord : loginRecords) {
            loginRecord.setIp(loginRecord.getIp().replaceAll("\\d+\\.\\d+$", "*.*"));
        }
        setAttribute("loginRecords", loginRecords);

        setTitle("个人中心");
        addJs("lib/handlebars.min-v1.3.0");
        addCss("personal-center-frame");
        addCssAndJs("personal-center");
        return "personal-center";
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


        if (!StringUtils.hasText(gender)) {
            gender = null;
        }
        if (!StringUtils.hasText(constellation)) {
            constellation = null;
        }
        if (!StringUtils.hasText(hometown)) {
            hometown = null;
        }
        if (!StringUtils.hasText(residence)) {
            residence = null;
        }

        String birthday = null;
        if (IntegerUtils.isPositive(birthdayYear) && IntegerUtils.isPositive(birthdayMonth)
                && IntegerUtils.isPositive(birthdayDay)) {
            birthday = birthdayYear + "-" + birthdayMonth + "-" + birthdayDay;
        }

        Boolean lunarBirthday = StringUtils.hasText(lunarBirthdayString);

        try {
            new UserDao().updateAdditionalInfo(userId, gender, birthday, constellation, hometown, residence,
                    lunarBirthday);
            return redirect(index);
        } catch (Exception e) {
            LOGGER.error("Fail to update additional information, info: {}", e);
            return redirect(index, "数据库操作失败");
        }
    }

    @RequestMapping("/personal-center-update-real-name.json")
    @ResponseBody
    public String updateRealName(@RequestParam(value = "realName") String realName) {
        User user = getUser();
        if (user.getRealName() != null && user.getRealName().equals(realName)) {
            return fail("新姓名与原姓名相同，未作修改");
        }

        try {
            user.setRealName(realName);
            HibernateUtils.update(user);
            return success();
        } catch (Exception e) {
            LOGGER.error("Fail to update real name of user, userId: {}, realName: {}, info: {}",
                    user.getId(), realName, e);
            return failByDatabaseError();
        }
    }

    @RequestMapping("/personal-center-update-password.json")
    @ResponseBody
    public String updatePassword(@RequestParam(value = "oldPassword", required = true) String oldPassword,
                                 @RequestParam(value = "newPassword", required = true) String newPassword) {

        User user = getUser();
        if (user == null) {
            return failByInvalidParam();
        }

        if (!StringUtils.hasText(oldPassword)) {
            return fail("原密码不能为空！");
        }

        if (!StringUtils.hasText(newPassword)) {
            return fail("新密码不能为空！");
        }

        if (!oldPassword.equals(user.getPassword())) {
            return fail("原密码错误！");
        }

        try {
            user.setPassword(newPassword);
            HibernateUtils.update(user);
            return success();
        } catch (Exception e) {
            LOGGER.error("Fail to update password, info: {}", e);
            return failByDatabaseError();
        }
    }

    @RequestMapping("/personal-center-update-tel.json")
    @ResponseBody
    public String updateTel(@RequestParam(value = "tel", required = true) String tel) {
        User user = getUser();
        if (user == null) {
            return failByInvalidParam();
        }

        if (!StringUtils.hasText(tel)) {
            return fail("电话号码不能为空！");
        }

        if (!new TelValidator().validate(tel)) {
            return fail("电话号码必须为11位数字！");
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


    @RequestMapping("/personal-center-update-email.json")
    @ResponseBody
    public String updateEmail(@RequestParam(value = "email", required = true) String email) {
        if (!new MailAddressValidator().validate(email)) {
            return fail("请输入有效的邮箱格式");
        }

        UserDao dao = new UserDao();
        if (dao.hasEmail(email)) {
            return fail("邮箱已被注册");
        }

        try {
            new ResetEmailMailSender(email).send(securitySearcher.getUserId());
            return success();
        } catch (Exception e) {
            LOGGER.error("Fail to update email of user, user id: {}, email: {}, info: {}",
                    getUser().getId(), email, e);
            return fail("邮件发送失败！");
        }
    }

    private User getUser() {
        return new UserDao().getInstance(securitySearcher.getUserId());
    }
}
