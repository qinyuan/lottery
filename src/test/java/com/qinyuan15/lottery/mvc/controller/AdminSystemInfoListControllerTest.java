package com.qinyuan15.lottery.mvc.controller;

import com.qinyuan.lib.mvc.controller.DatabaseTable;
import org.junit.Test;
import org.powermock.reflect.Whitebox;

public class AdminSystemInfoListControllerTest {
    @Test
    public void test() throws Exception {
        AdminSystemInfoListController controller = new AdminSystemInfoListController();
        DatabaseTable table = (DatabaseTable) Whitebox.getMethod(AdminSystemInfoListController.class, "getTable").invoke(controller);
        System.out.println(table.getCount());
        System.out.println(table.getRows().size());
    }
}
