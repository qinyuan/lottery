package com.qinyuan15.lottery.mvc.controller;

import com.qinyuan.lib.mvc.controller.DatabaseTable;
import com.qinyuan.lib.mvc.controller.DatabaseTableColumnPostHandler;
import com.qinyuan.lib.mvc.controller.TableController;
import com.qinyuan.lib.network.html.HtmlUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class AdminMailListController extends TableController {

    @RequestMapping("/admin-mail-list")
    public String index(@RequestParam(value = "orderType", required = false) String orderType) {
        IndexHeaderUtils.setHeaderParameters(this);

        DatabaseTable table = getTable();
        if (StringUtils.isBlank(orderType)) {
            table.addOrder("r.id", false);
        }
        getTableUtil().addIndexAttributes(table);

        setTitle("邮件列表");
        addCss("admin-form");
        addCssAndJs("admin-mail-list");
        return "admin-mail-list";
    }

    @RequestMapping(value = "/admin-mail-list-distinct-values.json", method = RequestMethod.GET)
    @ResponseBody
    public String getDistinctValues() {
        return super.getDistinctValues();
    }

    @RequestMapping(value = "/admin-mail-list-filter.json", method = RequestMethod.POST)
    @ResponseBody
    public String addFilter() {
        return super.addFilter();
    }

    @RequestMapping(value = "/admin-mail-list-filter-remove.json", method = RequestMethod.POST)
    @ResponseBody
    public String removeFilter() {
        return super.removeFilter();
    }

    protected DatabaseTable getTable() {
        String tableName = "mail_send_record AS r LEFT JOIN email AS m ON r.mail_id=m.id";
        tableName += " INNER JOIN mail_account AS a ON r.mail_account_id=a.id";
        tableName += " INNER JOIN user AS u ON r.user_id=u.id";
        tableName += " LEFT JOIN simple_mail_account AS sma ON (a.reference_id=sma.id AND a.type='SimpleMailAccount')";
        tableName += " LEFT JOIN sendcloud_account AS sca ON (a.reference_id=sca.id AND a.type='SendCloudAccount')";

        DatabaseTable table = new DatabaseTable(tableName, "r.id", DatabaseTable.QueryType.SQL);
        table.addField("时间", "r.send_time", "send_time");
        table.addField("发件箱", "CASE WHEN sma.username IS NULL THEN CONCAT(sca.user,'@',sca.domain_name) ELSE sma.username END"
                , "send_mail");
        table.addField("收件人", "u.username", "receiver");
        table.addField("收件箱", "u.email", "receive_mail");
        table.addField("标题", "m.subject", "subject");
        table.addField("正文", "m.content", "content", new DatabaseTableColumnPostHandler() {
            @Override
            public Object handle(Object targetValue) {
                return targetValue == null ? null : HtmlUtils.toText(targetValue.toString());
            }
        });
        return table;
    }
}
