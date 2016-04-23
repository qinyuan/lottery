package com.qinyuan15.lottery.mvc.controller;

import com.qinyuan.lib.image.CachedImageMapDao;
import com.qinyuan.lib.lang.IntegerUtils;
import com.qinyuan.lib.mvc.controller.ImageController;
import com.qinyuan15.lottery.mvc.ImageMapType;
import com.qinyuan15.lottery.mvc.dao.SubIndexImage;
import com.qinyuan15.lottery.mvc.dao.SubIndexImageDao;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class AdminSubIndexLinkController extends ImageController {
    @RequestMapping("/admin-sub-index-link")
    public String index(@RequestParam(value = "id", required = true) Integer imageId) {
        IndexHeaderUtils.setHeaderParameters(this);

        if (!IntegerUtils.isPositive(imageId)) {
            return BLANK_PAGE;
        }

        SubIndexImage subIndexImage = new SubIndexImageDao().getInstance(imageId);
        if (subIndexImage == null) {
            return BLANK_PAGE;
        }

        SubIndexHeaderUtils.adapt(this, subIndexImage);

        setAttribute("image", subIndexImage.getPath());
        setAttributeAndJavaScriptData("imageMaps", new CachedImageMapDao(ImageMapType.SUB_INDEX)
                .getInstancesByRelateId(imageId));
        addJavaScriptData("relateType", ImageMapType.SUB_INDEX);

        String title = "编辑链接";
        if (subIndexImage.getPageIndex() == 1) {
            title += "-affiliate.html";
        } else if (subIndexImage.getPageIndex() == 2) {
            title += "-join.html";
        }

        setTitle(title);
        addCssAndJs("admin-image-map");
        return "admin-image-map";
    }
}
