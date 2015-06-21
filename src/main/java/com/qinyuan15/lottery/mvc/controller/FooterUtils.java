package com.qinyuan15.lottery.mvc.controller;

import com.qinyuan15.lottery.mvc.AppConfig;
import com.qinyuan15.utils.mvc.controller.ImageController;

/**
 * Utility class about footer
 * Created by qinyuan on 15-6-21.
 */
public class FooterUtils {
    static void setFooterParameters(ImageController controller) {
        controller.setAttribute("footerPoster", controller.pathToUrl(AppConfig.getFooterPoster()));
        controller.setAttribute("footerText", controller.pathToUrl(AppConfig.getFooterText()));
    }
}
