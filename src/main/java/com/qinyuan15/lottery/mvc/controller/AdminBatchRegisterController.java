package com.qinyuan15.lottery.mvc.controller;

import com.qinyuan.lib.mvc.controller.BaseController;
import com.qinyuan.lib.mvc.controller.ImageController;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class AdminBatchRegisterController extends ImageController {

    @RequestMapping("/admin-batch-register")
    public String index() {
        IndexHeaderUtils.setHeaderParameters(this);

        setTitle("批量注册新用户");
        addCss("admin-form");
        addCssAndJs("admin-batch-register");
        return "admin-batch-register";
    }

    /*
    @RequestMapping("/hello-world.json")
    @ResponseBody
    public String json(){
        return success();
    }
    */
}
