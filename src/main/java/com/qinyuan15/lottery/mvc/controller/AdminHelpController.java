package com.qinyuan15.lottery.mvc.controller;

import com.qinyuan15.utils.mvc.controller.BaseController;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class AdminHelpController extends BaseController {

    @RequestMapping("/admin-help")
    public String index() {
        setTitle("");
        addCssAndJs("admin-help");
        return "admin-help";
    }

    /*
    @RequestMapping("/hello-world.json")
    @ResponseBody
    public String json(){
        return success();
    }
    */
}
