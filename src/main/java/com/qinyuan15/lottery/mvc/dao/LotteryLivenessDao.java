package com.qinyuan15.lottery.mvc.dao;

import com.google.common.base.Joiner;
import com.qinyuan.lib.database.hibernate.HibernateListBuilder;
import com.qinyuan.lib.database.hibernate.HibernateUtils;
import com.qinyuan.lib.lang.IntegerUtils;
import com.qinyuan15.lottery.mvc.activity.LivenessIncreaseSystemInfoSender;
import com.qinyuan15.lottery.mvc.activity.NewLotteryChanceSystemInfoSender;
import com.qinyuan15.lottery.mvc.activity.lot.LotteryLotCounter;
import com.qinyuan15.lottery.mvc.config.AppConfig;
import com.qinyuan15.lottery.mvc.mail.NewLotteryChanceMailSender;
import org.apache.commons.lang3.BooleanUtils;
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
                .addFilter("receiveUserId IN (SELECT id FROM User)")
                .getFirstItem("SELECT SUM(liveness) FROM LotteryLiveness");
        return liveness == null ? 0 : liveness.intValue();
    }

    public List<Pair<Integer, String>> getReceiveUsers(Integer spreadUserId) {
        String sql = "SELECT receive_user_id,username FROM lottery_liveness AS l " +
                "INNER JOIN user AS u ON l.receive_user_id=u.id";
        List<Object[]> list = new HibernateListBuilder().addEqualFilter("l.spread_user_id", spreadUserId)
                .buildBySQL(sql);
        List<Pair<Integer, String>> users = new ArrayList<>();
        for (Object[] objects : list) {
            Integer userId = (Integer) objects[0];
            String username = (String) objects[1];
            users.add(Pair.of(userId, username));
        }
        return users;
    }

    public Pair<String, Integer> getMaxLivenessUsernames() {
        String subSQL = "SELECT spread_user_id,SUM(liveness) AS liveness FROM lottery_liveness " +
                "GROUP BY spread_user_id";
        String sql = "SELECT u.username,l.liveness FROM user AS u INNER JOIN (" +
                subSQL + ") AS l ON u.id=l.spread_user_id " +
                "WHERE l.liveness=(SELECT MAX(liveness) FROM (" + subSQL + ") AS temp)";
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

    public boolean hasInstance(Integer spreadUserId, Integer receiveUserId) {
        return IntegerUtils.isPositive(spreadUserId) && IntegerUtils.isPositive(receiveUserId)
                && new HibernateListBuilder().addEqualFilter("spreadUserId", spreadUserId)
                .addEqualFilter("receiveUserId", receiveUserId).count(LotteryLiveness.class) > 0;
    }

    public Integer add(Integer spreadUserId, Integer receiveUserId, int liveness, String spreadWay,
                       boolean alreadyRegistered) {
        LotteryActivity activity = new LotteryActivityDao().getLastActiveInstance();
        int activityId = activity == null ? -1 : activity.getId();
        return add(spreadUserId, receiveUserId, liveness, spreadWay, alreadyRegistered, activityId);
    }

    public Integer add(Integer spreadUserId, Integer receiveUserId, int liveness, String spreadWay,
                       boolean alreadyRegistered, Integer activityId) {
        if (!IntegerUtils.isPositive(spreadUserId) || !IntegerUtils.isPositive(receiveUserId)
                || spreadUserId.equals(receiveUserId) || activityId == null
                || hasInstance(spreadUserId, receiveUserId)) {
            return null;
        }

        LotteryLiveness ll = new LotteryLiveness();
        ll.setSpreadUserId(spreadUserId);
        ll.setReceiveUserId(receiveUserId);
        ll.setActivityId(activityId);
        ll.setLiveness(liveness);
        ll.setSpreadWay(spreadWay);
        ll.setRegisterBefore(alreadyRegistered);
        Integer id = HibernateUtils.save(ll);

        // send email or system information to user
        Boolean remindLivenessIncreaseBySystemInfo = AppConfig.getRemindLivenessIncreaseBySystemInfo();
        if (BooleanUtils.toBoolean(remindLivenessIncreaseBySystemInfo)) {
            try {
                new LivenessIncreaseSystemInfoSender().send(ll);
            } catch (Exception e) {
                LOGGER.error("Fail to send liveness increase system info, activityId: {}, userId: {}, info: {}",
                        activityId, spreadUserId, e);
            }
        }

        if (new LotteryLotCounter().getAvailableLotCount(activityId, spreadUserId) > 0) {
            Boolean remindNewLotteryChanceByMail = AppConfig.lottery.getRemindNewChanceByMail();
            if (BooleanUtils.toBoolean(remindNewLotteryChanceByMail)) {
                try {
                    new NewLotteryChanceMailSender().send(spreadUserId, activityId);
                } catch (Exception e) {
                    LOGGER.error("Fail to send new lottery chance mail, activityId: {}, userId: {}, info: {}",
                            activityId, spreadUserId, e);
                }
            }

            Boolean remindNewLotteryChanceBySystemInfo = AppConfig.lottery.getRemindNewChanceBySystemInfo();
            if (BooleanUtils.toBoolean(remindNewLotteryChanceBySystemInfo)) {
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
