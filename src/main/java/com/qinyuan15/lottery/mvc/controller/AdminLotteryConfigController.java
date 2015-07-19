package com.qinyuan15.lottery.mvc.controller;

import com.qinyuan15.lottery.mvc.AppConfig;
import com.qinyuan15.utils.mvc.controller.ImageController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class AdminLotteryConfigController extends ImageController {
    @RequestMapping("/admin-lottery-config")
    public String index() {
        IndexHeaderUtils.setHeaderParameters(this);
        CommodityHeaderUtils.setHeaderParameters(this);

        setAttribute("sinaWeiboTitle", AppConfig.getSinaWeiboTitle());
        setAttribute("sinaWeiboIncludePicture", AppConfig.getSinaWeiboIncludePicture());
        setAttribute("qqTitle", AppConfig.getQQTitle());
        setAttribute("qqSummary", AppConfig.getQQSummary());
        setAttribute("qqIncludePicture", AppConfig.getQQIncludePicture());
        setAttribute("qzoneTitle", AppConfig.getQZoneTitle());
        setAttribute("qzoneSummary", AppConfig.getQzoneSummary());
        setAttribute("qzoneIncludePicture", AppConfig.getQzoneIncludePicture());
        setAttribute("newLotLiveness", AppConfig.getNewLotLiveness());
        setAttribute("shareSucceedLiveness", AppConfig.getShareSucceedLiveness());

        setTitle("抽奖配置");
        addCss("admin-form");
        addCss("admin");
        addJs("admin-lottery-config");
        return "admin-lottery-config";
    }

    @RequestMapping("/admin-lottery-config-submit")
    public String submit(@RequestParam(value = "sinaWeiboTitle", required = true) String sinaWeiboTitle,
                         @RequestParam(value = "sinaWeiboIncludePicture", required = true) Boolean sinaWeiboIncludePicture,
                         @RequestParam(value = "qqTitle", required = true) String qqTitle,
                         @RequestParam(value = "qqSummary", required = true) String qqSummary,
                         @RequestParam(value = "qqIncludePicture", required = true) Boolean qqIncludePicture,
                         @RequestParam(value = "qzoneTitle", required = true) String qzoneTitle,
                         @RequestParam(value = "qzoneSummary", required = true) String qzoneSummary,
                         @RequestParam(value = "qzoneIncludePicture", required = true) Boolean qzoneIncludePicture,
                         @RequestParam(value = "newLotLiveness", required = true) Integer newLotLiveness,
                         @RequestParam(value = "shareSucceedLiveness", required = true) Integer shareSucceedLiveness) {
        AppConfig.updateSinaWeiboTitle(sinaWeiboTitle);
        AppConfig.updateSinaWeiboIncludePicture(sinaWeiboIncludePicture);
        AppConfig.updateQQTitle(qqTitle);
        AppConfig.updateQQSummary(qqSummary);
        AppConfig.updateQQIncludePicture(qqIncludePicture);
        AppConfig.updateQzoneTitle(qzoneTitle);
        AppConfig.updateQzoneSummary(qzoneSummary);
        AppConfig.updateQzoneIncludePicture(qzoneIncludePicture);
        AppConfig.updateNewLotLiveness(newLotLiveness);
        AppConfig.updateShareSucceedLiveness(shareSucceedLiveness);
        return redirect("admin-lottery-config");
    }
}
