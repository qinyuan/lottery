package com.qinyuan15.lottery.mvc.activity;

import org.junit.Test;

public class InvalidLotteryLotSystemInfoSenderTest {
    @Test
    public void testSend() throws Exception {
        new InvalidLotteryLotSystemInfoSender().send(15);
    }
}
