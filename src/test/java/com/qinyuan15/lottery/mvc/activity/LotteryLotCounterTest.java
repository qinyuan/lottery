package com.qinyuan15.lottery.mvc.activity;

import org.junit.Test;

public class LotteryLotCounterTest {
    @Test
    public void testGetAvailableLotCount() {
        int count = new LotteryLotCounter().getAvailableLotCount(11, 2);
        System.out.println(count);
    }
}
