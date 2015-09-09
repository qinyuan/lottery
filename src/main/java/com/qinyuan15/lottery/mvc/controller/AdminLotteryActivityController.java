package com.qinyuan15.lottery.mvc.controller;

import com.qinyuan.lib.lang.DateUtils;
import com.qinyuan.lib.lang.IntegerRange;
import com.qinyuan.lib.lang.IntegerUtils;
import com.qinyuan15.lottery.mvc.activity.DualColoredBallTermValidator;
import com.qinyuan15.lottery.mvc.dao.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.text.DecimalFormat;

@Controller
public class AdminLotteryActivityController extends AbstractActivityAdminController {
    private final static Logger LOGGER = LoggerFactory.getLogger(AdminLotteryActivityController.class);

    @Override
    protected AbstractActivityDao getActivityDao() {
        return new LotteryActivityDao();
    }

    @RequestMapping("/admin-lottery-activity")
    public String index(@RequestParam(value = "listType", required = false) String listType) {
        setTitle("抽奖管理");
        DualColoredBallRecord latestRecord = new DualColoredBallRecordDao().getLatestInstance();
        setAttribute("nextDualColoredBallTerm", latestRecord.getYear() +
                new DecimalFormat("000").format(latestRecord.getTerm() + 1));

        setAttribute("latestMinLivenessToParticipate", new LotteryActivityDao().getLatestMinLivenessToParticipate());
        setAttribute("latestSerialNumberRange", new LotteryActivityDao().getLatestSerialNumberRange());

        return super.index(listType, "admin-lottery-activity");
    }

    @RequestMapping("/admin-lottery-activity-add-edit.json")
    @ResponseBody
    public String addEdit(@RequestParam(value = "id", required = false) Integer id,
                          @RequestParam(value = "term", required = true) Integer term,
                          @RequestParam(value = "commodityId", required = true) Integer commodityId,
                          @RequestParam(value = "startTime", required = false) String startTime,
                          @RequestParam(value = "autoStartTime", required = false) String autoStartTime,
                          @RequestParam(value = "expectEndTime", required = true) String expectEndTime,
                          @RequestParam(value = "continuousSerialLimit", required = true) Integer continuousSerialLimit,
                          @RequestParam(value = "expectParticipantCount", required = true) Integer expectParticipantCount,
                          @RequestParam(value = "virtualLiveness", required = true) Integer virtualLiveness,
                          @RequestParam(value = "virtualLivenessUsers", required = true) String virtualLivenessUsers,
                          @RequestParam(value = "dualColoredBallTerm", required = true) Integer dualColoredBallTerm,
                          @RequestParam(value = "description", required = true) String description,
                          @RequestParam(value = "minLivenessToParticipate", required = true) Integer minLivenessToParticipate,
                          @RequestParam(value = "serialNumberRange", required = true) String serialNumberRangeString) {

        if (StringUtils.hasText(autoStartTime)) {
            startTime = DateUtils.nowString();
        } else {
            if (!DateUtils.isDateOrDateTime(startTime)) {
                return fail("开始时间格式错误！");
            }
        }

        if (!IntegerUtils.isPositive(term)) {
            return fail("期数未选择！");
        }

        if (!IntegerUtils.isPositive(commodityId)) {
            return fail("商品未选择！");
        }

        if (!new DualColoredBallTermValidator().validate(dualColoredBallTerm)) {
            return fail("双色球期数应为19或20开头的7位数字！");
        }

        if (!StringUtils.hasText(expectEndTime)) {
            return fail("预计结束时间未填写！");
        }
        if (!DateUtils.isDateOrDateTime(expectEndTime)) {
            return fail("预计结束时间格式错误！");
        }

        if (!StringUtils.hasText(serialNumberRangeString) ||
                !IntegerRange.validate(serialNumberRangeString)) {
            return fail("抽奖号取值范围的正确格式如'10~100000'");
        }

        if (!IntegerUtils.isPositive(virtualLiveness)) {
            virtualLiveness = null;
        }

        if (!StringUtils.hasText(virtualLivenessUsers)) {
            virtualLivenessUsers = null;
        }

        if (minLivenessToParticipate == null) {
            minLivenessToParticipate = 0;
        }

        try {
            IntegerRange serialNumberRange = new IntegerRange(serialNumberRangeString);
            LotteryActivityDao dao = new LotteryActivityDao();
            if (IntegerUtils.isPositive(id)) {
                dao.update(id, term, commodityId, startTime, expectEndTime, continuousSerialLimit,
                        expectParticipantCount, virtualLiveness, virtualLivenessUsers, dualColoredBallTerm,
                        description, minLivenessToParticipate, serialNumberRange.getStart(),
                        serialNumberRange.getEnd());
            } else {
                CommodityDao commodityDao = new CommodityDao();
                if (commodityDao.hasActiveSeckill(commodityId)) {
                    return fail("此商品的上一期秒杀还未结束，不能为准备进行秒杀的商品添加抽奖！");
                } else if (commodityDao.hasActiveLottery(commodityId)) {
                    return fail("此商品的上一期抽奖还未结束，不能重复添加抽奖！");
                } else if (dao.hasTerm(term)) {
                    return fail("第" + term + "期抽奖已经存在，请填写别的期数！");
                }
                dao.add(term, commodityId, startTime, expectEndTime, continuousSerialLimit,
                        expectParticipantCount, dualColoredBallTerm, description, minLivenessToParticipate,
                        serialNumberRange.getStart(), serialNumberRange.getEnd());
            }
            return success();
        } catch (Exception e) {
            LOGGER.error("Fail to add or update lottery activity, info: {}", e);
            return failByDatabaseError();
        }
    }

    @RequestMapping("/admin-lottery-activity-delete.json")
    @ResponseBody
    public String delete(@RequestParam(value = "id", required = true) Integer id) {
        return super.delete(id);
    }

    @RequestMapping("/admin-lottery-activity-stop.json")
    @ResponseBody
    public String stop(@RequestParam(value = "id", required = true) Integer id) {
        return super.stop(id);
    }

    @RequestMapping("/admin-lottery-activity-update-announcement.json")
    @ResponseBody
    public String updateAnnouncement(@RequestParam(value = "id", required = true) Integer id,
                                     @RequestParam(value = "winners", required = true) String winners,
                                     @RequestParam(value = "announcement", required = true) String announcement) {
        if (StringUtils.hasText(winners)) {
            for (String winner : winners.split(",")) {
                if (!winner.matches("^0+$") && !IntegerUtils.isPositive(winner)) {
                    return fail("'" + winner + "'不是有效的抽奖号");
                }
            }
        }
        return super.updateAnnouncement(id, winners, announcement);
    }
}
