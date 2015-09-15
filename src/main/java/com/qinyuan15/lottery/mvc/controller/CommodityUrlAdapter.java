package com.qinyuan15.lottery.mvc.controller;

import com.qinyuan.lib.image.ImageSize;
import com.qinyuan.lib.image.ThumbnailBuilder;
import com.qinyuan.lib.image.ThumbnailSuffix;
import com.qinyuan.lib.mvc.controller.ImageController;
import com.qinyuan15.lottery.mvc.dao.Commodity;

import java.util.List;

/**
 * class to adapt url of commodity
 * Created by qinyuan on 15-6-24.
 */
public class CommodityUrlAdapter {
    private final ImageController controller;

    private final static ImageSize SMALL_THUMBNAIL_SIZE = new ImageSize(120, 120);
    private final static ImageSize MIDDLE_THUMBNAIL_SIZE = new ImageSize(310, 176);

    public CommodityUrlAdapter(ImageController controller) {
        this.controller = controller;
    }

    public Commodity adapt(Commodity commodity) {
        return commodity == null ? null : adapt(commodity, commodity.getSnapshot());
    }

    public Commodity adaptToSmall(Commodity commodity) {
        if (commodity == null) {
            return null;
        }
        return adapt(commodity, ThumbnailSuffix.SMALL, SMALL_THUMBNAIL_SIZE);
    }

    public Commodity adaptToMiddle(Commodity commodity) {
        if (commodity == null) {
            return null;
        }
        return adapt(commodity, ThumbnailSuffix.MIDDLE, MIDDLE_THUMBNAIL_SIZE);
    }

    private Commodity adapt(Commodity commodity, String thumbnailSuffix, ImageSize thumbnailSize) {
        String snapshot = new ThumbnailBuilder().get(commodity.getSnapshot(), thumbnailSuffix, thumbnailSize);
        return adapt(commodity, snapshot);
    }

    private Commodity adapt(Commodity commodity, String snapshot) {
        if (commodity == null) {
            return null;
        }
        System.out.println(controller.pathToUrl(snapshot));
        commodity.setSnapshot(controller.pathToUrl(snapshot));
        commodity.setDetailImage(controller.pathToUrl(commodity.getDetailImage()));
        commodity.setBackImage(controller.pathToUrl(commodity.getBackImage()));
        return commodity;
    }

    public List<Commodity> adapt(List<Commodity> commodities) {
        if (commodities == null) {
            return null;
        }

        for (Commodity commodity : commodities) {
            adapt(commodity);
        }
        return commodities;
    }
}
