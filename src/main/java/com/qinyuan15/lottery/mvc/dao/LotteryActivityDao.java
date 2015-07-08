package com.qinyuan15.lottery.mvc.dao;

import com.qinyuan15.utils.DateUtils;
import com.qinyuan15.utils.hibernate.HibernateListBuilder;
import com.qinyuan15.utils.hibernate.HibernateUtils;

import java.util.List;

public class LotteryActivityDao {
    public LotteryActivity getInstance(Integer id) {
        return HibernateUtils.get(LotteryActivity.class, id);
    }

    public List<LotteryActivity> getRunningInstances() {
        return new HibernateListBuilder().addFilter("endTime IS NULL").build(LotteryActivity.class);
    }

    public List<LotteryActivity> getExpireInstances() {
        return new HibernateListBuilder().addFilter("endTime IS NOT NULL").build(LotteryActivity.class);
    }

    public Integer add(Integer commodityId, String startTime, String expectEndTime) {
        LotteryActivity activity = new LotteryActivity();
        activity.setCommodityId(commodityId);
        activity.setStartTime(startTime);
        activity.setExpectEndTime(expectEndTime);
        return HibernateUtils.save(activity);
    }

    public void update(Integer id, Integer commodityId, String startTime, String expectEndTime) {
        LotteryActivity activity = getInstance(id);
        activity.setCommodityId(commodityId);
        activity.setStartTime(startTime);
        activity.setExpectEndTime(expectEndTime);
        HibernateUtils.update(activity);
    }

    public void end(Integer id) {
        LotteryActivity activity = getInstance(id);
        activity.setEndTime(DateUtils.nowString());
        HibernateUtils.update(activity);
    }

    public void updateAnnouncement(Integer id, String announcement) {
        LotteryActivity activity = getInstance(id);
        activity.setAnnouncement(announcement);
        HibernateUtils.update(activity);
    }
}
