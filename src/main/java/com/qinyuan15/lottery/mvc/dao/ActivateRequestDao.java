package com.qinyuan15.lottery.mvc.dao;

import com.qinyuan15.utils.DateUtils;
import com.qinyuan15.utils.IntegerUtils;
import com.qinyuan15.utils.hibernate.HibernateListBuilder;
import com.qinyuan15.utils.hibernate.HibernateUtils;
import org.apache.commons.lang3.RandomStringUtils;

/**
 * Dao of ActivateRequest
 * Created by qinyuan on 15-7-1.
 */
public class ActivateRequestDao {
    private final static int SERIAL_KEY_LENGTH = 100;

    public Integer add(Integer userId) {
        ActivateRequest request = new ActivateRequest();
        request.setUserId(userId);

        do {
            request.setSerialKey(RandomStringUtils.randomAlphanumeric(SERIAL_KEY_LENGTH));
        } while (getInstanceBySerialKey(request.getSerialKey()) != null);

        request.setSendTime(DateUtils.nowString());
        return HibernateUtils.save(request);
    }

    public ActivateRequest getInstance(Integer id) {
        return HibernateUtils.get(ActivateRequest.class, id);
    }

    public ActivateRequest getInstanceByUserId(Integer userId) {
        return new HibernateListBuilder().addFilter("userId=:userId").addOrder("id", true)
                .addArgument("userId", userId).getFirstItem(ActivateRequest.class);
    }

    public ActivateRequest getInstanceBySerialKey(String serialKey) {
        return new HibernateListBuilder().addFilter("serialKey=:serialKey")
                .addArgument("serialKey", serialKey).getFirstItem(ActivateRequest.class);
    }

    public void response(Integer id) {
        if (!IntegerUtils.isPositive(id)) {
            return;
        }

        String hql = "UPDATE ActivateRequest SET responseTime='" + DateUtils.nowString() + "' WHERE id=" + id;
        HibernateUtils.executeUpdate(hql);
    }
}
