package com.qinyuan15.lottery.mvc.dao;

import com.qinyuan.lib.database.hibernate.AbstractRanking;
import com.qinyuan.lib.lang.CurrencyUtils;

import java.util.List;

/**
 * Class about Commodity
 * Created by qinyuan on 15-6-22.
 */
public class Commodity extends AbstractRanking {
    private Double price;
    private String name;
    private Boolean own;
    private String snapshot;
    private Boolean visible;

    public Double getPrice() {
        return price;
    }

    public String getName() {
        return name;
    }

    public Boolean getOwn() {
        return own;
    }

    public String getSnapshot() {
        return snapshot;
    }

    public Boolean getVisible() {
        return visible;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setOwn(Boolean own) {
        this.own = own;
    }

    public void setSnapshot(String snapshot) {
        this.snapshot = snapshot;
    }

    public void setVisible(Boolean visible) {
        this.visible = visible;
    }

    ///////////////////////////// derivative fields //////////////////////////////
    public String getFormattedPrice() {
        return this.price == null ? null : CurrencyUtils.trimUselessDecimal(this.price);
    }

    public List<CommodityImage> getImages() {
        return new CommodityImageDao().getInstancesByCommodityId(getId());
    }

    public Commodity copy() {
        Commodity commodity = new Commodity();

        commodity.setId(getId());
        commodity.setPrice(price);
        commodity.setName(name);
        commodity.setOwn(own);
        commodity.setSnapshot(snapshot);
        commodity.setVisible(visible);
        commodity.setRanking(getRanking());

        return commodity;
    }
}
