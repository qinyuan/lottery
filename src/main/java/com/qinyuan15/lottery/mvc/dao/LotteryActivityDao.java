package com.qinyuan15.lottery.mvc.dao;

import com.qinyuan15.utils.DateUtils;
import com.qinyuan15.utils.IntegerUtils;
import com.qinyuan15.utils.database.hibernate.AbstractDao;
import com.qinyuan15.utils.database.hibernate.HibernateListBuilder;
import com.qinyuan15.utils.database.hibernate.HibernateUtils;
import com.qinyuan15.utils.mvc.controller.AbstractPaginationItemFactory;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

public class LotteryActivityDao extends AbstractDao<LotteryActivity> {
    public static class Factory extends AbstractPaginationItemFactory<LotteryActivity> {
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

        @Override
        protected HibernateListBuilder getListBuilder() {
            // order by expire desc, end time desc, start time desc
            HibernateListBuilder listBuilder = new HibernateListBuilder()
                    .addOrder("expire", false)
                    .addOrder("endTime", false)
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

    public LotteryActivity getActiveInstanceByCommodityId(Integer commodityId) {
        return factory().setCommodityId(commodityId).setExpire(false).getFirstInstance();
    }

    public LotteryActivity getLastInstance() {
        return factory().getFirstInstance(); // factory order by id desc
    }

    public LotteryActivity getLastActiveInstance() {
        return factory().setExpire(false).getFirstInstance();
    }

    public Integer getMaxTerm() {
        return (Integer) new HibernateListBuilder().getFirstItem("SELECT MAX(term) FROM LotteryActivity");
    }

    public Integer add(Integer term, Integer commodityId, String startTime, String expectEndTime,
                       Integer continuousSerialLimit, Integer expectParticipantCount, Integer dualColoredBallTerm) {
        LotteryActivity activity = new LotteryActivity();
        activity.setTerm(term);
        activity.setCommodityId(commodityId);
        activity.setStartTime(startTime);
        activity.setExpectEndTime(expectEndTime);
        activity.setContinuousSerialLimit(continuousSerialLimit);
        activity.setExpectParticipantCount(expectParticipantCount);
        activity.setDualColoredBallTerm(dualColoredBallTerm);

        // set default values
        activity.setMaxSerialNumber(0);
        activity.setVirtualParticipants(0);

        activity.setExpire(false);
        return HibernateUtils.save(activity);
    }

    public void updateMaxSerialNumber(Integer id, int serialNumber) {
        String hql = "UPDATE LotteryActivity SET maxSerialNumber=" + serialNumber + " WHERE id=" + id;
        HibernateUtils.executeUpdate(hql);
    }

    public void increaseMaxSerialNumber(Integer id) {
        increaseMaxSerialNumber(id, 1);
    }

    public void increaseMaxSerialNumber(Integer id, int n) {
        String hql = "UPDATE LotteryActivity SET maxSerialNumber=maxSerialNumber+" + n + " WHERE id=" + id;
        HibernateUtils.executeUpdate(hql);
    }

    public void update(Integer id, Integer term, Integer commodityId, String startTime, String expectEndTime,
                       Integer continuousSerialLimit, Integer expectParticipantCount,
                       Integer virutalLiveness, String virtualLivenessUsers, Integer dualColoredBallTerm) {
        LotteryActivity activity = getInstance(id);
        activity.setTerm(term);
        activity.setCommodityId(commodityId);
        activity.setStartTime(startTime);
        activity.setExpectEndTime(expectEndTime);
        activity.setContinuousSerialLimit(continuousSerialLimit);
        activity.setExpectParticipantCount(expectParticipantCount);
        activity.setVirtualLiveness(virutalLiveness);
        activity.setVirtualLivenessUsers(virtualLivenessUsers);
        activity.setDualColoredBallTerm(dualColoredBallTerm);
        HibernateUtils.update(activity);
    }

    public void end(Integer id) {
        LotteryActivity activity = getInstance(id);
        activity.setEndTime(DateUtils.nowString());
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

        LotteryActivity activity = getInstance(id);
        activity.setWinners(winners);
        activity.setAnnouncement(announcement);
        HibernateUtils.update(activity);
    }

    /**
     * validate if lottery activity is expired
     *
     * @param id id of lottery activity to query
     * @return true if lottery activity can be found and is expired, otherwise false
     */
    public boolean isExpire(Integer id) {
        String hql = "SELECT expire FROM LotteryActivity WHERE id=" + id;
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

    public Integer getMaxSerialNumber(Integer activityId) {
        return (Integer) new HibernateListBuilder().addEqualFilter("id", activityId)
                .getFirstItem("SELECT maxSerialNumber FROM LotteryActivity");
    }
}
