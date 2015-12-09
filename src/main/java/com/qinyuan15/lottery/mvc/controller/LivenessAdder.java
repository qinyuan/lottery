package com.qinyuan15.lottery.mvc.controller;

import com.qinyuan.lib.lang.IntegerUtils;
import com.qinyuan.lib.mvc.security.SecurityUtils;
import com.qinyuan15.lottery.mvc.AppConfig;
import com.qinyuan15.lottery.mvc.dao.LotteryActivity;
import com.qinyuan15.lottery.mvc.dao.LotteryActivityDao;
import com.qinyuan15.lottery.mvc.dao.LotteryLivenessDao;
import com.qinyuan15.lottery.mvc.dao.UserDao;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpSession;

public class LivenessAdder {
    private final static Logger LOGGER = LoggerFactory.getLogger(LivenessAdder.class);

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

    /**
     * add liveness
     *
     * @param alreadyRegistered whether user alreadyRegistered
     * @return liveness record id
     */
    Integer addLiveness(boolean alreadyRegistered) {
        Integer userId = new UserDao().getIdByName(SecurityUtils.getUsername());
        return addLiveness(userId, alreadyRegistered);
    }

    /**
     * add liveness to certain user
     *
     * @param receiveUserId     id of user receiving spread url
     * @param alreadyRegistered whether user alreadyRegistered
     * @return liveness record id
     */
    Integer addLiveness(Integer receiveUserId, boolean alreadyRegistered) {
        return addLiveness(receiveUserId, alreadyRegistered, getSpreadUserId(), getSpreadWay(), getActivityId());
    }

    boolean canAddLiveness() {
        Integer userId = new UserDao().getIdByName(SecurityUtils.getUsername());
        return !(new LotteryLivenessDao().hasInstance(getSpreadUserId(), userId));
    }

    static Integer addLiveness(Integer receiveUserId, boolean alreadyRegistered, Integer spreadUserId, String spreadWay, Integer activityId) {
        if (!IntegerUtils.isPositive(receiveUserId)) {
            LOGGER.info("invalid receiveUserId: {}", receiveUserId);
            return null;
        }

        if (!IntegerUtils.isPositive(spreadUserId)) {
            LOGGER.info("invalid spreadUserId: {}", spreadUserId);
            return null;
        }

        if (receiveUserId.equals(spreadUserId)) {
            LOGGER.warn("spreadUserId and receiveUserId are all {}", spreadUserId);
            return null;
        }

        if (!StringUtils.hasText(spreadWay)) {
            LOGGER.error("invalid spreadWay: {}", spreadWay);
            return null;
        }

        Integer liveness = AppConfig.getShareSucceedLiveness();
        if (!IntegerUtils.isPositive(liveness)) {
            LOGGER.warn("invalid share succeed liveness: {}", liveness);
            return null;
        }

        LotteryLivenessDao dao = new LotteryLivenessDao();
        if (IntegerUtils.isPositive(activityId)) {
            return dao.add(spreadUserId, receiveUserId, liveness, spreadWay, alreadyRegistered, activityId);
        } else {
            LOGGER.warn("add liveness without activityId");
            return dao.add(spreadUserId, receiveUserId, liveness, spreadWay, alreadyRegistered);
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
