package com.qinyuan15.lottery.mvc.controller;

import com.qinyuan15.lottery.mvc.dao.CommodityDao;
import com.qinyuan15.lottery.mvc.dao.DualColoredBallRecord;
import com.qinyuan15.lottery.mvc.dao.DualColoredBallRecordDao;
import com.qinyuan15.lottery.mvc.dao.SeckillActivityDao;
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
public class AdminSeckillActivityController extends ImageController {
    private final static Logger LOGGER = LoggerFactory.getLogger(AdminSeckillActivityController.class);

    @RequestMapping("/admin-seckill-activity")
    public String index(@RequestParam(value = "listType", required = false) String listType) {
        IndexHeaderUtils.setHeaderParameters(this);

        boolean listExpire = (listType != null && listType.equals("expire"));
        setAttribute("listExpire", listExpire);

        new PaginationAttributeAdder(SeckillActivityDao.factory().setExpire(listExpire), request)
                .setRowItemsName("activities").setPageSize(10).add();

        // used by commodity select form
        setAttribute("allCommodities", new SelectFormItemsBuilder().build(new CommodityDao().getInstances(), "name"));
        DualColoredBallRecord latestRecord = new DualColoredBallRecordDao().getLatestInstance();
        setAttribute("nextDualColoredBallTerm", latestRecord.getYear() +
                new DecimalFormat("000").format(latestRecord.getTerm() + 1));
        setAttribute("nextTerm", new SeckillActivityDao().getMaxTerm() + 1);

        setTitle("秒杀管理");
        addJs("lib/bootstrap/js/bootstrap.min", false);
        addJs("lib/ckeditor/ckeditor", false);
        addCss("admin-form");
        addCssAndJs("admin-seckill-activity");
        return "admin-seckill-activity";
    }


    @RequestMapping("/admin-seckill-activity-add-edit.json")
    @ResponseBody
    public String addEdit(@RequestParam(value = "id", required = false) Integer id,
                          @RequestParam(value = "term", required = true) Integer term,
                          @RequestParam(value = "commodityId", required = true) Integer commodityId,
                          @RequestParam(value = "startTime", required = false) String startTime,
                          @RequestParam(value = "autoStartTime", required = false) String autoStartTime,
                          @RequestParam(value = "expectParticipantCount", required = true) Integer expectParticipantCount,
                          @RequestParam(value = "description", required = true) String description) {
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

        try {
            SeckillActivityDao dao = new SeckillActivityDao();
            if (IntegerUtils.isPositive(id)) {
                dao.update(id, term, commodityId, startTime, expectParticipantCount, description);
            } else {
                if (new CommodityDao().hasActiveLottery(commodityId)) {
                    return fail("此商品的上一期秒杀还未结束，不能重复添加秒杀！");
                } else if (dao.hasTerm(term)) {
                    return fail("第" + term + "期秒杀已经存在，请填写别的期数！");
                }
                dao.add(term, commodityId, startTime, expectParticipantCount, description);
            }
            return success();
        } catch (Exception e) {
            LOGGER.error("Fail to add or update seckill activity, info: {}", e);
            return failByDatabaseError();
        }
    }

    @RequestMapping("/admin-seckill-activity-delete.json")
    @ResponseBody
    public String delete(@RequestParam(value = "id", required = true) Integer id) {
        if (!IntegerUtils.isPositive(id)) {
            return failByInvalidParam();
        }

        SeckillActivityDao dao = new SeckillActivityDao();
        if (dao.isExpire(id)) {
            return fail("无法删除已经结束的秒杀活动！");
        }

        try {
            dao.delete(id);
            return success();
        } catch (Exception e) {
            LOGGER.error("fail to delete seckill activity, info: {}", e);
            return failByDatabaseError();
        }
    }

    @RequestMapping("/admin-seckill-activity-stop.json")
    @ResponseBody
    public String stop(@RequestParam(value = "id", required = true) Integer id) {
        if (!IntegerUtils.isPositive(id)) {
            return failByInvalidParam();
        }

        try {
            new SeckillActivityDao().end(id);
            return success();
        } catch (Exception e) {
            LOGGER.error("fail to stop seckill activity, info: {}", e);
            return failByDatabaseError();
        }
    }

    @RequestMapping("/admin-seckill-activity-update-announcement.json")
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
                    return fail("'" + winner + "'不是有效的秒杀号");
                }
            }
        }

        try {
            new SeckillActivityDao().updateResult(id, winners, announcement);
            return success();
        } catch (Exception e) {
            LOGGER.error("Fail to update announcement, info: {}", e);
            return failByDatabaseError();
        }
    }
}
