package com.qinyuan15.lottery.mvc.dao;

import com.qinyuan15.utils.hibernate.HibernateUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Dao of CommodityMap
 * Created by qinyuan on 15-6-19.
 *//*
public class CommodityMapDao {
    public Integer add(Integer commodityId, Integer xStart, Integer yStart, Integer xEnd, Integer yEnd, String href, String comment) {
        CommodityMap map = new CommodityMap();
        map.setCommodityId(commodityId);
        map.setxStart(xStart);
        map.setyStart(yStart);
        map.setxEnd(xEnd);
        map.setyEnd(yEnd);
        map.setHref(href);
        map.setComment(comment);
        return HibernateUtils.save(map);
    }

    public CommodityMap getInstance(Integer id) {
        return HibernateUtils.get(CommodityMap.class, id);
    }

    public void update(Integer id, String href, String comment) {
        CommodityMap map = getInstance(id);
        if (map != null) {
            map.setHref(href);
            map.setComment(comment);
            HibernateUtils.update(map);
        }
    }

    public void delete(Integer id) {
        HibernateUtils.delete(CommodityMap.class, id);
    }

    public void deleteByCommodityId(Integer commodityId) {
        HibernateUtils.delete(CommodityMap.class, "commodityId=" + commodityId);
    }

    public List<CommodityMap> getInstancesByCommodityId(Integer commodityId) {
        return HibernateUtils.getList(CommodityMap.class, "commodityId=" + commodityId + " ORDER BY id ASC");
    }

    public List<CommodityMap> getInstances() {
        return HibernateUtils.getList(CommodityMap.class, "ORDER BY commodityId ASC");
    }

    public Map<Integer, List<CommodityMap>> getInstancesAndGroupByCommodityId() {
        Map<Integer, List<CommodityMap>> map = new HashMap<>();
        for (CommodityMap CommodityMap : getInstances()) {
            Integer commodityId = CommodityMap.getCommodityId();
            if (!map.containsKey(commodityId)) {
                map.put(commodityId, new ArrayList<CommodityMap>());
            }
            map.get(commodityId).add(CommodityMap);
        }
        return map;
    }
}*/
