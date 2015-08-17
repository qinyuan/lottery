package com.qinyuan15.lottery.mvc.dao;

import com.qinyuan15.utils.DateUtils;
import com.qinyuan15.utils.database.hibernate.HibernateListBuilder;
import com.qinyuan15.utils.database.hibernate.HibernateUtils;

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
