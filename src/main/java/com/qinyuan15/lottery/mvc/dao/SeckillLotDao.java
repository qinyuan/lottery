package com.qinyuan15.lottery.mvc.dao;

import com.qinyuan.lib.database.hibernate.AbstractDao;
import com.qinyuan.lib.database.hibernate.HibernateListBuilder;
import com.qinyuan.lib.database.hibernate.HibernateUpdater;
import com.qinyuan.lib.database.hibernate.HibernateUtils;
import com.qinyuan.lib.lang.time.DateUtils;
import com.qinyuan.lib.lang.IntegerUtils;
import com.qinyuan.lib.mvc.controller.AbstractPaginationItemFactory;

import java.util.List;

public class SeckillLotDao extends AbstractDao<SeckillLot> {
    public Integer add(Integer activityId, Integer userId) {
        SeckillLot SeckillLot = new SeckillLot();
        SeckillLot.setActivityId(activityId);
        SeckillLot.setUserId(userId);
        SeckillLot.setLotTime(DateUtils.nowString());
        return HibernateUtils.save(SeckillLot);
    }

    public static class Factory extends AbstractPaginationItemFactory<SeckillLot> {
        private Integer activityId;
        private Integer userId;
        private Boolean win;

        public Factory setActivityId(Integer activityId) {
            this.activityId = activityId;
            return this;
        }

        public Factory setUserId(Integer userId) {
            this.userId = userId;
            return this;
        }

        public Factory setWin(Boolean win) {
            this.win = win;
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
            if (win != null) {
                listBuilder.addEqualFilter("win", win);
            }
            return listBuilder;
        }
    }

    public static Factory factory() {
        return new Factory();
    }

    public void updateWinnerBySerialNumbers(Integer activityId, List<String> winners) {
        if (!IntegerUtils.isPositive(activityId)) {
            return;
        }

        String hql = "UPDATE SeckillLot SET win=null WHERE activityId=" + activityId;
        HibernateUtils.executeUpdate(hql);

        for (String winner : winners) {
            new HibernateUpdater().addEqualFilter("activityId", activityId)
                    .addFilter("userId in (SELECT id FROM User WHERE username=:username)")
                    .addArgument("username", winner).update(SeckillLot.class, "win=true");
        }
    }
}
