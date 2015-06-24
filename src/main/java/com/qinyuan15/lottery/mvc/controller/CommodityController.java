package com.qinyuan15.lottery.mvc.controller;

import com.qinyuan15.lottery.mvc.dao.Commodity;
import com.qinyuan15.lottery.mvc.dao.CommodityDao;
import com.qinyuan15.utils.DoubleUtils;
import com.qinyuan15.utils.mvc.controller.ImageController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class CommodityController extends ImageController {

    @RequestMapping("/commodity")
    public String index(@RequestParam(value = "id", required = false) Integer id) {
        CommodityHeaderUtils.setHeaderParameters(this);

        CommodityDao dao = new CommodityDao();
        Commodity commodity = dao.getInstance(id);
        if (commodity != null) {
            commodity = dao.getFirstInstance();
        }

        if (commodity == null) {
            setTitle("未找到相关商品");
        } else {
            if (DoubleUtils.is)
            setAttribute("commodity", commodity);
        }

        setTitle("商品详细信息");
        addCssAndJs("commodity");
        return "commodity";
    }

    public static class CommoditySnapshot {
        private Integer id;
        private String name;
        private Double price;

        public void setId(Integer id) {
            this.id = id;
        }

        public void setName(String name) {
            this.name = name;
        }

        public void setPrice(Double price) {
            this.price = price;
        }

        static List<CommoditySnapshot> build() {
            for (Commodity commodity : new CommodityDao().getInstances()) {

            }
        }
    }

    /*
    @RequestMapping("/hello-world.json")
    @ResponseBody
    public String json(){
        return success();
    }
    */
}
