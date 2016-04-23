package com.qinyuan15.lottery.mvc.dao;

import com.qinyuan.lib.database.hibernate.AbstractRankingDao;
import com.qinyuan.lib.database.hibernate.HibernateUtils;
import com.qinyuan.lib.database.hibernate.RankingDao;
import com.qinyuan.lib.lang.Cache;
import com.qinyuan15.lottery.mvc.CacheFactory;

import java.util.ArrayList;
import java.util.List;

public class SubIndexImageDao extends AbstractRankingDao<SubIndexImage> {
    private final static Cache CACHE = CacheFactory.getInstance();
    private final static String CACHE_KEY = "subIndexImages";
    private final static String PAGE_INDEX = "pageIndex";

    protected void clearCache() {
        CACHE.deleteValue(CACHE_KEY);
    }

    public Integer add(Integer pageIndex, String path, String backPath) {
        clearCache();

        SubIndexImage image = new SubIndexImage();
        image.setPageIndex(pageIndex);
        image.setPath(path);
        image.setBackPath(backPath);

        return new RankingDao().add(image);
    }

    public void update(Integer id, String path, String backPath) {
        SubIndexImage image = getInstance(id);
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

    @SuppressWarnings("unchecked")
    @Override
    public List<SubIndexImage> getInstances() {
        List<SubIndexImage> list = (List) CACHE.getValue(CACHE_KEY, new Cache.Source() {
            @Override
            public Object getValue() {
                return oldGetInstances();
            }
        });

        List<SubIndexImage> copyList = new ArrayList<>();
        for (SubIndexImage image : list) {
            copyList.add(image.copy());
        }

        return copyList;
    }

    private List<SubIndexImage> oldGetInstances() {
        return super.getInstances();
    }

    @Override
    public int count() {
        return getInstances().size();
    }

    @Override
    public void rankUp(int id) {
        clearCache();
        new RankingDao().rankUp(getPersistClass(), id, PAGE_INDEX);
    }

    @Override
    public void rankDown(int id) {
        clearCache();
        new RankingDao().rankDown(getPersistClass(), id, PAGE_INDEX);
    }
}
