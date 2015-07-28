package com.qinyuan15.lottery.mvc.controller;

import com.google.common.collect.Lists;
import com.qinyuan15.lottery.mvc.dao.User;
import com.qinyuan15.utils.mvc.controller.DatabaseTable;
import com.qinyuan15.utils.mvc.controller.ImageController;
import com.qinyuan15.utils.mvc.controller.PaginationAttributeAdder;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class AdminUserListController extends ImageController {

    @RequestMapping("/admin-user-list")
    public String index(@RequestParam(value = "orderField", required = false) String orderField,
                        @RequestParam(value = "orderType", required = false) String orderType) {
        IndexHeaderUtils.setHeaderParameters(this);

        DatabaseTable userTable = getUserTable();
        if (StringUtils.hasText(orderField)) {
            if (orderType != null && orderType.toLowerCase().equals("desc")) {
                userTable.addOrder(orderField, false);
            } else {
                userTable.addOrder(orderField, true);
            }
        }
        for (Map.Entry<String, String[]> entry : getFilters().entrySet()) {
            userTable.addFilter(entry.getKey(), Lists.newArrayList(entry.getValue()));
        }

        setAttribute("userTable", userTable);
        new PaginationAttributeAdder(userTable, request).setRowItemsName("users").setPageSize(10).add();

        setTitle("用户列表");
        addCss("admin-form");
        addCssAndJs("admin-user-list");
        addJs("lib/ckeditor/ckeditor");
        return "admin-user-list";
    }

    private final static String NULL_STRING = "(空白)";

    @RequestMapping(value = "/admin-user-list-distinct-values.json", method = RequestMethod.GET)
    @ResponseBody
    public String json(@RequestParam(value = "alias", required = false) String alias) {
        DatabaseTable userTable = getUserTable();
        List<DistinctItem> items = new ArrayList<>();
        for (Object value : userTable.getDistinctValues(alias)) {
            if (value == null) {
                value = NULL_STRING;
            }
            items.add(new DistinctItem(value, !isFiltered(alias, value)));
        }
        return toJson(items);
    }

    /**
     * validate is certain value in certain field is filtered
     *
     * @param field field in database
     * @param value value of given field
     * @return true is value is filtered
     */
    private boolean isFiltered(String field, Object value) {
        String[] values = getFilters().get(field);
        if (values == null) {
            return false;
        }

        if (value.equals(NULL_STRING)) {
            for (String v : values) {
                if (v == null) {
                    return false;
                }
            }
        } else {
            String valueString = value.toString();
            for (String v : values) {
                if (v != null && v.equals(valueString)) {
                    return false;
                }
            }
        }
        return true;
    }

    @RequestMapping(value = "/admin-user-list-filter.json", method = RequestMethod.POST)
    @ResponseBody
    public String addFilter(@RequestParam(value = "filterField", required = true) String filterField,
                            @RequestParam(value = "filterValues[]", required = false) String[] filterValues) {
        if (!StringUtils.hasText(filterField)) {
            return failByInvalidParam();
        }

        if (filterValues == null) {
            filterValues = new String[0];
        }
        for (int i = 0; i < filterValues.length; i++) {
            if (NULL_STRING.equals(filterValues[i])) {
                filterValues[i] = null;
            }
        }

        getFilters().put(filterField, filterValues);
        return success();
    }

    @RequestMapping(value = "/admin-user-list-filter-remove.json", method = RequestMethod.POST)
    @ResponseBody
    public String removeFilter(@RequestParam(value = "filterField", required = true) String filterField) {
        if (!StringUtils.hasText(filterField)) {
            return failByInvalidParam();
        }

        getFilters().remove(filterField);
        return success();
    }

    private final static String FILTERS_KEY = "admin-user-list-filters";

    private Map<String, String[]> getFilters() {
        @SuppressWarnings("unchecked")
        Map<String, String[]> filters = (Map) session.getAttribute(FILTERS_KEY);
        if (filters == null) {
            filters = new HashMap<>();
            session.setAttribute(FILTERS_KEY, filters);
        }
        return filters;
    }

    private static class DistinctItem {
        public final Object text;
        public final boolean checked;

        DistinctItem(Object text, boolean checked) {
            this.text = text;
            this.checked = checked;
        }
    }

    private DatabaseTable getUserTable() {
        String livenessTable = "SELECT spread_user_id,SUM(liveness) AS sum_liveness FROM lottery_liveness " +
                "WHERE activity_id=(SELECT MAX(activity_id) FROM lottery_activity WHERE expire=false)";
        String tableName = "user AS u LEFT JOIN (" + livenessTable + ") AS l ON u.id=l.spread_user_id";

        String lotTable = "SELECT user_id,MAX(lot_time) AS last_lot_time FROM lottery_lot GROUP BY user_id";
        tableName += " LEFT JOIN (" + lotTable + ") AS lot ON u.id=lot.user_id";

        tableName += " LEFT JOIN user AS u2 ON u.id=u2.spread_user_id";

        String invitedUserTable = "SELECT u.id,u.spread_user_id,group_concat(u2.username) AS invited_users " +
                "FROM user AS u JOIN user AS u2 ON u.id=u2.spread_user_id GROUP BY u.id";
        tableName += " LEFT JOIN (" + invitedUserTable + ") AS idu ON u.id=idu.id";

        tableName += " LEFT JOIN user AS iu ON u.spread_user_id=iu.id";

        String loginRecordTable = "SELECT user_id,location FROM login_record WHERE id IN " +
                "(SELECT MAX(id) FROM login_record GROUP BY user_id)";
        tableName += " LEFT JOIN (" + loginRecordTable + ") AS lr ON u.id=lr.user_id";

        DatabaseTable table = new DatabaseTable(tableName, "u.id", DatabaseTable.QueryType.SQL);
        table.addField("用户名", "u.username", "username");
        table.addField("邮箱", "u.email", "email");
        table.addField("地区", "lr.location", "location");
        table.addField("活跃度", "l.sum_liveness", "liveness");
        table.addField("最近一次抽奖", "DATE_FORMAT(lot.last_lot_time,'%Y-%m-%d %T')", "lot_time");
        table.addField("邀请了谁", "idu.invited_users", "invited_users");
        table.addField("被请邀请", "iu.username", "invite_user");
        table.addEqualFilter("u.role", User.NORMAL);
        return table;
    }
}