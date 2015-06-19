package com.qinyuan15.lottery.mvc.dao;

import com.qinyuan15.utils.hibernate.HibernateUtils;

/**
 * Dao of IndexImageMap
 * Created by qinyuan on 15-6-19.
 */
public class IndexImageMapDao {
    public Integer add(Integer imageId, Integer xStart, Integer yStart, Integer xEnd, Integer yEnd, String href, String comment) {
        IndexImageMap map = new IndexImageMap();
        map.setImageId(imageId);
        map.setxStart(xStart);
        map.setyStart(yStart);
        map.setxEnd(xEnd);
        map.setyEnd(yEnd);
        map.setHref(href);
        map.setComment(comment);
        return HibernateUtils.save(map);
    }

    public void deleteByImageId(Integer imageId) {
        HibernateUtils.delete(IndexImageMap.class, "imageId=" + imageId);
    }
}
