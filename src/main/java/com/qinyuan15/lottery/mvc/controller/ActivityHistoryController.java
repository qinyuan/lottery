package com.qinyuan15.lottery.mvc.controller;

import com.google.common.base.Joiner;
import com.qinyuan.lib.lang.IntegerUtils;
import com.qinyuan.lib.mvc.controller.ImageController;
import com.qinyuan.lib.mvc.security.SecurityUtils;
import com.qinyuan15.lottery.mvc.activity.lot.InvalidLotteryLotUtils;
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
        setAttribute("activityType", activityType);

        setAttribute("activities", getActivities());

        User user = new UserDao().getInstanceByName(SecurityUtils.getUsername());
        if (user != null && BooleanUtils.isNotTrue(user.getReceiveMail())) {
            setAttribute("email", user.getEmail());
            addJavaScriptData("email", user.getEmail());
            SubscribeHeaderUtils.setHeaderParameters(this);
        }

        setTitle("活动历史");
        addCss("resources/js/lib/icheck/skins/all", false);
        addJs("lib/icheck/icheck.min", false);

        addCssAndJs("subscribe-float-panel");
        addCssAndJs("activity-history");
        return "activity-history";
    }

    private List<Activity> getActivities() {
        List<Activity> activities = new ArrayList<>();
        User user = new UserDao().getInstanceByName(SecurityUtils.getUsername());
        Integer userId = user.getId();
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
                activities.add(buildActivityByLottery(lotteryActivities.get(lotteryIndex++), user));
            } else {
                LotteryActivity lotteryActivity = lotteryActivities.get(lotteryIndex);
                SeckillActivity seckillActivity = seckillActivities.get(seckillIndex);

                if (!BooleanUtils.toBoolean(lotteryActivity.getExpire()) &&
                        BooleanUtils.toBoolean(seckillActivity.getExpire())) {
                    // add the active one first
                    activities.add(buildActivityByLottery(lotteryActivities.get(lotteryIndex++), user));
                } else if (!BooleanUtils.toBoolean(seckillActivity.getExpire()) ||
                        BooleanUtils.toBoolean(lotteryActivity.getExpire())) {
                    // add the active one first
                    activities.add(buildActivityBySeckill(seckillActivities.get(seckillIndex++)));
                } else {
                    // add the earlier one first
                    if (lotteryActivity.getStartTime().compareTo(seckillActivity.getStartTime()) <= 0) {
                        activities.add(buildActivityByLottery(lotteryActivities.get(lotteryIndex++), user));
                    } else {
                        activities.add(buildActivityBySeckill(seckillActivities.get(seckillIndex++)));
                    }
                }
            }
        }
        return activities;
    }

    private Activity buildActivityByLottery(LotteryActivity lotteryActivity, User user) {
        Activity activity = new Activity();
        activity.term = lotteryActivity.getTerm();
        Commodity commodity = new CommodityUrlAdapter(this).adaptToMiddle(lotteryActivity.getCommodity());
        activity.snapshot = commodity.getSnapshot();
        activity.name = commodity.getName();
        activity.participantCount = lotteryActivity.getParticipantCount();
        activity.expire = BooleanUtils.toBoolean(lotteryActivity.getExpire());
        activity.announcement = lotteryActivity.getAnnouncement();
        activity.type = "lottery";

        if (user != null && IntegerUtils.isPositive(user.getId())) {
            List<LotteryLot> lots = LotteryLotDao.factory().setActivityId(lotteryActivity.getId())
                    .setUserId(user.getId()).getInstances();
            if (lots.size() > 0) {
                List<String> serials = new ArrayList<>();
                for (LotteryLot lot : lots) {
                    serials.add(lotNumberFormat.format(lot.getSerialNumber()));
                }
                activity.serials = Joiner.on(",").join(serials);
                //activity.invalid = isUserInvalidToTakeLot(user, lotteryActivity);
                activity.invalid = false; // TODO modify this logic
            }
        }
        activity.closed = BooleanUtils.isTrue(lotteryActivity.getClosed());

        return activity;
    }

    private boolean isUserInvalidToTakeLot(User user, LotteryActivity lotteryActivity) {
        if (InvalidLotteryLotUtils.isNoTelInvalidLot(user, lotteryActivity)) {
            return true;
        }
        if (!IntegerUtils.isPositive(lotteryActivity.getMinLivenessToParticipate())) {
            return false;
        }
        int liveness = new LotteryLivenessDao().getLiveness(user.getId());
        return liveness < lotteryActivity.getMinLivenessToParticipate();
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
        private Boolean closed;
        private boolean invalid;

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

        public Boolean getClosed() {
            return closed;
        }

        public boolean isInvalid() {
            return invalid;
        }
    }
}
