package com.qinyuan15.lottery.mvc.controller;

import com.qinyuan.lib.image.ImageMapDao;
import com.qinyuan.lib.lang.IntegerUtils;
import com.qinyuan.lib.mvc.controller.ImageController;
import com.qinyuan15.lottery.mvc.ImageMapType;
import com.qinyuan15.lottery.mvc.dao.Commodity;
import com.qinyuan15.lottery.mvc.dao.CommodityDao;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class AdminCommodityLinkController extends ImageController {
    @RequestMapping("/admin-commodity-link")
    public String index(@RequestParam(value = "id", required = true) Integer commodityId) {
        IndexHeaderUtils.setHeaderParameters(this);

        if (!IntegerUtils.isPositive(commodityId)) {
            return BLANK_PAGE;
        }

        Commodity commodity = new CommodityDao().getInstance(commodityId);
        if (commodity == null) {
            return BLANK_PAGE;
        }
        new CommodityUrlAdapter(this).adapt(commodity);

        setAttribute("image", commodity.getDetailImage());
        setAttributeAndJavaScriptData("imageMaps", new ImageMapDao(ImageMapType.COMMODITY)
                .getInstancesByRelateId(commodityId));
        addJavaScriptData("relateType", ImageMapType.COMMODITY);
        setAttribute("buildInHrefs", new AdminImageMapController.BuildInHrefBuilder()
                .add("javascript:void(getLotteryLot())", "抽奖链接")
                .add("javascript:void(showLotteryRule())", "显示抽奖规则")
                .add("javascript:void(getSeckillLot())", "秒杀链接")
                .add("javascript:void(showSeckillRule())", "显示秒杀规则")
                .build());

        setTitle(commodity.getName() + "_编辑链接");
        addCssAndJs("admin-image-map");
        return "admin-image-map";
    }
}
