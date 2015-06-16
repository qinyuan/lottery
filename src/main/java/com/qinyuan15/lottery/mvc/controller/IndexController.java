package com.qinyuan15.lottery.mvc.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class IndexController extends IndexHeaderController {

    @RequestMapping("/index")
    public String index() {
        setHeaderParameters();

        setTitle("首页");
        addCss("index");
        return "index";
    }

    /*
    @RequestMapping("/hello-world.json")
    @ResponseBody
    public String json(){
        return success();
    }
    */
}
