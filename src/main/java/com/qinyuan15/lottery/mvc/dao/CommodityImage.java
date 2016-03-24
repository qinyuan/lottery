package com.qinyuan15.lottery.mvc.dao;

import com.qinyuan.lib.database.hibernate.AbstractRanking;

public class CommodityImage extends AbstractRanking {
    private Integer commodityId;
    private String path;
    private String backPath;

    public Integer getCommodityId() {
        return commodityId;
    }

    public void setCommodityId(Integer commodityId) {
        this.commodityId = commodityId;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getBackPath() {
        return backPath;
    }

    public void setBackPath(String backPath) {
        this.backPath = backPath;
    }

    ///////////////// derive fields /////////////////////
    /*private Commodity commodityCache;*/

    public /*synchronized*/ Commodity getCommodity() {
        /*if (commodityCache == null) {
            commodityCache = new CommodityDao().getInstance(commodityId);
        }
        return commodityCache;*/
        return new CommodityDao().getInstance(commodityId);
    }

    public CommodityImage copy() {
        CommodityImage image = new CommodityImage();
        image.setId(getId());
        image.setCommodityId(commodityId);
        image.setPath(path);
        image.setBackPath(backPath);
        image.setRanking(getRanking());
        return image;
    }
}
