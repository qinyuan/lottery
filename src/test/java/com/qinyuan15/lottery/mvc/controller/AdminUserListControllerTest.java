package com.qinyuan15.lottery.mvc.controller;

import com.qinyuan15.utils.mvc.controller.DatabaseTable;
import org.junit.Test;
import org.powermock.reflect.Whitebox;

public class AdminUserListControllerTest {
    @Test
    public void test() throws Exception {
        AdminUserListController controller = new AdminUserListController();
        DatabaseTable table = (DatabaseTable) Whitebox.getMethod(AdminUserListController.class, "getUserTable")
                .invoke(controller);
        table.addOrder("username", false);

        System.out.println(table.getRows().size());
        table.addEqualFilter("last_send_time", "2012-12-12 12:12:12");
        System.out.println(table.getRows().size());
    }
}
