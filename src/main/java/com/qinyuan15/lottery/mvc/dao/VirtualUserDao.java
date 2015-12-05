package com.qinyuan15.lottery.mvc.dao;

import com.qinyuan.lib.database.hibernate.*;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Random;

public class VirtualUserDao extends AbstractDao<VirtualUser> {

    public int countActive() {
        return new HibernateListBuilder().addEqualFilter("active", true).count(getPersistClass());
    }

    public void activate(List<VirtualUser> virtualUsers) {
        String ids = PersistObjectUtils.getIdsString(virtualUsers);
        new HibernateUpdater().addFilter("id IN (" + ids + ")").update(getPersistClass(), "active=true");
    }

    public void activate(VirtualUser virtualUser) {
        virtualUser.setActive(true);
        HibernateUtils.update(virtualUser);
    }

    public Integer add(String username) {
        String telPrefix = new Random().nextBoolean() ? "15" : "13";
        String telSuffix = RandomStringUtils.randomNumeric(4);
        String mailPrefix = RandomStringUtils.randomAlphabetic(2).toLowerCase();
        String mailSuffix = "@qq.com";
        return add(username, telPrefix, telSuffix, mailPrefix, mailSuffix);
    }

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
