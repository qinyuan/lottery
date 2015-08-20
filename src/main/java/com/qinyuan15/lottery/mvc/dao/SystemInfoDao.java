package com.qinyuan15.lottery.mvc.dao;

import com.qinyuan.lib.database.hibernate.HibernateListBuilder;
import com.qinyuan.lib.database.hibernate.HibernateUtils;
import com.qinyuan.lib.lang.DateUtils;

import java.util.List;

public class SystemInfoDao {
    public SystemInfo getInstance(Integer id) {
        return HibernateUtils.get(SystemInfo.class, id);
    }

    public List<SystemInfo> getInstances() {
        return new HibernateListBuilder().build(SystemInfo.class);
    }

    public Integer add(String content) {
        SystemInfo info = new SystemInfo();
        info.setBuildTime(DateUtils.nowString());
        info.setContent(content);
        return HibernateUtils.save(info);
    }
}
