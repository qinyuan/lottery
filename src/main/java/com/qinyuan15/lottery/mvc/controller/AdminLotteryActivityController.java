package com.qinyuan15.lottery.mvc.controller;

import com.qinyuan15.lottery.mvc.dao.CommodityDao;
import com.qinyuan15.lottery.mvc.dao.LotteryActivityDao;
import com.qinyuan15.utils.DateUtils;
import com.qinyuan15.utils.IntegerUtils;
import com.qinyuan15.utils.mvc.PaginationAttributeAdder;
import com.qinyuan15.utils.mvc.controller.ImageController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class AdminLotteryActivityController extends ImageController {
    private final static Logger LOGGER = LoggerFactory.getLogger(AdminLotteryActivityController.class);

    @RequestMapping("/admin-lottery-activity")
    public String index() {
        IndexHeaderUtils.setHeaderParameters(this);

        new PaginationAttributeAdder(LotteryActivityDao.factory(), request)
                .setRowItemsName("activities").setPageSize(10).add();

        // used by commodity select form
        setAttribute("allCommodities", new CommodityDao().getInstances());

        setTitle("抽奖管理");
        addCss("admin-form");
        addJs("lib/bootstrap/js/bootstrap.min");
        addJs("commodity-select-form");
        addCssAndJs("admin-lottery-activity");
        return "admin-lottery-activity";
    }


    @RequestMapping("/admin-lottery-activity-add-edit")
    public String addEdit(@RequestParam(value = "id", required = false) Integer id,
                          @RequestParam(value = "pageNumber", required = false) Integer pageNumber,
                          @RequestParam(value = "commodityId", required = true) Integer commodityId,
                          @RequestParam(value = "startTime", required = false) String startTime,
                          @RequestParam(value = "autoStartTime", required = false) String autoStartTime,
                          @RequestParam(value = "expectEndTime", required = true) String expectEndTime,
                          @RequestParam(value = "continuousSerialLimit", required = true) Integer continuousSerialLimit,
                          @RequestParam(value = "expectParticipantCount", required = true) Integer expectParticipantCount) {
        String page = "admin-lottery-activity";
        if (IntegerUtils.isPositive(pageNumber)) {
            page = page + "?pageNumber=" + pageNumber;
        }

        if (StringUtils.hasText(autoStartTime)) {
            startTime = DateUtils.nowString();
        } else {
            if (!DateUtils.isDateOrDateTime(startTime)) {
                return redirect(page, "开始时间格式错误！");
            }
        }

        if (!IntegerUtils.isPositive(commodityId)) {
            return redirect(page, "商品未选择！");
        }

        if (StringUtils.hasText(expectEndTime)) {
            if (!DateUtils.isDateOrDateTime(expectEndTime)) {
                return redirect(page, "预计结束时间格式错误！");
            }
        } else {
            expectEndTime = null;
        }

        try {
            LotteryActivityDao dao = new LotteryActivityDao();
            if (IntegerUtils.isPositive(id)) {
                dao.update(id, commodityId, startTime, expectEndTime, continuousSerialLimit, expectParticipantCount);
            } else {
                dao.add(commodityId, startTime, expectEndTime, continuousSerialLimit, expectParticipantCount);
            }
            return redirect(page);
        } catch (Exception e) {
            LOGGER.error("Fail to add or update lottery activity, info: {}", e);
            return redirect(page, "数据库操作失败");
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
}
