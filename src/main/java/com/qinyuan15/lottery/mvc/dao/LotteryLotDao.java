package com.qinyuan15.lottery.mvc.dao;

import com.google.common.base.Joiner;
import com.qinyuan15.lottery.mvc.lottery.LotteryLotSerialGenerator;
import com.qinyuan15.utils.DateUtils;
import com.qinyuan15.utils.IntegerUtils;
import com.qinyuan15.utils.hibernate.HibernateListBuilder;
import com.qinyuan15.utils.hibernate.HibernateUtils;

import java.util.List;

public class LotteryLotDao {
    public LotteryLot getInstance(Integer id) {
        return HibernateUtils.get(LotteryLot.class, id);
    }

    public Integer add(Integer activityId, Integer userId, LotteryLotSerialGenerator serialGenerator) {
        LotteryLot lotteryLot = new LotteryLot();
        lotteryLot.setActivityId(activityId);
        lotteryLot.setUserId(userId);
        lotteryLot.setLotTime(DateUtils.nowString());
        lotteryLot.setSerialNumber(serialGenerator.next());
        return HibernateUtils.save(lotteryLot);
    }

    public void updateWinnerBySerialNumbers(Integer activityId, List<Integer> serialNumbers) {
        if (!IntegerUtils.isPositive(activityId)) {
            return;
        }

        String hql = "UPDATE LotteryLot SET win=null WHERE activityId=" + activityId;
        HibernateUtils.executeUpdate(hql);

        if (serialNumbers != null && serialNumbers.size() > 0) {
            hql = "UPDATE LotteryLot SET win=true WHERE activityId=" + activityId;
            hql += " AND serialNumber in (" + Joiner.on(",").join(serialNumbers) + ")";
            HibernateUtils.executeUpdate(hql);
        }
    }

    public boolean hasSerialNumber(Integer activityId, Integer serialNumber) {
        return factory().setActivityId(activityId).setSerialNumber(serialNumber).getCount() > 0;
    }

    public static class Factory {
        private Integer activityId;
        private Integer userId;
        private Integer serialNumber;
        private Boolean win;

        public Factory setActivityId(Integer activityId) {
            this.activityId = activityId;
            return this;
        }

        public Factory setUserId(Integer userId) {
            this.userId = userId;
            return this;
        }

        public Factory setSerialNumber(Integer serialNumber) {
            this.serialNumber = serialNumber;
            return this;
        }

        public Factory setWin(Boolean win) {
            this.win = win;
            return this;
        }

        private HibernateListBuilder createListBuilder() {
            HibernateListBuilder listBuilder = new HibernateListBuilder();
            if (IntegerUtils.isPositive(activityId)) {
                listBuilder.addEqualFilter("activityId", activityId);
            }
            if (IntegerUtils.isPositive(userId)) {
                listBuilder.addEqualFilter("userId", userId);
            }
            if (IntegerUtils.isPositive(serialNumber)) {
                listBuilder.addEqualFilter("serialNumber", serialNumber);
            }
            if (win != null) {
                listBuilder.addEqualFilter("win", win);
            }
            return listBuilder;
        }

        public List<LotteryLot> getInstances() {
            return createListBuilder().build(LotteryLot.class);
        }

        public int getCount() {
            return createListBuilder().count(LotteryLot.class);
        }
    }

    public static Factory factory() {
        return new Factory();
    }

    public int countBySerialNumberRange(Integer startSerialNumber, Integer endSerialNumber) {
        return new HibernateListBuilder()
                .addFilter("serialNumber BETWEEN :startSerialNumber AND :endSerialNumber")
                .addArgument("startSerialNumber", startSerialNumber)
                .addArgument("endSerialNumber", endSerialNumber)
                .count(LotteryLot.class);
    }
}
