package com.qinyuan15.lottery.mvc.controller;

import com.qinyuan.lib.lang.IntegerUtils;
import com.qinyuan.lib.mvc.controller.ImageController;
import com.qinyuan15.lottery.mvc.config.AppConfig;
import com.qinyuan15.lottery.mvc.dao.IndexImageDao;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

@Controller
public class AdminIndexEditController extends ImageController {
    private final static Logger LOGGER = LoggerFactory.getLogger(AdminIndexEditController.class);

    @RequestMapping("/admin-index-edit")
    public String index() {
        IndexHeaderUtils.setHeaderParameters(this);
        IndexHeaderUtils.setIndexImageGroups(this);

        setAttribute("cycleInterval", AppConfig.index.getIndexImageCycleInterval());

        setTitle("主页设置");
        addCss("admin-form");
        addHeadJs("lib/image-adjust.js");
        addCssAndJs("admin-index-edit");
        return "admin-index-edit";
    }


    @RequestMapping("/admin-index-add-image")
    public String addImage(@RequestParam(value = "image", required = true) String image,
                           @RequestParam(value = "imageFile", required = true) MultipartFile imageFile,
                           @RequestParam(value = "backImage", required = true) String backImage,
                           @RequestParam(value = "backImageFile", required = true) MultipartFile backImageFile,
                           @RequestParam(value = "rowIndex", required = false) Integer rowIndex,
                           @RequestParam(value = "id", required = false) Integer id) {

        final String page = "admin-index-edit";

        String imagePath;
        try {
            imagePath = getSavePath(image, imageFile);
        } catch (Exception e) {
            LOGGER.error("Fail to deal with image: {}", e);
            return redirect(page, "前景图片处理失败");
        }

        String backImagePath;
        if (isUploadFileEmpty(backImageFile) && !StringUtils.hasText(backImage)) {
            backImagePath = "";
        } else {
            try {
                backImagePath = getSavePath(backImage, backImageFile);
            } catch (Exception e) {
                LOGGER.error("Fail to deal with backImage: {}", e);
                return redirect(page, "背景图片处理失败");
            }
        }

        IndexImageDao dao = new IndexImageDao();
        if (IntegerUtils.isPositive(id)) {
            dao.update(id, imagePath, backImagePath);
        } else if (IntegerUtils.isPositive(rowIndex)) {
            dao.add(imagePath, backImagePath, rowIndex);
        } else {
            dao.add(imagePath, backImagePath);
        }

        return redirect(page);
    }

    @RequestMapping("/admin-index-image-cycle-interval.json")
    @ResponseBody
    public String updateInterval(@RequestParam(value = "cycleInterval", required = true) Integer cycleInterval) {
        if (!IntegerUtils.isPositive(cycleInterval)) {
            return fail("必须输入一个整数");
        }
        try {
            AppConfig.index.updateIndexImageCycleInterval(cycleInterval);
            return success();
        } catch (Exception e) {
            LOGGER.error("Fail to update image cycle interval, info: {}", e);
            return failByDatabaseError();
        }
    }

    @RequestMapping("/admin-index-delete-image.json")
    @ResponseBody
    public String deleteImage(@RequestParam(value = "id", required = true) Integer id) {
        if (!IntegerUtils.isPositive(id)) {
            return failByInvalidParam();
        }
        try {
            new IndexImageDao().delete(id);
            return success();
        } catch (Exception e) {
            LOGGER.error("Fail to delete index image: {}", e);
            return failByDatabaseError();
        }
    }
}
