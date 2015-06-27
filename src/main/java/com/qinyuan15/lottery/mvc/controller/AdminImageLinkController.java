package com.qinyuan15.lottery.mvc.controller;

import com.qinyuan15.utils.IntegerUtils;
import com.qinyuan15.utils.config.LinkAdapter;
import com.qinyuan15.utils.image.ImageMapDao;
import com.qinyuan15.utils.mvc.controller.ImageController;
import org.springframework.util.StringUtils;

public abstract class AdminImageLinkController extends ImageController {

    protected abstract ImageMapDao newImageMapDao();

    protected String edit(Integer id, String href, String comment) {
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
            newImageMapDao().update(id, href, comment);
            return success();
        } catch (Exception e) {
            return fail("数据库操作失败");
        }
    }

    protected String add(Integer relateId, Integer xStart, Integer yStart, Integer xEnd, Integer yEnd,
                      String href, String comment) {
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
            newImageMapDao().add(relateId, xStart, yStart, xEnd, yEnd, href, comment);
            return success();
        } catch (Exception e) {
            return fail("数据库操作失败");
        }
    }

    protected String delete(Integer id) {
        try {
            newImageMapDao().delete(id);
            return success();
        } catch (Exception e) {
            return fail("数据库操作失败");
        }
    }
}
