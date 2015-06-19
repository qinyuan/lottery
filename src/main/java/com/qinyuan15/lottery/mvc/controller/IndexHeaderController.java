package com.qinyuan15.lottery.mvc.controller;

import com.qinyuan15.lottery.mvc.AppConfig;
import com.qinyuan15.lottery.mvc.IndexImageGroup;
import com.qinyuan15.lottery.mvc.dao.IndexImage;
import com.qinyuan15.lottery.mvc.dao.NavigationLinkDao;
import com.qinyuan15.utils.mvc.controller.ImageController;

import java.util.List;

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

    protected void setIndexImageGroups() {
        setAttribute("indexImageGroups", adapt(IndexImageGroup.build()));
    }

    private List<IndexImageGroup> adapt(List<IndexImageGroup> indexImageGroups) {
        for (IndexImageGroup indexImageGroup : indexImageGroups) {
            for (IndexImage indexImage : indexImageGroup.getIndexImages()) {
                indexImage.setPath(this.pathToUrl(indexImage.getPath()));
                indexImage.setBackPath(this.pathToUrl(indexImage.getBackPath()));
            }
        }
        return indexImageGroups;
    }
}
