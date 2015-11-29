package com.qinyuan15.lottery.mvc.controller;

import com.qinyuan.lib.lang.IntegerUtils;
import com.qinyuan.lib.mvc.controller.ImageController;
import com.qinyuan15.lottery.mvc.AppConfig;
import com.qinyuan15.lottery.mvc.activity.ShareMedium;
import com.qinyuan15.lottery.mvc.dao.*;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.Map;

@Controller
public class SupportController extends ImageController {
    private final static Logger LOGGER = LoggerFactory.getLogger(SupportController.class);

    @RequestMapping("/support")
    public String index(@RequestParam("serial") String serial) {
        User spreadUser = new UserDao().getInstanceBySerialKey(serial);

        if (spreadUser != null) {
            String username = spreadUser.getUsername();
            setAttribute("username", username);
            setTitle("支持一下-" + username);

            User receiveUser = (User) securitySearcher.getUser();
            if (new LotteryLivenessDao().hasInstance(spreadUser.getId(), receiveUser.getId())) {
                setAttribute("alreadySupported", true);
            }
        } else {
            setAttribute("invalidUser", true);
            setTitle("用户不存在");
        }

        RegisterHeaderUtils.setParameters(this);
        addCssAndJs("support");
        return "support";
    }

    @RequestMapping("/add-liveness.json")
    @ResponseBody
    public String addLiveness(@RequestParam("serial") String serial,
                              @RequestParam(value = "activityId", required = false) Integer activityId,
                              @RequestParam(value = "medium", required = false) String medium) {
        final String livenessToAddKey = "livenessToAdd";

        Integer userId = new UserDao().getIdBySerialKey(serial);
        if (!IntegerUtils.isPositive(userId)) {
            return fail("用户不存在");
        }

        Map<String, Object> result = new HashMap<>();
        try {
            Integer receiveUserId = securitySearcher.getUserId();
            if (!IntegerUtils.isPositive(receiveUserId)) {
                return fail("请重新登录");
            }

            result.put("success", true);

            // set default activityId
            if (!IntegerUtils.isPositive(activityId)) {
                LotteryActivity activity = new LotteryActivityDao().getFirstInstance();
                if (activity == null) {
                    result.put(livenessToAddKey, 0);
                    return toJson(result);
                }
            }

            if (StringUtils.isBlank(medium)) {
                medium = ShareMedium.INITIATIVE.en;
            }

            if (new LotteryLivenessDao().hasInstance(userId, receiveUserId)) {
                result.put(livenessToAddKey, 0);
                return toJson(result);
            } else {
                LivenessAdder.addLiveness(receiveUserId, false, userId, medium, activityId);
                result.put(livenessToAddKey, AppConfig.getShareSucceedLiveness());
                return toJson(result);
            }
        } catch (Exception e) {
            LOGGER.error("fail to add liveness, userId: {}, activityId: {}, info: {}", userId, activityId, e);
            return failByDatabaseError();
        }
    }

    /*
    @RequestMapping("/hello-world.json")
    @ResponseBody
    public String json(){
        return success();
    }
    */
}
