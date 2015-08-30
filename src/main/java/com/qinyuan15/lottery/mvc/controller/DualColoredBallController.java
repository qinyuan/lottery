package com.qinyuan15.lottery.mvc.controller;

import com.qinyuan.lib.lang.DateUtils;
import com.qinyuan.lib.mvc.controller.BaseController;
import com.qinyuan15.lottery.mvc.activity.DualColoredBallCalculator;
import com.qinyuan15.lottery.mvc.activity.DualColoredBallResultDownloader;
import com.qinyuan15.lottery.mvc.dao.DualColoredBallRecord;
import com.qinyuan15.lottery.mvc.dao.DualColoredBallRecordDao;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.sql.Date;
import java.util.HashMap;
import java.util.List;
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

    @RequestMapping("/dual-colored-ball-all-records.json")
    @ResponseBody
    public String queryAll(@RequestParam(value = "year", required = false) Integer year) {
        if (year == null || year > 2100 || year < 2000) {
            return "{}";
        }

        new DualColoredBallResultDownloader().download(year);

        List<DualColoredBallRecord> records = new DualColoredBallRecordDao().getInstancesByYear(year);
        for (DualColoredBallRecord record : records) {
            record.setId(null);
        }
        return toJson(records);
    }


}
