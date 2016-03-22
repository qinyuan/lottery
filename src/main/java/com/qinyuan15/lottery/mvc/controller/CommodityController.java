package com.qinyuan15.lottery.mvc.controller;

import com.qinyuan.lib.image.ImageMap;
import com.qinyuan.lib.image.ImageMapDao;
import com.qinyuan.lib.lang.IntegerUtils;
import com.qinyuan.lib.mvc.controller.ImageController;
import com.qinyuan.lib.mvc.security.SecurityUtils;
import com.qinyuan.lib.mvc.security.UserRole;
import com.qinyuan.lib.network.url.UrlUtils;
import com.qinyuan15.lottery.mvc.ImageMapType;
import com.qinyuan15.lottery.mvc.activity.share.ShareMedium;
import com.qinyuan15.lottery.mvc.config.AppConfig;
import com.qinyuan15.lottery.mvc.dao.Commodity;
import com.qinyuan15.lottery.mvc.dao.CommodityDao;
import com.qinyuan15.lottery.mvc.dao.CommodityImage;
import com.qinyuan15.lottery.mvc.dao.CommodityImageDao;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.List;

@Controller
public class CommodityController extends ImageController {
    private ImageMapDao mapDao = new ImageMapDao(ImageMapType.COMMODITY);

    private final static String DEFAULT_TITLE = "布迪网-商品及活动";
    private final static String DEFAULT_DESCRIPTION = "这里有一个免费的商品抽奖活动，赶快来参加吧！！！";

    /*@RequestMapping("/commodity-test")
    public String test(@RequestParam(value = "id", required = false) Integer id,
                       @RequestParam(value = "fromUser", required = false) String userSerialKey,
                       @RequestParam(value = "medium", required = false) String medium) {
        return index(id, userSerialKey, medium);
    }*/

    @RequestMapping("/commodity")
    public String index(@RequestParam(value = "id", required = false) Integer id,
                        @RequestParam(value = "fromUser", required = false) String userSerialKey,
                        @RequestParam(value = "medium", required = false) String medium) {
        LivenessAdder livenessAdder = new LivenessAdder(session);
        if (StringUtils.isNotBlank(userSerialKey) && StringUtils.isNotBlank(medium)) {
            String redirectUrl = "commodity.html";
            if (IntegerUtils.isPositive(id)) {
                redirectUrl += "?id=" + id;
            }
            setAttributeAndJavaScriptData("redirectUrl", "support.html?serial=" + userSerialKey + "&redirectUrl=" +
                    UrlUtils.encode(redirectUrl));
        }

        CommodityHeaderUtils.setHeaderParameters(this);
        Commodity commodity = getCommodity(id);

        if (commodity == null) {
            livenessAdder.recordSpreader(userSerialKey, medium, null);
            setTitle(AppConfig.props.getCommodityPageTitle());
        } else {
            livenessAdder.recordSpreader(userSerialKey, medium, commodity.getId());
            setAttribute("seoKeyword", "抽奖,免费," + commodity.getName());
            String title = DEFAULT_TITLE, description = DEFAULT_DESCRIPTION;
            if (StringUtils.isNotBlank(medium)) {
                if (medium.equals(ShareMedium.QQ.en)) {
                    title = AppConfig.getLotteryQQTitle();
                    description = AppConfig.getLotteryQQSummary();
                } else if (medium.equals(ShareMedium.QZONE.en)) {
                    title = AppConfig.getLotteryQzoneTitle();
                    description = AppConfig.getLotteryQzoneSummary();
                } else if (medium.equals(ShareMedium.SINA_WEIBO.en)) {
                    title = AppConfig.getLotterySinaWeiboTitle();
                }
            }
            setTitle(title);
            setAttribute("seoDescription", description);
            addJavaScriptData("selectedCommodityId", commodity.getId());
        }

        setAttribute("snapshots", buildSnapshots());
        setAttribute("lotteryRuleLink", AppConfig.getLotteryRuleLink());
        setAttribute("telValidateDescriptionPage", AppConfig.sys.getTelValidateDescriptionPage());

        SubscribeHeaderUtils.setHeaderParameters(this);

        addJs("lib/jsutils/jsutils.digit", false);
        addJs("lib/handlebars.min-v1.3.0", false);
        addHeadJs("lib/image-adjust");
        addCssAndJs("subscribe-float-panel");
        addCss("commodity-header");
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
            snapshot.inLottery = dao.hasActiveUnCloseLottery(commodity.getId());
            snapshot.inSeckill = dao.hasActiveSeckill(commodity.getId());

            snapshots.add(snapshot);
        }
        return snapshots;
    }

    @RequestMapping("/commodity-images.json")
    @ResponseBody
    public String json(@RequestParam(value = "id", required = true) Integer id) {
        List<CommodityImage> images = new CommodityImageDao().getInstancesByCommodityId(id);
        List<CommodityImageWrapper> wrappers = new ArrayList<>();
        for (CommodityImage image : images) {
            new CommodityImageUrlAdapter(this).adapt(image);
            wrappers.add(new CommodityImageWrapper(image, mapDao.getInstancesByRelateId(image.getId())));
        }
        return toJson(wrappers);
    }

    private static class CommodityImageWrapper {
        CommodityImage image;
        List<ImageMap> commodityMaps;

        private CommodityImageWrapper(CommodityImage image, List<ImageMap> commodityMaps) {
            this.image = image;
            this.commodityMaps = commodityMaps;
        }
    }
}
