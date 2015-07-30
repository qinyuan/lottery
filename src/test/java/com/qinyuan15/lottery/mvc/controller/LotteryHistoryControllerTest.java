package com.qinyuan15.lottery.mvc.controller;

import com.qinyuan15.utils.mvc.controller.DatabaseTable;
import org.junit.Test;
import org.powermock.reflect.Whitebox;

import java.text.DecimalFormat;

public class LotteryHistoryControllerTest {
    @Test
    public void test() throws Exception {
        LotteryHistoryController controller = new LotteryHistoryController();
        Whitebox.getField(LotteryHistoryController.class, "lotNumberFormat").set(controller, new DecimalFormat("000000"));
        DatabaseTable table = (DatabaseTable) Whitebox.getMethod(LotteryHistoryController.class, "getTable")
                .invoke(controller);
        System.out.println(table.getCount());
        System.out.println(table.getRows(0, 10).get(0).getCols()[0]);
        System.out.println(table.getRows(0, 10).get(1).getCols()[0]);
        System.out.println(table.getRows(0, 10).get(0).getCols()[2]);
    }
}
