package com.qinyuan15.lottery.mvc.lottery;

import org.junit.Test;

public class LotteryLotCounterTest {
    @Test
    public void testGetAvailableLotCount() {
        int count = new LotteryLotCounter().getAvailableLotCount(1, 2);
        System.out.println(count);
    }
}
