package com.qinyuan15.lottery.mvc.controller;

import com.qinyuan.lib.contact.mail.MailAddressValidator;
import com.qinyuan.lib.database.hibernate.HibernateUtils;
import com.qinyuan.lib.lang.IntegerUtils;
import com.qinyuan.lib.mvc.controller.ImageController;
import com.qinyuan.lib.mvc.security.LoginRecord;
import com.qinyuan.lib.mvc.security.LoginRecordDao;
import com.qinyuan15.lottery.mvc.AppConfig;
import com.qinyuan15.lottery.mvc.dao.LotteryLivenessDao;
import com.qinyuan15.lottery.mvc.dao.User;
import com.qinyuan15.lottery.mvc.dao.UserDao;
import com.qinyuan15.lottery.mvc.mail.ResetEmailMailSender;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

import java.util.List;

public class AbstractUserController extends ImageController {
    private final static Logger LOGGER = LoggerFactory.getLogger(AbstractUserController.class);
    private final static int LOGIN_RECORD_SIZE = 20;

    protected String index(User user, String title) {
        IndexHeaderUtils.setHeaderParameters(this);

        setAttribute("user", user);
        setAttribute("liveness", new LotteryLivenessDao().getLiveness(user.getId()));

        List<LoginRecord> loginRecords = LoginRecordDao.factory().setUserId(user.getId())
                .setLimitSize(LOGIN_RECORD_SIZE).getInstances();
        for (LoginRecord loginRecord : loginRecords) {
            loginRecord.setIp(loginRecord.getIp().replaceAll("\\d+\\.\\d+$", "*.*"));
        }
        setAttribute("loginRecords", loginRecords);

        addJavaScriptData("telValidateDescriptionPage", AppConfig.getTelValidateDescriptionPage());

        setTitle(title);
        addJs("lib/handlebars.min-v1.3.0");
        addCss("personal-center-frame");
        addCssAndJs("personal-center");
        return "personal-center";
    }

    protected String updateAdditionalInfo(int userId, String redirectIndex, String gender, Integer birthdayYear,
                                          Integer birthdayMonth, Integer birthdayDay, String constellation, String hometown,
                                          String residence, String lunarBirthdayString) {
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
            return redirect(redirectIndex);
        } catch (Exception e) {
            LOGGER.error("Fail to update additional information, info: {}", e);
            return redirect(redirectIndex, "数据库操作失败");
        }
    }

    protected String updateRealName(User user, String realName) {
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

    protected String updatePassword(User user, String oldPassword, String newPassword) {
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

    protected String updateEmail(int userId, String email) {
        if (!new MailAddressValidator().validate(email)) {
            return fail("请输入有效的邮箱格式");
        }

        UserDao dao = new UserDao();
        if (dao.hasEmail(email)) {
            return fail("邮箱已被注册");
        }

        try {
            new ResetEmailMailSender(email).send(userId);
            return success();
        } catch (Exception e) {
            LOGGER.error("Fail to update email of user, user id: {}, email: {}, info: {}",
                    userId, email, e);
            return fail("邮件发送失败！");
        }
    }
}
