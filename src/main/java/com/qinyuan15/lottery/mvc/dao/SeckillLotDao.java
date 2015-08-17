package com.qinyuan15.lottery.mvc.dao;

import com.qinyuan15.utils.DateUtils;
import com.qinyuan15.utils.IntegerUtils;
import com.qinyuan15.utils.database.hibernate.AbstractDao;
import com.qinyuan15.utils.database.hibernate.HibernateListBuilder;
import com.qinyuan15.utils.database.hibernate.HibernateUtils;
import com.qinyuan15.utils.mvc.controller.AbstractPaginationItemFactory;

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
}
