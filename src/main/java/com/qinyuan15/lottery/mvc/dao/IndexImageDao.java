package com.qinyuan15.lottery.mvc.dao;

import com.qinyuan15.utils.IntegerUtils;
import com.qinyuan15.utils.hibernate.HibernateUtils;

import java.util.List;

/**
 * Dao of IndexImage
 * Created by qinyuan on 15-6-18.
 */
public class IndexImageDao {

    public List<IndexImage> getInstances() {
        return HibernateUtils.getList(IndexImage.class, "ORDER BY rowIndex ASC,id ASC");
    }

    public IndexImage getInstance(Integer id) {
        return HibernateUtils.get(IndexImage.class, id);
    }

    public synchronized Integer add(String path, String backPath) {
        Integer maxRowIndex = (Integer) HibernateUtils.getFirstItem("SELECT MAX(rowIndex) FROM IndexImage");
        if (!IntegerUtils.isPositive(maxRowIndex)) {
            maxRowIndex = 1;
        } else {
            maxRowIndex++;
        }

        return add(path, backPath, maxRowIndex);
    }

    public Integer add(String path, String backPath, int rowIndex) {
        IndexImage indexImage = new IndexImage();
        indexImage.setPath(path);
        indexImage.setBackPath(backPath);
        indexImage.setRowIndex(rowIndex);
        return HibernateUtils.save(indexImage);
    }

    public void update(int id, String path, String backPath) {
        IndexImage indexImage = getInstance(id);
        if (indexImage != null) {
            indexImage.setPath(path);
            indexImage.setBackPath(backPath);
            HibernateUtils.update(indexImage);
        }
    }

    public void delete(Integer id) {
        HibernateUtils.delete(IndexImage.class, id);
    }
}
