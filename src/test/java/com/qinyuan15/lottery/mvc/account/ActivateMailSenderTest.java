package com.qinyuan15.lottery.mvc.account;

import org.junit.Test;

/**
 * Test ActivateMailSender
 * Created by qinyuan on 15-7-1.
 */
public class ActivateMailSenderTest {
    @Test
    public void testSend() throws Exception {
        ActivateMailSender sender = new ActivateMailSender();
        sender.send(2);
    }
}
