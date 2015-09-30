package com.qinyuan15.lottery.mvc.controller;

import com.google.common.base.Joiner;
import com.qinyuan.lib.lang.IntegerUtils;
import com.qinyuan.lib.mvc.controller.ImageController;
import com.qinyuan.lib.mvc.security.SecurityUtils;
import com.qinyuan15.lottery.mvc.dao.*;
import org.apache.commons.lang3.BooleanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

@Controller
public class ActivityHistoryController extends ImageController {
    private final static String DEFAULT_ACTIVITY_TYPE = "lottery";

    @Autowired
    private DecimalFormat lotNumberFormat;

    @RequestMapping("/activity-history")
    public String index(@RequestParam(value = "activityType", required = false) String activityType) {
        IndexHeaderUtils.setHeaderParameters(this);

        if (activityType == null) {
            activityType = DEFAULT_ACTIVITY_TYPE;
        }
        /*DatabaseTable table = getTable();
        setAttribute("lotteryHistoryTable", table);
        new PaginationAttributeAdder(table, request).setRowItemsName("lotteryHistories").setPageSize(10).add();*/
        setAttribute("activityType", activityType);

        /*if (activityType.equals(DEFAULT_ACTIVITY_TYPE)) {
            setAttribute("activities", new LotteryActivityDao().getInstances());
        } else {
            setAttribute("activities", new SeckillActivityDao().getInstances());
        }*/
        setAttribute("activities", getActivities());

        User user = new UserDao().getInstanceByName(SecurityUtils.getUsername());
        if (user != null && BooleanUtils.isNotTrue(user.getReceiveMail())) {
            setAttribute("email", user.getEmail());
            addJavaScriptData("email", user.getEmail());
            SubscribeHeaderUtils.setHeaderParameters(this);
        }

        setTitle("活动历史");
        //addCss("resources/js/lib/bootstrap/css/bootstrap-switch.min");
        //addJs("lib/bootstrap/js/bootstrap-switch.min");
        addCss("resources/js/lib/icheck/skins/all", false);
        addJs("lib/icheck/icheck.min", false);

        addCssAndJs("subscribe-float-panel");
        addCssAndJs("activity-history");
        return "activity-history";
    }

    private List<Activity> getActivities() {
        List<Activity> activities = new ArrayList<>();
        Integer userId = securitySearcher.getUserId();
        if (!IntegerUtils.isPositive(userId)) {
            return activities;
        }

        List<LotteryActivity> lotteryActivities = LotteryActivityDao.factory().setUserId(userId).getInstances();
        List<SeckillActivity> seckillActivities = SeckillActivityDao.factory().setUserId(userId).getInstances();
        int lotteryIndex = 0, seckillIndex = 0;

        while (lotteryIndex < lotteryActivities.size() || seckillIndex < seckillActivities.size()) {
            if (lotteryIndex >= lotteryActivities.size()) {
                activities.add(buildActivityBySeckill(seckillActivities.get(seckillIndex++)));
            } else if (seckillIndex >= seckillActivities.size()) {
                activities.add(buildActivityByLottery(lotteryActivities.get(lotteryIndex++), userId));
            } else {
                LotteryActivity lotteryActivity = lotteryActivities.get(lotteryIndex);
                SeckillActivity seckillActivity = seckillActivities.get(seckillIndex);

                if (!BooleanUtils.toBoolean(lotteryActivity.getExpire()) &&
                        BooleanUtils.toBoolean(seckillActivity.getExpire())) {
                    // add the active one first
                    activities.add(buildActivityByLottery(lotteryActivities.get(lotteryIndex++), userId));
                } else if (!BooleanUtils.toBoolean(seckillActivity.getExpire()) ||
                        BooleanUtils.toBoolean(lotteryActivity.getExpire())) {
                    // add the active one first
                    activities.add(buildActivityBySeckill(seckillActivities.get(seckillIndex++)));
                } else {
                    // add the earlier one first
                    if (lotteryActivity.getStartTime().compareTo(seckillActivity.getStartTime()) <= 0) {
                        activities.add(buildActivityByLottery(lotteryActivities.get(lotteryIndex++), userId));
                    } else {
                        activities.add(buildActivityBySeckill(seckillActivities.get(seckillIndex++)));
                    }
                }
            }
        }
        return activities;
    }

    private Activity buildActivityByLottery(LotteryActivity lotteryActivity, Integer userId) {
        Activity activity = new Activity();
        activity.term = lotteryActivity.getTerm();
        Commodity commodity = new CommodityUrlAdapter(this).adaptToMiddle(lotteryActivity.getCommodity());
        activity.snapshot = commodity.getSnapshot();
        activity.name = commodity.getName();
        activity.participantCount = lotteryActivity.getParticipantCount();
        activity.expire = BooleanUtils.toBoolean(lotteryActivity.getExpire());
        activity.announcement = lotteryActivity.getAnnouncement();
        activity.type = "lottery";

        if (IntegerUtils.isPositive(userId)) {
            List<LotteryLot> lots = LotteryLotDao.factory().setActivityId(lotteryActivity.getId()).setUserId(userId).getInstances();
            if (lots.size() > 0) {
                List<String> serials = new ArrayList<>();
                for (LotteryLot lot : lots) {
                    serials.add(lotNumberFormat.format(lot.getSerialNumber()));
                }
                activity.serials = Joiner.on(",").join(serials);
            }
        }

        return activity;
    }

    private Activity buildActivityBySeckill(SeckillActivity seckillActivity) {
        Activity activity = new Activity();
        activity.term = seckillActivity.getTerm();
        Commodity commodity = new CommodityUrlAdapter(this).adaptToMiddle(seckillActivity.getCommodity());
        activity.snapshot = commodity.getSnapshot();
        activity.name = commodity.getName();
        activity.participantCount = seckillActivity.getParticipantCount();
        activity.expire = BooleanUtils.toBoolean(seckillActivity.getExpire());
        activity.announcement = seckillActivity.getAnnouncement();
        activity.type = "seckill";
        return activity;
    }

    public static class Activity {
        private Integer term;
        private String name;
        private String snapshot;
        private Integer participantCount;
        private Boolean expire;
        private String announcement;
        private String type;
        private String serials;

        public Integer getTerm() {
            return term;
        }

        public String getName() {
            return name;
        }

        public String getSnapshot() {
            return snapshot;
        }

        public Integer getParticipantCount() {
            return participantCount;
        }

        public Boolean getExpire() {
            return expire;
        }

        public String getAnnouncement() {
            return announcement;
        }

        public String getType() {
            return type;
        }

        public String getSerials() {
            return serials;
        }
    }
/*
    private DatabaseTable getTable() {
        String tableName = "lottery_lot AS ll LEFT JOIN lottery_activity AS la ON ll.activity_id=la.id";
        tableName += " LEFT JOIN commodity AS c ON la.commodity_id=c.id";

        DatabaseTable table = new DatabaseTable(tableName, "ll.id", DatabaseTable.QueryType.SQL);
        table.addField("奖品", "c.name", "prize");
        table.addField("抽奖时间", "DATE_FORMAT(ll.lot_time,'%Y-%m-%d %T')", "lot_time");

        String serialNumberField = "ll.serial_number";
        table.addField("抽奖号", serialNumberField, "serial_number", new DatabaseTableColumnPostHandler() {
            @Override
            public Object handle(Object targetValue) {
                if (targetValue == null || lotNumberFormat == null) {
                    return targetValue;
                } else {
                    return lotNumberFormat.format(targetValue);
                }
            }
        });

        table.addField("开奖时间", "DATE_FORMAT(la.end_time,'%Y-%m-%d %T')", "end_time");
        table.addField("中奖号", "la.winners", "winners");
        table.addField("中奖公告", "la.announcement", "announcement");
        table.addField("状态", "CASE WHEN la.expire=TRUE THEN '已结束' ELSE '进行中' END", "status");

        table.addOrder("ll.id", false);

        return table;
    }*/
}
