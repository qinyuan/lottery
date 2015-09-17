package com.qinyuan15.lottery.mvc.mail;

import org.junit.Test;

public class RegisterMailSenderTest {
    @Test
    public void testSend() throws Exception {
        new RegisterMailSender().send("qinyuan15@qq.com", "AAAAAAAAAAAAA");
    }
}
