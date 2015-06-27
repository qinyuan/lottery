package com.qinyuan15.lottery.mvc.controller;

import com.qinyuan15.lottery.mvc.ImageMapType;
import com.qinyuan15.lottery.mvc.dao.Commodity;
import com.qinyuan15.lottery.mvc.dao.CommodityDao;
import com.qinyuan15.utils.IntegerUtils;
import com.qinyuan15.utils.image.ImageMapDao;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class AdminCommodityLinkController extends AdminImageLinkController {

    @Override
    protected ImageMapDao newImageMapDao() {
        return new ImageMapDao(ImageMapType.COMMODITY);
    }

    @RequestMapping("/admin-commodity-link")
    public String index(@RequestParam(value = "commodityId", required = true) Integer commodityId) {
        IndexHeaderUtils.setHeaderParameters(this);

        if (!IntegerUtils.isPositive(commodityId)) {
            return BLANK_PAGE;
        }

        Commodity commodity = new CommodityDao().getInstance(commodityId);
        if (commodity == null) {
            return BLANK_PAGE;
        }
        new CommodityUrlAdapter(this).adapt(commodity);

        setAttribute("commodity", commodity);
        setAttributeAndJavaScriptData("commodityMaps", newImageMapDao().getInstancesByRelateId(commodityId));

        setTitle("编辑商品图片链接");
        addCssAndJs("admin-commodity-link");
        return "admin-commodity-link";
    }

    @RequestMapping("/admin-commodity-link-edit.json")
    @ResponseBody
    public String editAction(@RequestParam(value = "id", required = true) Integer id,
                             @RequestParam(value = "href", required = true) String href,
                             @RequestParam(value = "comment", required = true) String comment) {
        return super.edit(id, href, comment);
    }

    @RequestMapping("/admin-commodity-link-add.json")
    @ResponseBody
    public String addAction(@RequestParam(value = "commodityId", required = true) Integer commodityId,
                            @RequestParam(value = "xStart", required = true) Integer xStart,
                            @RequestParam(value = "yStart", required = true) Integer yStart,
                            @RequestParam(value = "xEnd", required = true) Integer xEnd,
                            @RequestParam(value = "yEnd", required = true) Integer yEnd,
                            @RequestParam(value = "href", required = true) String href,
                            @RequestParam(value = "comment", required = true) String comment) {
        return super.add(commodityId, xStart, yStart, xEnd, yEnd, href, comment);
    }

    @RequestMapping("/admin-commodity-link-delete.json")
    @ResponseBody
    public String deleteAction(@RequestParam(value = "id", required = true) Integer id) {
        return super.delete(id);
    }
}
