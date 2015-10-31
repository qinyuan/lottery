package com.qinyuan15.lottery.mvc.dao;

import com.qinyuan.lib.database.hibernate.AbstractRanking;
import com.qinyuan.lib.lang.CurrencyUtils;

/**
 * Class about Commodity
 * Created by qinyuan on 15-6-22.
 */
public class Commodity extends AbstractRanking {
    private Double price;
    private String name;
    private Boolean own;
    private String snapshot;
    private String detailImage;
    private String backImage;
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

    public String getDetailImage() {
        return detailImage;
    }

    public String getBackImage() {
        return backImage;
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

    public void setDetailImage(String detailImage) {
        this.detailImage = detailImage;
    }

    public void setBackImage(String backImage) {
        this.backImage = backImage;
    }

    public void setVisible(Boolean visible) {
        this.visible = visible;
    }

    // derivative fields
    public String getFormattedPrice() {
        return this.price == null ? null : CurrencyUtils.trimUselessDecimal(this.price);
    }
}
