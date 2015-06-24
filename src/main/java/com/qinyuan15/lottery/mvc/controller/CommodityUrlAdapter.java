package com.qinyuan15.lottery.mvc.controller;

import com.qinyuan15.lottery.mvc.dao.Commodity;
import com.qinyuan15.utils.mvc.controller.ImageController;

import java.util.List;

/**
 * class to adapt url of commodity
 * Created by qinyuan on 15-6-24.
 */
public class CommodityUrlAdapter {
    private final ImageController controller;

    public CommodityUrlAdapter(ImageController controller) {
        this.controller = controller;
    }

    public Commodity adapt(Commodity commodity) {
        commodity.setSnapshot(controller.pathToUrl(commodity.getSnapshot()));
        commodity.setDetailImage(controller.pathToUrl(commodity.getDetailImage()));
        return commodity;
    }

    public List<Commodity> adapt(List<Commodity> commodities) {
        for (Commodity commodity : commodities) {
            adapt(commodity);
        }
        return commodities;
    }


}
