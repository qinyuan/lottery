package com.qinyuan15.lottery.mvc.dao;

import com.qinyuan.lib.database.hibernate.AbstractRankingDao;
import com.qinyuan.lib.database.hibernate.HibernateListBuilder;
import com.qinyuan.lib.database.hibernate.HibernateUtils;
import com.qinyuan.lib.database.hibernate.RankingDao;
import com.qinyuan.lib.lang.IntegerUtils;

import java.util.ArrayList;
import java.util.List;

public class CommodityImageDao extends AbstractRankingDao<CommodityImage> {
    public Integer add(Integer commodityId, String path, String backPath) {
        CommodityImage image = new CommodityImage();
        image.setCommodityId(commodityId);
        image.setPath(path);
        image.setBackPath(backPath);

        return new RankingDao().add(image);
    }

    public void update(Integer id, Integer commodityId, String path, String backPath) {
        CommodityImage image = getInstance(id);
        if (image != null) {
            image.setCommodityId(commodityId);
            image.setPath(path);
            image.setBackPath(backPath);
            HibernateUtils.update(image);
        }
    }

    public List<CommodityImage> getInstancesByCommodityId(Integer commodityId) {
        if (!IntegerUtils.isPositive(commodityId)) {
            return new ArrayList<>();
        }

        return new HibernateListBuilder().addOrder("ranking", true).addEqualFilter("commodityId", commodityId)
                .build(CommodityImage.class);
    }
}
