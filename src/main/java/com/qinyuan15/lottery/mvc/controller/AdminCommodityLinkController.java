package com.qinyuan15.lottery.mvc.controller;

import com.qinyuan15.lottery.mvc.ImageMapType;
import com.qinyuan15.lottery.mvc.dao.Commodity;
import com.qinyuan15.lottery.mvc.dao.CommodityDao;
import com.qinyuan15.utils.IntegerUtils;
import com.qinyuan15.utils.config.LinkAdapter;
import com.qinyuan15.utils.image.ImageMapDao;
import com.qinyuan15.utils.mvc.controller.ImageController;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class AdminCommodityLinkController extends ImageController {

    private ImageMapDao dao = new ImageMapDao(ImageMapType.COMMODITY);

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
        setAttributeAndJavaScriptData("commodityMaps", dao.getInstancesByRelateId(commodityId));

        setTitle("编辑商品图片链接");
        addCssAndJs("admin-commodity-link");
        return "admin-commodity-link";
    }

    @RequestMapping("/admin-commodity-link-edit.json")
    @ResponseBody
    public String edit(@RequestParam(value = "id", required = true) Integer id,
                       @RequestParam(value = "href", required = true) String href,
                       @RequestParam(value = "comment", required = true) String comment) {
        if (!IntegerUtils.isPositive(id)) {
            return fail("数据错误");
        }

        if (!StringUtils.hasText(href)) {
            return fail("链接未填写");
        }
        href = new LinkAdapter().adjust(href);

        if (!StringUtils.hasText(comment)) {
            return fail("备注未填写");
        }

        try {
            dao.update(id, href, comment);
            return success();
        } catch (Exception e) {
            return fail("数据库操作失败");
        }
    }

    @RequestMapping("/admin-commodity-link-add.json")
    @ResponseBody
    public String add(@RequestParam(value = "commodityId", required = true) Integer commodityId,
                      @RequestParam(value = "xStart", required = true) Integer xStart,
                      @RequestParam(value = "yStart", required = true) Integer yStart,
                      @RequestParam(value = "xEnd", required = true) Integer xEnd,
                      @RequestParam(value = "yEnd", required = true) Integer yEnd,
                      @RequestParam(value = "href", required = true) String href,
                      @RequestParam(value = "comment", required = true) String comment) {

        if (!IntegerUtils.isPositive(commodityId) || !IntegerUtils.isNotNegative(xStart)
                || !IntegerUtils.isNotNegative(yStart) || !IntegerUtils.isNotNegative(xEnd)
                || !IntegerUtils.isNotNegative(yEnd)) {
            return fail("数据错误");
        }

        if (!StringUtils.hasText(href)) {
            return fail("链接未填写");
        }
        href = new LinkAdapter().adjust(href);

        if (!StringUtils.hasText(comment)) {
            return fail("备注未填写");
        }

        try {
            dao.add(commodityId, xStart, yStart, xEnd, yEnd, href, comment);
            return success();
        } catch (Exception e) {
            return fail("数据库操作失败");
        }
    }

    @RequestMapping("/admin-commodity-link-delete.json")
    @ResponseBody
    public String json(@RequestParam(value = "id", required = true) Integer id) {
        try {
            dao.delete(id);
            return success();
        } catch (Exception e) {
            return fail("数据库操作失败");
        }
    }
}
