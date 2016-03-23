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
        setAttribute("sinaWeiboTitle", AppConfig.lottery.getSinaWeiboTitle());
        setAttribute("sinaWeiboIncludePicture", AppConfig.lottery.getSinaWeiboIncludePicture());

        // data about qq
        setAttribute("qqTitle", AppConfig.lottery.getQQTitle());
        setAttribute("qqSummary", AppConfig.lottery.getQQSummary());
        setAttribute("qqIncludePicture", AppConfig.lottery.getQQIncludePicture());

        // data about qzone
        setAttribute("qzoneTitle", AppConfig.lottery.getQzoneTitle());
        setAttribute("qzoneSummary", AppConfig.lottery.getQzoneSummary());
        setAttribute("qzoneIncludePicture", AppConfig.lottery.getQzoneIncludePicture());

        // data about new lottery chance
        setAttribute("remindNewLotteryChanceByMail", AppConfig.lottery.getRemindNewChanceByMail());
        setAttribute("newLotteryChanceMailSubjectTemplate", AppConfig.lottery.getNewChanceMailSubjectTemplate());
        setAttribute("newLotteryChanceMailContentTemplate", AppConfig.lottery.getNewChanceMailContentTemplate());
        addJavaScriptData("currentNewLotteryChanceMailAccountId", AppConfig.lottery.getNewChanceMailAccountId());
        setAttribute("remindNewLotteryChanceBySystemInfo", AppConfig.lottery.getRemindNewChanceBySystemInfo());
        setAttribute("newLotteryChanceSystemInfoTemplate", AppConfig.lottery.getNewChanceSystemInfoTemplate());

        // data about liveness
        //setAttribute("newLotLiveness", AppConfig.getNewLotLiveness());
        setAttribute("shareSucceedLiveness", AppConfig.liveness.getShareSucceedLiveness());
        setAttribute("remindLivenessIncreaseBySystemInfo", AppConfig.liveness.getRemindIncreaseBySystemInfo());
        setAttribute("livenessIncreaseSystemInfoTemplate", AppConfig.liveness.getIncreaseSystemInfoTemplate());

        // data about mail account
        setAttribute("mailSelectFormItems", new MailSelectFormItemBuilder().build());

        // no tel lot
        setAttribute("noTelLotteryLotCount", AppConfig.lottery.getNoTelLotCount());
        setAttribute("noTelLotteryLotPrice", AppConfig.lottery.getNoTelLotPrice());
        setAttribute("noTelLiveness", AppConfig.lottery.getNoTelLiveness());

        // tel modification
        setAttribute("maxTelModificationTimes", AppConfig.getMaxTelModificationTimes());

        // other data
        setAttribute("lotteryAnnouncementTemplate", AppConfig.lottery.getAnnouncementTemplate());
        //setAttribute("lotteryRule", AppConfig.getLotteryRule());
        setAttribute("lotteryRuleLink", AppConfig.lottery.getRuleLink());
        setAttribute("noTelInvalidLotSystemInfoTemplate", AppConfig.lottery.getNoTelInvalidLotSystemInfoTemplate());
        setAttribute("insufficientLivenessInvalidLotSystemInfoTemplate",
                AppConfig.lottery.getInsufficientLivenessInvalidLotSystemInfoTemplate());

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

        AppConfig.lottery.updateSinaWeiboTitle(sinaWeiboTitle);
        AppConfig.lottery.updateSinaWeiboIncludePicture(sinaWeiboIncludePicture);
        AppConfig.lottery.updateQQTitle(qqTitle);
        AppConfig.lottery.updateQQSummary(qqSummary);
        AppConfig.lottery.updateQQIncludePicture(qqIncludePicture);
        AppConfig.lottery.updateQzoneTitle(qzoneTitle);
        AppConfig.lottery.updateQzoneSummary(qzoneSummary);
        AppConfig.lottery.updateQzoneIncludePicture(qzoneIncludePicture);
        //AppConfig.updateNewLotLiveness(newLotLiveness);
        AppConfig.lottery.updateRuleLink(lotteryRuleLink);
        AppConfig.lottery.updateAnnouncementTemplate(lotteryAnnouncementTemplate);

        AppConfig.liveness.updateShareSucceedLiveness(shareSucceedLiveness);
        AppConfig.lottery.updateNoTelInvalidLotSystemInfoTemplate(noTelInvalidLotSystemInfoTemplate);
        AppConfig.lottery.updateInsufficientLivenessInvalidLotSystemInfoTemplate(
                insufficientLivenessInvalidLotSystemInfoTemplate);
        /*AppConfig.updateNoTelLotteryLotCount(noTelLotteryLotCount);
        AppConfig.updateNoTelLotteryLotPrice(noTelLotteryLotPrice);*/
        AppConfig.lottery.updateNoTelLiveness(noTelLiveness);
        AppConfig.updateMaxTelModificationTimes(maxTelModificationTimes);
        AppConfig.updateSupportPageImage(supportPageImagePath);
        AppConfig.updateSupportPageText(supportPageText);

        if (remindNewLotteryChanceByMail != null) {
            AppConfig.lottery.updateRemindNewChanceByMail(true);
            AppConfig.lottery.updateNewChanceMailAccountId(newLotteryChanceMailAccountId);
            AppConfig.lottery.updateNewChanceMailSubjectTemplate(newLotteryChanceMailSubjectTemplate);
            AppConfig.lottery.updateNewChanceMailContentTemplate(newLotteryChanceMailContentTemplate);
        } else {
            AppConfig.lottery.updateRemindNewChanceByMail(false);
        }

        if (remindNewLotteryChanceBySystemInfo != null) {
            AppConfig.lottery.updateRemindNewChanceBySystemInfo(true);
            AppConfig.lottery.updateNewChanceSystemInfoTemplate(newLotteryChanceSystemInfoTemplate);
        } else {
            AppConfig.lottery.updateRemindNewChanceBySystemInfo(false);
        }

        if (remindLivenessIncreaseBySystemInfo != null) {
            AppConfig.liveness.updateRemindIncreaseBySystemInfo(true);
            AppConfig.liveness.updateIncreaseSystemInfoTemplate(livenessIncreaseSystemInfoTemplate);
        } else {
            AppConfig.liveness.updateRemindIncreaseBySystemInfo(false);
        }

        return redirect(index);
    }
}
