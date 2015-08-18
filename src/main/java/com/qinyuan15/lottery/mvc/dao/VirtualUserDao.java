package com.qinyuan15.lottery.mvc.dao;

import com.qinyuan15.utils.database.hibernate.AbstractDao;
import com.qinyuan15.utils.database.hibernate.HibernateUtils;

public class VirtualUserDao extends AbstractDao<VirtualUser> {
    public Integer add(String username, String telPrefix, String telSuffix, String mailPrefix, String mailSuffix) {
        VirtualUser user = new VirtualUser();
        user.setUsername(username);
        user.setTelPrefix(telPrefix);
        user.setTelSuffix(telSuffix);
        user.setMailPrefix(mailPrefix);
        user.setMailSuffix(mailSuffix);
        return HibernateUtils.save(user);
    }

    public void update(Integer id, String username, String telPrefix, String telSuffix, String mailPrefix,
                       String mailSuffix) {
        VirtualUser user = getInstance(id);

        user.setUsername(username);
        user.setTelPrefix(telPrefix);
        user.setTelSuffix(telSuffix);
        user.setMailPrefix(mailPrefix);
        user.setMailSuffix(mailSuffix);

        HibernateUtils.update(user);
    }
}
