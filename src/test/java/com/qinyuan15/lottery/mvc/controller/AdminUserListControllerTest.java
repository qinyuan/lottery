package com.qinyuan15.lottery.mvc.controller;

import com.qinyuan15.utils.mvc.controller.DatabaseTable;
import org.junit.Test;
import org.powermock.reflect.Whitebox;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class AdminUserListControllerTest {
    @Test
    public void test() throws Exception {
        AdminUserListController controller = new AdminUserListController();
        DatabaseTable table = (DatabaseTable) Whitebox.getMethod(AdminUserListController.class, "getUserTable")
                .invoke(controller);
        table.addOrder("username", false);
        System.out.println(table.getRows(0, 10).get(0).getCols()[0]);
        System.out.println(table.getRows(0, 10).get(1).getCols()[0]);
        System.out.println(table.getInstances(0, 10).get(0).getCols()[0]);
        System.out.println(table.getInstances(0, 10).get(1).getCols()[0]);
        System.out.println(table.getCount());
        System.out.println(table.getHeadStyles());

        System.out.println(table.getDistinctValues("username"));

        table.addFilter("username", new ArrayList());
        assertThat(table.getCount()).isZero();

        table = (DatabaseTable) Whitebox.getMethod(AdminUserListController.class, "getUserTable")
                .invoke(controller);
        List<String> list = new ArrayList<>();
        list.add(null);
        table.addFilter("invited_users", list);
        System.out.println(table.getCount());
    }
}
