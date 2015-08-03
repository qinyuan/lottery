package com.qinyuan15.lottery.mvc.controller;

import com.qinyuan15.utils.html.HtmlUtils;
import com.qinyuan15.utils.mvc.controller.DatabaseTable;
import com.qinyuan15.utils.mvc.controller.DatabaseTableColumnPostHandler;
import com.qinyuan15.utils.mvc.controller.ImageController;
import com.qinyuan15.utils.mvc.controller.PaginationAttributeAdder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class AdminSystemInfoListController extends ImageController {

    @RequestMapping("/admin-system-info-list")
    public String index() {
        IndexHeaderUtils.setHeaderParameters(this);

        DatabaseTable table = getTable();
        setAttribute("infoTable", table);
        new PaginationAttributeAdder(table, request).setRowItemsName("infoRecords").setPageSize(10).add();

        setTitle("系统消息列表");
        addCss("admin-form");
        addCssAndJs("admin-system-info-list");
        return "admin-system-info-list";
    }

    private DatabaseTable getTable() {
        String tableName = "system_info_send_record AS r LEFT JOIN system_info AS i ON r.info_id=i.id";
        tableName += " LEFT JOIN user AS u ON r.user_id=u.id";

        DatabaseTable table = new DatabaseTable(tableName, "r.id", DatabaseTable.QueryType.SQL);
        table.addField("时间", "DATE_FORMAT(i.build_time,'%Y-%m-%d %T')", "build_time");
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
