package com.qinyuan15.lottery.mvc.controller;

import com.google.common.collect.Lists;
import com.qinyuan.lib.contact.mail.MailAddressValidator;
import com.qinyuan.lib.database.hibernate.HibernateUtils;
import com.qinyuan.lib.lang.IntegerUtils;
import com.qinyuan.lib.mvc.controller.CDNSource;
import com.qinyuan.lib.mvc.controller.ImageController;
import com.qinyuan.lib.mvc.controller.PaginationAttributeAdder;
import com.qinyuan.lib.mvc.controller.PaginationItemFactory;
import com.qinyuan.lib.mvc.security.LoginRecord;
import com.qinyuan.lib.mvc.security.LoginRecordDao;
import com.qinyuan.lib.mvc.security.SecurityUtils;
import com.qinyuan15.lottery.mvc.AppConfig;
import com.qinyuan15.lottery.mvc.account.DatabaseTelValidator;
import com.qinyuan15.lottery.mvc.account.NewUserValidator;
import com.qinyuan15.lottery.mvc.activity.LotteryShareUrlBuilder;
import com.qinyuan15.lottery.mvc.dao.*;
import com.qinyuan15.lottery.mvc.mail.ResetEmailMailSender;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.tuple.Pair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.List;

@Controller
public class SettingController extends ImageController {
    private final static Logger LOGGER = LoggerFactory.getLogger(SettingController.class);
    private final static List<String> titles = Lists.newArrayList("个人资料", "通知", "安全", "绑定手机", "更改邮箱", "增加爱心");
    private final static int LOGIN_RECORD_SIZE = 20;

    @RequestMapping("/setting")
    public String index(@RequestParam(value = "index", required = false) Integer index,
                        @RequestParam(value = "commodityId", required = false) Integer commodityId) {
        IndexHeaderUtils.setHeaderParameters(this);

        if (!IntegerUtils.isPositive(index)) {
            index = 0;
        }
        setTitle("设置-" + titles.get(index));

        setAttribute("pageIndex", index);
        setAttribute("titles", titles);
        User user = (User) securitySearcher.getUser();
        if (index <= 0) {
            List<LoginRecord> loginRecords = LoginRecordDao.factory().setAscOrder(false).setLimitSize(1).getInstances();
            if (loginRecords.size() > 0) {
                setAttribute("location", loginRecords.get(0).getLocation());
            } else {
                setAttribute("location", "从未登录");
            }
            setAttribute("user", user);
            setAttribute("liveness", new LotteryLivenessDao().getLiveness(user.getId()));
        } else if (index == 1) {
            new PaginationAttributeAdder(new SystemInfoFactory(user.getId()), request).setRowItemsName("systemInfoItems").add();
        } else if (index == 2) {
            List<LoginRecord> loginRecords = LoginRecordDao.factory().setUserId(user.getId())
                    .setLimitSize(LOGIN_RECORD_SIZE).getInstances();
            for (LoginRecord loginRecord : loginRecords) {
                loginRecord.setIp(loginRecord.getIp().replaceAll("\\d+\\.\\d+$", "*.*"));
                if (isMobilePlatform(loginRecord.getPlatform())) {
                    loginRecord.setLocation("移动端登录");
                }
                loginRecord.setPlatform(adaptPlatform(loginRecord.getPlatform()));
            }
            setAttribute("loginRecords", loginRecords);
        } else if (index == 3 || index == 4) {
            setAttribute("user", user);
        } else if (index == 5) {
            setAttribute("liveness", new LotteryLivenessDao().getLiveness(user.getId()));
            // share urls
            new UserDao().updateSerialKeyIfNecessary(user);

            Commodity commodity = null;
            if (IntegerUtils.isPositive(commodityId)) {
                commodity = new CommodityDao().getInstance(commodityId);
            }
            if (commodity == null) {
                commodity = new CommodityDao().getFirstVisibleInstance();
            }
            new CommodityUrlAdapter(this).adapt(commodity);

            LotteryShareUrlBuilder lotteryShareUrlBuilder = new LotteryShareUrlBuilder(
                    user.getSerialKey(), AppConfig.getAppHost(), commodity);
            setAttribute("sinaWeiboShareUrl", lotteryShareUrlBuilder.getSinaShareUrl());
            setAttribute("qqShareUrl", lotteryShareUrlBuilder.getQQShareUrl());
            setAttribute("qzoneShareUrl", lotteryShareUrlBuilder.getQzoneShareUrl());
        }

        addCssAndJs("setting");
        return "setting";
    }

    @RequestMapping("/setting-change-username.json")
    @ResponseBody
    public String changeUsername(@RequestParam(value = "username", required = true) String username) {
        if (StringUtils.isBlank(username)) {
            return fail("昵称不能为空");
        } else if (username.contains(" ")) {
            return fail("昵称不能包含空格");
        }

        try {
            Pair<Boolean, String> validation = new NewUserValidator().validateUsername(username);
            if (!validation.getLeft()) {
                return fail(validation.getRight());
            }

            User user = (User) securitySearcher.getUser();
            user.setUsername(username);
            HibernateUtils.update(user);
            SecurityUtils.logout();
            login(user.getUsername(), user.getPassword());
            return success();
        } catch (Exception e) {
            LOGGER.error("fail to change username, username: {}, info: {}", username, e);
            return failByDatabaseError();
        }
    }

    @RequestMapping("/setting-change-password.json")
    @ResponseBody
    public String changePassword(@RequestParam(value = "oldPassword", required = true) String oldPassword,
                                 @RequestParam(value = "newPassword", required = true) String newPassword) {

        if (StringUtils.isBlank(oldPassword)) {
            return fail("原密码不能为空！");
        }

        if (StringUtils.isBlank(newPassword)) {
            return fail("新密码不能为空！");
        }

        try {
            User user = (User) securitySearcher.getUser();
            if (!oldPassword.equals(user.getPassword())) {
                return fail("原密码错误！");
            }
            user.setPassword(newPassword);
            HibernateUtils.update(user);
            return success();
        } catch (Exception e) {
            LOGGER.error("Fail to update password, info: {}", e);
            return failByDatabaseError();
        }
    }

    @RequestMapping("/setting-validate-tel.json")
    @ResponseBody
    public String validateTel(@RequestParam(value = "tel", required = true) String tel) {
        if (StringUtils.isBlank(tel)) {
            return fail("手机号码不能为空");
        }

        try {
            Pair<Boolean, String> validation = new DatabaseTelValidator().validate(tel);
            if (!validation.getLeft()) {
                return fail(validation.getRight());
            }
        } catch (Exception e) {
            LOGGER.error("fail to validate tel, tel: {}, info: {}", tel, e);
        }
        return success();
    }

    @RequestMapping("/setting-change-tel.json")
    @ResponseBody
    public String changeTel(@RequestParam(value = "password", required = true) String password,
                            @RequestParam(value = "tel", required = true) String tel) {
        if (StringUtils.isBlank(password)) {
            return fail("密码不能为空");
        } else if (StringUtils.isBlank(tel)) {
            return fail("手机号不能为空");
        }

        try {
            Pair<Boolean, String> validation = new DatabaseTelValidator().validate(tel);
            if (!validation.getLeft()) {
                return fail(validation.getRight());
            }

            User user = (User) securitySearcher.getUser();
            if (!password.equals(user.getPassword())) {
                return fail("密码输入错误");
            }

            user.setTel(tel);
            HibernateUtils.update(user);
            return success();
        } catch (Exception e) {
            LOGGER.error("Fail to update tel, tel: {}, info {}", tel, e);
            return failByDatabaseError();
        }
    }

    @RequestMapping("/setting-validate-email.json")
    @ResponseBody
    public String validateEmail(@RequestParam(value = "email", required = true) String email) {
        if (!new MailAddressValidator().validate(email)) {
            return fail("邮箱格式错误");
        }

        try {
            UserDao dao = new UserDao();
            if (dao.hasEmail(email)) {
                return fail("邮箱已被注册");
            }
        } catch (Exception e) {
            LOGGER.error("fail to validate email, email: {}, info: {}", email, e);
        }
        return success();
    }

    @RequestMapping("/setting-update-email.json")
    @ResponseBody
    public String updateEmail(@RequestParam(value = "email", required = true) String email,
                              @RequestParam(value = "password", required = true) String password) {
        if (StringUtils.isBlank(email)) {
            return fail("邮箱不能为空");
        } else if (!new MailAddressValidator().validate(email)) {
            return fail("邮箱格式错误");
        } else if (StringUtils.isBlank(password)) {
            return fail("密码不能为空");
        }

        try {
            UserDao dao = new UserDao();
            User user = (User) securitySearcher.getUser();
            if (email.equals(user.getEmail())) {
                return fail("邮箱未修改");
            } else if (!password.equals(user.getPassword())) {
                return fail("密码输入错误");
            } else if (dao.hasEmail(email)) {
                return fail("邮箱已被注册");
            } else {
                new ResetEmailMailSender(email).send(user.getId());
                return success();
            }
        } catch (Exception e) {
            LOGGER.error("fail to update email, email: {}, info: {}", email, e);
            return failByDatabaseError();
        }
    }


    private boolean isMobilePlatform(String platform) {
        if (StringUtils.isBlank(platform)) {
            return false;
        }

        platform = platform.toUpperCase();
        return platform.equals("ANDROID") || platform.equals("IOS");
    }

    private String adaptPlatform(String platform) {
        if (StringUtils.isBlank(platform)) {
            return platform;
        }
        switch (platform) {
            case "ANDROID":
                return "Android";
            case "LINUX":
                return "Linux";
            case "WINDOWS":
                return "Windows";
            case "OTHER":
                return "其他";
            default:
                return platform;
        }
    }

    private static class SystemInfoFactory implements PaginationItemFactory<InfoItem> {
        final Integer userId;

        SystemInfoFactory(Integer userId) {
            this.userId = userId;
        }

        SystemInfoSendRecordDao.Factory getFactory() {
            return SystemInfoSendRecordDao.factory().setUserId(userId);
        }

        @Override
        public int getCount() {
            return getFactory().getCount();
        }

        @Override
        public List<InfoItem> getInstances(int firstResult, int maxResults) {
            List<SystemInfoSendRecord> items = getFactory().getInstances(firstResult, maxResults);

            String username = SecurityUtils.getUsername();
            List<InfoItem> infoItems = new ArrayList<>();
            List<Integer> unreadIds = new ArrayList<>();

            for (SystemInfoSendRecord record : items) {
                InfoItem item = new InfoItem();
                item.id = record.getId();
                item.content = record.getContent();
                if (item.content != null) {
                    item.content = item.content.replace("{{user}}", username);
                }
                item.buildTime = record.getBuildTime();
                item.unread = record.getUnread();
                infoItems.add(item);
                if (record.getUnread()) {
                    unreadIds.add(record.getId());
                }
            }
            if (unreadIds.size() > 0) {
                new SystemInfoSendRecordDao().read(userId, unreadIds);
            }
            return infoItems;
        }
    }

    public static class InfoItem {
        public int id;
        public String content;
        public String buildTime;
        public boolean unread;

        public int getId() {
            return id;
        }

        public String getContent() {
            return content;
        }

        public String getBuildTime() {
            return buildTime;
        }

        public boolean isUnread() {
            return unread;
        }
    }
}
