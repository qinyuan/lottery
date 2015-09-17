package com.qinyuan15.lottery.mvc.controller;

import com.qinyuan.lib.lang.IntegerUtils;
import com.qinyuan.lib.mvc.security.SecurityUtils;
import com.qinyuan15.lottery.mvc.AppConfig;
import com.qinyuan15.lottery.mvc.dao.LotteryActivity;
import com.qinyuan15.lottery.mvc.dao.LotteryActivityDao;
import com.qinyuan15.lottery.mvc.dao.LotteryLivenessDao;
import com.qinyuan15.lottery.mvc.dao.UserDao;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpSession;

public class LivenessAdder {
    private final HttpSession session;

    public LivenessAdder(HttpSession session) {
        this.session = session;
    }

    public final static String SPREAD_USER_SERIAL_KEY_SESSION_KEY = "spreadUserSerialKey";
    public final static String SPREAD_MEDIUM_SESSION_KEY = "spreadMedium";
    public final static String SPREAD_LOTTERY_ACTIVITY_ID = "spreadLotteryActivityId";

    void recordSpreader(String userSerialKey, String medium, Integer commodityId) {
        if (StringUtils.hasText(userSerialKey) && StringUtils.hasText(medium)) {
            session.setAttribute(SPREAD_USER_SERIAL_KEY_SESSION_KEY, userSerialKey);
            session.setAttribute(SPREAD_MEDIUM_SESSION_KEY, medium);
            if (IntegerUtils.isPositive(commodityId)) {
                LotteryActivity activity = new LotteryActivityDao().getActiveInstanceByCommodityId(commodityId);
                if (activity != null) {
                    session.setAttribute(SPREAD_LOTTERY_ACTIVITY_ID, activity.getId());
                }
            }
        }
    }

    void addLiveness(boolean registerBefore) {
        Integer userId = new UserDao().getIdByName(SecurityUtils.getUsername());
        addLiveness(userId, registerBefore);
    }

    void addLiveness(Integer receiveUserId, boolean registerBefore) {
        addLiveness(receiveUserId, registerBefore, getSpreadUserId(), getSpreadWay(), getActivityId());
    }

    static void addLiveness(Integer receiveUserId, boolean registerBefore, Integer spreadUserId, String spreadWay, Integer activityId) {
        if (!IntegerUtils.isPositive(receiveUserId)) {
            return;
        }

        if (spreadUserId == null) {
            return;
        }

        if (receiveUserId.equals(spreadUserId)) {
            return;
        }

        if (!StringUtils.hasText(spreadWay)) {
            return;
        }

        Integer liveness = AppConfig.getShareSucceedLiveness();
        if (!IntegerUtils.isPositive(liveness)) {
            return;
        }

        LotteryLivenessDao dao = new LotteryLivenessDao();
        if (IntegerUtils.isPositive(activityId)) {
            dao.add(spreadUserId, receiveUserId, liveness, spreadWay, registerBefore, activityId);
        } else {
            dao.add(spreadUserId, receiveUserId, liveness, spreadWay, registerBefore);
        }
    }

    Integer getActivityId() {
        Object activityId = session.getAttribute(SPREAD_LOTTERY_ACTIVITY_ID);
        if (activityId != null && activityId instanceof Integer) {
            return (Integer) activityId;
        } else {
            return null;
        }
    }

    String getSpreadWay() {
        Object spreadWay = session.getAttribute(SPREAD_MEDIUM_SESSION_KEY);
        if (spreadWay != null && spreadWay instanceof String) {
            return (String) spreadWay;
        } else {
            return null;
        }
    }

    Integer getSpreadUserId() {
        Object spreadUserSerialKey = session.getAttribute(SPREAD_USER_SERIAL_KEY_SESSION_KEY);
        if (spreadUserSerialKey != null && spreadUserSerialKey instanceof String) {
            return new UserDao().getIdBySerialKey((String) spreadUserSerialKey);
        } else {
            return null;
        }
    }
}
