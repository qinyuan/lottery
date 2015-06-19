package com.qinyuan15.lottery.mvc.controller;

import com.qinyuan15.lottery.mvc.dao.IndexImageDao;
import com.qinyuan15.utils.IntegerUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

@Controller
public class AdminIndexEditController extends IndexHeaderController {
    private final static Logger LOGGER = LoggerFactory.getLogger(AdminIndexEditController.class);

    @RequestMapping("/admin-index-edit")
    public String index() {
        setHeaderParameters();
        setIndexImageGroups();

        setTitle("主页设置");
        addCss("resources/js/lib/bootstrap/css/bootstrap.min", false);
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
        try {
            backImagePath = getSavePath(backImage, backImageFile);
        } catch (Exception e) {
            LOGGER.error("Fail to deal with backImage: {}", e);
            return redirect(page, "背景图片处理失败");
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

    @RequestMapping("/admin-index-delete-image.json")
    @ResponseBody
    public String json(@RequestParam(value = "id", required = true) Integer id) {
        if (IntegerUtils.isPositive(id)) {
            try {
                new IndexImageDao().delete(id);
                return success();
            } catch (Exception e) {
                LOGGER.error("Fail to delete index image: {}", e);
                return fail("数据库操作失败");
            }
        } else {
            return fail("请求参数错误");
        }
    }
}
