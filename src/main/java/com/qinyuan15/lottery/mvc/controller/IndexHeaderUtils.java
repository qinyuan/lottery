package com.qinyuan15.lottery.mvc.controller;

import com.qinyuan.lib.mvc.controller.ImageController;
import com.qinyuan15.lottery.mvc.config.AppConfig;
import com.qinyuan15.lottery.mvc.IndexImageGroup;
import com.qinyuan15.lottery.mvc.dao.IndexImage;
import com.qinyuan15.lottery.mvc.dao.NavigationLinkDao;

import java.util.List;

/**
 * Utility class of IndexHeader
 * Created by qinyuan on 15-6-21.
 */
public class IndexHeaderUtils {
    static void setHeaderParameters(ImageController controller) {
        controller.setAttribute("indexHeaderLeftLogo", controller.pathToUrl(AppConfig.getIndexHeaderLeftLogo()));
        controller.setAttribute("indexHeaderSlogan", controller.pathToUrl(AppConfig.getIndexHeaderSlogan()));

        controller.setAttribute("indexHeaderLinks", new NavigationLinkDao().getInstances());
        controller.addCssAndJs("index-header");
    }

    static void setIndexImageGroups(ImageController controller) {
        controller.setAttribute("indexImageGroups", adapt(controller, IndexImageGroup.build()));
    }

    static List<IndexImageGroup> adapt(ImageController controller, List<IndexImageGroup> indexImageGroups) {
        for (IndexImageGroup indexImageGroup : indexImageGroups) {
            for (IndexImage indexImage : indexImageGroup.getIndexImages()) {
                adapt(controller, indexImage);
            }
        }
        return indexImageGroups;
    }

    static IndexImage adapt(ImageController controller, IndexImage indexImage) {
        indexImage.setPath(controller.pathToUrl(indexImage.getPath()));
        indexImage.setBackPath(controller.pathToUrl(indexImage.getBackPath()));
        return indexImage;
    }
}
