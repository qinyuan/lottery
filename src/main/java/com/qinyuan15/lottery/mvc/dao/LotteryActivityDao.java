package com.qinyuan15.lottery.mvc.dao;

import com.qinyuan15.utils.DateUtils;
import com.qinyuan15.utils.IntegerUtils;
import com.qinyuan15.utils.database.hibernate.HibernateListBuilder;
import com.qinyuan15.utils.database.hibernate.HibernateUtils;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

public class LotteryActivityDao extends AbstractActivityDao<LotteryActivity> {
    public static class Factory extends AbstractActivityPaginationItemFactory<LotteryActivity> {
        @Override
        protected void addOrders(HibernateListBuilder listBuilder) {
            listBuilder.addOrder("expire", false).addOrder("endTime", false)
                    .addOrder("startTime", false).addOrder("id", false);
        }
    }

    public static Factory factory() {
        return new Factory();
    }

    @Override
    public AbstractActivityPaginationItemFactory<LotteryActivity> getFactory() {
        return factory();
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

    @Override
    public void end(Integer id) {
        LotteryActivity activity = getInstance(id);
        activity.setEndTime(DateUtils.nowString());
        activity.setExpire(true);
        HibernateUtils.update(activity);
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

    public Integer getMaxSerialNumber(Integer activityId) {
        return (Integer) new HibernateListBuilder().addEqualFilter("id", activityId)
                .getFirstItem("SELECT maxSerialNumber FROM LotteryActivity");
    }
}
