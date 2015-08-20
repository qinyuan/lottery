package com.qinyuan15.lottery.mvc.dao;

import com.qinyuan.lib.database.hibernate.AbstractDao;
import com.qinyuan.lib.database.hibernate.HibernateListBuilder;
import com.qinyuan.lib.database.hibernate.HibernateUtils;
import org.springframework.util.StringUtils;

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

    public VirtualUser getInstanceByUsername(String username) {
        if (!StringUtils.hasText(username)) {
            return null;
        }

        return new HibernateListBuilder()
                .addFilter("username=:username").addArgument("username", username)
                .getFirstItem(VirtualUser.class);
    }

    public boolean hasUsername(String username) {
        return getInstanceByUsername(username) != null;
    }
}
