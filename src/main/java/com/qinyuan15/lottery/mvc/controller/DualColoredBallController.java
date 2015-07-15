package com.qinyuan15.lottery.mvc.controller;

import com.qinyuan15.lottery.mvc.lottery.DualColoredBallCalculator;
import com.qinyuan15.utils.DateUtils;
import com.qinyuan15.utils.mvc.controller.BaseController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.sql.Date;
import java.util.HashMap;
import java.util.Map;

@Controller
public class DualColoredBallController extends BaseController {
    @RequestMapping("/dual-colored-ball-query-date.json")
    @ResponseBody
    public String queryDate(@RequestParam(value = "fullTermNumber", required = true) Integer fullTermNumber) {
        Date date = new DualColoredBallCalculator().getDateTimeByTermNumber(fullTermNumber);
        Map<String, String> map = new HashMap<>();
        map.put("date", DateUtils.toLongString(date));
        return toJson(map);
    }
}
