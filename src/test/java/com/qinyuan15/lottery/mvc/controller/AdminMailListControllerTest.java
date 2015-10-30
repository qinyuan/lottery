package com.qinyuan15.lottery.mvc.controller;

import com.qinyuan.lib.database.test.DatabaseTestCase;
import com.qinyuan.lib.mvc.controller.DatabaseTable;
import org.junit.Test;
import org.powermock.reflect.Whitebox;

import static org.assertj.core.api.Assertions.assertThat;

public class AdminMailListControllerTest extends DatabaseTestCase {
    @Test
    public void test() throws Exception {
        AdminMailListController controller = new AdminMailListController();
        DatabaseTable table = (DatabaseTable) Whitebox.getMethod(AdminMailListController.class, "getTable").invoke(controller);
        table.addOrder("r.id", true);

        assertThat(table.getCount()).isEqualTo(3);
        assertThat(table.getRows().size()).isEqualTo(3);

        Object[] cols = table.getRows().get(0).getCols();
        assertThat(cols[0]).isEqualTo("2015-12-12 19:19:19");
        assertThat(cols[1]).isEqualTo("username1");
        assertThat(cols[2]).isEqualTo("normal-user1");
        assertThat(cols[3]).isNull();
        assertThat(cols[4]).isEqualTo("subject1");
        assertThat(cols[5]).isEqualTo("content1");

        cols = table.getRows().get(1).getCols();
        assertThat(cols[0]).isEqualTo("2015-12-13 18:18:18");
        assertThat(cols[1]).isEqualTo("username1");
        assertThat(cols[2]).isEqualTo("normal-user2");
        assertThat(cols[3]).isNull();
        assertThat(cols[4]).isEqualTo("subject2");
        assertThat(cols[5]).isEqualTo("content2");

        cols = table.getRows().get(2).getCols();
        assertThat(cols[0]).isEqualTo("2015-12-21 20:20:20");
        assertThat(cols[1]).isEqualTo("user1@domain1");
        assertThat(cols[2]).isEqualTo("admin-user1");
        assertThat(cols[3]).isEqualTo("12345@qq.com");
        assertThat(cols[4]).isEqualTo("subject2");
        assertThat(cols[5]).isEqualTo("content2");
    }
}
