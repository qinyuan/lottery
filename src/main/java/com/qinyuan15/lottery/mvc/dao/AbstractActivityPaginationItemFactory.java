package com.qinyuan15.lottery.mvc.dao;

import com.qinyuan.lib.database.hibernate.HibernateListBuilder;
import com.qinyuan.lib.lang.IntegerUtils;
import com.qinyuan.lib.mvc.controller.AbstractPaginationItemFactory;

public abstract class AbstractActivityPaginationItemFactory<T extends AbstractActivity>
        extends AbstractPaginationItemFactory<T> {
    private Integer commodityId;
    private Boolean expire;
    private Integer userId;

    public AbstractActivityPaginationItemFactory<T> setCommodityId(Integer commodityId) {
        this.commodityId = commodityId;
        return this;
    }

    public AbstractActivityPaginationItemFactory<T> setExpire(Boolean expire) {
        this.expire = expire;
        return this;
    }

    public AbstractActivityPaginationItemFactory<T> setUserId(Integer userId) {
        this.userId = userId;
        return this;
    }

    private void addFilters(HibernateListBuilder listBuilder) {
        if (IntegerUtils.isPositive(this.commodityId)) {
            listBuilder.addEqualFilter("commodityId", this.commodityId);
        }
        if (this.expire != null) {
            listBuilder.addEqualFilter("expire", this.expire);
        }
        if (this.userId != null) {
            listBuilder.addFilter("id IN (SELECT activityId FROM " + getLotClass().getSimpleName()
                    + " WHERE userId=" + userId + ")");
        }
    }

    protected void addOrders(HibernateListBuilder listBuilder) {
        // order by expire desc, start time desc, id desc
        listBuilder.addOrder("expire", true).addOrder("startTime", false).addOrder("id", false);
    }

    @Override
    protected HibernateListBuilder getListBuilder() {
        HibernateListBuilder listBuilder = new HibernateListBuilder();
        addOrders(listBuilder);
        addFilters(listBuilder);
        return listBuilder;
    }

    abstract protected Class<? extends AbstractLot> getLotClass();
}
