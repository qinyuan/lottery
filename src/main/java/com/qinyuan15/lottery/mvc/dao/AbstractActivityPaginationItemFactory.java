package com.qinyuan15.lottery.mvc.dao;

import com.qinyuan.lib.database.hibernate.HibernateListBuilder;
import com.qinyuan.lib.lang.IntegerUtils;
import com.qinyuan.lib.mvc.controller.AbstractPaginationItemFactory;

public abstract class AbstractActivityPaginationItemFactory<T extends AbstractActivity>
        extends AbstractPaginationItemFactory<T> {
    private Integer commodityId;
    private Boolean expire;

    public AbstractActivityPaginationItemFactory<T> setCommodityId(Integer commodityId) {
        this.commodityId = commodityId;
        return this;
    }

    public AbstractActivityPaginationItemFactory<T> setExpire(Boolean expire) {
        this.expire = expire;
        return this;
    }

    private void addFilters(HibernateListBuilder listBuilder) {
        if (IntegerUtils.isPositive(this.commodityId)) {
            listBuilder.addEqualFilter("commodityId", this.commodityId);
        }
        if (this.expire != null) {
            listBuilder.addEqualFilter("expire", this.expire);
        }
    }

    protected void addOrders(HibernateListBuilder listBuilder) {
        // order by expire desc, start time desc, id desc
        listBuilder.addOrder("expire", false).addOrder("startTime", false).addOrder("id", false);
    }

    @Override
    protected HibernateListBuilder getListBuilder() {
        HibernateListBuilder listBuilder = new HibernateListBuilder();
        addOrders(listBuilder);
        addFilters(listBuilder);
        return listBuilder;
    }
}
