package com.qinyuan15.lottery.mvc.dao;

import com.qinyuan.lib.database.hibernate.AbstractRankingDao;
import com.qinyuan.lib.database.hibernate.HibernateUtils;
import com.qinyuan.lib.database.hibernate.RankingDao;
import com.qinyuan.lib.lang.Cache;
import com.qinyuan.lib.lang.IntegerUtils;
import com.qinyuan15.lottery.mvc.CacheFactory;

import java.util.ArrayList;
import java.util.List;

public class CommodityImageDao extends AbstractRankingDao<CommodityImage> {
    private final static Cache CACHE = CacheFactory.getInstance();
    private final static String CACHE_KEY = "commodityImages";
    private final static String COMMODITY_ID = "commodityId";

    protected void clearCache() {
        CACHE.deleteValue(CACHE_KEY);
    }

    public Integer add(Integer commodityId, String path, String backPath) {
        clearCache();

        CommodityImage image = new CommodityImage();
        image.setCommodityId(commodityId);
        image.setPath(path);
        image.setBackPath(backPath);

        return new RankingDao().add(image);
    }

    public void update(Integer id, String path, String backPath) {
        CommodityImage image = getInstance(id);
        if (image != null) {
            clearCache();
            image.setPath(path);
            image.setBackPath(backPath);
            HibernateUtils.update(image);
        }
    }

    @Override
    public void delete(Integer id) {
        clearCache();
        super.delete(id);
    }

    @Override
    public List<CommodityImage> getInstances() {
        @SuppressWarnings("unchecked")
        List<CommodityImage> images = (List) CACHE.getValue(CACHE_KEY, new Cache.Source() {
            @Override
            public Object getValue() {
                return oldGetInstances();
            }
        });

        List<CommodityImage> result = new ArrayList<>();
        for (CommodityImage image : images) {
            result.add(image.copy());
        }
        return result;
    }

    private List<CommodityImage> oldGetInstances() {
        return super.getInstances();
    }

    public List<CommodityImage> getInstancesByCommodityId(Integer commodityId) {
        List<CommodityImage> result = new ArrayList<>();
        if (!IntegerUtils.isPositive(commodityId)) {
            return result;
        }

        for (CommodityImage image : getInstances()) {
            if (commodityId.equals(image.getCommodityId())) {
                result.add(image.copy());
            }
        }
        return result;
    }

    @Override
    public CommodityImage getFirstInstance() {
        List<CommodityImage> images = getInstances();
        return images.isEmpty() ? null : images.get(0);
    }

    @Override
    public void rankTo(int id, int index) {
        clearCache();
        super.rankTo(id, index);
    }

    @Override
    public CommodityImage getInstance(Integer id) {
        if (!IntegerUtils.isPositive(id)) {
            return null;
        }

        for (CommodityImage image : getInstances()) {
            if (id.equals(image.getId())) {
                return image;
            }
        }
        return null;
    }

    @Override
    public int count() {
        return getInstances().size();
    }

    @Override
    public void rankUp(int id) {
        clearCache();
        new RankingDao().rankUp(getPersistClass(), id, COMMODITY_ID);
    }

    @Override
    public void rankDown(int id) {
        clearCache();
        new RankingDao().rankDown(getPersistClass(), id, COMMODITY_ID);
    }
}
