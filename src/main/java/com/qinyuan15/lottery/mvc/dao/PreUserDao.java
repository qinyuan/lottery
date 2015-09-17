package com.qinyuan15.lottery.mvc.dao;

import com.qinyuan.lib.database.hibernate.AbstractDao;
import com.qinyuan.lib.database.hibernate.HibernateListBuilder;
import com.qinyuan.lib.database.hibernate.HibernateUtils;
import org.apache.commons.lang3.StringUtils;

public class PreUserDao extends AbstractDao<PreUser> {
    public Integer add(String email, Integer spreadUserId, String spreadWay, Integer activityId, String serialKey) {
        PreUser user = new PreUser();

        user.setEmail(email);
        user.setSpreadUserId(spreadUserId);
        user.setSpreadWay(spreadWay);
        user.setActivityId(activityId);
        user.setSerialKey(serialKey);

        return HibernateUtils.save(user);
    }

    public PreUser getInstanceBySerialKey(String serialKey) {
        if (StringUtils.isBlank(serialKey)) {
            return null;
        }
        return new HibernateListBuilder().addEqualFilter("serialKey", serialKey).getFirstItem(PreUser.class);
    }

    public boolean hasSerialKey(String serialKey) {
        return StringUtils.isNotBlank(serialKey) &&
                new HibernateListBuilder().addEqualFilter("serialKey", serialKey).count(PreUser.class) > 0;
    }

    public PreUser getInstanceByEmail(String email) {
        return new HibernateListBuilder().addOrder("id", false).addEqualFilter("email", email).getFirstItem(PreUser.class);
    }
}
