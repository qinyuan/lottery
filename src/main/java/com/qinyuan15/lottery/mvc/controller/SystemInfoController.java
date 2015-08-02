package com.qinyuan15.lottery.mvc.controller;

import com.qinyuan15.lottery.mvc.dao.LotteryLivenessDao;
import com.qinyuan15.lottery.mvc.dao.SystemInfoSendRecord;
import com.qinyuan15.lottery.mvc.dao.SystemInfoSendRecordDao;
import com.qinyuan15.utils.IntegerUtils;
import com.qinyuan15.utils.mvc.controller.ImageController;
import com.qinyuan15.utils.security.SecuritySearcher;
import com.qinyuan15.utils.security.SecurityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.List;

@Controller
public class SystemInfoController extends ImageController {
    private final static Logger LOGGER = LoggerFactory.getLogger(SystemInfoController.class);

    @Autowired
    private SecuritySearcher securitySearcher;

    @RequestMapping("/system-info")
    public String index() {
        IndexHeaderUtils.setHeaderParameters(this);

        LotteryLivenessDao livenessDao = new LotteryLivenessDao();
        Integer userId = securitySearcher.getUserId();
        setAttribute("livenesses", livenessDao.getInstances(userId));
        setAttribute("livenessCount", livenessDao.getLiveness(userId));

        addJavaScriptData("unreadSystemInfoItems", toInfoItems(SystemInfoSendRecordDao.factory().setUserId(userId)
                .setUnread(true).getInstances()));
        addJavaScriptData("readSystemInfoItems", toInfoItems(SystemInfoSendRecordDao.factory().setUserId(userId)
                .setUnread(false).getInstances()));

        // bootstrap switch
        addCss("resources/js/lib/bootstrap/css/bootstrap-switch.min", false);
        addJs("lib/bootstrap/js/bootstrap-switch.min", false);

        setTitle("系统消息");
        addJs("lib/handlebars.min-v1.3.0");
        addCss("personal-center-frame");
        addCssAndJs("system-info");
        return "system-info";
    }

    private List<InfoItem> toInfoItems(List<SystemInfoSendRecord> records) {
        String username = SecurityUtils.getUsername();
        List<InfoItem> infoItems = new ArrayList<>();
        for (SystemInfoSendRecord record : records) {
            InfoItem item = new InfoItem();
            item.id = record.getId();
            item.content = record.getContent();
            if (item.content != null) {
                item.content = item.content.replace("{{user}}", username);
            }
            item.buildTime = record.getBuildTime();
            item.unread = record.getUnread();
            infoItems.add(item);
        }
        return infoItems;
    }

    public static class InfoItem {
        public int id;
        public String content;
        public String buildTime;
        public boolean unread;
    }

    @RequestMapping("/system-info-mark-as-read.json")
    @ResponseBody
    public String markAsRead(@RequestParam(value = "id", required = true) Integer id) {
        if (!IntegerUtils.isPositive(id)) {
            return failByInvalidParam();
        }

        try {
            new SystemInfoSendRecordDao().read(id);
            return success();
        } catch (Exception e) {
            LOGGER.error("Fail to mark system information as read, SystemInfoSendRecordId: {}, info: {}", id, e);
            return failByDatabaseError();
        }
    }
}
