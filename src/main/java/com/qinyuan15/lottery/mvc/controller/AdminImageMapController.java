package com.qinyuan15.lottery.mvc.controller;

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
public class AdminImageMapController extends ImageController {
    @RequestMapping("/admin-image-map-edit.json")
    @ResponseBody
    public String edit(@RequestParam(value = "id", required = true) Integer id,
                       @RequestParam(value = "href", required = true) String href,
                       @RequestParam(value = "comment", required = true) String comment,
                       @RequestParam(value = "relateType", required = true) String relateType) {
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
            new ImageMapDao(relateType).update(id, href, comment);
            return success();
        } catch (Exception e) {
            return fail("数据库操作失败");
        }
    }

    @RequestMapping("/admin-image-map-add.json")
    @ResponseBody
    public String add(@RequestParam(value = "relateId", required = true) Integer relateId,
                      @RequestParam(value = "xStart", required = true) Integer xStart,
                      @RequestParam(value = "yStart", required = true) Integer yStart,
                      @RequestParam(value = "xEnd", required = true) Integer xEnd,
                      @RequestParam(value = "yEnd", required = true) Integer yEnd,
                      @RequestParam(value = "href", required = true) String href,
                      @RequestParam(value = "comment", required = true) String comment,
                      @RequestParam(value = "relateType", required = true) String relateType) {
        if (!IntegerUtils.isPositive(relateId) || !IntegerUtils.isNotNegative(xStart)
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
            new ImageMapDao(relateType).add(relateId, xStart, yStart, xEnd, yEnd, href, comment);
            return success();
        } catch (Exception e) {
            return fail("数据库操作失败");
        }
    }

    @RequestMapping("/admin-image-map-delete.json")
    @ResponseBody
    public String delete(@RequestParam(value = "relateType", required = true) String relateType,
                         @RequestParam(value = "id", required = true) Integer id) {
        try {
            new ImageMapDao(relateType).delete(id);
            return success();
        } catch (Exception e) {
            return fail("数据库操作失败");
        }
    }
}
