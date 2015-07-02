package com.qinyuan15.lottery.mvc.controller;

import com.qinyuan15.lottery.mvc.AppConfig;
import com.qinyuan15.lottery.mvc.dao.NavigationLink;
import com.qinyuan15.lottery.mvc.dao.NavigationLinkDao;
import com.qinyuan15.utils.config.LinkAdapter;
import com.qinyuan15.utils.mvc.controller.ImageController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
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

        setAttribute("activateMailAccount", AppConfig.getActivateMailAccount());
        setAttribute("activateMailSubjectTemplate", AppConfig.getActivateMailSubjectTemplate());
        setAttribute("activateMailContentTemplate", AppConfig.getActivateMailContentTemplate());

        setTitle("系统设置");
        addCss("admin-form");
        addJs("resources/js/lib/handlebars.min-v1.3.0", false);
        addHeadJs("lib/image-adjust.js");
        addJs("lib/ckeditor/ckeditor");
        addCssAndJs("admin");
        return "admin";
    }

    @RequestMapping("/admin-submit")
    public String submit(@RequestParam(value = "indexHeaderLeftLogo", required = true) String indexHeaderLeftLogo,
                         @RequestParam(value = "indexHeaderLeftLogoFile", required = true) MultipartFile indexHeaderLeftLogoFile,
                         @RequestParam(value = "indexHeaderRightLogo", required = true) String indexHeaderRightLogo,
                         @RequestParam(value = "indexHeaderRightLogoFile", required = true) MultipartFile indexHeaderRightLogoFile,
                         @RequestParam(value = "indexHeaderSlogan", required = true) String indexHeaderSlogan,
                         @RequestParam(value = "indexHeaderSloganFile", required = true) MultipartFile indexHeaderSloganFile,
                         @RequestParam(value = "headerLinkTitles", required = true) String[] headerLinkTitles,
                         @RequestParam(value = "headerLinkHrefs", required = true) String[] headerLinkHrefs,
                         @RequestParam(value = "footerPoster", required = true) String footerPoster,
                         @RequestParam(value = "footerPosterFile", required = true) MultipartFile footerPosterFile,
                         @RequestParam(value = "footerText", required = true) String footerText,
                         @RequestParam(value = "commodityHeaderLeftLogo", required = true) String commodityHeaderLeftLogo,
                         @RequestParam(value = "commodityHeaderLeftLogoFile", required = true) MultipartFile commodityHeaderLeftLogoFile,
                         @RequestParam(value = "favicon", required = true) String favicon,
                         @RequestParam(value = "faviconFile", required = true) MultipartFile faviconFile,
                         @RequestParam(value = "activateMailHost", required = true) String activateMailHost,
                         @RequestParam(value = "activateMailUsername", required = true) String activateMailUsername,
                         @RequestParam(value = "activateMailPassword", required = true) String activateMailPassword,
                         @RequestParam(value = "activateMailSubjectTemplate", required = true) String activateMailSubjectTemplate,
                         @RequestParam(value = "activateMailContentTemplate", required = true) String activateMailContentTemplate) {

        final String redirectPage = "admin";

        String indexHeaderLeftLogoPath = null, indexHeaderRightLogoPath = null,
                indexHeaderSloganPath = null, footerPosterPath = null,
                commodityHeaderLeftLogoPath = null, faviconPath = null;
        try {
            indexHeaderLeftLogoPath = getSavePath(indexHeaderLeftLogo, indexHeaderLeftLogoFile);
        } catch (Exception e) {
            LOGGER.error("error in getting save path of indexHeaderLeftLogo: {}", e);
            redirect(redirectPage, "主页页头左图标处理失败！");
        }

        try {
            indexHeaderRightLogoPath = getSavePath(indexHeaderRightLogo, indexHeaderRightLogoFile);
        } catch (Exception e) {
            LOGGER.error("error in getting save path of indexHeaderRightLogo: {}", e);
            redirect(redirectPage, "主页页头右图标处理失败！");
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
            faviconPath = getSavePath(favicon, faviconFile);
        } catch (Exception e) {
            LOGGER.error("error in getting save path of favicon");
            redirect(redirectPage, "网站图标处理失败！");
        }

        AppConfig.updateIndexHeaderLeftLogo(indexHeaderLeftLogoPath);
        AppConfig.updateIndexHeaderRightLogo(indexHeaderRightLogoPath);
        AppConfig.updateIndexHeaderSlogan(indexHeaderSloganPath);
        AppConfig.updateFooterPoster(footerPosterPath);
        AppConfig.updateFooterText(footerText);
        AppConfig.updateCommodityHeaderLeftLogo(commodityHeaderLeftLogoPath);
        AppConfig.updateFavicon(faviconPath);
        AppConfig.updateActivateMailAccount(activateMailHost, activateMailUsername, activateMailPassword);
        AppConfig.updateActivateMailSubjectTemplate(activateMailSubjectTemplate);
        AppConfig.updateActivateMailContentTemplate(activateMailContentTemplate);
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
}
