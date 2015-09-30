package com.qinyuan15.lottery.mvc.dao;

import com.qinyuan.lib.database.hibernate.AbstractDao;
import com.qinyuan.lib.database.hibernate.HibernateUtils;
import com.qinyuan.lib.lang.time.DateUtils;

public class SystemInfoDao extends AbstractDao<SystemInfo> {
    public Integer add(String content) {
        SystemInfo info = new SystemInfo();
        info.setBuildTime(DateUtils.nowString());
        info.setContent(content);
        return HibernateUtils.save(info);
    }
}
