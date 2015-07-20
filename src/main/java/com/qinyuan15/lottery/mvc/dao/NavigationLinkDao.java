package com.qinyuan15.lottery.mvc.dao;

import com.qinyuan15.utils.hibernate.HibernateDeleter;
import com.qinyuan15.utils.hibernate.HibernateListBuilder;
import com.qinyuan15.utils.hibernate.HibernateUtils;

import java.util.List;

/**
 * Dao of NavigationLink
 * Created by qinyuan on 15-6-17.
 */
public class NavigationLinkDao {

    public List<NavigationLink> getInstances() {
        return new HibernateListBuilder().build(NavigationLink.class);
    }

    public void clearAndSave(List<NavigationLink> links) {
        HibernateDeleter.deleteAll(NavigationLink.class);
        HibernateUtils.batchSave(links);
    }
}
