package com.qinyuan15.lottery.mvc.dao;

import com.google.common.base.Joiner;
import com.qinyuan.lib.database.hibernate.AbstractDao;
import com.qinyuan.lib.database.hibernate.HibernateListBuilder;
import com.qinyuan.lib.database.hibernate.HibernateUtils;
import com.qinyuan.lib.lang.IntegerUtils;
import com.qinyuan.lib.lang.time.DateUtils;
import com.qinyuan.lib.mvc.controller.AbstractPaginationItemFactory;
import com.qinyuan15.lottery.mvc.activity.lot.LotteryLotSerialGenerator;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class LotteryLotDao extends AbstractDao<LotteryLot> {
    public Integer add(Integer activityId, Integer userId, LotteryLotSerialGenerator serialGenerator) {
        return add(activityId, userId, serialGenerator.next());
    }

    public Integer add(Integer activityId, Integer userId, Integer serialNumber) {
        return add(activityId, userId, serialNumber, false);
    }

    public Integer add(Integer activityId, Integer userId, Integer serialNumber, Boolean virtual) {
        LotteryLot lotteryLot = new LotteryLot();
        lotteryLot.setActivityId(activityId);
        lotteryLot.setUserId(userId);
        lotteryLot.setLotTime(DateUtils.nowString());
        lotteryLot.setSerialNumber(serialNumber);
        lotteryLot.setVirtual(virtual);
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

    public static class Factory extends AbstractPaginationItemFactory<LotteryLot> {
        private Integer activityId;
        private Integer userId;
        private Integer serialNumber;
        private Boolean win;
        private Boolean virtual = false;

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

        public Factory setVirtual(Boolean virtual) {
            this.virtual = virtual;
            return this;
        }

        @Override
        protected HibernateListBuilder getListBuilder() {
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
            if (virtual != null) {
                listBuilder.addEqualFilter("virtual", virtual);
            }
            return listBuilder;
        }
    }

    public static Factory factory() {
        return new Factory();
    }

    @SuppressWarnings("unchecked")
    public List<Integer> getSerialNumbers(int activityId, int startSerialNumber, int endSerialNumber) {
        return new HibernateListBuilder()
                .addEqualFilter("activityId", activityId)
                .addFilter("serialNumber BETWEEN :startSerialNumber AND :endSerialNumber")
                .addArgument("startSerialNumber", startSerialNumber)
                .addArgument("endSerialNumber", endSerialNumber)
                .build("SELECT serialNumber FROM " + LotteryLot.class.getSimpleName());
    }

    @SuppressWarnings("unchecked")
    public List<Integer> getSerialNumbers(Integer activityId) {
        return (List) new HibernateListBuilder().addEqualFilter("activityId", activityId)
                .build("SELECT serialNumber FROM LotteryLot");
    }

    @SuppressWarnings("unchecked")
    public List<Integer> getVirtualSerialNumbers(Integer activityId) {
        return (List) new HibernateListBuilder().addEqualFilter("activityId", activityId)
                .addFilter("virtual=true")
                .build("SELECT serialNumber FROM LotteryLot");
    }

    public List<String> getSerialNumbers(int activityId, int userId, DecimalFormat format) {
        List<LotteryLot> lots = LotteryLotDao.factory().setActivityId(activityId).setUserId(userId).getInstances();
        List<String> serialNumbers = new ArrayList<>();
        for (LotteryLot lot : lots) {
            if (format == null) {
                serialNumbers.add(String.valueOf(lot.getSerialNumber()));
            } else {
                serialNumbers.add(format.format(lot.getSerialNumber()));
            }
        }
        return serialNumbers;
    }

    public List<String> getSerialNumbers(int activityId, int userId) {
        return getSerialNumbers(activityId, userId, null);
    }


}
