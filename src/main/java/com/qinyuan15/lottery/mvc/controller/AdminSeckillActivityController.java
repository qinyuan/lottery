package com.qinyuan15.lottery.mvc.controller;

import com.qinyuan15.lottery.mvc.dao.AbstractActivityDao;
import com.qinyuan15.lottery.mvc.dao.CommodityDao;
import com.qinyuan15.lottery.mvc.dao.SeckillActivityDao;
import com.qinyuan15.utils.DateUtils;
import com.qinyuan15.utils.IntegerUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class AdminSeckillActivityController extends AbstractActivityAdminController {
    private final static Logger LOGGER = LoggerFactory.getLogger(AdminSeckillActivityController.class);

    @Override
    protected AbstractActivityDao getActivityDao() {
        return new SeckillActivityDao();
    }

    @RequestMapping("/admin-seckill-activity")
    public String index(@RequestParam(value = "listType", required = false) String listType) {
        setTitle("秒杀管理");
        setAttribute("defaultStartTime", DateUtils.toLongString(DateUtils.oneDayLater()));
        return super.index(listType, "admin-seckill-activity");
    }


    @RequestMapping("/admin-seckill-activity-add-edit.json")
    @ResponseBody
    public String addEdit(@RequestParam(value = "id", required = false) Integer id,
                          @RequestParam(value = "term", required = true) Integer term,
                          @RequestParam(value = "commodityId", required = true) Integer commodityId,
                          @RequestParam(value = "startTime", required = true) String startTime,
                          @RequestParam(value = "expectParticipantCount", required = true) Integer expectParticipantCount,
                          @RequestParam(value = "description", required = true) String description,
                          @RequestParam(value = "winners", required = true) String winners) {
        if (!DateUtils.isDateOrDateTime(startTime)) {
            return fail("开始时间格式错误！");
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
                dao.update(id, term, commodityId, startTime, expectParticipantCount, description, winners);
            } else {
                CommodityDao commodityDao = new CommodityDao();
                if (commodityDao.hasActiveSeckill(commodityId)) {
                    return fail("此商品的上一期抽奖还未结束，正在抽奖的商品不能添加秒杀！");
                } else if (commodityDao.hasActiveLottery(commodityId)) {
                    return fail("此商品的上一期秒杀还未结束，不能重复添加秒杀！");
                } else if (dao.hasTerm(term)) {
                    return fail("第" + term + "期秒杀已经存在，请填写别的期数！");
                }
                dao.add(term, commodityId, startTime, expectParticipantCount, description, winners);
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
        return super.delete(id);
    }

    @RequestMapping("/admin-seckill-activity-stop.json")
    @ResponseBody
    public String stop(@RequestParam(value = "id", required = true) Integer id) {
        return super.stop(id);
    }

    @RequestMapping("/admin-seckill-activity-update-announcement.json")
    @ResponseBody
    public String updateAnnouncement(@RequestParam(value = "id", required = true) Integer id,
                                     @RequestParam(value = "winners", required = true) String winners,
                                     @RequestParam(value = "announcement", required = true) String announcement) {
        return super.updateAnnouncement(id, winners, announcement);
    }
}
