package com.qinyuan15.lottery.mvc.controller;

import com.qinyuan15.utils.html.HtmlUtils;
import com.qinyuan15.utils.mvc.controller.DatabaseTable;
import com.qinyuan15.utils.mvc.controller.DatabaseTableColumnPostHandler;
import com.qinyuan15.utils.mvc.controller.ImageController;
import com.qinyuan15.utils.mvc.controller.MVCTableUtil;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class AdminMailListController extends ImageController {

    @RequestMapping("/admin-mail-list")
    public String index() {
        IndexHeaderUtils.setHeaderParameters(this);

        getTableUtil().addIndexAttributes(getTable());

        setTitle("邮件列表");
        addCss("admin-form");
        addCssAndJs("admin-mail-list");
        return "admin-mail-list";
    }

    @RequestMapping(value = "/admin-mail-list-distinct-values.json", method = RequestMethod.GET)
    @ResponseBody
    public String getDistinctValues(@RequestParam(value = "alias", required = false) String alias) {
        if (!StringUtils.hasText(alias)) {
            return failByInvalidParam();
        }
        return toJson(getTableUtil().getDistinctValues(getTable(), alias));
    }

    @RequestMapping(value = "/admin-mail-list-filter.json", method = RequestMethod.POST)
    @ResponseBody
    public String addFilter(@RequestParam(value = "filterField", required = true) String filterField,
                            @RequestParam(value = "filterValues[]", required = false) String[] filterValues) {
        if (!StringUtils.hasText(filterField)) {
            return failByInvalidParam();
        }
        getTableUtil().addFilter(filterField, filterValues);
        return success();
    }

    @RequestMapping(value = "/admin-mail-list-filter-remove.json", method = RequestMethod.POST)
    @ResponseBody
    public String removeFilter(@RequestParam(value = "filterField", required = true) String filterField) {
        if (!StringUtils.hasText(filterField)) {
            return failByInvalidParam();
        }
        getTableUtil().removeFilter(filterField);
        return success();
    }

    private MVCTableUtil getTableUtil() {
        return new MVCTableUtil(request, this.getClass());
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
                return targetValue == null ? null : HtmlUtils.toText(targetValue.toString());
            }
        });
        return table;
    }
}