package com.qinyuan15.lottery.mvc.controller;

import com.qinyuan15.lottery.mvc.AppConfig;
import com.qinyuan15.lottery.mvc.dao.NavigationLinkDao;
import com.qinyuan15.utils.mvc.controller.ImageController;

/**
 * Controller with index header
 * Created by qinyuan on 15-6-16.
 */
abstract class IndexHeaderController extends ImageController {
    protected void setHeaderParameters() {
        setAttribute("indexHeaderLeftLogo", pathToUrl(AppConfig.getIndexHeaderLeftLogo()));
        setAttribute("indexHeaderRightLogo", pathToUrl(AppConfig.getIndexHeaderRightLogo()));
        setAttribute("indexHeaderSlogan", pathToUrl(AppConfig.getIndexHeaderSlogan()));

        setAttribute("indexHeaderLinks", new NavigationLinkDao().getInstances());
        addCssAndJs("index-header");
    }
}
