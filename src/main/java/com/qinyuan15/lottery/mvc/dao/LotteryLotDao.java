package com.qinyuan15.lottery.mvc.dao;

import com.qinyuan15.lottery.mvc.lottery.LotteryLotSerialGenerator;
import com.qinyuan15.utils.DateUtils;
import com.qinyuan15.utils.hibernate.HibernateListBuilder;
import com.qinyuan15.utils.hibernate.HibernateUtils;

import java.util.List;

public class LotteryLotDao {
    public Integer add(Integer activityId, Integer userId, LotteryLotSerialGenerator serialGenerator) {
        LotteryLot lotteryLot = new LotteryLot();
        lotteryLot.setActivityId(activityId);
        lotteryLot.setUserId(userId);
        lotteryLot.setLotTime(DateUtils.nowString());
        lotteryLot.setSerialNumber(serialGenerator.next());
        return HibernateUtils.save(lotteryLot);
    }

    public List<LotteryLot> getInstances(Integer activityId, Integer userId) {
        return new HibernateListBuilder().addEqualFilter("activityId", activityId).addEqualFilter("userId", userId)
                .build(LotteryLot.class);
    }

    public LotteryLot getInstance(Integer activityId, Integer userId, Integer serialNumber) {
        return new HibernateListBuilder().addEqualFilter("activityId", activityId).addEqualFilter("userId", userId)
                .addEqualFilter("serialNumber", serialNumber).getFirstItem(LotteryLot.class);
    }

    public int countBySerialNumberRange(Integer startSerialNumber, Integer endSerialNumber) {
        return new HibernateListBuilder()
                .addFilter("serialNumber BETWEEN :startSerialNumber AND :endSerialNumber")
                .addArgument("startSerialNumber", startSerialNumber)
                .addArgument("endSerialNumber", endSerialNumber)
                .count(LotteryLot.class);
    }
}
