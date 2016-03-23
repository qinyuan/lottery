package com.qinyuan15.lottery.mvc.controller;

import com.qinyuan.lib.image.CachedImageMapDao;
import com.qinyuan.lib.lang.IntegerUtils;
import com.qinyuan.lib.mvc.controller.ImageController;
import com.qinyuan15.lottery.mvc.ImageMapType;
import com.qinyuan15.lottery.mvc.dao.Commodity;
import com.qinyuan15.lottery.mvc.dao.CommodityImage;
import com.qinyuan15.lottery.mvc.dao.CommodityImageDao;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class AdminCommodityLinkController extends ImageController {
    @RequestMapping("/admin-commodity-link")
    public String index(@RequestParam(value = "id", required = true) Integer commodityImageId) {
        IndexHeaderUtils.setHeaderParameters(this);

        if (!IntegerUtils.isPositive(commodityImageId)) {
            return BLANK_PAGE;
        }

        CommodityImage commodityImage = new CommodityImageDao().getInstance(commodityImageId);
        if (commodityImage == null) {
            return BLANK_PAGE;
        }
        new CommodityImageUrlAdapter(this).adapt(commodityImage);

        setAttribute("image", commodityImage.getPath());
        setAttributeAndJavaScriptData("imageMaps", new CachedImageMapDao(ImageMapType.COMMODITY)
                .getInstancesByRelateId(commodityImageId));
        addJavaScriptData("relateType", ImageMapType.COMMODITY);
        setAttribute("buildInHrefs", new AdminImageMapController.BuildInHrefBuilder()
                .add("javascript:void(getLotteryLot())", "抽奖链接")
                .add("javascript:void(showLotteryRule())", "显示抽奖规则")
                .add("javascript:void(getSeckillLot())", "秒杀链接")
                .build());

        Commodity commodity = commodityImage.getCommodity();
        setTitle((commodity == null ? "" : commodity.getName()) + "_编辑链接");
        addCssAndJs("admin-image-map");
        return "admin-image-map";
    }
}
