package com.qinyuan15.lottery.mvc.controller;

import com.qinyuan.lib.database.hibernate.HibernateUtils;
import com.qinyuan.lib.lang.DateUtils;
import com.qinyuan.lib.lang.IntegerUtils;
import com.qinyuan.lib.mvc.controller.ImageController;
import com.qinyuan.lib.mvc.security.SecurityUtils;
import com.qinyuan15.lottery.mvc.AppConfig;
import com.qinyuan15.lottery.mvc.activity.*;
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
        String validation = validateCommodityToTakeLot(commodityId);
        if (validation != null) {
            return validation;
        }

        Map<String, Object> result = new HashMap<>();

        // if all lotteries of current commodity are expired
        LotteryActivity activity = new LotteryActivityDao().getActiveInstanceByCommodityId(commodityId);
        if (activity == null) {
            result.put(DETAIL, "activityExpire");
            activity = new LotteryActivityDao().getInstanceByCommodityId(commodityId);
        }

        // user parameters
        User user = new UserDao().getInstance(securitySearcher.getUserId());
        result.put("username", user.getUsername());
        result.put("receiveMail", user.getReceiveMail());
        result.put("tel", user.getTel());

        if (!result.containsKey(DETAIL)) {
            // get serial numbers
            LotteryLotCreator.CreateResult lotteryLotCreateResult = new LotteryLotCreator(
                    activity, user.getId()).create();
            result.put("serialNumbers", getSerialNumbersFromLotteryLots(lotteryLotCreateResult.getLots()));
            if (lotteryLotCreateResult.hasNewLot()) {
                result.put(SUCCESS, true);
            } else {
                result.put(SUCCESS, false);
                result.put(DETAIL, "alreadyAttended");
            }

            // liveness parameter
            result.put("minLivenessToParticipate", activity.getMinLivenessToParticipate());
            result.put("liveness", new LotteryLivenessDao().getLiveness(user.getId()));
            LivenessQuerier.LivenessInfo maxLivnessInfo = new LivenessQuerier().queryMax(activity);
            result.put("maxLiveness", maxLivnessInfo.liveness);
            result.put("maxLivenessUsers", maxLivnessInfo.users);
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

    @RequestMapping("/take-seckill.json")
    @ResponseBody
    public String takeSeckill(@RequestParam(value = "commodityId", required = true) Integer commodityId) {
        String validation = validateCommodityToTakeLot(commodityId);
        if (validation != null) {
            return validation;
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

        // participants data
        //result.put("participantCount", new SeckillLotCounter().count(activity));

        result.put("remainingSeconds", getRemainingSeconds(activity));
        return toJson(result);
    }

    @RequestMapping("/do-seckill-action.json")
    @ResponseBody
    public String doSeckillAction(@RequestParam(value = "commodityId", required = true) Integer commodityId) {
        if (new SeckillActivityDao().getActiveInstanceByCommodityId(commodityId) == null) {
            return fail("over");
        } else {
            return fail("notStart");
        }
    }

    private Commodity getCommodity(Integer commodityId) {
        return new CommodityUrlAdapter(this).adapt(new CommodityDao().getInstance(commodityId));
    }

    private String validateCommodityToTakeLot(Integer commodityId) {
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

    private String getNoPrivilegeResult() {
        Map<String, Object> result = new HashMap<>();
        result.put(SUCCESS, false);
        result.put(DETAIL, "noPrivilege");
        return toJson(result);
    }

    private int getRemainingSeconds(SeckillActivity activity) {
        String startTime = activity.getStartTime();
        if (!StringUtils.hasText(startTime)) {
            return 0;
        }
        long remainingSeconds = DateUtils.newDate(startTime).getTime() - System.currentTimeMillis();
        return remainingSeconds < 0 ? 0 : (int) (remainingSeconds / 1000);
    }

    @RequestMapping("/participant-count.json")
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


    @RequestMapping("/update-receive-mail.json")
    @ResponseBody
    public String updateReceiveMail(@RequestParam(value = "receiveMail", required = true) Boolean receiveMail) {
        User user = new UserDao().getInstance(securitySearcher.getUserId());
        if (user == null) {
            return fail("请重新登录");
        }

        user.setReceiveMail(receiveMail);
        try {
            HibernateUtils.update(user);
            return success();
        } catch (Exception e) {
            LOGGER.error("fail to update receive mail, info: {}", e);
            return failByDatabaseError();
        }
    }
}
