package com.qinyuan15.lottery.mvc.controller;

import com.qinyuan.lib.mvc.controller.ImageController;
import com.qinyuan15.lottery.mvc.config.AppConfig;

public class SubscribeHeaderUtils {
    static void setHeaderParameters(ImageController controller) {
        controller.setAttribute("qqlistId", AppConfig.getQQListId());
        controller.setAttribute("qqlistDescription", AppConfig.getQQListDescription());
    }
}
