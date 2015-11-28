package com.qinyuan15.lottery.mvc.controller;

import com.qinyuan.lib.lang.IntegerUtils;
import com.qinyuan.lib.mvc.controller.ImageController;
import com.qinyuan15.lottery.mvc.AppConfig;
import com.qinyuan15.lottery.mvc.activity.LotteryShareUrlBuilder;
import com.qinyuan15.lottery.mvc.activity.ShareMedium;
import com.qinyuan15.lottery.mvc.dao.*;
import org.apache.commons.lang3.tuple.Pair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class ShareController extends ImageController {
    private final static Logger LOGGER = LoggerFactory.getLogger(ShareController.class);

    @RequestMapping("/share")
    public String index(@RequestParam(value = "commodityId", required = false) Integer commodityId) {
        IndexHeaderUtils.setHeaderParameters(this);

        Commodity commodity = new CommodityDao().getInstance(commodityId);
        if (commodity == null) {
            commodity = new CommodityDao().getFirstVisibleInstance();
        }
        LotteryActivity activity = new LotteryActivityDao().getInstanceByCommodityId(commodityId);
        if (activity != null) {
            addJavaScriptData("activityId", activity.getId());
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
        setAttribute("receiveUsers", getReceiveUsers(user.getId()));

        setAttribute("registerHeaderLeftLogo", pathToUrl(AppConfig.getRegisterHeaderLeftLogo()));
        setAttribute("registerHeaderRightLogo", pathToUrl(AppConfig.getRegisterHeaderRightLogo()));
        setAttribute("noFooter", true);
        setAttribute("whiteFooter", true);

        addJs("lib/jquery-zclip/jquery.zclip.min");
        addCssAndJs("register");
        addCssAndJs("share");

        setTitle("增加支持");
        return "share";
    }

    @RequestMapping("/add-liveness-by-share.json")
    @ResponseBody
    public String addLiveness(@RequestParam("userId") Integer userId,
                              @RequestParam(value = "activityId", required = false) Integer activityId) {
        final String livenessToAddKey = "livenessToAdd";
        if (!IntegerUtils.isPositive(userId)) {
            return failByInvalidParam();
        }

        Map<String, Object> result = new HashMap<>();
        try {
            Integer receiveUserId = securitySearcher.getUserId();
            if (!IntegerUtils.isPositive(receiveUserId)) {
                return fail("请重新登录");
            }

            result.put("success", true);
            if (!IntegerUtils.isPositive(activityId)) {
                LotteryActivity activity = new LotteryActivityDao().getFirstInstance();
                if (activity == null) {
                    result.put(livenessToAddKey, 0);
                    return toJson(result);
                }
            }

            if (new LotteryLivenessDao().hasInstance(userId, receiveUserId)) {
                result.put(livenessToAddKey, 0);
                return toJson(result);
            } else {
                LivenessAdder.addLiveness(receiveUserId, false, userId, ShareMedium.INITIATIVE.en, activityId);
                result.put(livenessToAddKey, AppConfig.getShareSucceedLiveness());
                return toJson(result);
            }
        } catch (Exception e) {
            LOGGER.error("fail to add liveness, userId: {}, activityId: {}, info: {}", userId, activityId, e);
            return failByDatabaseError();
        }
    }


    private List<ReceiveUser> getReceiveUsers(Integer userId) {
        List<Pair<Integer, String>> list = new LotteryLivenessDao().getReceiveUsers(userId);
        List<ReceiveUser> users = new ArrayList<>();
        for (Pair<Integer, String> pair : list) {
            ReceiveUser user = new ReceiveUser();
            user.id = pair.getLeft();
            user.username = pair.getRight();
            user.liveness = new LotteryLivenessDao().getLiveness(user.id);
            users.add(user);
        }
        return users;
    }

    public static class ReceiveUser {
        private Integer id;
        private String username;
        private Integer liveness;

        public Integer getId() {
            return id;
        }

        public String getUsername() {
            return username;
        }

        public Integer getLiveness() {
            return liveness;
        }
    }
}
