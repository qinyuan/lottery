package com.qinyuan15.lottery.mvc.controller;

import com.qinyuan15.lottery.mvc.ImageMapType;
import com.qinyuan15.lottery.mvc.dao.IndexImage;
import com.qinyuan15.lottery.mvc.dao.IndexImageDao;
import com.qinyuan15.utils.IntegerUtils;
import com.qinyuan15.utils.image.ImageMapDao;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class AdminIndexImageLinkController extends AdminImageLinkController {
    @Override
    protected ImageMapDao newImageMapDao() {
        return new ImageMapDao(ImageMapType.INDEX);
    }

    @RequestMapping("/admin-index-image-link")
    public String index(@RequestParam(value = "imageId", required = true) Integer imageId) {
        IndexHeaderUtils.setHeaderParameters(this);

        if (!IntegerUtils.isPositive(imageId)) {
            return BLANK_PAGE;
        }

        IndexImage indexImage = new IndexImageDao().getInstance(imageId);
        if (indexImage == null) {
            return BLANK_PAGE;
        }
        indexImage.setPath(this.pathToUrl(indexImage.getPath()));

        setAttribute("indexImage", indexImage);
        setAttributeAndJavaScriptData("indexImageMaps", newImageMapDao().getInstancesByRelateId(imageId));

        setTitle("编辑主页图片链接");
        addCssAndJs("admin-index-image-link");

        return "admin-index-image-link";
    }

    @RequestMapping("/admin-index-image-link-edit.json")
    @ResponseBody
    public String editAction(@RequestParam(value = "id", required = true) Integer id,
                             @RequestParam(value = "href", required = true) String href,
                             @RequestParam(value = "comment", required = true) String comment) {
        return super.edit(id, href, comment);
    }

    @RequestMapping("/admin-index-image-link-add.json")
    @ResponseBody
    public String addAction(@RequestParam(value = "imageId", required = true) Integer imageId,
                            @RequestParam(value = "xStart", required = true) Integer xStart,
                            @RequestParam(value = "yStart", required = true) Integer yStart,
                            @RequestParam(value = "xEnd", required = true) Integer xEnd,
                            @RequestParam(value = "yEnd", required = true) Integer yEnd,
                            @RequestParam(value = "href", required = true) String href,
                            @RequestParam(value = "comment", required = true) String comment) {
        return super.add(imageId, xStart, yStart, xEnd, yEnd, href, comment);
    }

    @RequestMapping("/admin-index-image-link-delete.json")
    @ResponseBody
    public String deleteAction(@RequestParam(value = "id", required = true) Integer id) {
        return super.delete(id);
    }
}
