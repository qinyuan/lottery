package com.qinyuan15.lottery.mvc.dao;

import com.qinyuan15.utils.database.hibernate.AbstractDao;
import com.qinyuan15.utils.database.hibernate.HibernateListBuilder;
import com.qinyuan15.utils.database.hibernate.HibernateUtils;

abstract class AbstractActivityDao<T extends AbstractActivity> extends AbstractDao<T> {
    public abstract AbstractActivityPaginationItemFactory<T> getFactory();

    public boolean hasTerm(Integer term) {
        return new HibernateListBuilder().addEqualFilter("term", term).count(getPersistClass()) > 0;
    }

    public T getActiveInstanceByCommodityId(Integer commodityId) {
        return getFactory().setCommodityId(commodityId).setExpire(false).getFirstInstance();
    }

    public T getLastInstance() {
        return getFactory().getFirstInstance(); // factory order by id desc
    }

    public T getLastActiveInstance() {
        return getFactory().setExpire(false).getFirstInstance();
    }

    public Integer getMaxTerm() {
        return (Integer) new HibernateListBuilder().getFirstItem("SELECT MAX(term) FROM " +
                getPersistClass().getSimpleName());
    }

    public void end(Integer id) {
        AbstractActivity activity = getInstance(id);
        activity.setExpire(true);
        HibernateUtils.update(activity);
    }

    public void updateResult(Integer id, String winners, String announcement) {
        AbstractActivity activity = getInstance(id);
        activity.setWinners(winners);
        activity.setAnnouncement(announcement);
        HibernateUtils.update(activity);
    }

    /**
     * validate if activity is expired
     *
     * @param id id of seckill activity to query
     * @return true if seckill activity can be found and is expired, otherwise false
     */
    public boolean isExpire(Integer id) {
        String hql = "SELECT expire FROM " + getPersistClass().getSimpleName() + " WHERE id=" + id;
        Boolean expire = (Boolean) new HibernateListBuilder().getFirstItem(hql);
        if (expire == null) {
            expire = false;
        }
        return expire;
    }

    public void delete(Integer id) {
        if (isExpire(id)) {
            throw new RuntimeException("Can not delete activity expired");
        } else {
            super.delete(id);
        }
    }
}
