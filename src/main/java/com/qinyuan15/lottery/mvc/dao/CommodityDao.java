package com.qinyuan15.lottery.mvc.dao;

import com.qinyuan.lib.database.hibernate.*;
import com.qinyuan.lib.lang.Cache;
import com.qinyuan.lib.mvc.controller.RankingPaginationItemFactory;
import com.qinyuan15.lottery.mvc.CacheFactory;

import java.util.ArrayList;
import java.util.List;

/**
 * Dao class of commodity
 * Created by qinyuan on 15-6-22.
 */
public class CommodityDao extends AbstractRankingDao<Commodity> {
    private final static Cache CACHE = CacheFactory.getInstance();
    private final static String CACHE_KEY = "commodities";

    public static class Factory extends RankingPaginationItemFactory<Commodity> {
        @Override
        protected HibernateListBuilder getListBuilder() {
            return super.getListBuilder();
        }
    }

    public static Factory factory() {
        return new Factory();
    }

    private void clearCache() {
        CACHE.deleteValue(CACHE_KEY);
    }

    public Integer add(String name, Double price, String snapshot) {
        clearCache();

        Commodity commodity = new Commodity();
        commodity.setPrice(price);
        commodity.setName(name);
        commodity.setSnapshot(snapshot);

        // set default field
        commodity.setVisible(false);
        commodity.setOwn(true);

        return new RankingDao().add(commodity);
    }

    @Override
    public void rankUp(int id) {
        clearCache();
        super.rankUp(id);
    }

    @Override
    public void rankDown(int id) {
        clearCache();
        super.rankDown(id);
    }

    @Override
    public void rankTo(int id, int index) {
        clearCache();
        super.rankTo(id, index);
    }

    public boolean isUsed(Integer id) {
        return new ReferenceValidator().add(LotteryActivity.class, "commodityId").isUsed(id);
    }

    @Override
    public void delete(Integer id) {
        if (!isUsed(id)) {
            clearCache();
            super.delete(id);
        }
    }

    public void updateVisible(Integer id, boolean visible) {
        Commodity commodity = getInstance(id);
        if (commodity != null) {
            clearCache();
            commodity.setVisible(visible);
            HibernateUtils.update(commodity);
        }
    }

    public void update(Integer id, String name, Double price, String snapshot) {
        Commodity commodity = getInstance(id);
        if (commodity != null) {
            clearCache();
            commodity.setPrice(price);
            commodity.setName(name);
            commodity.setSnapshot(snapshot);
            HibernateUtils.update(commodity);
        }
    }

    private List<Commodity> oldGetInstances() {
        return super.getInstances();
    }

    @Override
    public List<Commodity> getInstances() {
        @SuppressWarnings("unchecked")
        List<Commodity> commodities = (List) CACHE.getValue(CACHE_KEY, new Cache.Source() {
            @Override
            public Object getValue() {
                return oldGetInstances();
            }
        });

        List<Commodity> result = new ArrayList<>();
        for (Commodity commodity : commodities) {
            result.add(commodity.copy());
        }

        return result;
    }

    @Override
    public Commodity getFirstInstance() {
        List<Commodity> list = getInstances();
        return list.isEmpty() ? null : list.get(0);
    }

    public Commodity getFirstVisibleInstance() {
        List<Commodity> list = getVisibleInstances();
        return list.isEmpty() ? null : list.get(0);
    }

    public List<Commodity> getVisibleInstances() {
        List<Commodity> result = new ArrayList<>();
        for (Commodity commodity : getInstances()) {
            if (commodity.getVisible()) {
                result.add(commodity);
            }
        }
        return result;
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
