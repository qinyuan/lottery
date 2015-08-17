package com.qinyuan15.lottery.mvc.dao;

import com.qinyuan15.utils.IntegerUtils;
import com.qinyuan15.utils.database.hibernate.AbstractDao;
import com.qinyuan15.utils.database.hibernate.HibernateListBuilder;
import com.qinyuan15.utils.database.hibernate.HibernateUtils;
import com.qinyuan15.utils.mvc.controller.AbstractPaginationItemFactory;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

public class SeckillActivityDao extends AbstractDao<SeckillActivity> {
    public static class Factory extends AbstractPaginationItemFactory<SeckillActivity> {
        private Integer commodityId;
        private Boolean expire;

        public Factory setCommodityId(Integer commodityId) {
            this.commodityId = commodityId;
            return this;
        }

        public Factory setExpire(Boolean expire) {
            this.expire = expire;
            return this;
        }

        protected HibernateListBuilder getListBuilder() {
            // order by expire desc, start time desc
            HibernateListBuilder listBuilder = new HibernateListBuilder()
                    .addOrder("expire", false)
                    .addOrder("startTime", false);

            if (IntegerUtils.isPositive(this.commodityId)) {
                listBuilder.addEqualFilter("commodityId", this.commodityId);
            }
            if (this.expire != null) {
                listBuilder.addEqualFilter("expire", this.expire);
            }
            listBuilder.addOrder("id", false);
            return listBuilder;
        }
    }

    public static Factory factory() {
        return new Factory();
    }

    public boolean hasTerm(Integer term) {
        return new HibernateListBuilder().addEqualFilter("term", term).count(getPersistClass()) > 0;
    }

    public SeckillActivity getActiveInstanceByCommodityId(Integer commodityId) {
        return factory().setCommodityId(commodityId).setExpire(false).getFirstInstance();
    }

    public SeckillActivity getLastInstance() {
        return factory().getFirstInstance(); // factory order by id desc
    }

    public SeckillActivity getLastActiveInstance() {
        return factory().setExpire(false).getFirstInstance();
    }

    public Integer getMaxTerm() {
        return (Integer) new HibernateListBuilder().getFirstItem("SELECT MAX(term) FROM SeckillActivity");
    }

    public Integer add(Integer term, Integer commodityId, String startTime, Integer expectParticipantCount) {
        SeckillActivity activity = new SeckillActivity();
        activity.setTerm(term);
        activity.setCommodityId(commodityId);
        activity.setStartTime(startTime);
        activity.setExpectParticipantCount(expectParticipantCount);

        activity.setExpire(false);
        return HibernateUtils.save(activity);
    }

    public void update(Integer id, Integer term, Integer commodityId, String startTime, Integer expectParticipantCount) {
        SeckillActivity activity = getInstance(id);
        activity.setTerm(term);
        activity.setCommodityId(commodityId);
        activity.setStartTime(startTime);
        activity.setExpectParticipantCount(expectParticipantCount);
        HibernateUtils.update(activity);
    }

    public void end(Integer id) {
        SeckillActivity activity = getInstance(id);
        activity.setExpire(true);
        HibernateUtils.update(activity);
    }

    public void updateResult(Integer id, String winners, String announcement) {
        List<Integer> serialNumbers = new ArrayList<>();
        if (StringUtils.hasText(winners)) {
            for (String winner : winners.split(",")) {
                if (IntegerUtils.isPositive(winner)) {
                    serialNumbers.add(Integer.parseInt(winner));
                }
            }
        }
        new LotteryLotDao().updateWinnerBySerialNumbers(id, serialNumbers);

        SeckillActivity activity = getInstance(id);
        activity.setWinners(winners);
        activity.setAnnouncement(announcement);
        HibernateUtils.update(activity);
    }

    /**
     * validate if seckill activity is expired
     *
     * @param id id of seckill activity to query
     * @return true if seckill activity can be found and is expired, otherwise false
     */
    public boolean isExpire(Integer id) {
        String hql = "SELECT expire FROM SeckillActivity WHERE id=" + id;
        Boolean expire = (Boolean) new HibernateListBuilder().getFirstItem(hql);
        if (expire == null) {
            expire = false;
        }
        return expire;
    }

    public void delete(Integer id) {
        if (isExpire(id)) {
            throw new RuntimeException("Can not delete lottery activity expired");
        } else {
            super.delete(id);
        }
    }
}
