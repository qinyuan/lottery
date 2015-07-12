package com.qinyuan15.lottery.mvc.controller;

import com.google.common.collect.Lists;
import com.qinyuan15.lottery.mvc.dao.LotteryActivity;
import com.qinyuan15.lottery.mvc.dao.LotteryActivityDao;
import com.qinyuan15.lottery.mvc.dao.User;
import com.qinyuan15.lottery.mvc.dao.UserDao;
import com.qinyuan15.lottery.mvc.lottery.LotteryLotCounter;
import com.qinyuan15.utils.DateUtils;
import com.qinyuan15.utils.IntegerUtils;
import com.qinyuan15.utils.mvc.controller.BaseController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.Map;

/**
 * Controller about lottery
 * Created by qinyuan on 15-7-10.
 */
@Controller
public class LotteryController extends BaseController {
    private final static Logger LOGGER = LoggerFactory.getLogger(LotteryController.class);
    private final static String SUCCESS = "success";
    private final static String DETAIL = "detail";

    @RequestMapping("/take-lottery.json")
    @ResponseBody
    public String takeLottery(@RequestParam(value = "commodityId", required = true) Integer commodityId) {
        if (!IntegerUtils.isPositive(commodityId)) {
            return failByInvalidParam();
        }

        LotteryActivityDao lotteryActivityDao = new LotteryActivityDao();
        if (!lotteryActivityDao.hasLottery(commodityId)) {
            return fail("noLottery");
        }

        LotteryActivity activity = new LotteryActivityDao().getActiveInstanceByCommodityId(commodityId);
        if (activity == null) {
            return fail("activityExpire");
        }

        if (securitySearcher.getUsername() == null) {
            return fail("noLogin");
        }


        Map<String, Object> result = new HashMap<>();
        User user = new UserDao().getInstance(securitySearcher.getUserId());
        result.put("username", user.getUsername());

        if (!securitySearcher.hasAuthority(User.NORMAL)) {
            result.put(SUCCESS, false);
            result.put(DETAIL, "noPrivilege");
            return toJson(result);
        }

        if (!StringUtils.hasText(user.getTel())) {
            result.put(SUCCESS, false);
            result.put(DETAIL, "noTel");
            return toJson(result);
        }

        // TODO change the test data to real implement
        result.put("participantCount", new LotteryLotCounter().count(activity));
        result.put("liveness", user.getLiveness() == null ? 0 : user.getLiveness());
        result.put("serialNumbers", Lists.newArrayList(101111, 201112));
        result.put("maxLiveness", Math.max(activity.getMaxSerialNumber(), user.getLiveness()));
        result.put("remainingSeconds", getRemainingSeconds(activity));

        result.put(SUCCESS, false);
        result.put(DETAIL, "alreadyAttended");
        return toJson(result);
        /*
        'participantCount': 25311,
        'serialNumbers': [101111, 201112],
        'liveness': 312,
        'maxLiveness': 456,
        'success': false,
        'remainingSeconds': 123111
         */
    }

    private int getRemainingSeconds(LotteryActivity lotteryActivity) {
        String expectEndTime = lotteryActivity.getExpectEndTime();
        if (expectEndTime == null) {
            return 0;
        }
        long remainingSeconds = DateUtils.newDate(expectEndTime).getTime() - System.currentTimeMillis();
        return remainingSeconds < 0 ? 0 : (int) (remainingSeconds / 1000);
    }

    @RequestMapping("/update-tel.json")
    @ResponseBody
    public String updateTel(@RequestParam(value = "tel", required = true) String tel,
                            @RequestParam(value = "identityCode", required = true) String identityCode) {
        if (!validateIdentityCode(identityCode)) {
            return fail("验证码输入错误！");
        }

        try {
            new UserDao().updateTel(securitySearcher.getUserId(), tel);
            return success();
        } catch (Exception e) {
            LOGGER.error("Fail to update tel");
            return failByDatabaseError();
        }
    }
}
