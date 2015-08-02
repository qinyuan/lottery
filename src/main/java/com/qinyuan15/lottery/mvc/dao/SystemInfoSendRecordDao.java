package com.qinyuan15.lottery.mvc.dao;

import com.qinyuan15.utils.hibernate.HibernateListBuilder;
import com.qinyuan15.utils.hibernate.HibernateUtils;

import java.util.List;

public class SystemInfoSendRecordDao {
    public SystemInfoSendRecord getInstance(Integer id) {
        return HibernateUtils.get(SystemInfoSendRecord.class, id);
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
}
