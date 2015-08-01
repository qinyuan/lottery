package com.qinyuan15.lottery.mvc.controller;

import com.qinyuan15.lottery.mvc.dao.LotteryLivenessDao;
import com.qinyuan15.lottery.mvc.dao.User;
import com.qinyuan15.lottery.mvc.dao.UserDao;
import com.qinyuan15.lottery.mvc.mail.ResetEmailMailSender;
import com.qinyuan15.utils.hibernate.HibernateUtils;
import com.qinyuan15.utils.mail.MailAddressValidator;
import com.qinyuan15.utils.mvc.controller.ImageController;
import com.qinyuan15.utils.security.LoginRecord;
import com.qinyuan15.utils.security.LoginRecordDao;
import com.qinyuan15.utils.security.SecuritySearcher;
import com.qinyuan15.utils.tel.TelValidator;
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
