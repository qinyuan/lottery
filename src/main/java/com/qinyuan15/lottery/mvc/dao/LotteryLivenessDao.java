package com.qinyuan15.lottery.mvc.dao;

import com.google.common.base.Joiner;
import com.qinyuan15.utils.IntegerUtils;
import com.qinyuan15.utils.hibernate.HibernateListBuilder;
import com.qinyuan15.utils.hibernate.HibernateUtils;
import org.apache.commons.lang3.tuple.Pair;
import org.springframework.util.StringUtils;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class LotteryLivenessDao {
    /**
     * Get liveness of certain user in his/her last lottery activity
     *
     * @param userId id of user
     * @return liveness of last lottery activity
     */
    public int getLiveness(Integer userId) {
        LotteryActivityDao activityDao = new LotteryActivityDao();
        LotteryActivity activity = activityDao.getLastActiveInstance();
        if (activity == null) {
            activity = activityDao.getLastInstance();
        }
        return activity == null ? 0 : getLiveness(userId, activity.getId());
    }

    public int getLiveness(Integer spreadUserId, Integer activityId) {
        Long liveness = (Long) new HibernateListBuilder().addEqualFilter("spreadUserId", spreadUserId)
                .addEqualFilter("activityId", activityId)
                .getFirstItem("SELECT SUM(liveness) FROM LotteryLiveness");
        return liveness == null ? 0 : liveness.intValue();
    }

    public Pair<String, Integer> getMaxLivenessUsernames(Integer activityId) {
        String subSQL = "SELECT spread_user_id,SUM(liveness) AS liveness FROM lottery_liveness " +
                "WHERE activity_id=" + activityId + " GROUP BY spread_user_id";
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


    private boolean hasInstance(Integer spreadUserId, Integer receiveUserId, Integer activityId) {
        return new HibernateListBuilder().addEqualFilter("spreadUserId", spreadUserId)
                .addEqualFilter("receiveUserId", receiveUserId)
                .addEqualFilter("activityId", activityId)
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
                || hasInstance(spreadUserId, receiveUserId, activityId)) {
            return null;
        }

        LotteryLiveness ll = new LotteryLiveness();
        ll.setSpreadUserId(spreadUserId);
        ll.setReceiveUserId(receiveUserId);
        ll.setActivityId(activityId);
        ll.setLiveness(liveness);
        ll.setSpreadWay(spreadWay);
        ll.setRegisterBefore(registerBefore);
        return HibernateUtils.save(ll);
    }
}
