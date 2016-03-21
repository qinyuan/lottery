package com.qinyuan15.lottery.mvc.controller;

import com.qinyuan.lib.config.LinkAdapter;
import com.qinyuan.lib.contact.mail.*;
import com.qinyuan.lib.lang.IntegerUtils;
import com.qinyuan.lib.mvc.controller.CDNSource;
import com.qinyuan.lib.mvc.controller.ImageController;
import com.qinyuan15.lottery.mvc.config.AppConfig;
import com.qinyuan15.lottery.mvc.dao.MailAccountReferenceValidator;
import com.qinyuan15.lottery.mvc.dao.NavigationLink;
import com.qinyuan15.lottery.mvc.dao.NavigationLinkDao;
import com.qinyuan15.lottery.mvc.mail.MailSelectFormItemBuilder;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

@Controller
public class AdminController extends ImageController {
    private final static Logger LOGGER = LoggerFactory.getLogger(AdminController.class);

    @RequestMapping("/admin")
    public String index() {
        IndexHeaderUtils.setHeaderParameters(this);
        CommodityHeaderUtils.setHeaderParameters(this);

        setAttribute("registerMailSubjectTemplate", AppConfig.getRegisterMailSubjectTemplate());
        setAttribute("registerMailContentTemplate", AppConfig.getRegisterMailContentTemplate());
        addJavaScriptData("currentRegisterMailAccountId", AppConfig.getRegisterMailAccountId());
        setAttribute("registerHeaderLeftLogo", pathToUrl(AppConfig.getRegisterHeaderLeftLogo()));
        setAttribute("registerHeaderRightLogo", pathToUrl(AppConfig.getRegisterHeaderRightLogo()));

        setAttribute("resetPasswordMailSubjectTemplate", AppConfig.getResetPasswordMailSubjectTemplate());
        setAttribute("resetPasswordMailContentTemplate", AppConfig.getResetPasswordMailContentTemplate());
        addJavaScriptData("currentResetPasswordMailAccountId", AppConfig.getResetPasswordMailAccountId());

        setAttribute("resetEmailMailSubjectTemplate", AppConfig.getResetEmailMailSubjectTemplate());
        setAttribute("resetEmailMailContentTemplate", AppConfig.getResetEmailMailContentTemplate());
        addJavaScriptData("currentResetEmailMailAccountId", AppConfig.getResetEmailMailAccountId());

        setAttribute("telValidateDescriptionPage", AppConfig.getTelValidateDescriptionPage());
        setAttribute("websiteIntroductionLink", AppConfig.getWebsiteIntroductionLink());

        setAttribute("forumImage", pathToUrl(AppConfig.getForumImage()));

        List<MailAccount> accounts = new MailAccountDao().getInstances();
        setAttribute("mails", accounts);
        setAttribute("mailSelectFormItems", new MailSelectFormItemBuilder().build(accounts));

        setTitle("系统设置");
        addCss("admin-form");
        addJs(CDNSource.HANDLEBARS_JS, false);
        addJs("lib/ckeditor/ckeditor", false);
        addJs(CDNSource.BOOTSTRAP_JS, false);
        addHeadJs("lib/image-adjust.js");
        addCssAndJs("admin");
        return "admin";
    }

    @RequestMapping("/admin-query-mail-account.json")
    @ResponseBody
    public String queryMail(@RequestParam(value = "id", required = true) Integer id) {
        try {
            return toJson(new MailAccountDao().getReference(id));
        } catch (Exception e) {
            return failByDatabaseError();
        }
    }

    @RequestMapping("/admin-add-edit-mail-account.json")
    @ResponseBody
    public String addEditMailAccount(@RequestParam(value = "id", required = false) Integer id,
                                     @RequestParam(value = "type", required = true) String type,
                                     @RequestParam(value = "username", required = false) String username,
                                     @RequestParam(value = "host", required = false) String host,
                                     @RequestParam(value = "password", required = false) String password,
                                     @RequestParam(value = "user", required = false) String user,
                                     @RequestParam(value = "domainName", required = false) String domainName,
                                     @RequestParam(value = "apiKey", required = false) String apiKey) {

        if (StringUtils.isBlank(type)) {
            return failByInvalidParam();
        }

        Integer referenceId = new MailAccountDao().getReferenceId(id);
        switch (type) {
            case "SimpleMailAccount":
                return addEditSimpleMailAccount(referenceId, username, host, password);
            case "SendCloudAccount":
                return addEditSendCloudAccount(referenceId, user, domainName, apiKey);
            default:
                return failByInvalidParam();
        }
    }

    private String addEditSimpleMailAccount(Integer id, String username, String host, String password) {
        if (StringUtils.isBlank(host)) {
            return fail("发件箱服务器地址不能为空！");
        }

        SimpleMailAccountDao dao = new SimpleMailAccountDao();

        // if add mail account, validate username
        if (!IntegerUtils.isPositive(id)) {
            if (StringUtils.isBlank(username)) {
                return fail("发件箱用户名不能为空！");
            } else if (!new MailAddressValidator().validate(username)) {
                return fail("发件箱用户名必须为有效的邮件地址！");
            } else if (dao.hasUsername(username)) {
                return fail("用户名'" + username + "'已经存在！");
            }
        }

        if (StringUtils.isBlank(password)) {
            return fail("发件箱密码不能为空！");
        }

        try {
            if (IntegerUtils.isPositive(id)) {
                dao.update(id, host, password);
            } else {
                dao.add(host, username, password);
            }
            return success();
        } catch (Exception e) {
            return failByDatabaseError();
        }
    }

    private String addEditSendCloudAccount(Integer id, String user, String domainName, String apiKey) {

        if (StringUtils.isBlank(user)) {
            return fail("用户名不能为空！");
        } else if (user.contains("@")) {
            return fail("用户名不能包含'@'字符！");
        } else if (StringUtils.isBlank(domainName)) {
            return fail("域名不能为空！");
        } else if (domainName.contains("@")) {
            return fail("域名不能包含'@'字符！");
        } else if (StringUtils.isBlank(apiKey)) {
            return fail("apiKey不能为空！");
        }
        try {
            SendCloudAccountDao dao = new SendCloudAccountDao();
            if (IntegerUtils.isPositive(id)) {
                dao.update(id, user, domainName, apiKey);
            } else {
                dao.add(user, domainName, apiKey);
            }
            return success();
        } catch (Exception e) {
            return failByDatabaseError();
        }
    }

    @RequestMapping("/admin-delete-mail.json")
    @ResponseBody
    public String deleteEmail(@RequestParam(value = "id", required = true) Integer id) {
        try {
            if (new MailAccountReferenceValidator().isUsed(id)) {
                return fail("该邮箱账户已经发过邮件，不能将其删除！");
            }
            new MailAccountDao().delete(id);
            return success();
        } catch (Exception e) {
            LOGGER.error("Fail to delete mail. id: {}, info: {}", id, e);
            return failByDatabaseError();
        }
    }

    @RequestMapping("/admin-submit")
    public String submit(@RequestParam("indexHeaderLeftLogo") String indexHeaderLeftLogo,
                         @RequestParam("indexHeaderLeftLogoFile") MultipartFile indexHeaderLeftLogoFile,
                         @RequestParam("indexHeaderSlogan") String indexHeaderSlogan,
                         @RequestParam("indexHeaderSloganFile") MultipartFile indexHeaderSloganFile,
                         @RequestParam("headerLinkTitles") String[] headerLinkTitles,
                         @RequestParam("headerLinkHrefs") String[] headerLinkHrefs,
                         @RequestParam("footerPoster") String footerPoster,
                         @RequestParam("footerPosterFile") MultipartFile footerPosterFile,
                         @RequestParam("footerText") String footerText,
                         @RequestParam("commodityHeaderLeftLogo") String commodityHeaderLeftLogo,
                         @RequestParam("commodityHeaderLeftLogoFile") MultipartFile commodityHeaderLeftLogoFile,
                         @RequestParam("favicon") String favicon,
                         @RequestParam("faviconFile") MultipartFile faviconFile,
                         @RequestParam("registerMailAccountId") Integer registerMailAccountId,
                         @RequestParam("registerMailSubjectTemplate") String registerMailSubjectTemplate,
                         @RequestParam("registerMailContentTemplate") String registerMailContentTemplate,
                         @RequestParam("registerHeaderLeftLogo") String registerHeaderLeftLogo,
                         @RequestParam("registerHeaderLeftLogoFile") MultipartFile registerHeaderLeftLogoFile,
                         @RequestParam("registerHeaderRightLogo") String registerHeaderRightLogo,
                         @RequestParam("registerHeaderRightLogoFile") MultipartFile registerHeaderRightLogoFile,
                         @RequestParam("resetPasswordMailAccountId") Integer resetPasswordMailAccountId,
                         @RequestParam("resetPasswordMailSubjectTemplate") String resetPasswordMailSubjectTemplate,
                         @RequestParam("resetPasswordMailContentTemplate") String resetPasswordMailContentTemplate,
                         @RequestParam("resetEmailMailAccountId") Integer resetEmailMailAccountId,
                         @RequestParam("resetEmailMailSubjectTemplate") String resetEmailMailSubjectTemplate,
                         @RequestParam("resetEmailMailContentTemplate") String resetEmailMailContentTemplate,
                         @RequestParam("telValidateDescriptionPage") String telValidateDescriptionPage,
                         @RequestParam("websiteIntroductionLink") String websiteIntroductionLink,
                         @RequestParam("forumImage") String forumImage,
                         @RequestParam("forumImageFile") MultipartFile forumImageFile) {

        final String redirectPage = "admin";

        String indexHeaderLeftLogoPath = null, indexHeaderSloganPath = null, footerPosterPath = null,
                commodityHeaderLeftLogoPath = null, faviconPath = null, registerHeaderLeftLogoPath = null,
                registerHeaderRightLogoPath = null, forumImagePath = null;
        try {
            indexHeaderLeftLogoPath = getSavePath(indexHeaderLeftLogo, indexHeaderLeftLogoFile);
        } catch (Exception e) {
            LOGGER.error("error in getting save path of indexHeaderLeftLogo: {}", e);
            redirect(redirectPage, "主页页头左图标处理失败！");
        }

        try {
            indexHeaderSloganPath = getSavePath(indexHeaderSlogan, indexHeaderSloganFile);
        } catch (Exception e) {
            LOGGER.error("error in getting save path of indexHeaderSlogan: {}", e);
            redirect(redirectPage, "主页页头右侧宣传图片处理失败！");
        }

        try {
            footerPosterPath = getSavePath(footerPoster, footerPosterFile);
        } catch (Exception e) {
            LOGGER.error("error in getting save path of footerPoster: {}", e);
            redirect(redirectPage, "主页页尾图片处理失败！");
        }

        try {
            commodityHeaderLeftLogoPath = getSavePath(commodityHeaderLeftLogo, commodityHeaderLeftLogoFile);
        } catch (Exception e) {
            LOGGER.error("error in getting save path of commodityHeaderLeftLogo");
            redirect(redirectPage, "商品页页头左图标处理失败！");
        }

        try {
            registerHeaderLeftLogoPath = getSavePath(registerHeaderLeftLogo, registerHeaderLeftLogoFile);
        } catch (Exception e) {
            LOGGER.error("error in getting save path of registerHeaderLeftLogo");
        }

        try {
            registerHeaderRightLogoPath = getSavePath(registerHeaderRightLogo, registerHeaderRightLogoFile);
        } catch (Exception e) {
            LOGGER.error("error in getting save path of registerHeaderRightLogo");
        }

        try {
            faviconPath = getSavePath(favicon, faviconFile);
        } catch (Exception e) {
            LOGGER.error("error in getting save path of favicon");
            redirect(redirectPage, "网站图标处理失败！");
        }

        try {
            forumImagePath = getSavePath(forumImage, forumImageFile);
        } catch (Exception e) {
            LOGGER.error("error in getting save path of favicon");
            redirect(redirectPage, "论坛页图片处理失败！");
        }

        AppConfig.updateIndexHeaderLeftLogo(indexHeaderLeftLogoPath);
        AppConfig.updateIndexHeaderSlogan(indexHeaderSloganPath);
        AppConfig.updateFooterPoster(footerPosterPath);
        AppConfig.updateFooterText(footerText);
        AppConfig.updateCommodityHeaderLeftLogo(commodityHeaderLeftLogoPath);
        AppConfig.updateFavicon(faviconPath);

        AppConfig.updateRegisterMailAccountId(registerMailAccountId);
        AppConfig.updateRegisterMailSubjectTemplate(registerMailSubjectTemplate);
        AppConfig.updateRegisterMailContentTemplate(registerMailContentTemplate);
        AppConfig.updateRegisterHeaderLeftLogo(registerHeaderLeftLogoPath);
        AppConfig.updateRegisterHeaderRightLogo(registerHeaderRightLogoPath);

        AppConfig.updateResetPasswordMailAccountId(resetPasswordMailAccountId);
        AppConfig.updateResetPasswordMailSubjectTemplate(resetPasswordMailSubjectTemplate);
        AppConfig.updateResetPasswordMailContentTemplate(resetPasswordMailContentTemplate);

        AppConfig.updateResetEmailMailAccountId(resetEmailMailAccountId);
        AppConfig.updateResetEmailMailSubjectTemplate(resetEmailMailSubjectTemplate);
        AppConfig.updateResetEmailMailContentTemplate(resetEmailMailContentTemplate);

        AppConfig.updateTelValidateDescriptionPage(telValidateDescriptionPage);
        AppConfig.updateWebsiteIntroductionLink(websiteIntroductionLink);

        AppConfig.updateForumImage(forumImagePath);

        new NavigationLinkDao().clearAndSave(buildNavigationLinks(headerLinkTitles, headerLinkHrefs));

        return redirect("admin");
    }

    private List<NavigationLink> buildNavigationLinks(String[] titles, String[] hrefs) {
        if (titles == null) {
            titles = new String[0];
        }
        if (hrefs == null) {
            hrefs = new String[0];
        }

        int size = Math.min(titles.length, hrefs.length);

        List<NavigationLink> navigationLinks = new ArrayList<>();
        LinkAdapter linkAdapter = new LinkAdapter();
        for (int i = 0; i < size; i++) {
            NavigationLink navigationLink = new NavigationLink();
            navigationLink.setTitle(titles[i]);
            navigationLink.setHref(linkAdapter.adjust(hrefs[i]));
            navigationLinks.add(navigationLink);
        }
        return navigationLinks;
    }

    /*@RequestMapping("ckeditor-image-upload2")
    public void index(HttpServletResponse response) throws Exception {
        PrintWriter out = response.getWriter();
        String imageUrl = "https://ss0.bdstatic.com/5aV1bjqh_Q23odCf/static/superman/img/logo/bd_logo1_31bdc765.png";
        out.print("<script type=\"text/javascript\">window.parent.CKEDITOR.tools.callFunction(1,'" + imageUrl + "','')</script>");
    }*/
}
