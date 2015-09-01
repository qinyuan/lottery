package com.qinyuan15.lottery.mvc.dao;

import com.google.common.collect.Lists;
import com.qinyuan.lib.database.hibernate.HibernateUtils;
import org.apache.commons.lang3.StringUtils;

public class SeckillActivityDao extends AbstractActivityDao<SeckillActivity> {
    public static class Factory extends AbstractActivityPaginationItemFactory<SeckillActivity> {
    }

    public static Factory factory() {
        return new Factory();
    }

    @Override
    public AbstractActivityPaginationItemFactory<SeckillActivity> getFactory() {
        return factory();
    }

    public Integer add(Integer term, Integer commodityId, String startTime, Integer expectParticipantCount,
                       String description, String winners) {
        SeckillActivity activity = new SeckillActivity();
        activity.setTerm(term);
        activity.setCommodityId(commodityId);
        activity.setStartTime(startTime);
        activity.setExpectParticipantCount(expectParticipantCount);
        activity.setDescription(description);
        activity.setWinners(winners);

        activity.setExpire(false);
        return HibernateUtils.save(activity);
    }

    public void update(Integer id, Integer term, Integer commodityId, String startTime,
                       Integer expectParticipantCount, String description, String winners) {
        SeckillActivity activity = getInstance(id);
        activity.setTerm(term);
        activity.setCommodityId(commodityId);
        activity.setStartTime(startTime);
        activity.setExpectParticipantCount(expectParticipantCount);
        activity.setDescription(description);
        activity.setWinners(winners);
        HibernateUtils.update(activity);
    }

    @Override
    public void updateResult(Integer id, String winners, String announcement) {
        if (StringUtils.isNotBlank(winners)) {
            String[] winnerArray = winners.split(",");
            new SeckillLotDao().updateWinnerBySerialNumbers(id, Lists.newArrayList(winnerArray));
        }

        super.updateResult(id, winners, announcement);
    }
}
