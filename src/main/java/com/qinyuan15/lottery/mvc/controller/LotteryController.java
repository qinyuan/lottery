package com.qinyuan15.lottery.mvc.controller;

import com.qinyuan15.lottery.mvc.AppConfig;
import com.qinyuan15.lottery.mvc.dao.*;
import com.qinyuan15.lottery.mvc.lottery.LivenessQuerier;
import com.qinyuan15.lottery.mvc.lottery.LotteryLotCounter;
import com.qinyuan15.lottery.mvc.lottery.LotteryLotCreator;
import com.qinyuan15.lottery.mvc.lottery.ShareUrlBuilder;
import com.qinyuan15.utils.DateUtils;
import com.qinyuan15.utils.IntegerUtils;
import com.qinyuan15.utils.hibernate.HibernateUtils;
import com.qinyuan15.utils.mvc.controller.ImageController;
import org.apache.commons.lang3.RandomStringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Controller about lottery
 * Created by qinyuan on 15-7-10.
 */
@Controller
public class LotteryController extends ImageController {
    private final static Logger LOGGER = LoggerFactory.getLogger(LotteryController.class);
    private final static String SUCCESS = "success";
    private final static String DETAIL = "detail";

    @Autowired
    private DecimalFormat lotNumberFormat;

    @RequestMapping("/take-lottery.json")
    @ResponseBody
    public String takeLottery(@RequestParam(value = "commodityId", required = true) Integer commodityId) {
        if (!IntegerUtils.isPositive(commodityId)) {
            return failByInvalidParam();
        }

        // if current commodity has no lottery
        LotteryActivityDao lotteryActivityDao = new LotteryActivityDao();
        if (!lotteryActivityDao.hasLottery(commodityId)) {
            return fail("noLottery");
        }

        // if all lotteries of current commodity are expired
        LotteryActivity activity = new LotteryActivityDao().getActiveInstanceByCommodityId(commodityId);
        if (activity == null) {
            return fail("activityExpire");
        }

        // if visitor has not login
        if (securitySearcher.getUsername() == null) {
            return fail("noLogin");
        }

        Map<String, Object> result = new HashMap<>();
        User user = new UserDao().getInstance(securitySearcher.getUserId());
        result.put("username", user.getUsername());

        // if no privilege to take lottery
        if (!securitySearcher.hasAuthority(User.NORMAL)) {
            result.put(SUCCESS, false);
            result.put(DETAIL, "noPrivilege");
            return toJson(result);
        }

        // if tel has not been provided
        if (!StringUtils.hasText(user.getTel())) {
            result.put(SUCCESS, false);
            result.put(DETAIL, "noTel");
            return toJson(result);
        }

        // get serial numbers
        LotteryLotCreator.CreateResult lotteryLotCreateResult = new LotteryLotCreator(
                activity.getId(), activity.getContinuousSerialLimit(), user).create();
        result.put("serialNumbers", getSerialNumbersFromLotteryLots(lotteryLotCreateResult.getLots()));
        if (lotteryLotCreateResult.hasNewLot()) {
            result.put(SUCCESS, true);
        } else {
            result.put(SUCCESS, false);
            result.put(DETAIL, "alreadyAttended");
        }

        // liveness parameter
        Integer liveness = user.getLiveness();
        if (liveness == null) {
            liveness = 0;
        }
        result.put("liveness", liveness);

        // maxLiveness parameter
        /*
        Integer virtualLiveness = activity.getVirtualLiveness();
        if (virtualLiveness == null) {
            virtualLiveness = 0;
        }
        final String MAX_LIVENESS = "maxLiveness";
        final String MAX_LIVENESS_USERS = "maxLivenessUsers";
        if (liveness < virtualLiveness) {
            result.put(MAX_LIVENESS, virtualLiveness);
            result.put(MAX_LIVENESS_USERS, activity.getVirtualLivenessUsers());
        } else if (liveness.equals(virtualLiveness)) {
            result.put(MAX_LIVENESS, virtualLiveness);
            result.put(MAX_LIVENESS_USERS, user.getUsername() + "," + activity.getVirtualLivenessUsers());
        } else {
            result.put(MAX_LIVENESS, liveness);
            result.put(MAX_LIVENESS_USERS, user.getUsername());
        }*/
        LivenessQuerier.LivenessInfo maxLivnessInfo = new LivenessQuerier().queryMax(activity);
        result.put("maxLiveness", maxLivnessInfo.liveness);
        result.put("maxLivenessUsers", maxLivnessInfo.users);

        // share urls
        if (user.getSerialKey() == null) {
            user.setSerialKey(RandomStringUtils.randomAlphanumeric(UserDao.SERIAL_KEY_LENGTH));
            HibernateUtils.update(user);
        }
        ShareUrlBuilder shareUrlBuilder = new ShareUrlBuilder(user.getSerialKey(), AppConfig.getAppHost(),
                new CommodityUrlAdapter(this).adapt(new CommodityDao().getInstance(commodityId)));
        result.put("sinaWeiboShareUrl", shareUrlBuilder.getSinaShareUrl());
        result.put("qqShareUrl", shareUrlBuilder.getQQShareUrl());
        result.put("qzoneShareUrl", shareUrlBuilder.getQzoneShareUrl());

        // participants data
        result.put("participantCount", new LotteryLotCounter().count(activity));
        result.put("remainingSeconds", getRemainingSeconds(activity));


        return toJson(result);
    }

    private List<String> getSerialNumbersFromLotteryLots(List<LotteryLot> lotteryLots) {
        List<String> serialNumbers = new ArrayList<>();
        if (lotteryLots == null) {
            return serialNumbers;
        }

        for (LotteryLot lotteryLot : lotteryLots) {
            serialNumbers.add(lotNumberFormat.format(lotteryLot.getSerialNumber()));
        }

        return serialNumbers;
    }

    private int getRemainingSeconds(LotteryActivity lotteryActivity) {
        String expectEndTime = lotteryActivity.getExpectEndTime();
        if (!StringUtils.hasText(expectEndTime)) {
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
