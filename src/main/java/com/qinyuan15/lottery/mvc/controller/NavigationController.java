package com.qinyuan15.lottery.mvc.controller;

import com.qinyuan.lib.mvc.controller.BaseController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class NavigationController extends BaseController {
    @RequestMapping("/navigation")
    public String index() {
        return "security-navigation";
    }
}
