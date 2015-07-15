package com.qinyuan15.lottery.mvc.dao;

import com.qinyuan15.utils.DateUtils;
import com.qinyuan15.utils.IntegerUtils;
import com.qinyuan15.utils.hibernate.HibernateDeleter;
import com.qinyuan15.utils.hibernate.HibernateListBuilder;
import com.qinyuan15.utils.hibernate.HibernateUtils;
import com.qinyuan15.utils.mvc.PaginationItemFactory;

import java.util.List;

public class LotteryActivityDao {
    public static class Factory implements PaginationItemFactory<LotteryActivity> {
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

        private HibernateListBuilder getListBuilder() {
            HibernateListBuilder listBuilder = new HibernateListBuilder()
                    .addOrder("expire", true)
                    .addOrder("endTime", false)
                    .addOrder("startTime", false);
            if (IntegerUtils.isPositive(this.commodityId)) {
                listBuilder.addEqualFilter("commodityId", this.commodityId);
            }
            if (this.expire != null) {
                listBuilder.addEqualFilter("expire", this.expire);
            }
            return listBuilder;
        }

        @Override
        public long getCount() {
            return getListBuilder().count(LotteryActivity.class);
        }

        @Override
        public List<LotteryActivity> getInstances(int firstResult, int maxResults) {
            return getListBuilder().limit(firstResult, maxResults).build(LotteryActivity.class);
        }

        public List<LotteryActivity> getInstances() {
            return getListBuilder().build(LotteryActivity.class);
        }
    }

    public static Factory factory() {
        return new Factory();
    }

    public LotteryActivity getInstance(Integer id) {
        return HibernateUtils.get(LotteryActivity.class, id);
    }

    public boolean hasLottery(Integer commodityId) {
        return factory().setCommodityId(commodityId).getCount() > 0;
    }

    public LotteryActivity getActiveInstanceByCommodityId(Integer commodityId) {
        List<LotteryActivity> activities = factory().setCommodityId(commodityId).setExpire(false).getInstances();
        if (activities.size() == 0) {
            return null;
        } else {
            return activities.get(0);
        }
    }

    public Integer add(Integer commodityId, String startTime, String expectEndTime, Integer continuousSerialLimit,
                       Integer expectParticipantCount, Integer dualColoredBallTerm) {
        LotteryActivity activity = new LotteryActivity();
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

    public void update(Integer id, Integer commodityId, String startTime, String expectEndTime,
                       Integer continuousSerialLimit, Integer expectParticipantCount,
                       Integer virutalLiveness, String virtualLivenessUsers, Integer dualColoredBallTerm) {
        LotteryActivity activity = getInstance(id);
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

    public void updateAnnouncement(Integer id, String announcement) {
        LotteryActivity activity = getInstance(id);
        activity.setAnnouncement(announcement);
        HibernateUtils.update(activity);
    }

    /**
     * validate if lottery activity is expired
     *
     * @param id id of lottery activity to query
     * @return true is lottery activity can be found and is expired, otherwise false
     */
    public boolean isExpire(Integer id) {
        LotteryActivity activity = getInstance(id);
        if (activity == null) {
            return false;
        }
        return activity.getExpire();
    }

    public void delete(Integer id) {
        if (isExpire(id)) {
            throw new RuntimeException("Can not delete lottery activity expired");
        }
        HibernateDeleter.deleteById(LotteryActivity.class, id);
    }

    public Integer getMaxSerialNumber(Integer activityId) {
        return (Integer) new HibernateListBuilder().addEqualFilter("id", activityId)
                .getFirstItem("SELECT maxSerialNumber FROM LotteryActivity");
    }
}
