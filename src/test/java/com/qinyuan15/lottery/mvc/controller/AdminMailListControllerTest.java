package com.qinyuan15.lottery.mvc.controller;

import com.qinyuan15.utils.mvc.controller.DatabaseTable;
import org.junit.Test;
import org.powermock.reflect.Whitebox;

public class AdminMailListControllerTest {
    @Test
    public void test() throws Exception {
        AdminMailListController controller = new AdminMailListController();
        DatabaseTable table = (DatabaseTable) Whitebox.getMethod(AdminMailListController.class, "getTable").invoke(controller);
        System.out.println(table.getCount());
        System.out.println(table.getRows().size());
        System.out.println(table.getRows().get(0).getCols()[4]);
    }
}
