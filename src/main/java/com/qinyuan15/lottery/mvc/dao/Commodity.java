package com.qinyuan15.lottery.mvc.dao;

import com.qinyuan15.utils.hibernate.PersistObject;

/**
 * Class about Commodity
 * Created by qinyuan on 15-6-22.
 */
public class Commodity extends PersistObject {
    private Double price;
    private String name;
    private Boolean inLottery;
    private Boolean own;
    private String snapshot;
    private String detailImage;

    public Double getPrice() {
        return price;
    }

    public String getName() {
        return name;
    }

    public Boolean getInLottery() {
        return inLottery;
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

    public void setPrice(Double price) {
        this.price = price;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setInLottery(Boolean inLottery) {
        this.inLottery = inLottery;
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
}
