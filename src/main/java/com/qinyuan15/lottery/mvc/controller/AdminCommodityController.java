package com.qinyuan15.lottery.mvc.controller;

import com.qinyuan.lib.lang.DoubleUtils;
import com.qinyuan.lib.lang.IntegerUtils;
import com.qinyuan.lib.mvc.controller.CDNSource;
import com.qinyuan.lib.mvc.controller.ImageController;
import com.qinyuan.lib.mvc.controller.PaginationAttributeAdder;
import com.qinyuan15.lottery.mvc.dao.Commodity;
import com.qinyuan15.lottery.mvc.dao.CommodityDao;
import com.qinyuan15.lottery.mvc.dao.CommodityImageDao;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Controller
public class AdminCommodityController extends ImageController {

    private final static Logger LOGGER = LoggerFactory.getLogger(AdminCommodityController.class);

    @RequestMapping("/admin-commodity")
    public String index() {
        IndexHeaderUtils.setHeaderParameters(this);

        final String keyName = "commodities";
        CommodityDao.Factory factory = CommodityDao.factory();
        new PaginationAttributeAdder(factory, request).setRowItemsName(keyName).setPageSize(5).add();

        @SuppressWarnings("unchecked")
        List<Commodity> commodities = (List) request.getAttribute(keyName);
        new CommodityUrlAdapter(this).adapt(commodities);

        setTitle("编辑商品");

        // bootstrap switch
        addCss("resources/js/lib/bootstrap/css/bootstrap-switch.min", false);
        addJs("lib/bootstrap/js/bootstrap-switch.min", false);

        addJs(CDNSource.HANDLEBARS);
        addHeadJs("lib/image-adjust.js");
        addCss("admin-form");
        addCssAndJs("admin-commodity");
        return "admin-commodity";
    }

    @RequestMapping("/admin-commodity-submit")
    public String commoditySubmit(@RequestParam(value = "id", required = false) Integer id,
                                  @RequestParam("name") String name,
                                  @RequestParam("price") Double price,
                                  @RequestParam("snapshot") String snapshot,
                                  @RequestParam(value = "snapshotFile", required = false) MultipartFile snapshotFile,
                                  @RequestParam(value = "pageNumber", required = false) Integer pageNumber) {

        String indexPage = "admin-commodity";
        if (IntegerUtils.isPositive(pageNumber)) {
            indexPage = indexPage + "?pageNumber=" + pageNumber;
        }

        if (StringUtils.isBlank(name)) {
            return redirect(indexPage, "名称未填写");
        }

        if (!DoubleUtils.isNotNegative(price)) {
            return redirect(indexPage, "价格必须大于或等于0");
        }

        String snapshotPath = null;
        try {
            snapshotPath = getSavePath(snapshot, snapshotFile);
        } catch (Exception e) {
            LOGGER.error("error in getting save path of snapshot: {}", e);
            redirect(indexPage, "商品缩略图(小图片)处理失败！");
        }

        try {
            if (IntegerUtils.isPositive(id)) {
                new CommodityDao().update(id, name, price, snapshotPath);
            } else {
                new CommodityDao().add(name, price, snapshotPath);
            }
            return redirect(indexPage);
        } catch (Exception e) {
            LOGGER.error("error in saving or updating commodity: {}", e);
            return redirect(indexPage, "数据库操作失败！");
        }
    }

    @RequestMapping("/admin-commodity-image-submit")
    public String commodityImageSubmit(@RequestParam(value = "id", required = false) Integer id,
                                       @RequestParam(value = "commodityId", required = false) Integer commodityId,
                                       @RequestParam("detailImage") String detailImage,
                                       @RequestParam(value = "detailImageFile", required = false) MultipartFile detailImageFile,
                                       @RequestParam("backImage") String backImage,
                                       @RequestParam(value = "backImageFile", required = false) MultipartFile backImageFile,
                                       @RequestParam(value = "pageNumber", required = false) Integer pageNumber) {

        String indexPage = "admin-commodity";
        if (IntegerUtils.isPositive(pageNumber)) {
            indexPage = indexPage + "?pageNumber=" + pageNumber;
        }

        String detailImagePath = null;
        try {
            detailImagePath = getSavePath(detailImage, detailImageFile);
        } catch (Exception e) {
            LOGGER.error("error in getting save path of snapshot: {}", e);
            redirect(indexPage, "商品前景图处理失败！");
        }

        String backImagePath;
        if (isUploadFileEmpty(backImageFile) && StringUtils.isBlank(backImage)) {
            backImagePath = "";
        } else {
            try {
                backImagePath = getSavePath(backImage, backImageFile);
            } catch (Exception e) {
                LOGGER.error("error in getting save path of backImage: {}", e);
                return redirect(indexPage, "背景图片处理失败");
            }
        }

        try {
            if (IntegerUtils.isPositive(id)) {
                new CommodityImageDao().update(id, detailImagePath, backImagePath);
            } else {
                if (IntegerUtils.isPositive(commodityId)) {
                    new CommodityImageDao().add(commodityId, detailImagePath, backImagePath);
                } else {
                    return redirect(indexPage, "商品ID无效");
                }
            }
            return redirect(indexPage);
        } catch (Exception e) {
            LOGGER.error("error in saving or updating commodity: {}", e);
            return redirect(indexPage, "数据库操作失败！");
        }
    }

    @RequestMapping("/admin-commodity-update-visible.json")
    @ResponseBody
    public String updateVisible(@RequestParam(value = "id", required = true) Integer id,
                                @RequestParam(value = "visible", required = true) Boolean visible) {
        if ((!IntegerUtils.isPositive(id)) || visible == null) {
            return failByInvalidParam();
        }
        try {
            new CommodityDao().updateVisible(id, visible);
            return success();
        } catch (Exception e) {
            LOGGER.error("Fail to update commodity visible, id: {}, info: {}", id, e);
            return failByDatabaseError();
        }
    }

    @RequestMapping("/admin-commodity-delete.json")
    @ResponseBody
    public String deleteCommodity(@RequestParam("id") Integer id) {
        if (!IntegerUtils.isPositive(id)) {
            return failByInvalidParam();
        }
        try {
            CommodityDao dao = new CommodityDao();
            if (dao.isUsed(id)) {
                return fail("该商品已经参与过抽奖，不能将其删除！");
            }
            dao.delete(id);
            return success();
        } catch (Exception e) {
            LOGGER.error("Fail to delete commodity, id: {}, info: {}", id, e);
            return failByDatabaseError();
        }
    }

    @RequestMapping("/admin-commodity-image-delete.json")
    @ResponseBody
    public String deleteCommodityImage(@RequestParam("id") Integer id) {
        if (!IntegerUtils.isPositive(id)) {
            return failByInvalidParam();
        }
        try {
            CommodityImageDao dao = new CommodityImageDao();
            dao.delete(id);
            return success();
        } catch (Exception e) {
            LOGGER.error("Fail to delete commodity image, id: {}, info: {}", id, e);
            return failByDatabaseError();
        }
    }

    @RequestMapping("/admin-commodity-rank-up.json")
    @ResponseBody
    public String rankUpCommodity(@RequestParam("id") Integer id) {
        if (!IntegerUtils.isPositive(id)) {
            return failByInvalidParam();
        }
        try {
            new CommodityDao().rankUp(id);
            return success();
        } catch (Exception e) {
            LOGGER.error("Fail to rank up commodity, id: {}, info: {}", id, e);
            return failByDatabaseError();
        }
    }

    @RequestMapping("/admin-commodity-image-rank-up.json")
    @ResponseBody
    public String rankUpCommodityImage(@RequestParam("id") Integer id) {
        if (!IntegerUtils.isPositive(id)) {
            return failByInvalidParam();
        }
        try {
            new CommodityImageDao().rankUp(id);
            return success();
        } catch (Exception e) {
            LOGGER.error("Fail to rank up commodity image, id: {}, info: {}", id, e);
            return failByDatabaseError();
        }
    }

    @RequestMapping("/admin-commodity-rank-down.json")
    @ResponseBody
    public String rankDownCommodity(@RequestParam("id") Integer id) {
        if (!IntegerUtils.isPositive(id)) {
            return failByInvalidParam();
        }
        try {
            new CommodityDao().rankDown(id);
            return success();
        } catch (Exception e) {
            LOGGER.error("Fail to rank down commodity, id: {}, info: {}", id, e);
            return failByDatabaseError();
        }
    }

    @RequestMapping("/admin-commodity-image-rank-down.json")
    @ResponseBody
    public String rankDownCommodityImage(@RequestParam("id") Integer id) {
        if (!IntegerUtils.isPositive(id)) {
            return failByInvalidParam();
        }
        try {
            new CommodityImageDao().rankDown(id);
            return success();
        } catch (Exception e) {
            LOGGER.error("Fail to rank down commodity image, id: {}, info: {}", id, e);
            return failByDatabaseError();
        }
    }

    @RequestMapping("/admin-commodity-rank-to.json")
    @ResponseBody
    public String rankTo(@RequestParam(value = "id", required = true) Integer id,
                         @RequestParam(value = "rankIndex", required = true) Integer rankIndex) {
        if (!IntegerUtils.isPositive(id) || !IntegerUtils.isPositive(rankIndex)) {
            return failByInvalidParam();
        }
        try {
            new CommodityDao().rankTo(id, rankIndex);
            return success();
        } catch (Exception e) {
            LOGGER.error("fail to rank to commodity, id: {}, rankIndex: {}, info: {}", id, rankIndex, e);
            return failByDatabaseError();
        }
    }
}
