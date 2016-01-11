package com.qinyuan15.lottery.mvc.dao;

import com.qinyuan.lib.database.hibernate.AbstractDao;
import com.qinyuan.lib.database.hibernate.HibernateUtils;

public class LotteryWinnerLivenessDao extends AbstractDao<LotteryWinnerLiveness> {
    public Integer add(Integer activityId, Integer userId, Boolean virtual, Integer liveness, String recordTime) {
        LotteryWinnerLiveness l = new LotteryWinnerLiveness();

        l.setActivityId(activityId);
        l.setUserId(userId);
        l.setVirtual(virtual);
        l.setLiveness(liveness);
        l.setRecordTime(recordTime);

        return HibernateUtils.save(l);
    }
}
