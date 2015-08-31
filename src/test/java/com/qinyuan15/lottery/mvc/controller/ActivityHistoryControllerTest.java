package com.qinyuan15.lottery.mvc.controller;

import com.qinyuan.lib.mvc.controller.DatabaseTable;
import org.junit.Test;
import org.powermock.reflect.Whitebox;

import java.text.DecimalFormat;

public class ActivityHistoryControllerTest {
    @Test
    public void test() throws Exception {
        ActivityHistoryController controller = new ActivityHistoryController();
        Whitebox.getField(ActivityHistoryController.class, "lotNumberFormat").set(controller, new DecimalFormat("000000"));
        DatabaseTable table = (DatabaseTable) Whitebox.getMethod(ActivityHistoryController.class, "getTable")
                .invoke(controller);
        System.out.println(table.getCount());
        System.out.println(table.getRows(0, 10).get(0).getCols()[0]);
        System.out.println(table.getRows(0, 10).get(1).getCols()[0]);
        System.out.println(table.getRows(0, 10).get(0).getCols()[2]);
    }
}
