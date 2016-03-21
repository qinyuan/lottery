package com.qinyuan15.lottery.mvc.controller;

import com.qinyuan.lib.mvc.controller.ImageController;
import com.qinyuan15.lottery.mvc.config.AppConfig;

public class RegisterHeaderUtils {
    public static void setParameters(ImageController controller) {
        controller.setAttribute("noFooter", true);
        controller.setAttribute("whiteFooter", true);
        controller.setAttribute("registerHeaderLeftLogo", controller.pathToUrl(AppConfig.getRegisterHeaderLeftLogo()));
        controller.setAttribute("registerHeaderRightLogo", controller.pathToUrl(AppConfig.getRegisterHeaderRightLogo()));
        controller.addCss("register-header");
    }
}
