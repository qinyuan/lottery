package com.qinyuan15.lottery.mvc.controller;

import com.qinyuan15.lottery.mvc.RichHelpGroup;
import com.qinyuan15.utils.mvc.controller.ImageController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class HelpController extends ImageController {

    @RequestMapping("/help")
    public String index() {
        IndexHeaderUtils.setHeaderParameters(this);

        setAttribute("helpGroups", RichHelpGroup.getInstances());

        setTitle("帮助中心");
        addCssAndJs("help");
        return "help";
    }
}
