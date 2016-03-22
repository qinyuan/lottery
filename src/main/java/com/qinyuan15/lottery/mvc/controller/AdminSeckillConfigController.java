package com.qinyuan15.lottery.mvc.controller;

import com.qinyuan.lib.mvc.controller.ImageController;
import com.qinyuan15.lottery.mvc.config.AppConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

@Controller
public class AdminSeckillConfigController extends ImageController {
    private final static Logger LOGGER = LoggerFactory.getLogger(AdminSeckillConfigController.class);

    @RequestMapping("/admin-seckill-config")
    public String index() {
        IndexHeaderUtils.setHeaderParameters(this);

        // data about sina weibo
        setAttribute("sinaWeiboTitle", AppConfig.seckill.getSinaWeiboTitle());
        setAttribute("sinaWeiboIncludePicture", AppConfig.seckill.getSinaWeiboIncludePicture());

        // data about qq
        setAttribute("qqTitle", AppConfig.seckill.getQQTitle());
        setAttribute("qqSummary", AppConfig.seckill.getQQSummary());
        setAttribute("qqIncludePicture", AppConfig.seckill.getQQIncludePicture());

        // data about qzone
        setAttribute("qzoneTitle", AppConfig.seckill.getQzoneTitle());
        setAttribute("qzoneSummary", AppConfig.seckill.getQzoneSummary());
        setAttribute("qzoneIncludePicture", AppConfig.seckill.getQzoneIncludePicture());

        // data about poker
        setAttribute("pokerFrontSide", pathToUrl(AppConfig.seckill.getPokerFrontSide()));
        setAttribute("pokerBackSide", pathToUrl(AppConfig.seckill.getPokerBackSide()));

        // announcement template
        setAttribute("seckillAnnouncementTemplate", AppConfig.getSeckillAnnouncementTemplate());

        setTitle("秒杀配置");
        addJs("lib/ckeditor/ckeditor", false);
        addCss("admin-form");
        addCss("admin");
        addHeadJs("lib/image-adjust.js");
        addCssAndJs("admin-seckill-config");
        return "admin-seckill-config";
    }

    @RequestMapping("/admin-seckill-config-submit")
    public String submit(@RequestParam(value = "sinaWeiboTitle", required = true) String sinaWeiboTitle,
                         @RequestParam(value = "sinaWeiboIncludePicture", required = true) Boolean sinaWeiboIncludePicture,
                         @RequestParam(value = "qqTitle", required = true) String qqTitle,
                         @RequestParam(value = "qqSummary", required = true) String qqSummary,
                         @RequestParam(value = "qqIncludePicture", required = true) Boolean qqIncludePicture,
                         @RequestParam(value = "qzoneTitle", required = true) String qzoneTitle,
                         @RequestParam(value = "qzoneSummary", required = true) String qzoneSummary,
                         @RequestParam(value = "qzoneIncludePicture", required = true) Boolean qzoneIncludePicture,
                         @RequestParam(value = "pokerFrontSide", required = true) String pokerFrontSide,
                         @RequestParam(value = "pokerFrontSideFile", required = true) MultipartFile pokerFrontSideFile,
                         @RequestParam(value = "pokerBackSide", required = true) String pokerBackSide,
                         @RequestParam(value = "pokerBackSideFile", required = true) MultipartFile pokerBackSideFile,
                         @RequestParam(value = "seckillAnnouncementTemplate", required = true) String seckillAnnouncementTemplate) {

        final String redirectPage = "admin-seckill-config";
        String pokerFrontSidePath = null, pokerBackSidePath = null;
        try {
            pokerFrontSidePath = getSavePath(pokerFrontSide, pokerFrontSideFile);
        } catch (Exception e) {
            LOGGER.error("error in getting save path of poker front side: {}", e);
            redirect(redirectPage, "扑克牌背景图1处理失败！");
        }

        try {
            pokerBackSidePath = getSavePath(pokerBackSide, pokerBackSideFile);
        } catch (Exception e) {
            LOGGER.error("error in getting save path of poker back side: {}", e);
            redirect(redirectPage, "扑克牌背景图2处理失败！");
        }

        AppConfig.seckill.updateSinaWeiboTitle(sinaWeiboTitle);
        AppConfig.seckill.updateSinaWeiboIncludePicture(sinaWeiboIncludePicture);
        AppConfig.seckill.updateQQTitle(qqTitle);
        AppConfig.seckill.updateQQSummary(qqSummary);
        AppConfig.seckill.updateQQIncludePicture(qqIncludePicture);
        AppConfig.seckill.updateQzoneTitle(qzoneTitle);
        AppConfig.seckill.updateQzoneSummary(qzoneSummary);
        AppConfig.seckill.updateQzoneIncludePicture(qzoneIncludePicture);
        AppConfig.seckill.updatePokerFrontSide(pokerFrontSidePath);
        AppConfig.seckill.updatePokerBackSide(pokerBackSidePath);
        AppConfig.updateSeckillAnnouncementTemplate(seckillAnnouncementTemplate);

        return redirect(redirectPage);
    }
}
