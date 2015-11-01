package com.qinyuan15.lottery.mvc.controller;

import com.qinyuan.lib.lang.IntegerUtils;
import com.qinyuan.lib.lang.time.DateUtils;
import com.qinyuan.lib.mvc.controller.ImageController;
import com.qinyuan.lib.mvc.security.SecurityUtils;
import com.qinyuan.lib.mvc.security.UserRole;
import com.qinyuan15.lottery.mvc.AppConfig;
import com.qinyuan15.lottery.mvc.activity.LotteryLotCounter;
import com.qinyuan15.lottery.mvc.activity.LotteryLotCreator;
import com.qinyuan15.lottery.mvc.activity.LotteryShareUrlBuilder;
import com.qinyuan15.lottery.mvc.activity.SeckillShareUrlBuilder;
import com.qinyuan15.lottery.mvc.dao.*;
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
public class LotController extends ImageController {
    private final static Logger LOGGER = LoggerFactory.getLogger(LotController.class);
    private final static String SUCCESS = "success";
    private final static String DETAIL = "detail";

    @Autowired
    private DecimalFormat lotNumberFormat;

    @RequestMapping("/take-lottery.json")
    @ResponseBody
    public String takeLottery(@RequestParam(value = "commodityId", required = true) Integer commodityId) {
        if (!IntegerUtils.isPositive(commodityId)) {
            return failByInvalidParam();
        } else if (!new CommodityDao().hasLottery(commodityId)) {
            return fail("noLottery");
        } else if (SecurityUtils.getUsername() == null) {
            return fail("noLogin");
        } else if (!canTakeLot()) {
            return getNoPrivilegeResult();
        }

        Map<String, Object> result = new HashMap<>();

        // if all lotteries of current commodity are expired
        LotteryActivity activity = new LotteryActivityDao().getActiveInstanceByCommodityId(commodityId);
        if (activity == null) {
            result.put(DETAIL, "activityExpire");
            activity = new LotteryActivityDao().getInstanceByCommodityId(commodityId);
        } else if (activity.getClosed()) {
            result.put(DETAIL, "activityExpire");
        }

        // user parameters
        User user = (User) securitySearcher.getUser();
        if (!result.containsKey(DETAIL)) {
            if (new LotteryLotCounter().countReal(activity.getId(), user.getId()) == 0) {
                result.put(SUCCESS, true);
            } else {
                result.put("serialNumbers", new LotteryLotDao().getSerialNumbers(activity.getId(), user.getId(), lotNumberFormat));
                putLotteryLivenessParameters(result, user, activity);
                result.put(SUCCESS, false);
                result.put(DETAIL, "alreadyAttended");
            }
        }

        // commodity
        Commodity commodity = getCommodity(commodityId);
        result.put("commodity", commodity);

        // activity
        result.put("activityDescription", activity.getDescription());

        // share urls
        new UserDao().updateSerialKeyIfNecessary(user);
        LotteryShareUrlBuilder lotteryShareUrlBuilder = new LotteryShareUrlBuilder(
                user.getSerialKey(), AppConfig.getAppHost(), commodity);
        result.put("sinaWeiboShareUrl", lotteryShareUrlBuilder.getSinaShareUrl());
        result.put("qqShareUrl", lotteryShareUrlBuilder.getQQShareUrl());
        result.put("qzoneShareUrl", lotteryShareUrlBuilder.getQzoneShareUrl());

        // participants data
        result.put("participantCount", new LotteryLotCounter().count(activity));
        //result.put("remainingSeconds", getRemainingSeconds(activity));

        return toJson(result);
    }

    @RequestMapping("/do-lottery-action.json")
    @ResponseBody
    public String doLotteryAction(@RequestParam(value = "commodityId", required = true) Integer commodityId) {
        User user = (User) securitySearcher.getUser();
        if (user == null) {
            return fail("请重新登录");
        }
        try {
            LotteryActivity activity = new LotteryActivityDao().getActiveInstanceByCommodityId(commodityId);
            Map<String, Object> result = new HashMap<>();
            LotteryLotCreator.CreateResult lotteryLotCreateResult = new LotteryLotCreator(
                    activity, securitySearcher.getUserId()).create();
            result.put("serialNumbers", getSerialNumbersFromLotteryLots(lotteryLotCreateResult.getLots()));
            putLotteryLivenessParameters(result, user, activity);
            result.put(SUCCESS, true);
            return toJson(result);
        } catch (Exception e) {
            LOGGER.error("fail to create lot, commodityId: {}, info: {}", commodityId, e);
            return failByDatabaseError();
        }
    }

    private void putLotteryLivenessParameters(Map<String, Object> result, User user, LotteryActivity activity) {
        result.put("tel", user.getTel());
        result.put("liveness", new LotteryLivenessDao().getLiveness(user.getId()));
        result.put("minLivenessToParticipate", activity.getMinLivenessToParticipate());
    }

    @RequestMapping("/take-seckill.json")
    @ResponseBody
    public String takeSeckill(@RequestParam(value = "commodityId", required = true) Integer commodityId) {
        if (!IntegerUtils.isPositive(commodityId)) {
            return failByInvalidParam();
        } else if (!new CommodityDao().hasSeckill(commodityId)) {
            return fail("noSeckill");
        } else if (SecurityUtils.getUsername() == null) {
            return fail("noLogin");
        } else if (!canTakeLot()) {
            return getNoPrivilegeResult();
        }

        Map<String, Object> result = new HashMap<>();

        // if all lotteries of current commodity are expired
        SeckillActivity activity = new SeckillActivityDao().getActiveInstanceByCommodityId(commodityId);
        if (activity == null) {
            result.put(DETAIL, "activityExpire");
            activity = new SeckillActivityDao().getInstanceByCommodityId(commodityId);
        } else {
            result.put(SUCCESS, true);
        }

        // user parameters
        User user = new UserDao().getInstance(securitySearcher.getUserId());
        result.put("username", user.getUsername());

        // commodity
        Commodity commodity = getCommodity(commodityId);
        result.put("commodity", commodity);

        // activity
        result.put("activityDescription", activity.getDescription());

        // share urls
        new UserDao().updateSerialKeyIfNecessary(user);
        SeckillShareUrlBuilder seckillShareUrlBuilder = new SeckillShareUrlBuilder(
                user.getSerialKey(), AppConfig.getAppHost(), commodity);
        result.put("sinaWeiboShareUrl", seckillShareUrlBuilder.getSinaShareUrl());
        result.put("qqShareUrl", seckillShareUrlBuilder.getQQShareUrl());
        result.put("qzoneShareUrl", seckillShareUrlBuilder.getQzoneShareUrl());

        return toJson(result);
    }

    @RequestMapping("/do-seckill-action.json")
    @ResponseBody
    public String doSeckillAction(@RequestParam(value = "commodityId", required = true) Integer commodityId) {
        Integer userId = securitySearcher.getUserId();
        if (!IntegerUtils.isPositive(userId)) {
            return fail("noLogin");
        }

        SeckillActivityDao activityDao = new SeckillActivityDao();
        SeckillActivity activity = activityDao.getActiveInstanceByCommodityId(commodityId);
        if (activity == null) {
            activity = activityDao.getInstanceByCommodityId(commodityId);
            if (activity != null) {
                new SeckillLotDao().add(activity.getId(), userId);
            }
            return fail("over");
        } else {
            return fail("notStart");
        }
    }

    @RequestMapping("/activity-remaining-seconds.json")
    @ResponseBody
    public String getActivityRemainingSeconds(@RequestParam(value = "commodityId", required = true) Integer commodityId,
                                              @RequestParam(value = "activityType", required = true) String activityType) {
        if (!StringUtils.hasText(activityType) || !IntegerUtils.isPositive(commodityId)) {
            return failByInvalidParam();
        }

        switch (activityType) {
            case "lottery": {
                LotteryActivity activity = new LotteryActivityDao().getActiveInstanceByCommodityId(commodityId);
                if (activity == null || activity.getExpire()) {
                    return fail("noActivity");
                }
                return getRemainingSecondsJson(activity.getCloseTime());
            }
            case "seckill": {
                SeckillActivity activity = new SeckillActivityDao().getActiveInstanceByCommodityId(commodityId);
                if (activity == null || activity.getExpire()) {
                    return fail("noActivity");
                }
                return getRemainingSecondsJson(activity.getStartTime());
            }
            default:
                return failByInvalidParam();
        }
    }

    private String getRemainingSecondsJson(String time) {
        Map<String, Object> map = new HashMap<>();
        if (!StringUtils.hasText(time)) {
            map.put("seconds", 0);
            return toJson(map);
        }
        long remainingSeconds = DateUtils.newDate(time).getTime() - System.currentTimeMillis();
        map.put("seconds", remainingSeconds < 0 ? 0 : (int) (remainingSeconds / 1000));
        return toJson(map);
    }

    /*
    private int getRemainingSeconds(SeckillActivity activity) {
        String startTime = activity.getStartTime();
        if (!StringUtils.hasText(startTime)) {
            return 0;
        }
        long remainingSeconds = DateUtils.newDate(startTime).getTime() - System.currentTimeMillis();
        return remainingSeconds < 0 ? 0 : (int) (remainingSeconds / 1000);
    }
    */

    private Commodity getCommodity(Integer commodityId) {
        return new CommodityUrlAdapter(this).adaptToSmall(new CommodityDao().getInstance(commodityId));
    }

    /*private String validateCommodityToTakeLot(Integer commodityId) {
        if (!IntegerUtils.isPositive(commodityId)) {
            return failByInvalidParam();
        } else if (!new CommodityDao().hasLottery(commodityId)) {
            return fail("noLottery");
        } else if (SecurityUtils.getUsername() == null) {
            return fail("noLogin");
        } else if (!SecurityUtils.hasAuthority(User.NORMAL)) {
            return getNoPrivilegeResult();
        } else {
            return null;
        }
    }*/

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

    private boolean canTakeLot() {
        return SecurityUtils.hasAuthority(UserRole.NORMAL) || SecurityUtils.hasAuthority(UserRole.ADMIN);
    }

    private String getNoPrivilegeResult() {
        Map<String, Object> result = new HashMap<>();
        result.put(SUCCESS, false);
        result.put(DETAIL, "noPrivilege");
        return toJson(result);
    }

    @RequestMapping("/lottery-participant-count.json")
    @ResponseBody
    public String participantCount(@RequestParam(value = "commodityId", required = true) Integer commodityId) {
        LotteryActivity activity = new LotteryActivityDao().getActiveInstanceByCommodityId(commodityId);
        if (activity == null) {
            return "{}";
        }
        Map<String, Integer> map = new HashMap<>();
        map.put("participantCount", new LotteryLotCounter().count(activity));
        return toJson(map);
    }
}
