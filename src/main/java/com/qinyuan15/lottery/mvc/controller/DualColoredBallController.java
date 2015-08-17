package com.qinyuan15.lottery.mvc.controller;

import com.qinyuan15.lottery.mvc.dao.DualColoredBallRecord;
import com.qinyuan15.lottery.mvc.dao.DualColoredBallRecordDao;
import com.qinyuan15.lottery.mvc.activity.BaiduLecaiCrawler;
import com.qinyuan15.lottery.mvc.activity.DualColoredBallCalculator;
import com.qinyuan15.lottery.mvc.activity.DualColoredBallCrawler;
import com.qinyuan15.lottery.mvc.activity.DualColoredBallTerm;
import com.qinyuan15.utils.DateUtils;
import com.qinyuan15.utils.mvc.controller.BaseController;
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

        DualColoredBallRecordDao dao = new DualColoredBallRecordDao();
        int term = 1;
        while (dao.hasTerm(year, term) || downloadResult(year, term)) {
            term++;
        }


        List<DualColoredBallRecord> records = dao.getInstancesByYear(year);
        for (DualColoredBallRecord record : records) {
            record.setId(null);
        }
        return toJson(records);
    }

    private boolean downloadResult(int year, int term) {
        DualColoredBallCrawler.Result result = new BaiduLecaiCrawler().getResult(DualColoredBallTerm.toFullTerm(year, term));
        if (result == null || result.result == null || result.drawTime == null) {
            return false;
        } else {
            new DualColoredBallRecordDao().add(year, term, result.drawTime, result.result);
            return true;
        }
    }
}
