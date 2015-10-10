package com.qinyuan15.lottery.mvc.dao;

import com.qinyuan.lib.database.hibernate.HibernateListBuilder;
import com.qinyuan.lib.database.hibernate.HibernateUtils;
import com.qinyuan.lib.lang.IntegerRange;
import com.qinyuan.lib.lang.IntegerUtils;
import com.qinyuan.lib.lang.time.DateUtils;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

public class LotteryActivityDao extends AbstractActivityDao<LotteryActivity> {
    public static class Factory extends AbstractActivityPaginationItemFactory<LotteryActivity> {
        private Boolean closed;

        public Factory setClosed(Boolean closed) {
            this.closed = closed;
            return this;
        }

        @Override
        protected void addOrders(HibernateListBuilder listBuilder) {
            listBuilder.addOrder("expire", true).addOrder("endTime", false)
                    .addOrder("startTime", false).addOrder("id", false);
        }

        @Override
        protected HibernateListBuilder getListBuilder() {
            HibernateListBuilder listBuilder = super.getListBuilder();
            if (closed != null) {
                listBuilder.addEqualFilter("closed", closed);
            }
            return listBuilder;
        }

        @Override
        protected Class<? extends AbstractLot> getLotClass() {
            return LotteryLot.class;
        }
    }

    public static Factory factory() {
        return new Factory();
    }

    @Override
    public AbstractActivityPaginationItemFactory<LotteryActivity> getFactory() {
        return factory();
    }

    public Integer add(Integer term, Integer commodityId, String startTime, String expectEndTime, String closeTime,
                       Integer continuousSerialLimit, Integer expectParticipantCount, Integer dualColoredBallTerm,
                       String description, Integer minLivenessToParticipant, Integer minSerialNumber,
                       Integer maxSerialNumber) {
        LotteryActivity activity = new LotteryActivity();
        activity.setTerm(term);
        activity.setCommodityId(commodityId);
        activity.setStartTime(startTime);
        activity.setExpectEndTime(expectEndTime);
        activity.setCloseTime(closeTime);
        activity.setContinuousSerialLimit(continuousSerialLimit);
        activity.setExpectParticipantCount(expectParticipantCount);
        activity.setDualColoredBallTerm(dualColoredBallTerm);
        activity.setDescription(description);
        activity.setMinLivenessToParticipate(minLivenessToParticipant);
        activity.setMinSerialNumber(minSerialNumber);
        activity.setMaxSerialNumber(maxSerialNumber);

        // set default values
        activity.setVirtualParticipants(0);
        activity.setExpire(false);
        activity.setClosed(false);

        return HibernateUtils.save(activity);
    }

    public void update(Integer id, Integer term, Integer commodityId, String startTime, String expectEndTime,
                       String closeTime, Integer continuousSerialLimit, Integer expectParticipantCount,
                       Integer virutalLiveness, String virtualLivenessUsers, Integer dualColoredBallTerm,
                       String description, Integer minLivenessToParticipant, Integer minSerialNumber,
                       Integer maxSerialNumber) {
        LotteryActivity activity = getInstance(id);

        if (activity != null) {
            activity.setTerm(term);
            activity.setCommodityId(commodityId);
            activity.setStartTime(startTime);
            activity.setExpectEndTime(expectEndTime);
            activity.setCloseTime(closeTime);
            activity.setContinuousSerialLimit(continuousSerialLimit);
            activity.setExpectParticipantCount(expectParticipantCount);
            activity.setVirtualLiveness(virutalLiveness);
            activity.setVirtualLivenessUsers(virtualLivenessUsers);
            activity.setDualColoredBallTerm(dualColoredBallTerm);
            activity.setDescription(description);
            activity.setMinLivenessToParticipate(minLivenessToParticipant);
            activity.setMinSerialNumber(minSerialNumber);
            activity.setMaxSerialNumber(maxSerialNumber);

            HibernateUtils.update(activity);
        }
    }

    @Override
    public void end(Integer id) {
        LotteryActivity activity = getInstance(id);
        activity.setEndTime(DateUtils.nowString());
        activity.setExpire(true);
        HibernateUtils.update(activity);
    }

    public void close(LotteryActivity activity) {
        activity.setClosed(true);
        HibernateUtils.update(activity);
        new CommodityDao().updateVisible(activity.getCommodityId(), false);
    }

    @Override
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

        super.updateResult(id, winners, announcement);
    }

    /*public Integer getMaxSerialNumber(Integer activityId) {
        return (Integer) new HibernateListBuilder().addEqualFilter("id", activityId)
                .getFirstItem("SELECT maxSerialNumber FROM LotteryActivity");
    }*/

    public Integer getLatestMinLivenessToParticipate() {
        return (Integer) new HibernateListBuilder().addOrder("id", false)
                .getFirstItem("SELECT minLivenessToParticipate FROM " + LotteryActivity.class.getSimpleName());
    }

    private final static String DEFAULT_SERIAL_NUMBER_RANGE = "10~100000";

    public String getLatestSerialNumberRange() {
        LotteryActivity activity = new HibernateListBuilder().addOrder("id", false).getFirstItem(LotteryActivity.class);
        if (activity == null) {
            return DEFAULT_SERIAL_NUMBER_RANGE;
        }

        Integer minSerialNumber = activity.getMinSerialNumber();
        if (!IntegerUtils.isPositive(minSerialNumber)) {
            return DEFAULT_SERIAL_NUMBER_RANGE;
        }

        Integer maxSerialNumber = activity.getMaxSerialNumber();
        if (!IntegerUtils.isPositive(maxSerialNumber)) {
            return DEFAULT_SERIAL_NUMBER_RANGE;
        }

        return new IntegerRange(minSerialNumber, maxSerialNumber).toString();
    }

    public List<LotteryActivity> getUnclosedInstances() {
        return factory().setClosed(false).getInstances();
    }
}
