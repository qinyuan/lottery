package com.qinyuan15.lottery.mvc.dao;

import com.qinyuan15.utils.hibernate.HibernateListBuilder;
import com.qinyuan15.utils.hibernate.HibernateUtils;

import java.util.List;

public class SystemInfoDao {
    public SystemInfo getInstance(Integer id) {
        return HibernateUtils.get(SystemInfo.class, id);
    }

    public List<SystemInfo> getInstances() {
        return new HibernateListBuilder().build(SystemInfo.class);
    }
}
