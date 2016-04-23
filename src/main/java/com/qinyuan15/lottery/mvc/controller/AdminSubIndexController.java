package com.qinyuan15.lottery.mvc.controller;

import com.qinyuan.lib.lang.IntegerUtils;
import com.qinyuan.lib.mvc.controller.ImageController;
import com.qinyuan15.lottery.mvc.config.AppConfig;
import com.qinyuan15.lottery.mvc.dao.SubIndexImageDao;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

@Controller
public class AdminSubIndexController extends ImageController {
    private final static Logger LOGGER = LoggerFactory.getLogger(AdminSubIndexController.class);

    @RequestMapping("/admin-sub-index")
    public String index() {
        IndexHeaderUtils.setHeaderParameters(this);

        SubIndexHeaderUtils.setSubIndexImageGroups(this);

        //setAttribute("cycleInterval", AppConfig.index.getIndexImageCycleInterval());
        addCss("admin-form");
        addHeadJs("lib/image-adjust.js");

        setTitle("图片页设置");
        addCssAndJs("admin-sub-index");
        return "admin-sub-index";
    }

    @RequestMapping("/admin-sub-index-add-image")
    public String addImage(@RequestParam(value = "image", required = true) String image,
                           @RequestParam(value = "imageFile", required = true) MultipartFile imageFile,
                           @RequestParam(value = "backImage", required = true) String backImage,
                           @RequestParam(value = "backImageFile", required = true) MultipartFile backImageFile,
                           @RequestParam(value = "pageIndex", required = false) Integer pageIndex,
                           @RequestParam(value = "id", required = false) Integer id) {

        final String page = "admin-sub-index";

        String imagePath;
        try {
            imagePath = getSavePath(image, imageFile);
        } catch (Exception e) {
            LOGGER.error("Fail to deal with image: {}", e);
            return redirect(page, "前景图片处理失败");
        }

        String backImagePath;
        if (isUploadFileEmpty(backImageFile) && StringUtils.isBlank(backImage)) {
            backImagePath = "";
        } else {
            try {
                backImagePath = getSavePath(backImage, backImageFile);
            } catch (Exception e) {
                LOGGER.error("Fail to deal with backImage: {}", e);
                return redirect(page, "背景图片处理失败");
            }
        }

        SubIndexImageDao dao = new SubIndexImageDao();
        if (IntegerUtils.isPositive(id)) {
            dao.update(id, imagePath, backImagePath);
        } else if (IntegerUtils.isPositive(pageIndex)) {
            dao.add(pageIndex, imagePath, backImagePath);
        } else {
            return redirect(page, "参数错误");
        }

        return redirect(page);
    }

    @RequestMapping("/admin-sub-index-delete-image.json")
    @ResponseBody
    public String deleteImage(@RequestParam(value = "id", required = true) Integer id) {
        if (!IntegerUtils.isPositive(id)) {
            return failByInvalidParam();
        }
        try {
            new SubIndexImageDao().delete(id);
            return success();
        } catch (Exception e) {
            LOGGER.error("Fail to delete index image: {}", e);
            return failByDatabaseError();
        }
    }
}
