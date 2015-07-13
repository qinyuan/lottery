package com.qinyuan15.lottery.mvc.controller;

import com.qinyuan15.utils.mvc.controller.BaseController;
import com.qinyuan15.utils.mvc.controller.ImageController;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class AdminLotteryStatisticController extends ImageController {

    @RequestMapping("/admin-lottery-statistic")
    public String index() {
        IndexHeaderUtils.setHeaderParameters(this);

        setTitle("抽奖统计");
        addCss("admin-form");
        addCssAndJs("admin-lottery-statistic");
        return "admin-lottery-statistic";
    }

    /*
    @RequestMapping("/hello-world.json")
    @ResponseBody
    public String json(){
        return success();
    }
    */
}
