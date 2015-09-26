package com.qinyuan15.lottery.mvc.controller;

import com.qinyuan.lib.image.ImageMap;
import com.qinyuan.lib.image.ImageMapDao;
import com.qinyuan.lib.lang.DoubleUtils;
import com.qinyuan.lib.mvc.controller.ImageController;
import com.qinyuan.lib.mvc.security.SecurityUtils;
import com.qinyuan.lib.mvc.security.UserRole;
import com.qinyuan15.lottery.mvc.AppConfig;
import com.qinyuan15.lottery.mvc.ImageMapType;
import com.qinyuan15.lottery.mvc.dao.Commodity;
import com.qinyuan15.lottery.mvc.dao.CommodityDao;
import com.qinyuan15.lottery.mvc.dao.User;
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

        Commodity commodity = getCommodity(id);

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
            //setAttribute("commodity", getUrlAdapter().adapt(commodity));
            addJavaScriptData("selectedCommodityId", commodity.getId());
            addJavaScriptData("commodityMaps", mapDao.getInstancesByRelateId(commodity.getId()));
        }
        if (StringUtils.hasText(userSerialKey) && StringUtils.hasText(medium)
                && StringUtils.hasText(SecurityUtils.getUsername())) {
            livenessAdder.addLiveness(true);
        }

        // seckill poker
        setAttribute("pokerFrontSide", pathToUrl(AppConfig.getSeckillPokerFrontSide()));
        setAttribute("pokerBackSide", pathToUrl(AppConfig.getSeckillPokerBackSide()));

        setAttribute("snapshots", buildSnapshots());
        setAttribute("lotteryRule", AppConfig.getLotteryRule());
        setAttribute("telValidateDescriptionPage", AppConfig.getTelValidateDescriptionPage());

        // qqlist
        setAttribute("qqlistId", AppConfig.getQQListId());
        setAttribute("qqlistDescription", AppConfig.getQQListDescription());

        addJs("lib/jsutils.digit", false);
        addJs("lib/handlebars.min-v1.3.0", false);
        addHeadJs("lib/image-adjust");
        addCssAndJs("commodity");
        return "commodity";
    }

    private Commodity getCommodity(Integer commodityId) {
        CommodityDao dao = new CommodityDao();
        Commodity commodity = dao.getInstance(commodityId);

        if (SecurityUtils.hasAuthority(UserRole.ADMIN)) {
            if (commodity == null) {
                commodity = dao.getFirstInstance();
            }
        } else {
            if (commodity == null || !commodity.getVisible()) {
                commodity = dao.getFirstVisibleInstance();
            }
        }

        return commodity;
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
        private boolean inSeckill;

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

        public boolean isInSeckill() {
            return inSeckill;
        }
    }

    private List<CommoditySnapshot> buildSnapshots() {
        List<CommoditySnapshot> snapshots = new ArrayList<>();

        CommodityDao dao = new CommodityDao();
        List<Commodity> commodities = SecurityUtils.hasAuthority(UserRole.ADMIN) ?
                dao.getInstances() : dao.getVisibleInstances();

        for (Commodity commodity : commodities) {
            getUrlAdapter().adaptToSmall(commodity);

            CommoditySnapshot snapshot = new CommoditySnapshot();
            snapshot.id = commodity.getId();
            snapshot.name = commodity.getName();
            snapshot.price = commodity.getFormattedPrice();
            snapshot.snapshot = commodity.getSnapshot();
            snapshot.inLottery = dao.hasActiveLottery(commodity.getId());
            snapshot.inSeckill = dao.hasActiveSeckill(commodity.getId());

            snapshots.add(snapshot);
        }
        return snapshots;
    }


    @RequestMapping("/commodity-info.json")
    @ResponseBody
    public String json(@RequestParam(value = "id", required = true) Integer id) {
        CommodityInfo commodityInfo = new CommodityInfo(
                getUrlAdapter().adaptToSmall(new CommodityDao().getInstance(id)),
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
