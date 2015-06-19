package com.qinyuan15.lottery.mvc.controller;

import com.qinyuan15.lottery.mvc.AppConfig;
import com.qinyuan15.lottery.mvc.dao.IndexImageMapDao;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class IndexController extends IndexHeaderController {

    @RequestMapping("/index")
    public String index() {
        setHeaderParameters();
        setIndexImageGroups();

        setAttribute("indexImageMaps", new IndexImageMapDao().getInstancesAndGroupByImageId());
        addJavaScriptData("cycleInterval", AppConfig.getIndexImageCycleInterval());

        setTitle("首页");
        addCssAndJs("index");
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
