package com.qinyuan15.lottery.mvc.controller;

import com.qinyuan15.lottery.mvc.dao.User;
import com.qinyuan15.utils.mvc.controller.DatabaseTable;
import com.qinyuan15.utils.mvc.controller.ImageController;
import com.qinyuan15.utils.mvc.controller.PaginationAttributeAdder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class AdminUserListController extends ImageController {

    @RequestMapping("/admin-user-list")
    public String index() {
        IndexHeaderUtils.setHeaderParameters(this);

        DatabaseTable userTable = getUserTable();
        new PaginationAttributeAdder(getUserTable(), request)
                .setRowItemsName("users").setPageSize(10).add();
        setAttribute("userTable", userTable);

        setTitle("用户列表");
        addCss("admin-form");
        addCssAndJs("admin-user-list");
        return "admin-user-list";
    }

    private DatabaseTable getUserTable() {
        String livenessTable = "SELECT spread_user_id,SUM(liveness) AS sum_liveness FROM lottery_liveness " +
                "WHERE activity_id=(SELECT MAX(activity_id) FROM lottery_activity WHERE expire=false)";
        String tableName = "user AS u LEFT JOIN (" + livenessTable + ") AS l "
                + "ON u.id=l.spread_user_id";

        DatabaseTable table = new DatabaseTable(tableName, "u.id", DatabaseTable.QueryType.SQL);
        table.addField("用户名", "u.username", "username");
        table.addField("邮箱", "u.email", "email");
        table.addField("活跃度", "l.sum_liveness", "liveness");
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
