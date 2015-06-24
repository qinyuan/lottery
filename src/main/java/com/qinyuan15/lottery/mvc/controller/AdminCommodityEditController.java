package com.qinyuan15.lottery.mvc.controller;

import com.qinyuan15.lottery.mvc.dao.Commodity;
import com.qinyuan15.lottery.mvc.dao.CommodityDao;
import com.qinyuan15.utils.DoubleUtils;
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
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Controller
public class AdminCommodityEditController extends ImageController {

    private final static Logger LOGGER = LoggerFactory.getLogger(AdminCommodityEditController.class);

    @RequestMapping("/admin-commodity-edit")
    public String index() {
        IndexHeaderUtils.setHeaderParameters(this);

        final String keyName = "commodities";
        CommodityDao.Factory factory = CommodityDao.factory();
        new PaginationAttributeAdder(factory, request).setRowItemsName(keyName).setPageSize(5).add();

        @SuppressWarnings("unchecked")
        List<Commodity> commodities = (List) request.getAttribute(keyName);
        new CommodityUrlAdapter(this).adapt(commodities);

        setTitle("编辑商品");

        addCss("resources/js/lib/bootstrap/css/bootstrap.min", false);
        addHeadJs("lib/image-adjust.js");
        addCss("admin-form");
        addCssAndJs("admin-commodity-edit");
        return "admin-commodity-edit";
    }

    @RequestMapping("/admin-commodity-edit-submit")
    public String submit(@RequestParam(value = "id", required = true) Integer id,
                         @RequestParam(value = "name", required = true) String name,
                         @RequestParam(value = "price", required = true) Double price,
                         @RequestParam(value = "own", required = false) Boolean own,
                         @RequestParam(value = "snapshot", required = true) String snapshot,
                         @RequestParam(value = "snapshotFile", required = true) MultipartFile snapshotFile,
                         @RequestParam(value = "detailImage", required = true) String detailImage,
                         @RequestParam(value = "detailImageFile", required = true) MultipartFile detailImageFile,
                         @RequestParam(value = "pageNumber", required = false) Integer pageNumber) {
        // adjust own parameter
        if (own == null) {
            own = false;
        }

        String indexPage = "admin-commodity-edit";
        if (IntegerUtils.isPositive(pageNumber)) {
            indexPage = indexPage + "?pageNumber=" + pageNumber;
        }

        if (!StringUtils.hasText(name)) {
            return redirect(indexPage, "名称未填写");
        }

        if (!DoubleUtils.isNotNegative(price)) {
            return redirect(indexPage, "价格必须大于或等于0");
        }

        String snapshotPath = null, detailImagePath = null;

        try {
            snapshotPath = getSavePath(snapshot, snapshotFile);
        } catch (Exception e) {
            LOGGER.error("error in getting save path of snapshot: {}", e);
            redirect(indexPage, "商品缩略图(小图片)处理失败！");
        }

        try {
            detailImagePath = getSavePath(detailImage, detailImageFile);
        } catch (Exception e) {
            LOGGER.error("error in getting save path of detailImage: {}", e);
            redirect(indexPage, "商品详细图(大图片)处理失败！");
        }

        try {
            CommodityDao dao = new CommodityDao();
            if (IntegerUtils.isPositive(id)) {
                dao.update(id, name, price, own, snapshotPath, detailImagePath);
            } else {
                dao.add(name, price, own, snapshotPath, detailImagePath);
            }
        } catch (Exception e) {
            LOGGER.error("error in saving or updating commodity: {}", e);
            e.printStackTrace();
            redirect(indexPage, "数据库操作失败！");
        }
        return redirect(indexPage);
    }

    @RequestMapping("/admin-commodity-delete.json")
    @ResponseBody
    public String delete(@RequestParam(value = "id", required = true) Integer id) {
        if (IntegerUtils.isPositive(id)) {
            new CommodityDao().delete(id);
            return success();
        } else {
            return fail("数据出错！");
        }
    }
}
