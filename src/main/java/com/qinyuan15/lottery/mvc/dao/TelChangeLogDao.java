package com.qinyuan15.lottery.mvc.dao;

import com.qinyuan.lib.database.hibernate.AbstractDao;
import com.qinyuan.lib.database.hibernate.HibernateListBuilder;
import com.qinyuan.lib.database.hibernate.HibernateUtils;
import com.qinyuan.lib.lang.time.DateUtils;

import java.util.Date;

public class TelChangeLogDao extends AbstractDao<TelChangeLog> {
    public Integer add(Integer userId, String oldTel, String newTel) {
        TelChangeLog log = new TelChangeLog();
        log.setUserId(userId);
        log.setOldTel(oldTel);
        log.setNewTel(newTel);
        log.setChangeTime(DateUtils.nowString());
        return HibernateUtils.save(log);
    }

    public int count(Integer userId, Date startTime) {
        return new HibernateListBuilder()
                .addEqualFilter("userId", userId)
                .addFilter("changeTime>=:startTime")
                .addArgument("startTime", DateUtils.toLongString(startTime))
                .count(getPersistClass());
    }

    public int countInOneYear(Integer userId) {
        return count(userId, DateUtils.oneYearAgo());
    }
}
