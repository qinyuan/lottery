package com.qinyuan15.lottery.mvc.controller;

import com.qinyuan.lib.mvc.controller.ImageController;
import com.qinyuan15.lottery.mvc.AppConfig;
import com.qinyuan15.lottery.mvc.activity.LotteryShareUrlBuilder;
import com.qinyuan15.lottery.mvc.activity.ShareMedium;
import com.qinyuan15.lottery.mvc.dao.*;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class ShareController extends ImageController {

    @RequestMapping("/share")
    public String index(@RequestParam(value = "commodityId", required = false) Integer commodityId) {
        IndexHeaderUtils.setHeaderParameters(this);

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
        setAttribute("finalTargetUrl", lotteryShareUrlBuilder.getFinalTargetUrl(ShareMedium.COPY.en));

        setAttribute("liveness", new LotteryLivenessDao().getLiveness(user.getId()));

        setAttribute("registerHeaderLeftLogo", pathToUrl(AppConfig.getRegisterHeaderLeftLogo()));
        setAttribute("registerHeaderRightLogo", pathToUrl(AppConfig.getRegisterHeaderRightLogo()));
        setAttribute("noFooter", true);

        addJs("lib/jquery-zclip/jquery.zclip.js");
        addCssAndJs("register");
        addCssAndJs("share");

        setTitle("增加支持");
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
