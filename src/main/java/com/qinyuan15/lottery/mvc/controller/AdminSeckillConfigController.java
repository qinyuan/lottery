package com.qinyuan15.lottery.mvc.controller;

import com.qinyuan15.lottery.mvc.AppConfig;
import com.qinyuan15.utils.mvc.controller.ImageController;
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
        setAttribute("sinaWeiboTitle", AppConfig.getSeckillSinaWeiboTitle());
        setAttribute("sinaWeiboIncludePicture", AppConfig.getSeckillSinaWeiboIncludePicture());

        // data about qq
        setAttribute("qqTitle", AppConfig.getSeckillQQTitle());
        setAttribute("qqSummary", AppConfig.getSeckillQQSummary());
        setAttribute("qqIncludePicture", AppConfig.getSeckillQQIncludePicture());

        // data about qzone
        setAttribute("qzoneTitle", AppConfig.getSeckillQzoneTitle());
        setAttribute("qzoneSummary", AppConfig.getSeckillQzoneSummary());
        setAttribute("qzoneIncludePicture", AppConfig.getSeckillQzoneIncludePicture());

        // data about poker
        setAttribute("pokerFrontSide", pathToUrl(AppConfig.getSeckillPokerFrontSide()));
        setAttribute("pokerBackSide", pathToUrl(AppConfig.getSeckillPokerBackSide()));

        setTitle("秒杀配置");
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
                         @RequestParam(value = "pokerBackSideFile", required = true) MultipartFile pokerBackSideFile) {

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

        AppConfig.updateSeckillSinaWeiboTitle(sinaWeiboTitle);
        AppConfig.updateSeckillSinaWeiboIncludePicture(sinaWeiboIncludePicture);
        AppConfig.updateSeckillQQTitle(qqTitle);
        AppConfig.updateSeckillQQSummary(qqSummary);
        AppConfig.updateSeckillQQIncludePicture(qqIncludePicture);
        AppConfig.updateSeckillQzoneTitle(qzoneTitle);
        AppConfig.updateSeckillQzoneSummary(qzoneSummary);
        AppConfig.updateSeckillQzoneIncludePicture(qzoneIncludePicture);
        AppConfig.updateSeckillPokerFrontSide(pokerFrontSidePath);
        AppConfig.updateSeckillPokerBackSide(pokerBackSidePath);

        return redirect(redirectPage);
    }
}
