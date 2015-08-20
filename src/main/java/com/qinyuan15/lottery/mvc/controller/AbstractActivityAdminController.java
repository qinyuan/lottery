package com.qinyuan15.lottery.mvc.controller;

import com.qinyuan.lib.lang.IntegerUtils;
import com.qinyuan.lib.mvc.controller.ImageController;
import com.qinyuan.lib.mvc.controller.PaginationAttributeAdder;
import com.qinyuan.lib.mvc.controller.SelectFormItemsBuilder;
import com.qinyuan15.lottery.mvc.dao.AbstractActivityDao;
import com.qinyuan15.lottery.mvc.dao.CommodityDao;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestParam;

abstract class AbstractActivityAdminController extends ImageController {
    private final static Logger LOGGER = LoggerFactory.getLogger(AbstractActivityAdminController.class);

    protected String index(String listType, String indexPage) {
        IndexHeaderUtils.setHeaderParameters(this);

        boolean listExpire = (listType != null && listType.equals("expire"));
        setAttribute("listExpire", listExpire);

        AbstractActivityDao activityDao = getActivityDao();
        new PaginationAttributeAdder(activityDao.getFactory().setExpire(listExpire), request)
                .setRowItemsName("activities").setPageSize(10).add();

        // used by commodity select form
        setAttribute("allCommodities", new SelectFormItemsBuilder().build(new CommodityDao().getInstances(), "name"));

        setAttribute("nextTerm", getActivityDao().getMaxTerm() + 1);

        addJs("lib/bootstrap/js/bootstrap.min", false);
        addJs("lib/ckeditor/ckeditor", false);
        addCss("admin-form");
        addCssAndJs(indexPage);
        return indexPage;
    }

    abstract protected AbstractActivityDao getActivityDao();

    public String delete(Integer id) {
        if (!IntegerUtils.isPositive(id)) {
            return failByInvalidParam();
        }

        AbstractActivityDao dao = getActivityDao();
        if (dao.isExpire(id)) {
            return fail("无法删除已经结束的活动！");
        }

        try {
            dao.delete(id);
            return success();
        } catch (Exception e) {
            LOGGER.error("fail to delete activity, dao: {}, info: {}", getActivityDao().getClass().getSimpleName(), e);
            return failByDatabaseError();
        }
    }

    public String stop(Integer id) {
        if (!IntegerUtils.isPositive(id)) {
            return failByInvalidParam();
        }

        AbstractActivityDao dao = getActivityDao();
        try {
            dao.end(id);
            return success();
        } catch (Exception e) {
            LOGGER.error("fail to stop activity, dao: {}, info: {}", dao.getClass().getSimpleName(), e);
            return failByDatabaseError();
        }
    }

    public String updateAnnouncement(@RequestParam(value = "id", required = true) Integer id,
                                     @RequestParam(value = "winners", required = true) String winners,
                                     @RequestParam(value = "announcement", required = true) String announcement) {
        if (!IntegerUtils.isPositive(id)) {
            return failByInvalidParam();
        }

        AbstractActivityDao dao = getActivityDao();
        try {
            dao.updateResult(id, winners, announcement);
            return success();
        } catch (Exception e) {
            LOGGER.error("Fail to update announcement, dao: {}, info: {}", dao.getClass().getSimpleName(), e);
            return failByDatabaseError();
        }
    }
}
