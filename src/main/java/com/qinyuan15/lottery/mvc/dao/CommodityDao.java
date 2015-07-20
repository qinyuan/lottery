package com.qinyuan15.lottery.mvc.dao;

import com.qinyuan15.utils.hibernate.HibernateDeleter;
import com.qinyuan15.utils.hibernate.HibernateListBuilder;
import com.qinyuan15.utils.hibernate.HibernateUtils;
import com.qinyuan15.utils.mvc.PaginationItemFactory;

import java.util.List;

/**
 * Dao class of commodity
 * Created by qinyuan on 15-6-22.
 */
public class CommodityDao {

    public static class Factory implements PaginationItemFactory<Commodity> {
        @Override
        public long getCount() {
            return new HibernateListBuilder().count(Commodity.class);
        }

        @Override
        public List<Commodity> getInstances(int firstResult, int maxResults) {
            return new HibernateListBuilder().limit(firstResult, maxResults).build(Commodity.class);
        }
    }

    public static Factory factory() {
        return new Factory();
    }

    public Integer add(String name, Double price, Boolean own, String snapshot, String detailImage) {
        Commodity commodity = new Commodity();
        commodity.setPrice(price);
        commodity.setName(name);
        commodity.setOwn(own);
        commodity.setSnapshot(snapshot);
        commodity.setDetailImage(detailImage);

        // set default field
        commodity.setInLottery(false);

        return HibernateUtils.save(commodity);
    }

    public void delete(Integer id) {
        HibernateDeleter.deleteById(Commodity.class, id);
    }

    public void update(Integer id, String name, Double price, Boolean own, String snapshot, String detailImage) {
        Commodity commodity = getInstance(id);
        commodity.setPrice(price);
        commodity.setName(name);
        commodity.setOwn(own);
        commodity.setSnapshot(snapshot);
        commodity.setDetailImage(detailImage);
        HibernateUtils.update(commodity);
    }

    public Commodity getInstance(Integer id) {
        return HibernateUtils.get(Commodity.class, id);
    }

    public Commodity getFirstInstance() {
        return new HibernateListBuilder().getFirstItem(Commodity.class);
    }

    public List<Commodity> getInstances() {
        return new HibernateListBuilder().build(Commodity.class);
    }

    private void changeLottery(Integer commodityId, Boolean inLottery) {
        Commodity commodity = getInstance(commodityId);
        commodity.setInLottery(inLottery);
        HibernateUtils.update(commodity);
    }

    public void startLottery(Integer commodityId) {
        changeLottery(commodityId, true);
    }

    public void endLottery(Integer commodityId) {
        changeLottery(commodityId, false);
    }
}
