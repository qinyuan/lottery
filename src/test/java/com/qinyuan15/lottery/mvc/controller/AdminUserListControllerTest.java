package com.qinyuan15.lottery.mvc.controller;

import com.qinyuan15.utils.mvc.controller.DatabaseTable;
import org.junit.Test;
import org.powermock.reflect.Whitebox;

import java.util.List;

public class AdminUserListControllerTest {
    private AdminUserListController controller = new AdminUserListController();

    @Test
    public void testGetTable() throws Exception {
        DatabaseTable table = controller.getTable();
        table.addOrder("username", false);

        System.out.println(table.getRows().size());
        System.out.println(table.getRows().get(0).getCols()[4]);
        System.out.println(table.getDistinctValues("last_send_time"));
    }

    @SuppressWarnings("unchecked")
    @Test
    public void testGetSimpleTable() throws Exception {
        List<Object[]> table = (List) Whitebox.getMethod(AdminUserListController.class, "getSimpleTable")
                .invoke(controller);
        System.out.println(table.size());
        for (Object[] objects : table) {
            System.out.println(objects[1]);
            //System.out.println(objects[0] + " " + objects[1] + " " + objects[2]);
        }
    }
}
