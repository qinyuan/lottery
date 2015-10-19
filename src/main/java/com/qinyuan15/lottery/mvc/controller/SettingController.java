package com.qinyuan15.lottery.mvc.controller;

import com.qinyuan.lib.mvc.controller.CDNSource;
import com.qinyuan.lib.mvc.controller.ImageController;
import com.qinyuan.lib.mvc.security.User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class SettingController extends ImageController {

    @RequestMapping("/setting")
    public String index() {
        IndexHeaderUtils.setHeaderParameters(this);
        setTitle("设置");

        addCssAndJs("setting");
        addJs(CDNSource.HANDLEBARS);
        return "setting";
    }
}
