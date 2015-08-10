package com.qinyuan15.lottery.mvc.dao;

import com.qinyuan15.utils.hibernate.AbstractRankingDao;
import com.qinyuan15.utils.hibernate.HibernateListBuilder;
import com.qinyuan15.utils.hibernate.HibernateUtils;
import com.qinyuan15.utils.hibernate.ReferenceValidator;
import com.qinyuan15.utils.mvc.controller.PaginationItemFactory;

import java.util.List;

/**
 * Dao class of commodity
 * Created by qinyuan on 15-6-22.
 */
public class CommodityDao extends AbstractRankingDao<Commodity> {

    public static class Factory implements PaginationItemFactory<Commodity> {
        @Override
        public long getCount() {
            return new HibernateListBuilder().count(Commodity.class);
        }

        @Override
        public List<Commodity> getInstances(int firstResult, int maxResults) {
            return new CommodityDao().getInstances(firstResult, maxResults);
        }
    }

    public static Factory factory() {
        return new Factory();
    }

    public Integer add(String name, Double price, Boolean own, String snapshot, String detailImage,
                       String backImage) {
        Commodity commodity = new Commodity();
        commodity.setPrice(price);
        commodity.setName(name);
        commodity.setOwn(own);
        commodity.setSnapshot(snapshot);
        commodity.setDetailImage(detailImage);
        commodity.setBackImage(backImage);

        // set default field
        commodity.setInLottery(false);
        commodity.setVisible(true);

        return HibernateUtils.save(commodity);
    }

    public boolean isUsed(Integer id) {
        return new ReferenceValidator().add(LotteryActivity.class, "commodityId").isUsed(id);
    }

    @Override
    public void delete(Integer id) {
        if (!isUsed(id)) {
            super.delete(id);
        }
    }

    public void updateVisible(Integer id, boolean visible) {
        Commodity commodity = getInstance(id);
        if (commodity != null) {
            commodity.setVisible(visible);
            HibernateUtils.update(commodity);
        }
    }

    public void update(Integer id, String name, Double price, Boolean own, String snapshot,
                       String detailImage, String backImage) {
        Commodity commodity = getInstance(id);
        if (commodity != null) {
            commodity.setPrice(price);
            commodity.setName(name);
            commodity.setOwn(own);
            commodity.setSnapshot(snapshot);
            commodity.setDetailImage(detailImage);
            commodity.setBackImage(backImage);
            HibernateUtils.update(commodity);
        }
    }

    public Commodity getFirstVisibleInstance() {
        return getVisibleListBuilder().getFirstItem(Commodity.class);
    }

    public List<Commodity> getVisibleInstances() {
        return getVisibleListBuilder().build(Commodity.class);
    }

    private HibernateListBuilder getVisibleListBuilder() {
        return new HibernateListBuilder().addEqualFilter("visible", true).addOrder("ranking", true);
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
