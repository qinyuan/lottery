package com.qinyuan15.lottery.mvc.controller;

import com.qinyuan.lib.mvc.controller.ImageController;
import com.qinyuan15.lottery.mvc.config.AppConfig;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class AdminSubscribeConfigController extends ImageController {
    private final static Logger LOGGER = LoggerFactory.getLogger(AdminSubscribeConfigController.class);

    @RequestMapping("/admin-subscribe-config")
    public String index() {
        IndexHeaderUtils.setHeaderParameters(this);
        SubscribeHeaderUtils.setHeaderParameters(this);

        setTitle("订阅邮件配置");
        addCss("admin-form");
        addCss("admin");
        addJs("lib/ckeditor/ckeditor", false);
        addCssAndJs("admin-subscribe-config");
        return "admin-subscribe-config";
    }

    @RequestMapping("/admin-subscribe-config-submit.json")
    @ResponseBody
    public String json(@RequestParam(value = "qqlistId", required = true) String qqlistId,
                       @RequestParam(value = "qqlistDescription", required = true) String qqlistDescription) {
        if (StringUtils.isBlank(qqlistId)) {
            return fail("qqlist的ID不能为空");
        }

        try {
            AppConfig.qqlist.updateId(qqlistId);
            AppConfig.qqlist.updateDescription(qqlistDescription);
            return success();
        } catch (Exception e) {
            LOGGER.error("fail to update subscribe config, id: {}, description: {}, info: {}"
                    , qqlistId, qqlistDescription, e);
            return failByDatabaseError();
        }
    }
}
