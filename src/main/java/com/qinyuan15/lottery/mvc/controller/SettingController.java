package com.qinyuan15.lottery.mvc.controller;

import com.google.common.collect.Lists;
import com.qinyuan.lib.database.hibernate.HibernateUtils;
import com.qinyuan.lib.lang.IntegerUtils;
import com.qinyuan.lib.mvc.controller.CDNSource;
import com.qinyuan.lib.mvc.controller.ImageController;
import com.qinyuan.lib.mvc.controller.PaginationAttributeAdder;
import com.qinyuan.lib.mvc.controller.PaginationItemFactory;
import com.qinyuan.lib.mvc.security.LoginRecord;
import com.qinyuan.lib.mvc.security.LoginRecordDao;
import com.qinyuan.lib.mvc.security.SecurityUtils;
import com.qinyuan15.lottery.mvc.account.NewUserValidator;
import com.qinyuan15.lottery.mvc.dao.LotteryLivenessDao;
import com.qinyuan15.lottery.mvc.dao.SystemInfoSendRecord;
import com.qinyuan15.lottery.mvc.dao.SystemInfoSendRecordDao;
import com.qinyuan15.lottery.mvc.dao.User;
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
    private final static List<String> titles = Lists.newArrayList("个人资料", "通知", "安全", "绑定手机", "更改邮箱");

    @RequestMapping("/setting")
    public String index(@RequestParam(value = "index", required = false) Integer index) {
        IndexHeaderUtils.setHeaderParameters(this);

        if (!IntegerUtils.isPositive(index)) {
            index = 0;
        }
        setTitle("设置-" + titles.get(index));

        setAttribute("pageIndex", index);
        setAttribute("titles", titles);
        User user = (User) securitySearcher.getUser();
        if (index < 0) {
            List<LoginRecord> loginRecords = LoginRecordDao.factory().setAscOrder(false).setLimitSize(1).getInstances();
            if (loginRecords.size() > 0) {
                setAttribute("location", loginRecords.get(0).getLocation());
            }
            setAttribute("user", user);
            setAttribute("liveness", new LotteryLivenessDao().getLiveness(user.getId()));
        } else if (index == 1) {
            new PaginationAttributeAdder(new SystemInfoFactory(user.getId()), request).setRowItemsName("systemInfoItems").add();
        } else {

        }

        addCssAndJs("setting");
        addJs(CDNSource.HANDLEBARS);
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

/*
    @RequestMapping("/setting-page.json")
    @ResponseBody
    public String page(@RequestParam(value = "index", required = true) Integer index,
                       @RequestParam(value = "pageNumber", required = false) Integer pageNumber) {
        Map<String, Object> map = new HashMap<>();
        User user = (User) securitySearcher.getUser();

        if (index <= 1) {
            map.put("username", user.getUsername());
            map.put("email", user.getEmail());
            map.put("tel", user.getTel());

            List<LoginRecord> loginRecords = LoginRecordDao.factory().setAscOrder(false).setLimitSize(1).getInstances();
            if (loginRecords.size() > 0) {
                map.put("location", loginRecords.get(0).getLocation());
            }
        } else if (index == 2) {
            map.put("username", user.getUsername());
        } else if (index == 3) {
            if (!IntegerUtils.isPositive(pageNumber)) {
                pageNumber = 0;
            }
            new PaginationAttributeAdder(new SystemInfoFactory(user.getId()), request).setRowItemsName("systemInfoItems").add();
        } else {

        }

        return toJson(map);
    }*/

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
