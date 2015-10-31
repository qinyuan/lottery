package com.qinyuan15.lottery.mvc.dao;

import com.qinyuan.lib.database.hibernate.*;
import com.qinyuan.lib.mvc.controller.RankingPaginationItemFactory;

import java.util.List;

/**
 * Dao class of commodity
 * Created by qinyuan on 15-6-22.
 */
public class CommodityDao extends AbstractRankingDao<Commodity> {

    public static class Factory extends RankingPaginationItemFactory<Commodity> {
        @Override
        protected HibernateListBuilder getListBuilder() {
            return super.getListBuilder();
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
        commodity.setVisible(false);

        return new RankingDao().add(commodity);
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

    /**
     * validate if certain commodity has lottery activity
     *
     * @param commodityId id of commodity
     * @return true if this commodity has lottery activity
     */
    public boolean hasLottery(Integer commodityId) {
        return LotteryActivityDao.factory().setCommodityId(commodityId).getCount() > 0;
    }

    /**
     * validate if certain commodity has active lottery activity
     *
     * @param commodityId id of commodity
     * @return true if this commodity has active lottery activity
     */
    public boolean hasActiveLottery(Integer commodityId) {
        return LotteryActivityDao.factory().setCommodityId(commodityId).setExpire(false).getCount() > 0;
    }

    /**
     * validate if certain commodity has active and unClosed lottery activity
     *
     * @param commodityId id of commodity
     * @return true if this commodity has active lottery activity
     */
    public boolean hasActiveUnCloseLottery(Integer commodityId) {
        return LotteryActivityDao.factory().setClosed(false).setCommodityId(commodityId).setExpire(false).getCount() > 0;
    }

    /**
     * validate if certain commodity has seckill activity
     *
     * @param commodityId id of commodity
     * @return true if this commodity has seckill activity
     */
    public boolean hasSeckill(Integer commodityId) {
        return SeckillActivityDao.factory().setCommodityId(commodityId).getCount() > 0;
    }

    /**
     * validate if certain commodity has active seckill activity
     *
     * @param commodityId id of commodity
     * @return true if this commodity has active seckill activity
     */
    public boolean hasActiveSeckill(Integer commodityId) {
        return SeckillActivityDao.factory().setCommodityId(commodityId).setExpire(false).getCount() > 0;
    }
}
