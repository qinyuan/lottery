package com.qinyuan15.lottery.mvc.dao;

import com.google.common.base.Joiner;
import com.qinyuan.lib.database.hibernate.HibernateListBuilder;
import com.qinyuan.lib.database.hibernate.HibernateUtils;
import com.qinyuan.lib.lang.IntegerUtils;
import com.qinyuan15.lottery.mvc.AppConfig;
import com.qinyuan15.lottery.mvc.activity.LotteryLotCounter;
import com.qinyuan15.lottery.mvc.activity.NewLotteryChanceSystemInfoSender;
import com.qinyuan15.lottery.mvc.mail.NewLotteryChanceMailSender;
import org.apache.commons.lang3.tuple.Pair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class LotteryLivenessDao {
    private final static Logger LOGGER = LoggerFactory.getLogger(LotteryLivenessDao.class);

    public List<LotteryLiveness> getInstances(Integer userId) {
        return new HibernateListBuilder().addEqualFilter("spreadUserId", userId)
                .build(LotteryLiveness.class);
    }

    public int getLiveness(Integer spreadUserId) {
        Long liveness = (Long) new HibernateListBuilder().addEqualFilter("spreadUserId", spreadUserId)
                .getFirstItem("SELECT SUM(liveness) FROM LotteryLiveness");
        return liveness == null ? 0 : liveness.intValue();
    }

    public Pair<String, Integer> getMaxLivenessUsernames() {
        String subSQL = "SELECT spread_user_id,SUM(liveness) AS liveness FROM lottery_liveness " +
                "GROUP BY spread_user_id";
        String sql = "SELECT u.username,l.liveness FROM user AS u INNER JOIN (" +
                subSQL + ") AS l ON u.id=l.spread_user_id";
        List<Object[]> list = new HibernateListBuilder().buildBySQL(sql);
        if (list.size() == 0) {
            return null;
        }

        BigDecimal liveness = (BigDecimal) list.get(0)[1];
        List<String> usernames = new ArrayList<>();
        for (Object[] item : list) {
            String username = (String) item[0];
            if (!StringUtils.hasText(username)) {
                continue;
            }
            usernames.add(username);
        }
        return Pair.of(Joiner.on(",").join(usernames), liveness == null ? 0 : liveness.intValue());
    }


    private boolean hasInstance(Integer spreadUserId, Integer receiveUserId) {
        return new HibernateListBuilder().addEqualFilter("spreadUserId", spreadUserId)
                .addEqualFilter("receiveUserId", receiveUserId)
                .count(LotteryLiveness.class) > 0;
    }

    public Integer add(Integer spreadUserId, Integer receiveUserId, int liveness, String spreadWay,
                       boolean registerBefore) {
        LotteryActivity activity = new LotteryActivityDao().getLastActiveInstance();
        if (activity != null) {
            return add(spreadUserId, receiveUserId, liveness, spreadWay, registerBefore, activity.getId());
        }
        return null;
    }

    public Integer add(Integer spreadUserId, Integer receiveUserId, int liveness, String spreadWay,
                       boolean registerBefore, Integer activityId) {
        if (!IntegerUtils.isPositive(spreadUserId) || !IntegerUtils.isPositive(receiveUserId)
                || spreadUserId.equals(receiveUserId) || !IntegerUtils.isPositive(activityId)
                || hasInstance(spreadUserId, receiveUserId)) {
            return null;
        }

        LotteryLiveness ll = new LotteryLiveness();
        ll.setSpreadUserId(spreadUserId);
        ll.setReceiveUserId(receiveUserId);
        ll.setActivityId(activityId);
        ll.setLiveness(liveness);
        ll.setSpreadWay(spreadWay);
        ll.setRegisterBefore(registerBefore);
        Integer id = HibernateUtils.save(ll);

        // send email to user
        if (new LotteryLotCounter().getAvailableLotCount(activityId, spreadUserId) > 0) {
            Boolean remindNewLotteryChanceByMail = AppConfig.getRemindNewLotteryChanceByMail();
            if (remindNewLotteryChanceByMail != null && remindNewLotteryChanceByMail) {
                try {
                    new NewLotteryChanceMailSender().send(spreadUserId, activityId);
                } catch (Exception e) {
                    LOGGER.error("Fail to send new lottery chance mail, activityId: {}, userId: {}, info: {}",
                            activityId, spreadUserId, e);
                }
            }

            Boolean remindNewLotteryChanceBySystemInfo = AppConfig.getRemindNewLotteryChanceBySystemInfo();
            if (remindNewLotteryChanceBySystemInfo != null && remindNewLotteryChanceBySystemInfo) {
                try {
                    new NewLotteryChanceSystemInfoSender().send(spreadUserId, activityId);
                } catch (Exception e) {
                    LOGGER.error("Fail to send new lottery chance system info, activityId: {}, userId: {}, info: {}",
                            activityId, spreadUserId, e);
                }
            }
        }

        return id;
    }
}
