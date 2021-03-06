package com.qinyuan15.lottery.mvc.dao;

import com.qinyuan.lib.database.hibernate.AbstractDao;
import com.qinyuan.lib.database.hibernate.HibernateListBuilder;
import com.qinyuan.lib.database.hibernate.HibernateUtils;
import com.qinyuan.lib.lang.Cache;
import com.qinyuan.lib.lang.IntegerUtils;
import com.qinyuan15.lottery.mvc.CacheFactory;

import java.util.ArrayList;
import java.util.List;

/**
 * Dao of IndexImage
 * Created by qinyuan on 15-6-18.
 */
public class IndexImageDao extends AbstractDao<IndexImage> {
    private final static Cache CACHE = CacheFactory.getInstance();
    private final static String CACHE_KEY = "indexImages";

    @SuppressWarnings("unchecked")
    @Override
    public List<IndexImage> getInstances() {
        List<IndexImage> list = (List) CACHE.getValue(CACHE_KEY, new Cache.Source() {
            @Override
            public Object getValue() {
                return new HibernateListBuilder().addOrder("rowIndex", true).addOrder("id", true)
                        .build(IndexImage.class);
            }
        });

        List<IndexImage> copyList = new ArrayList<>();
        for (IndexImage image : list) {
            copyList.add(image.copy());
        }

        return copyList;
    }

    public synchronized Integer add(String path, String backPath) {
        Integer maxRowIndex = (Integer) new HibernateListBuilder()
                .getFirstItem("SELECT MAX(rowIndex) FROM IndexImage");
        if (!IntegerUtils.isPositive(maxRowIndex)) {
            maxRowIndex = 1;
        } else {
            maxRowIndex++;
        }

        return add(path, backPath, maxRowIndex);
    }

    public Integer add(String path, String backPath, int rowIndex) {
        clearCache();
        IndexImage indexImage = new IndexImage();
        indexImage.setPath(path);
        indexImage.setBackPath(backPath);
        indexImage.setRowIndex(rowIndex);
        return HibernateUtils.save(indexImage);
    }

    public void update(int id, String path, String backPath) {
        clearCache();
        IndexImage indexImage = getInstance(id);
        if (indexImage != null) {
            indexImage.setPath(path);
            indexImage.setBackPath(backPath);
            HibernateUtils.update(indexImage);
        }
    }

    protected void clearCache() {
        CACHE.deleteValue(CACHE_KEY);
    }
}
