package com.qinyuan15.lottery.mvc.controller;

import com.qinyuan.lib.mvc.controller.CDNSource;
import com.qinyuan.lib.mvc.controller.ImageController;
import com.qinyuan15.lottery.mvc.AppConfig;
import com.qinyuan15.lottery.mvc.mail.MailSelectFormItemBuilder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class AdminLotteryConfigController extends ImageController {
    @RequestMapping("/admin-lottery-config")
    public String index() {
        IndexHeaderUtils.setHeaderParameters(this);

        // data about sina weibo
        setAttribute("sinaWeiboTitle", AppConfig.getLotterySinaWeiboTitle());
        setAttribute("sinaWeiboIncludePicture", AppConfig.getLotterySinaWeiboIncludePicture());

        // data about qq
        setAttribute("qqTitle", AppConfig.getLotteryQQTitle());
        setAttribute("qqSummary", AppConfig.getLotteryQQSummary());
        setAttribute("qqIncludePicture", AppConfig.getLotteryQQIncludePicture());

        // data about qzone
        setAttribute("qzoneTitle", AppConfig.getLotteryQzoneTitle());
        setAttribute("qzoneSummary", AppConfig.getLotteryQzoneSummary());
        setAttribute("qzoneIncludePicture", AppConfig.getLotteryQzoneIncludePicture());

        // data about new lottery chance
        setAttribute("remindNewLotteryChanceByMail", AppConfig.getRemindNewLotteryChanceByMail());
        setAttribute("newLotteryChanceMailSubjectTemplate", AppConfig.getNewLotteryChanceMailSubjectTemplate());
        setAttribute("newLotteryChanceMailContentTemplate", AppConfig.getNewLotteryChanceMailContentTemplate());
        addJavaScriptData("currentNewLotteryChanceMailAccountId", AppConfig.getNewLotteryChanceMailAccountId());
        setAttribute("remindNewLotteryChanceBySystemInfo", AppConfig.getRemindNewLotteryChanceBySystemInfo());
        setAttribute("newLotteryChanceSystemInfoTemplate", AppConfig.getNewLotteryChanceSystemInfoTemplate());

        // data about liveness
        //setAttribute("newLotLiveness", AppConfig.getNewLotLiveness());
        setAttribute("shareSucceedLiveness", AppConfig.getShareSucceedLiveness());
        setAttribute("remindLivenessIncreaseBySystemInfo", AppConfig.getRemindLivenessIncreaseBySystemInfo());
        setAttribute("livenessIncreaseSystemInfoTemplate", AppConfig.getLivenessIncreaseSystemInfoTemplate());

        // data about mail account
        setAttribute("mailSelectFormItems", new MailSelectFormItemBuilder().build());

        // other data
        setAttribute("lotteryAnnouncementTemplate", AppConfig.getLotteryAnnouncementTemplate());
        setAttribute("lotteryRule", AppConfig.getLotteryRule());
        setAttribute("noTelInvalidLotSystemInfoTemplate", AppConfig.getNoTelInvalidLotSystemInfoTemplate());
        setAttribute("insufficientLivenessInvalidLotSystemInfoTemplate", AppConfig.getInsufficientLivenessInvalidLotSystemInfoTemplate());

        setTitle("抽奖配置");
        addCss("admin-form");
        addCss("admin");
        addJs("lib/ckeditor/ckeditor", false);
        addJs(CDNSource.BOOTSTRAP_JS, false);
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
                         //@RequestParam(value = "newLotLiveness", required = true) Integer newLotLiveness,
                         @RequestParam(value = "shareSucceedLiveness", required = true) Integer shareSucceedLiveness,
                         @RequestParam(value = "lotteryRule", required = true) String lotteryRule,
                         @RequestParam(value = "remindNewLotteryChanceByMail", required = false) String remindNewLotteryChanceByMail,
                         @RequestParam(value = "newLotteryChanceMailAccountId", required = true) Integer newLotteryChanceMailAccountId,
                         @RequestParam(value = "newLotteryChanceMailSubjectTemplate", required = true) String newLotteryChanceMailSubjectTemplate,
                         @RequestParam(value = "newLotteryChanceMailContentTemplate", required = true) String newLotteryChanceMailContentTemplate,
                         @RequestParam(value = "lotteryAnnouncementTemplate", required = true) String lotteryAnnouncementTemplate,
                         @RequestParam(value = "remindNewLotteryChanceBySystemInfo", required = false) String remindNewLotteryChanceBySystemInfo,
                         @RequestParam(value = "newLotteryChanceSystemInfoTemplate", required = true) String newLotteryChanceSystemInfoTemplate,
                         @RequestParam(value = "remindLivenessIncreaseBySystemInfo", required = false) String remindLivenessIncreaseBySystemInfo,
                         @RequestParam(value = "livenessIncreaseSystemInfoTemplate", required = true) String livenessIncreaseSystemInfoTemplate,
                         @RequestParam(value = "noTelInvalidLotSystemInfoTemplate", required = true) String noTelInvalidLotSystemInfoTemplate,
                         @RequestParam(value = "insufficientLivenessInvalidLotSystemInfoTemplate", required = true) String insufficientLivenessInvalidLotSystemInfoTemplate) {

        AppConfig.updateLotterySinaWeiboTitle(sinaWeiboTitle);
        AppConfig.updateLotterySinaWeiboIncludePicture(sinaWeiboIncludePicture);
        AppConfig.updateLotteryQQTitle(qqTitle);
        AppConfig.updateLotteryQQSummary(qqSummary);
        AppConfig.updateLotteryQQIncludePicture(qqIncludePicture);
        AppConfig.updateLotteryQzoneTitle(qzoneTitle);
        AppConfig.updateLotteryQzoneSummary(qzoneSummary);
        AppConfig.updateLotteryQzoneIncludePicture(qzoneIncludePicture);
        //AppConfig.updateNewLotLiveness(newLotLiveness);
        AppConfig.updateShareSucceedLiveness(shareSucceedLiveness);
        AppConfig.updateLotteryRule(lotteryRule);
        AppConfig.updateLotteryAnnouncementTemplate(lotteryAnnouncementTemplate);
        AppConfig.updateNoTelInvalidLotSystemInfoTemplate(noTelInvalidLotSystemInfoTemplate);
        AppConfig.updateInsufficientLivenessInvalidLotSystemInfoTemplate(insufficientLivenessInvalidLotSystemInfoTemplate);

        if (remindNewLotteryChanceByMail != null) {
            AppConfig.updateRemindNewLotteryChanceByMail(true);
            AppConfig.updateNewLotteryChanceMailAccountId(newLotteryChanceMailAccountId);
            AppConfig.updateNewLotteryChanceMailSubjectTemplate(newLotteryChanceMailSubjectTemplate);
            AppConfig.updateNewLotteryChanceMailContentTemplate(newLotteryChanceMailContentTemplate);
        } else {
            AppConfig.updateRemindNewLotteryChanceByMail(false);
        }

        if (remindNewLotteryChanceBySystemInfo != null) {
            AppConfig.updateRemindNewLotteryChanceBySystemInfo(true);
            AppConfig.updateNewLotteryChanceSystemInfoTemplate(newLotteryChanceSystemInfoTemplate);
        } else {
            AppConfig.updateRemindNewLotteryChanceBySystemInfo(false);
        }

        if (remindLivenessIncreaseBySystemInfo != null) {
            AppConfig.updateRemindLivenessIncreaseBySystemInfo(true);
            AppConfig.updateLivenessIncreaseSystemInfoTemplate(livenessIncreaseSystemInfoTemplate);
        } else {
            AppConfig.updateRemindLivenessIncreaseBySystemInfo(false);
        }

        return redirect("admin-lottery-config");
    }
}
