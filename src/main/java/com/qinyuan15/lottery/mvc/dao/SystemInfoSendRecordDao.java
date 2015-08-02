package com.qinyuan15.lottery.mvc.dao;

import com.qinyuan15.utils.IntegerUtils;
import com.qinyuan15.utils.hibernate.HibernateListBuilder;
import com.qinyuan15.utils.hibernate.HibernateUtils;

import java.util.List;

public class SystemInfoSendRecordDao {
    public SystemInfoSendRecord getInstance(Integer id) {
        return HibernateUtils.get(SystemInfoSendRecord.class, id);
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

    public Integer add(Integer userId, Integer infoId) {
        SystemInfoSendRecord record = new SystemInfoSendRecord();
        record.setUserId(userId);
        record.setInfoId(infoId);
        record.setUnread(true);
        return HibernateUtils.save(record);
    }

    public void read(Integer id) {
        SystemInfoSendRecord record = getInstance(id);
        record.setUnread(false);
        HibernateUtils.update(record);
    }

    public List<SystemInfoSendRecord> getInstancesByUserId(Integer userId) {
        return new HibernateListBuilder().addEqualFilter("userId", userId).build(SystemInfoSendRecord.class);
    }

    public static class Factory {
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

        public List<SystemInfoSendRecord> getInstances() {
            HibernateListBuilder listBuilder = new HibernateListBuilder();
            if (IntegerUtils.isPositive(userId)) {
                listBuilder.addEqualFilter("userId", userId);
            }

            if (unread != null) {
                listBuilder.addEqualFilter("unread", unread);
            }

            return listBuilder.build(SystemInfoSendRecord.class);
        }
    }
}
