package com.qinyuan15.lottery.mvc.controller;

import com.qinyuan.lib.mvc.controller.DatabaseTable;
import com.qinyuan.lib.mvc.controller.DatabaseTableColumnPostHandler;
import com.qinyuan.lib.mvc.controller.ImageController;
import com.qinyuan.lib.mvc.controller.PaginationAttributeAdder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import java.text.DecimalFormat;

@Controller
public class ActivityHistoryController extends ImageController {
    @Autowired
    private DecimalFormat lotNumberFormat;

    @RequestMapping("/activity-history")
    public String index() {
        IndexHeaderUtils.setHeaderParameters(this);

        DatabaseTable table = getTable();
        setAttribute("lotteryHistoryTable", table);
        new PaginationAttributeAdder(table, request).setRowItemsName("lotteryHistories").setPageSize(10).add();

        setTitle("活动历史");
        addCssAndJs("activity-history");
        return "activity-history";
    }

    private DatabaseTable getTable() {
        String tableName = "lottery_lot AS ll LEFT JOIN lottery_activity AS la ON ll.activity_id=la.id";
        tableName += " LEFT JOIN commodity AS c ON la.commodity_id=c.id";

        DatabaseTable table = new DatabaseTable(tableName, "ll.id", DatabaseTable.QueryType.SQL);
        table.addField("奖品", "c.name", "prize");
        table.addField("抽奖时间", "DATE_FORMAT(ll.lot_time,'%Y-%m-%d %T')", "lot_time");

        String serialNumberField = "ll.serial_number";
        table.addField("抽奖号", serialNumberField, "serial_number", new DatabaseTableColumnPostHandler() {
            @Override
            public Object handle(Object targetValue) {
                if (targetValue == null || lotNumberFormat == null) {
                    return targetValue;
                } else {
                    return lotNumberFormat.format(targetValue);
                }
            }
        });

        table.addField("开奖时间", "DATE_FORMAT(la.end_time,'%Y-%m-%d %T')", "end_time");
        table.addField("中奖号", "la.winners", "winners");
        table.addField("中奖公告", "la.announcement", "announcement");
        table.addField("状态", "CASE WHEN la.expire=TRUE THEN '已结束' ELSE '进行中' END", "status");

        table.addOrder("ll.id", false);

        return table;
    }
}
