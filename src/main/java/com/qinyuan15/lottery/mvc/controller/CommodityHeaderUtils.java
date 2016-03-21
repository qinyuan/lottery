package com.qinyuan15.lottery.mvc.controller;

import com.qinyuan.lib.mvc.controller.ImageController;
import com.qinyuan15.lottery.mvc.config.AppConfig;

/**
 * Utility class about footer
 * Created by qinyuan on 15-6-21.
 */
public class CommodityHeaderUtils {
    static void setHeaderParameters(ImageController controller) {
        controller.setAttribute("commodityHeaderLeftLogo", controller.pathToUrl(AppConfig.getCommodityHeaderLeftLogo()));
    }
}
