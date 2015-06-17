package com.qinyuan15.lottery.mvc.controller;

import com.qinyuan15.lottery.mvc.AppConfig;
import com.qinyuan15.lottery.mvc.dao.NavigationLink;
import com.qinyuan15.lottery.mvc.dao.NavigationLinkDao;
import com.qinyuan15.utils.config.LinkAdapter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

@Controller
public class AdminController extends IndexHeaderController {
    private final static Logger LOGGER = LoggerFactory.getLogger(AdminController.class);

    @RequestMapping("/admin")
    public String index() {
        setHeaderParameters();

        setTitle("用户管理");
        addCss("resources/js/lib/bootstrap/css/bootstrap.min", false);
        addJs("resources/js/lib/handlebars.min-v1.3.0", false);
        addHeadJs("lib/image-adjust.js");
        addCssAndJs("admin");

        //addJavaScriptData("indexHeaderLinks", new NavigationLinkDao().getInstances());
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
                         @RequestParam(value = "headerLinkHrefs", required = true) String[] headerLinkHrefs) {
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

    /*
    @RequestMapping("/hello-world.json")
    @ResponseBody
    public String json(){
        return success();
    }
    */
}
