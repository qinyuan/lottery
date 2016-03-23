package com.qinyuan15.lottery.mvc.controller;

import com.qinyuan.lib.image.CachedImageMapDao;
import com.qinyuan.lib.image.ImageMapDao;
import com.qinyuan.lib.lang.IntegerUtils;
import com.qinyuan.lib.mvc.controller.ImageController;
import com.qinyuan15.lottery.mvc.ImageMapType;
import com.qinyuan15.lottery.mvc.dao.IndexImage;
import com.qinyuan15.lottery.mvc.dao.IndexImageDao;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class AdminIndexImageLinkController extends ImageController {
    @RequestMapping("/admin-index-image-link")
    public String index(@RequestParam(value = "id", required = true) Integer imageId) {
        IndexHeaderUtils.setHeaderParameters(this);

        if (!IntegerUtils.isPositive(imageId)) {
            return BLANK_PAGE;
        }

        IndexImage indexImage = new IndexImageDao().getInstance(imageId);
        if (indexImage == null) {
            return BLANK_PAGE;
        }
        IndexHeaderUtils.adapt(this, indexImage);

        setAttribute("image", indexImage.getPath());
        setAttributeAndJavaScriptData("imageMaps", new CachedImageMapDao(ImageMapType.INDEX)
                .getInstancesByRelateId(imageId));
        addJavaScriptData("relateType", ImageMapType.INDEX);

        setTitle("编辑主页图片链接");
        addCssAndJs("admin-image-map");
        return "admin-image-map";
    }
}
