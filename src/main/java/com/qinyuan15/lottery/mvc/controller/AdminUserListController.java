package com.qinyuan15.lottery.mvc.controller;

import com.qinyuan15.lottery.mvc.dao.User;
import com.qinyuan15.utils.mvc.controller.DatabaseTable;
import com.qinyuan15.utils.mvc.controller.ImageController;
import com.qinyuan15.utils.mvc.controller.PaginationAttributeAdder;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class AdminUserListController extends ImageController {

    @RequestMapping("/admin-user-list")
    public String index(@RequestParam(value = "orderField", required = false) String orderField,
                        @RequestParam(value = "orderType", required = false) String orderType) {
        IndexHeaderUtils.setHeaderParameters(this);

        DatabaseTable userTable = getUserTable();
        /*if (StringUtils.hasText(orderField)) {
            if (orderType != null && orderType.toLowerCase().equals("desc")) {
                userTable.addOrder(orderField, false);
            } else {
                userTable.addOrder(orderField, true);
            }
        }*/
        userTable.addOrder("username", false);
        new PaginationAttributeAdder(userTable, request).setRowItemsName("users").setPageSize(10).add();
        setAttribute("userTable", userTable);

        setTitle("用户列表");
        addCss("admin-form");
        addCssAndJs("admin-user-list");
        return "admin-user-list";
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

        DatabaseTable table = new DatabaseTable(tableName, "u.id", DatabaseTable.QueryType.SQL);
        table.addField("用户名", "u.username", "username");
        table.addField("邮箱", "u.email", "email");
        table.addField("活跃度", "l.sum_liveness", "liveness");
        table.addField("最近一次抽奖", "DATE_FORMAT(lot.last_lot_time,'%Y-%m-%d %T')", "lot_time");
        table.addField("邀请了谁", "idu.invited_users", "invited_users");
        table.addField("被请邀请", "iu.username", "invite_user");
        table.addEqualFilter("u.role", User.NORMAL);
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
