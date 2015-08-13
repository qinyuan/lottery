package com.qinyuan15.lottery.mvc.controller;

import com.qinyuan15.lottery.mvc.dao.CommodityDao;
import com.qinyuan15.lottery.mvc.dao.DualColoredBallRecord;
import com.qinyuan15.lottery.mvc.dao.DualColoredBallRecordDao;
import com.qinyuan15.lottery.mvc.dao.LotteryActivityDao;
import com.qinyuan15.lottery.mvc.lottery.DualColoredBallTermValidator;
import com.qinyuan15.utils.DateUtils;
import com.qinyuan15.utils.IntegerUtils;
import com.qinyuan15.utils.mvc.controller.ImageController;
import com.qinyuan15.utils.mvc.controller.PaginationAttributeAdder;
import com.qinyuan15.utils.mvc.controller.SelectFormItemsBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.text.DecimalFormat;

@Controller
public class AdminLotteryActivityController extends ImageController {
    private final static Logger LOGGER = LoggerFactory.getLogger(AdminLotteryActivityController.class);

    @RequestMapping("/admin-lottery-activity")
    public String index(@RequestParam(value = "listType", required = false) String listType) {
        IndexHeaderUtils.setHeaderParameters(this);

        boolean listExpire = (listType != null && listType.equals("expire"));
        setAttribute("listExpire", listExpire);

        new PaginationAttributeAdder(LotteryActivityDao.factory().setExpire(listExpire), request)
                .setRowItemsName("activities").setPageSize(10).add();

        // used by commodity select form
        setAttribute("allCommodities", new SelectFormItemsBuilder().build(new CommodityDao().getInstances(), "name"));
        DualColoredBallRecord latestRecord = new DualColoredBallRecordDao().getLatestInstance();
        setAttribute("nextDualColoredBallTerm", latestRecord.getYear() +
                new DecimalFormat("000").format(latestRecord.getTerm() + 1));
        setAttribute("nextTerm", new LotteryActivityDao().getMaxTerm() + 1);

        setTitle("抽奖管理");
        addJs("lib/bootstrap/js/bootstrap.min", false);
        addJs("lib/ckeditor/ckeditor", false);
        addCss("admin-form");
        addCssAndJs("admin-lottery-activity");
        return "admin-lottery-activity";
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
                          @RequestParam(value = "dualColoredBallTerm", required = true) Integer dualColoredBallTerm) {
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

        if (!IntegerUtils.isPositive(virtualLiveness)) {
            virtualLiveness = null;
        }

        if (!StringUtils.hasText(virtualLivenessUsers)) {
            virtualLivenessUsers = null;
        }

        try {
            LotteryActivityDao dao = new LotteryActivityDao();
            if (IntegerUtils.isPositive(id)) {
                dao.update(id, term, commodityId, startTime, expectEndTime, continuousSerialLimit,
                        expectParticipantCount, virtualLiveness, virtualLivenessUsers, dualColoredBallTerm);
            } else {
                if (dao.hasActiveLottery(commodityId)) {
                    return fail("此商品的上一期抽奖还未结束，不能重复添加抽奖！");
                }
                dao.add(term, commodityId, startTime, expectEndTime, continuousSerialLimit,
                        expectParticipantCount, dualColoredBallTerm);
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
        if (!IntegerUtils.isPositive(id)) {
            return failByInvalidParam();
        }

        LotteryActivityDao dao = new LotteryActivityDao();
        if (dao.isExpire(id)) {
            return fail("无法删除已经结束的抽奖活动！");
        }

        try {
            dao.delete(id);
            return success();
        } catch (Exception e) {
            LOGGER.error("fail to delete lottery activity, info: {}", e);
            return failByDatabaseError();
        }
    }

    @RequestMapping("/admin-lottery-activity-stop.json")
    @ResponseBody
    public String stop(@RequestParam(value = "id", required = true) Integer id) {
        if (!IntegerUtils.isPositive(id)) {
            return failByInvalidParam();
        }

        try {
            new LotteryActivityDao().end(id);
            return success();
        } catch (Exception e) {
            LOGGER.error("fail to stop lottery activity, info: {}", e);
            return failByDatabaseError();
        }
    }

    @RequestMapping("/admin-lottery-activity-update-announcement.json")
    @ResponseBody
    public String updateAnnouncement(@RequestParam(value = "id", required = true) Integer id,
                                     @RequestParam(value = "winners", required = true) String winners,
                                     @RequestParam(value = "announcement", required = true) String announcement) {
        if (!IntegerUtils.isPositive(id)) {
            return failByInvalidParam();
        }

        if (StringUtils.hasText(winners)) {
            for (String winner : winners.split(",")) {
                if (!IntegerUtils.isPositive(winner)) {
                    return fail("'" + winner + "'不是有效的抽奖号");
                }
            }
        }

        try {
            new LotteryActivityDao().updateResult(id, winners, announcement);
            return success();
        } catch (Exception e) {
            LOGGER.error("Fail to update announcement, info: {}", e);
            return failByDatabaseError();
        }
    }
}
