package com.qinyuan15.lottery.mvc.controller;

import com.qinyuan.lib.mvc.controller.BaseController;
import com.qinyuan15.lottery.mvc.AppConfig;
import com.qinyuan15.lottery.mvc.activity.LotteryShareUrlBuilder;
import com.qinyuan15.lottery.mvc.dao.Commodity;
import com.qinyuan15.lottery.mvc.dao.CommodityDao;
import com.qinyuan15.lottery.mvc.dao.User;
import com.qinyuan15.lottery.mvc.dao.UserDao;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class ShareController extends BaseController {

    @RequestMapping("/share")
    public String index(@RequestParam(value = "commodityId", required = false) Integer commodityId) {
        Commodity commodity = new CommodityDao().getInstance(commodityId);
        if (commodity == null) {
            commodity = new CommodityDao().getFirstVisibleInstance();
        }

        User user = (User) securitySearcher.getUser();
        new UserDao().updateSerialKeyIfNecessary(user);
        LotteryShareUrlBuilder lotteryShareUrlBuilder = new LotteryShareUrlBuilder(
                user.getSerialKey(), AppConfig.getAppHost(), commodity);
        setAttribute("sinaWeiboShareUrl", lotteryShareUrlBuilder.getSinaShareUrl());
        setAttribute("qqShareUrl", lotteryShareUrlBuilder.getQQShareUrl());
        setAttribute("qzoneShareUrl", lotteryShareUrlBuilder.getQzoneShareUrl());
        setAttribute("finalTargetUrl", lotteryShareUrlBuilder.getFinalTargetUrl("copy"));

        setTitle("寻求支持");
        addCssAndJs("share");
        return "share";
    }

    /*
    @RequestMapping("/hello-world.json")
    @ResponseBody
    public String json(){
        return success();
    }
    */
}
