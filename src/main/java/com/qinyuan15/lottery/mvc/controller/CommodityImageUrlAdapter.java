package com.qinyuan15.lottery.mvc.controller;

import com.qinyuan.lib.mvc.controller.ImageController;
import com.qinyuan15.lottery.mvc.dao.CommodityImage;

import java.util.List;

public class CommodityImageUrlAdapter {
    private final ImageController controller;

    public CommodityImageUrlAdapter(ImageController controller) {
        this.controller = controller;
    }

    public CommodityImage adapt(CommodityImage image) {
        image.setPath(controller.pathToUrl(image.getPath()));
        image.setBackPath(controller.pathToUrl(image.getBackPath()));
        return image;
    }

    public List<CommodityImage> adapt(List<CommodityImage> images) {
        for (CommodityImage image : images) {
            adapt(image);
        }
        return images;
    }
}
