package com.qinyuan15.lottery.mvc.controller;

import com.qinyuan15.utils.mvc.controller.DatabaseTable;
import com.qinyuan15.utils.mvc.controller.DatabaseTableColumnPostHandler;
import com.qinyuan15.utils.mvc.controller.ImageController;
import com.qinyuan15.utils.mvc.controller.PaginationAttributeAdder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class AdminMailListController extends ImageController {

    @RequestMapping("/admin-mail-list")
    public String index() {
        IndexHeaderUtils.setHeaderParameters(this);

        setTitle("邮件列表");
        DatabaseTable table = getTable();
        setAttribute("mailTable", table);
        new PaginationAttributeAdder(table, request).setRowItemsName("mailRecords").setPageSize(10).add();

        addCss("admin-form");
        addCssAndJs("admin-mail-list");
        return "admin-mail-list";
    }


    private DatabaseTable getTable() {
        String tableName = "mail_send_record AS r LEFT JOIN email AS m ON r.mail_id=m.id";
        tableName += " LEFT JOIN mail_account AS a ON r.mail_account_id=a.id";
        tableName += " LEFT JOIN user AS u ON r.user_id=u.id";

        DatabaseTable table = new DatabaseTable(tableName, "r.id", DatabaseTable.QueryType.SQL);
        table.addField("时间", "DATE_FORMAT(r.send_time,'%Y-%m-%d %T')", "send_time");
        table.addField("发件箱", "a.username", "send_mail");
        table.addField("收件人", "u.username", "receiver");
        table.addField("标题", "m.subject", "subject");
        table.addField("正文", "m.content", "content", new DatabaseTableColumnPostHandler() {
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
}
