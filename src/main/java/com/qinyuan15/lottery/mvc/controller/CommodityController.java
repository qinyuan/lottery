package com.qinyuan15.lottery.mvc.controller;

import com.qinyuan15.lottery.mvc.AppConfig;
import com.qinyuan15.lottery.mvc.ImageMapType;
import com.qinyuan15.lottery.mvc.dao.Commodity;
import com.qinyuan15.lottery.mvc.dao.CommodityDao;
import com.qinyuan15.lottery.mvc.dao.LotteryActivityDao;
import com.qinyuan15.utils.DoubleUtils;
import com.qinyuan15.utils.image.ImageMap;
import com.qinyuan15.utils.image.ImageMapDao;
import com.qinyuan15.utils.mvc.controller.ImageController;
import com.qinyuan15.utils.security.SecurityUtils;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.List;

@Controller
public class CommodityController extends ImageController {
    private ImageMapDao mapDao = new ImageMapDao(ImageMapType.COMMODITY);

    @RequestMapping("/commodity")
    public String index(@RequestParam(value = "id", required = false) Integer id,
                        @RequestParam(value = "fromUser", required = false) String userSerialKey,
                        @RequestParam(value = "medium", required = false) String medium) {
        CommodityHeaderUtils.setHeaderParameters(this);

        CommodityDao dao = new CommodityDao();
        Commodity commodity = dao.getInstance(id);
        if (commodity == null || !commodity.getVisible()) {
            commodity = dao.getFirstVisibleInstance();
        }

        LivenessAdder livenessAdder = new LivenessAdder(session);
        if (commodity == null) {
            livenessAdder.recordSpreader(userSerialKey, medium, null);
            setTitle("未找到相关商品");
        } else {
            livenessAdder.recordSpreader(userSerialKey, medium, commodity.getId());
            if (DoubleUtils.isPositive(commodity.getPrice())) {
                setTitle("商品详细信息");
            } else {
                setTitle("参与抽奖");
            }
            setAttribute("commodity", getUrlAdapter().adapt(commodity));
            addJavaScriptData("selectedCommodityId", commodity.getId());
            addJavaScriptData("commodityMaps", mapDao.getInstancesByRelateId(commodity.getId()));
        }
        if (StringUtils.hasText(userSerialKey) && StringUtils.hasText(medium)
                && StringUtils.hasText(SecurityUtils.getUsername())) {
            livenessAdder.addLiveness(true);
        }

        setAttribute("snapshots", buildSnapshots());
        setAttribute("lotteryRule", AppConfig.getLotteryRule());

        addJs("resources/js/lib/handlebars.min-v1.3.0", false);
        addCssAndJs("commodity");
        return "commodity";
    }


    private CommodityUrlAdapter getUrlAdapter() {
        return new CommodityUrlAdapter(this);
    }

    public class CommoditySnapshot {
        private int id;
        private String name;
        private String price;
        private String snapshot;
        private boolean inLottery;

        public int getId() {
            return id;
        }

        public String getName() {
            return name;
        }

        public String getPrice() {
            return price;
        }

        public String getSnapshot() {
            return snapshot;
        }

        public boolean isInLottery() {
            return inLottery;
        }
    }

    private List<CommoditySnapshot> buildSnapshots() {
        List<CommoditySnapshot> snapshots = new ArrayList<>();
        for (Commodity commodity : new CommodityDao().getVisibleInstances()) {
            getUrlAdapter().adapt(commodity);

            CommoditySnapshot snapshot = new CommoditySnapshot();
            snapshot.id = commodity.getId();
            snapshot.name = commodity.getName();
            snapshot.price = commodity.getFormattedPrice();
            snapshot.snapshot = commodity.getSnapshot();
            snapshot.inLottery = new LotteryActivityDao().hasActiveLottery(commodity.getId());

            snapshots.add(snapshot);
        }
        return snapshots;
    }


    @RequestMapping("/commodity-info.json")
    @ResponseBody
    public String json(@RequestParam(value = "id", required = true) Integer id) {
        CommodityInfo commodityInfo = new CommodityInfo(
                getUrlAdapter().adapt(new CommodityDao().getInstance(id)),
                mapDao.getInstancesByRelateId(id)
        );
        return toJson(commodityInfo);
    }

    private static class CommodityInfo {
        Commodity commodity;
        List<ImageMap> commodityMaps;

        private CommodityInfo(Commodity commodity, List<ImageMap> commodityMaps) {
            this.commodity = commodity;
            this.commodityMaps = commodityMaps;
        }
    }
}
