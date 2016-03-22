package com.qinyuan15.lottery.mvc.controller;

import com.qinyuan.lib.mvc.controller.ImageController;
import com.qinyuan15.lottery.mvc.config.AppConfig;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class ForumController extends ImageController {

    @RequestMapping("/forum")
    public String index() {
        CommodityHeaderUtils.setHeaderParameters(this);

        setAttribute("forumImage", pathToUrl(AppConfig.sys.getForumImage()));
        setAttribute("noFooter", true);
        setAttribute("whiteFooter", true);
        setTitle("论坛");
        addCss("commodity-header");
        addCssAndJs("forum");
        return "forum";
    }
}
