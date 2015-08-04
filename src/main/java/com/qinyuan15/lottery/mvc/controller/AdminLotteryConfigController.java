package com.qinyuan15.lottery.mvc.controller;

import com.qinyuan15.lottery.mvc.AppConfig;
import com.qinyuan15.lottery.mvc.mail.MailSelectFormItemBuilder;
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

        // data about sina weibo
        setAttribute("sinaWeiboTitle", AppConfig.getSinaWeiboTitle());
        setAttribute("sinaWeiboIncludePicture", AppConfig.getSinaWeiboIncludePicture());

        // data about qq
        setAttribute("qqTitle", AppConfig.getQQTitle());
        setAttribute("qqSummary", AppConfig.getQQSummary());
        setAttribute("qqIncludePicture", AppConfig.getQQIncludePicture());

        // data about qzone
        setAttribute("qzoneTitle", AppConfig.getQZoneTitle());
        setAttribute("qzoneSummary", AppConfig.getQzoneSummary());
        setAttribute("qzoneIncludePicture", AppConfig.getQzoneIncludePicture());

        // data about new lottery chance
        setAttribute("newLotteryChanceMailSubjectTemplate", AppConfig.getNewLotteryChanceMailSubjectTemplate());
        setAttribute("newLotteryChanceMailContentTemplate", AppConfig.getNewLotteryChanceMailContentTemplate());
        addJavaScriptData("currentNewLotteryChanceMailAccountId", AppConfig.getNewLotteryChanceMailAccountId());

        // data about mail account
        setAttribute("mailSelectFormItems", new MailSelectFormItemBuilder().build());

        // other data
        setAttribute("newLotLiveness", AppConfig.getNewLotLiveness());
        setAttribute("shareSucceedLiveness", AppConfig.getShareSucceedLiveness());
        setAttribute("lotteryRule", AppConfig.getLotteryRule());

        setTitle("抽奖配置");
        addCss("admin-form");
        addCss("admin");
        addJs("lib/ckeditor/ckeditor", false);
        addJs("lib/bootstrap/js/bootstrap.min", false);
        addCssAndJs("admin-lottery-config");
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
                         @RequestParam(value = "shareSucceedLiveness", required = true) Integer shareSucceedLiveness,
                         @RequestParam(value = "lotteryRule", required = true) String lotteryRule,
                         @RequestParam(value = "newLotteryChanceMailAccountId", required = true) Integer newLotteryChanceMailAccountId,
                         @RequestParam(value = "newLotteryChanceMailSubjectTemplate", required = true) String newLotteryChanceMailSubjectTemplate,
                         @RequestParam(value = "newLotteryChanceMailContentTemplate", required = true) String newLotteryChanceMailContentTemplate) {

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
        AppConfig.updateLotteryRule(lotteryRule);

        AppConfig.updateNewLotteryChanceMailAccountId(newLotteryChanceMailAccountId);
        AppConfig.updateNewLotteryChanceMailSubjectTemplate(newLotteryChanceMailSubjectTemplate);
        AppConfig.updateNewLotteryChanceMailContentTemplate(newLotteryChanceMailContentTemplate);

        return redirect("admin-lottery-config");
    }
}
