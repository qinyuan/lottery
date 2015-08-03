package com.qinyuan15.lottery.mvc.controller;

import com.qinyuan15.utils.mvc.controller.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class AdminSystemInfoListController extends ImageController {

    @RequestMapping("/admin-system-info-list")
    public String index() {
        IndexHeaderUtils.setHeaderParameters(this);

        DatabaseTable table = getTable();
        setAttribute("mailTable", table);
        new PaginationAttributeAdder(table, request).setRowItemsName("mailRecords").setPageSize(10).add();

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
        table.addField("内容", "m.content", "content", new DatabaseTableColumnPostHandler() {
            @Override
            public Object handle(Object targetValue) {
                if (targetValue == null) {
                    return null;
                }

                return targetValue.toString().replaceAll("<[^>]+>", "");
            }
        });
        return table;
    }

    /*
    @RequestMapping("/hello-world.json")
    @ResponseBody
    public String json(){
        return success();
    }
    */
}
