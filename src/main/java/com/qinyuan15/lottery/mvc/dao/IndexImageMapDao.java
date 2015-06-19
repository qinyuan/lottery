package com.qinyuan15.lottery.mvc.dao;

import com.qinyuan15.utils.hibernate.HibernateUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    public IndexImageMap getInstance(Integer id) {
        return HibernateUtils.get(IndexImageMap.class, id);
    }

    public void update(Integer id, String href, String comment) {
        IndexImageMap map = getInstance(id);
        if (map != null) {
            map.setHref(href);
            map.setComment(comment);
            HibernateUtils.update(map);
        }
    }

    public void delete(Integer id) {
        HibernateUtils.delete(IndexImageMap.class, id);
    }

    public void deleteByImageId(Integer imageId) {
        HibernateUtils.delete(IndexImageMap.class, "imageId=" + imageId);
    }

    public List<IndexImageMap> getInstancesByImageId(Integer imageId) {
        return HibernateUtils.getList(IndexImageMap.class, "imageId=" + imageId + " ORDER BY id ASC");
    }

    public List<IndexImageMap> getInstances() {
        return HibernateUtils.getList(IndexImageMap.class, "ORDER BY imageId ASC");
    }

    public Map<Integer, List<IndexImageMap>> getInstancesAndGroupByImageId() {
        Map<Integer, List<IndexImageMap>> map = new HashMap<>();
        for (IndexImageMap indexImageMap : getInstances()) {
            Integer imageId = indexImageMap.getImageId();
            if (!map.containsKey(imageId)) {
                map.put(imageId, new ArrayList<IndexImageMap>());
            }
            map.get(imageId).add(indexImageMap);
        }
        return map;
    }
}
