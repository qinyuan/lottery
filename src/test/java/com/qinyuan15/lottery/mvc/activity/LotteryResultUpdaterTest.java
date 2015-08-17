package com.qinyuan15.lottery.mvc.activity;

import org.junit.Test;

import java.text.DecimalFormat;

public class LotteryResultUpdaterTest {
    @Test
    public void testUpdate() throws Exception {
        new LotteryResultUpdater(new DecimalFormat("000000")).update(7, "101214222533");
        //System.out.println(101214222533L % 2352);
    }
}
