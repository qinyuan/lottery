package com.qinyuan15.lottery.mvc.dao;

import com.qinyuan.lib.database.hibernate.AbstractDao;
import com.qinyuan.lib.database.hibernate.HibernateDeleter;
import com.qinyuan.lib.database.hibernate.HibernateListBuilder;
import com.qinyuan.lib.database.hibernate.HibernateUtils;
import com.qinyuan.lib.lang.Cache;

import java.util.List;

/**
 * Dao of NavigationLink
 * Created by qinyuan on 15-6-17.
 */
public class NavigationLinkDao extends AbstractDao<NavigationLink> {

    private final static Cache cache = new Cache();
    private final static String NAVIGATION_LINK_CACHE_KEY = "navigationLink";

    @SuppressWarnings("unchecked")
    public List<NavigationLink> getInstances() {
        return (List) cache.getValue(NAVIGATION_LINK_CACHE_KEY, new Cache.Source() {
            @Override
            public Object getValue() {
                return new HibernateListBuilder().build(NavigationLink.class);
            }
        });
    }

    public void clearAndSave(List<NavigationLink> links) {
        cache.deleteValue(NAVIGATION_LINK_CACHE_KEY);
        HibernateDeleter.deleteAll(NavigationLink.class);
        HibernateUtils.batchSave(links);
    }
}
