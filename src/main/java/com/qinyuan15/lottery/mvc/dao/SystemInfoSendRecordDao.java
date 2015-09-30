package com.qinyuan15.lottery.mvc.dao;

import com.google.common.base.Joiner;
import com.google.common.collect.Lists;
import com.qinyuan.lib.database.hibernate.AbstractDao;
import com.qinyuan.lib.database.hibernate.HibernateListBuilder;
import com.qinyuan.lib.database.hibernate.HibernateUpdater;
import com.qinyuan.lib.database.hibernate.HibernateUtils;
import com.qinyuan.lib.lang.IntegerUtils;
import com.qinyuan.lib.mvc.controller.AbstractPaginationItemFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class SystemInfoSendRecordDao extends AbstractDao<SystemInfoSendRecord> {
    private final static Logger LOGGER = LoggerFactory.getLogger(SystemInfoSendRecord.class);

    public void add(Integer userId, String infoContent) {
        add(Lists.newArrayList(userId), infoContent);
    }

    public void add(List<Integer> userIds, String infoContent) {
        if (userIds == null || userIds.size() == 0) {
            return;
        }
        Integer infoId = new SystemInfoDao().add(infoContent);
        for (Integer userId : userIds) {
            add(userId, infoId);
        }
    }

    private Integer add(Integer userId, Integer infoId) {
        SystemInfoSendRecord record = new SystemInfoSendRecord();
        record.setUserId(userId);
        record.setInfoId(infoId);
        record.setUnread(true);
        return HibernateUtils.save(record);
    }

    public void read(Integer userId, Integer id) {
        if (!IntegerUtils.isPositive(userId) || !IntegerUtils.isPositive(id)) {
            return;
        }

        SystemInfoSendRecord record = getInstance(id);
        if (!userId.equals(record.getUserId())) {
            LOGGER.warn("user {} try to read system info of user {}", userId, record.getUserId());
            return;
        }
        record.setUnread(false);
        HibernateUtils.update(record);
    }

    public void read(Integer userId, List<Integer> ids) {
        if (!IntegerUtils.isPositive(userId) || ids == null || ids.size() < 0) {
            return;
        }

        String filer = " id in (" + Joiner.on(",").join(ids) + ")";
        new HibernateUpdater().addEqualFilter("userId", userId).addFilter(filer)
                .update(getPersistClass(), "unread=false");
    }

    public static Factory factory() {
        return new Factory();
    }

    public static class Factory extends AbstractPaginationItemFactory<SystemInfoSendRecord> {
        private Integer userId;
        private Boolean unread;

        public Factory setUserId(Integer userId) {
            this.userId = userId;
            return this;
        }

        public Factory setUnread(Boolean unread) {
            this.unread = unread;
            return this;
        }

        @Override
        protected HibernateListBuilder getListBuilder() {
            HibernateListBuilder listBuilder = super.getListBuilder().addOrder("id", false);
            if (IntegerUtils.isPositive(userId)) {
                listBuilder.addEqualFilter("userId", userId);
            }
            if (unread != null) {
                listBuilder.addEqualFilter("unread", unread);
            }
            return listBuilder;
        }
    }
}
