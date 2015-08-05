package com.qinyuan15.lottery.mvc.controller;

import com.qinyuan15.utils.html.HtmlUtils;
import com.qinyuan15.utils.mvc.controller.*;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class AdminSystemInfoListController extends TableController {

    @RequestMapping("/admin-system-info-list")
    public String index() {
        IndexHeaderUtils.setHeaderParameters(this);

        getTableUtil().addIndexAttributes(getTable());
        /*//getTableUtil().addIndexAttributes(getTable());
        DatabaseTable table = getTable();
        setAttribute("infoTable", table);
        new PaginationAttributeAdder(table, request).setRowItemsName("infoRecords").setPageSize(10).add();*/

        setTitle("系统消息列表");
        addCss("admin-form");
        addCssAndJs("admin-system-info-list");
        return "admin-system-info-list";
    }
    
    @RequestMapping(value = "/admin-system-info-list-distinct-values.json", method = RequestMethod.GET)
    @ResponseBody
    public String getDistinctValues() {
        return super.getDistinctValues();
    }

    @RequestMapping(value = "/admin-system-info-list-filter.json", method = RequestMethod.POST)
    @ResponseBody
    public String addFilter() {
        return super.addFilter();
    }

    @RequestMapping(value = "/admin-system-info-list-filter-remove.json", method = RequestMethod.POST)
    @ResponseBody
    public String removeFilter() {
        return super.removeFilter();
    }

    protected DatabaseTable getTable() {
        String tableName = "system_info_send_record AS r LEFT JOIN system_info AS i ON r.info_id=i.id";
        tableName += " LEFT JOIN user AS u ON r.user_id=u.id";

        DatabaseTable table = new DatabaseTable(tableName, "r.id", DatabaseTable.QueryType.SQL);
        table.addField("时间", "i.build_time", "build_time");
        table.addField("接收者", "u.username", "receiver");
        table.addField("内容", "i.content", "content", new DatabaseTableColumnPostHandler() {
            @Override
            public Object handle(Object targetValue) {
                return targetValue == null ? null : HtmlUtils.toText(targetValue.toString());
            }
        });
        table.addField("状态", "CASE WHEN unread=TRUE THEN '未读' ELSE '已读' END", "status");
        return table;
    }
}
