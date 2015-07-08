package com.qinyuan15.lottery.mvc.dao;

import com.qinyuan15.utils.hibernate.HibernateDeleter;
import com.qinyuan15.utils.hibernate.HibernateListBuilder;
import com.qinyuan15.utils.hibernate.HibernateUtils;

import java.util.List;

public class LotteryWinnerDao {
    public List<Integer> getUserIdsByActivityId(Integer activityId) {
        @SuppressWarnings("unchecked")
        List<Integer> userIds = new HibernateListBuilder().addEqualFilter("activityId", activityId)
                .build("SELECT userId FROM LotteryWinner");
        return userIds;
    }

    public List<Integer> getActivityIdsByUserId(Integer userId) {
        @SuppressWarnings("unchecked")
        List<Integer> activityIds = new HibernateListBuilder().addEqualFilter("userId", userId)
                .build("SELECT activityId FROM LotteryWinner");
        return activityIds;
    }

    public void update(Integer activityId, List<Integer> userIds) {
        deleteByActivityId(activityId);
        for (Integer userId : userIds) {
            save(activityId, userId);
        }
    }

    private void save(Integer activityId, Integer userId) {
        LotteryWinner winner = new LotteryWinner();
        winner.setActivityId(activityId);
        winner.setUserId(userId);
        HibernateUtils.save(winner);
    }

    private void deleteByActivityId(Integer activityId) {
        new HibernateDeleter().addEqualFilter("activityId", activityId).delete(LotteryWinner.class);
    }
}
