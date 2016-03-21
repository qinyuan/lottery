package com.qinyuan15.lottery.mvc.controller;

import com.qinyuan.lib.mvc.controller.ImageController;
import com.qinyuan15.lottery.mvc.config.AppConfig;
import com.qinyuan15.lottery.mvc.activity.share.LotteryShareUrlBuilder;
import com.qinyuan15.lottery.mvc.activity.share.ShareMedium;
import com.qinyuan15.lottery.mvc.dao.*;
import org.apache.commons.lang3.tuple.Pair;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;

@Controller
public class ShareController extends ImageController {

    @RequestMapping("/share")
    public String index(@RequestParam(value = "commodityId", required = false) Integer commodityId) {
        IndexHeaderUtils.setHeaderParameters(this);

        Commodity commodity = new CommodityDao().getInstance(commodityId);
        if (commodity == null) {
            commodity = new CommodityDao().getFirstVisibleInstance();
        }
        LotteryActivity activity = new LotteryActivityDao().getInstanceByCommodityId(commodityId);
        if (activity != null) {
            setAttribute("activityId", activity.getId());
        }
        new CommodityUrlAdapter(this).adapt(commodity);

        User user = (User) securitySearcher.getUser();
        new UserDao().updateSerialKeyIfNecessary(user);
        LotteryShareUrlBuilder lotteryShareUrlBuilder = new LotteryShareUrlBuilder(
                user.getSerialKey(), AppConfig.properties.getAppHost(), commodity);
        setAttribute("sinaWeiboShareUrl", lotteryShareUrlBuilder.getSinaShareUrl());
        setAttribute("qqShareUrl", lotteryShareUrlBuilder.getQQShareUrl());
        setAttribute("qzoneShareUrl", lotteryShareUrlBuilder.getQzoneShareUrl());
        setAttribute("finalTargetUrl", lotteryShareUrlBuilder.getFinalTargetUrl(ShareMedium.COPY.en));

        setAttribute("liveness", new LotteryLivenessDao().getLiveness(user.getId()));
        setAttribute("receiveUsers", getReceiveUsers(user.getId()));

        RegisterHeaderUtils.setParameters(this);

        addJs("lib/jquery-zclip/jquery.zclip.min");
        addCssAndJs("share");

        setTitle("增加支持");
        return "share";
    }

    private List<ReceiveUser> getReceiveUsers(Integer userId) {
        List<Pair<Integer, String>> list = new LotteryLivenessDao().getReceiveUsers(userId);
        List<ReceiveUser> users = new ArrayList<>();
        for (Pair<Integer, String> pair : list) {
            Integer id = pair.getLeft();
            User user = new UserDao().getInstance(id);
            new UserDao().updateSerialKeyIfNecessary(user);

            users.add(new ReceiveUser(user.getSerialKey(), user.getUsername(),
                    new LotteryLivenessDao().getLiveness(id)));
        }
        return users;
    }

    public static class ReceiveUser {
        private String serial;
        private String username;
        private Integer liveness;

        public ReceiveUser(String serial, String username, Integer liveness) {
            this.serial = serial;
            this.username = username;
            this.liveness = liveness;
        }

        public String getSerial() {
            return serial;
        }

        public String getUsername() {
            return username;
        }

        public Integer getLiveness() {
            return liveness;
        }
    }
}
