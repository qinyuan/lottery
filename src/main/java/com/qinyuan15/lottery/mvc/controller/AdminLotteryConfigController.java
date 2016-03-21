package com.qinyuan15.lottery.mvc.controller;

import com.qinyuan.lib.mvc.controller.CDNSource;
import com.qinyuan.lib.mvc.controller.ImageController;
import com.qinyuan15.lottery.mvc.config.AppConfig;
import com.qinyuan15.lottery.mvc.mail.MailSelectFormItemBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

@Controller
public class AdminLotteryConfigController extends ImageController {
    private final static Logger LOGGER = LoggerFactory.getLogger(AdminLotteryConfigController.class);

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

        // no tel lot
        setAttribute("noTelLotteryLotCount", AppConfig.getNoTelLotteryLotCount());
        setAttribute("noTelLotteryLotPrice", AppConfig.getNoTelLotteryLotPrice());
        setAttribute("noTelLiveness", AppConfig.getNoTelLiveness());

        // tel modification
        setAttribute("maxTelModificationTimes", AppConfig.getMaxTelModificationTimes());

        // other data
        setAttribute("lotteryAnnouncementTemplate", AppConfig.getLotteryAnnouncementTemplate());
        //setAttribute("lotteryRule", AppConfig.getLotteryRule());
        setAttribute("lotteryRuleLink", AppConfig.getLotteryRuleLink());
        setAttribute("noTelInvalidLotSystemInfoTemplate", AppConfig.getNoTelInvalidLotSystemInfoTemplate());
        setAttribute("insufficientLivenessInvalidLotSystemInfoTemplate", AppConfig.getInsufficientLivenessInvalidLotSystemInfoTemplate());

        setAttribute("supportPageImage", pathToUrl(AppConfig.getSupportPageImage()));
        setAttribute("supportPageText", AppConfig.getSupportPageText());

        setTitle("抽奖配置");
        addCss("admin-form");
        addCss("admin");
        addHeadJs("lib/image-adjust");
        addJs("lib/ckeditor/ckeditor", false);
        addJs(CDNSource.BOOTSTRAP_JS, false);
        addCssAndJs("admin-lottery-config");
        return "admin-lottery-config";
    }

    @RequestMapping("/admin-lottery-config-submit")
    public String submit(@RequestParam("sinaWeiboTitle") String sinaWeiboTitle,
                         @RequestParam("sinaWeiboIncludePicture") Boolean sinaWeiboIncludePicture,
                         @RequestParam("qqTitle") String qqTitle,
                         @RequestParam("qqSummary") String qqSummary,
                         @RequestParam("qqIncludePicture") Boolean qqIncludePicture,
                         @RequestParam("qzoneTitle") String qzoneTitle,
                         @RequestParam("qzoneSummary") String qzoneSummary,
                         @RequestParam("qzoneIncludePicture") Boolean qzoneIncludePicture,
                         //@RequestParam(value = "newLotLiveness", required = true) Integer newLotLiveness,
                         @RequestParam("shareSucceedLiveness") Integer shareSucceedLiveness,
                         //@RequestParam(value = "lotteryRule", required = true) String lotteryRule,
                         @RequestParam("lotteryRuleLink") String lotteryRuleLink,
                         @RequestParam(value = "remindNewLotteryChanceByMail", required = false) String remindNewLotteryChanceByMail,
                         @RequestParam("newLotteryChanceMailAccountId") Integer newLotteryChanceMailAccountId,
                         @RequestParam("newLotteryChanceMailSubjectTemplate") String newLotteryChanceMailSubjectTemplate,
                         @RequestParam("newLotteryChanceMailContentTemplate") String newLotteryChanceMailContentTemplate,
                         @RequestParam("lotteryAnnouncementTemplate") String lotteryAnnouncementTemplate,
                         @RequestParam(value = "remindNewLotteryChanceBySystemInfo", required = false) String remindNewLotteryChanceBySystemInfo,
                         @RequestParam("newLotteryChanceSystemInfoTemplate") String newLotteryChanceSystemInfoTemplate,
                         @RequestParam(value = "remindLivenessIncreaseBySystemInfo", required = false) String remindLivenessIncreaseBySystemInfo,
                         @RequestParam("livenessIncreaseSystemInfoTemplate") String livenessIncreaseSystemInfoTemplate,
                         @RequestParam("noTelInvalidLotSystemInfoTemplate") String noTelInvalidLotSystemInfoTemplate,
                         @RequestParam("insufficientLivenessInvalidLotSystemInfoTemplate") String insufficientLivenessInvalidLotSystemInfoTemplate,
                         /*@RequestParam("noTelLotteryLotCount") Integer noTelLotteryLotCount,
                         @RequestParam("noTelLotteryLotPrice") Double noTelLotteryLotPrice,*/
                         @RequestParam("noTelLiveness") Integer noTelLiveness,
                         @RequestParam("maxTelModificationTimes") Integer maxTelModificationTimes,
                         @RequestParam("supportPageImage") String supportPageImage,
                         @RequestParam("supportPageImageFile") MultipartFile supportPageImageFile,
                         @RequestParam("supportPageText") String supportPageText) {

        final String index = "admin-lottery-config.html";

        String supportPageImagePath = null;

        try {
            supportPageImagePath = getSavePath(supportPageImage, supportPageImageFile);
        } catch (Exception e) {
            LOGGER.error("error in getting save path of supportPageImage: {}", e);
            redirect(index, "主页页头左图标处理失败！");
        }

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
        AppConfig.updateLotteryRuleLink(lotteryRuleLink);
        AppConfig.updateLotteryAnnouncementTemplate(lotteryAnnouncementTemplate);
        AppConfig.updateNoTelInvalidLotSystemInfoTemplate(noTelInvalidLotSystemInfoTemplate);
        AppConfig.updateInsufficientLivenessInvalidLotSystemInfoTemplate(insufficientLivenessInvalidLotSystemInfoTemplate);
        /*AppConfig.updateNoTelLotteryLotCount(noTelLotteryLotCount);
        AppConfig.updateNoTelLotteryLotPrice(noTelLotteryLotPrice);*/
        AppConfig.updateNoTelLiveness(noTelLiveness);
        AppConfig.updateMaxTelModificationTimes(maxTelModificationTimes);
        AppConfig.updateSupportPageImage(supportPageImagePath);
        AppConfig.updateSupportPageText(supportPageText);

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

        return redirect(index);
    }
}
