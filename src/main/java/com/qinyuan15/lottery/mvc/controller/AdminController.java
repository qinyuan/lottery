package com.qinyuan15.lottery.mvc.controller;

import com.qinyuan15.lottery.mvc.AppConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

@Controller
public class AdminController extends IndexHeaderController {
    private final static Logger LOGGER = LoggerFactory.getLogger(AdminController.class);

    @RequestMapping("/admin")
    public String index() {
        setHeaderParameters();

        setTitle("用户管理");
        addCss("resources/js/lib/bootstrap/css/bootstrap.min.css", false);
        addJs("resources/js/lib/handlebars.min-v1.3.0", false);
        addHeadJs("lib/image-adjust.js");
        addCssAndJs("admin");

        return "admin";
    }

    @RequestMapping("/admin-submit")
    public String submit(@RequestParam(value = "indexHeaderLeftLogo", required = true) String indexHeaderLeftLogo,
                         @RequestParam(value = "indexHeaderLeftLogoFile", required = true) MultipartFile indexHeaderLeftLogoFile,
                         @RequestParam(value = "indexHeaderRightLogo", required = true) String indexHeaderRightLogo,
                         @RequestParam(value = "indexHeaderRightLogoFile", required = true) MultipartFile indexHeaderRightLogoFile,
                         @RequestParam(value = "indexHeaderSlogan", required = true) String indexHeaderSlogan,
                         @RequestParam(value = "indexHeaderSloganFile", required = true) MultipartFile indexHeaderSloganFile) {
        final String redirectPage = "admin";

        String indexHeaderLeftLogoPath = null, indexHeaderRightLogoPath = null, indexHeaderSloganPath = null;
        try {
            indexHeaderLeftLogoPath = getSavePath(indexHeaderLeftLogo, indexHeaderLeftLogoFile);
        } catch (Exception e) {
            LOGGER.error("error in getting save path of indexHeaderLeftLogo: {}", e);
            redirect(redirectPage, "左图标处理失败!");
        }

        try {
            indexHeaderRightLogoPath = getSavePath(indexHeaderRightLogo, indexHeaderRightLogoFile);
        } catch (Exception e) {
            LOGGER.error("error in getting save path of indexHeaderRightLogo: {}", e);
        }

        try {
            indexHeaderSloganPath = getSavePath(indexHeaderSlogan, indexHeaderSloganFile);
        } catch (Exception e) {
            LOGGER.error("error in getting save path of indexHeaderSlogan: {}", e);
        }

        AppConfig.updateIndexHeaderLeftLogo(indexHeaderLeftLogoPath);
        AppConfig.updateIndexHeaderRightLogo(indexHeaderRightLogoPath);
        AppConfig.updateIndexHeaderSlogan(indexHeaderSloganPath);

        return redirect("admin");
    }

    /*
    @RequestMapping("/hello-world.json")
    @ResponseBody
    public String json(){
        return success();
    }
    */
}
