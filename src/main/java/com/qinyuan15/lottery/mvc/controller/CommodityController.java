package com.qinyuan15.lottery.mvc.controller;

import com.qinyuan15.lottery.mvc.dao.Commodity;
import com.qinyuan15.lottery.mvc.dao.CommodityDao;
import com.qinyuan15.utils.DoubleUtils;
import com.qinyuan15.utils.mvc.controller.ImageController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.List;

@Controller
public class CommodityController extends ImageController {

    @RequestMapping("/commodity")
    public String index(@RequestParam(value = "id", required = false) Integer id) {
        CommodityHeaderUtils.setHeaderParameters(this);

        CommodityDao dao = new CommodityDao();
        Commodity commodity = dao.getInstance(id);
        if (commodity == null) {
            commodity = dao.getFirstInstance();
        }

        if (commodity == null) {
            setTitle("未找到相关商品");
        } else {
            if (DoubleUtils.isPositive(commodity.getPrice())) {
                setTitle("商品详细信息");
            } else {
                setTitle("参与抽奖");
            }
            setAttribute("commodity", getUrlAdapter().adapt(commodity));
            addJavaScriptData("selectedCommodityId", commodity.getId());
        }

        setAttribute("snapshots", build());

        addCssAndJs("commodity");
        return "commodity";
    }

    private CommodityUrlAdapter getUrlAdapter() {
        return new CommodityUrlAdapter(this);
    }

    public class CommoditySnapshot {
        private Integer id;
        private String name;
        private Double price;
        private String snapshot;

        public Integer getId() {
            return id;
        }

        public String getName() {
            return name;
        }

        public Double getPrice() {
            return price;
        }

        public String getSnapshot() {
            return snapshot;
        }
    }

    private List<CommoditySnapshot> build() {
        List<CommoditySnapshot> snapshots = new ArrayList<>();
        for (Commodity commodity : new CommodityDao().getInstances()) {
            getUrlAdapter().adapt(commodity);

            CommoditySnapshot snapshot = new CommoditySnapshot();
            snapshot.id = commodity.getId();
            snapshot.name = commodity.getName();
            snapshot.price = commodity.getPrice();
            snapshot.snapshot = commodity.getSnapshot();

            snapshots.add(snapshot);
        }
        return snapshots;
    }

    @RequestMapping("/commodity-info.json")
    @ResponseBody
    public String json(@RequestParam(value = "id", required = true) Integer id) {
        Commodity commodity = new CommodityDao().getInstance(id);
        return toJson(getUrlAdapter().adapt(commodity));
    }
}
