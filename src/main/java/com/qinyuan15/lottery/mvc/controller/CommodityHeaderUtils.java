package com.qinyuan15.lottery.mvc.controller;

import com.qinyuan15.lottery.mvc.AppConfig;
import com.qinyuan15.utils.mvc.controller.ImageController;

/**
 * Utility class about footer
 * Created by qinyuan on 15-6-21.
 */
public class CommodityHeaderUtils {
    static void setHeaderParameters(ImageController controller) {
        controller.setAttribute("commodityHeaderLeftLogo", controller.pathToUrl(AppConfig.getCommodityHeaderLeftLogo()));
    }
}
