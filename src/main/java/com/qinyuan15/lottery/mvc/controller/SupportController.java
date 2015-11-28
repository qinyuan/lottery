package com.qinyuan15.lottery.mvc.controller;

import com.qinyuan.lib.mvc.controller.BaseController;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class SupportController extends BaseController {

    @RequestMapping("/support")
    public String index() {
        setTitle("");
        addCssAndJs("support");
        return "support";
    }

    /*
    @RequestMapping("/hello-world.json")
    @ResponseBody
    public String json(){
        return success();
    }
    */
}
