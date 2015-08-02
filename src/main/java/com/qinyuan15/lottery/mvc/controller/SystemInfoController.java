package com.qinyuan15.lottery.mvc.controller;

import com.qinyuan15.lottery.mvc.dao.LotteryLivenessDao;
import com.qinyuan15.utils.mvc.controller.ImageController;
import com.qinyuan15.utils.security.SecuritySearcher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class SystemInfoController extends ImageController {
    @Autowired
    private SecuritySearcher securitySearcher;

    @RequestMapping("/system-info")
    public String index() {
        IndexHeaderUtils.setHeaderParameters(this);

        LotteryLivenessDao dao = new LotteryLivenessDao();
        Integer userId = securitySearcher.getUserId();
        setAttribute("livenesses", dao.getInstances(userId));
        setAttribute("livenessCount", dao.getLiveness(userId));

        // bootstrap switch
        addCss("resources/js/lib/bootstrap/css/bootstrap-switch.min", false);
        addJs("lib/bootstrap/js/bootstrap-switch.min", false);

        setTitle("系统消息");
        addJs("lib/handlebars.min-v1.3.0");
        addCss("personal-center-frame");
        addCssAndJs("system-info");
        return "system-info";
    }

    /*
    @RequestMapping("/hello-world.json")
    @ResponseBody
    public String json(){
        return success();
    }
    */
}
