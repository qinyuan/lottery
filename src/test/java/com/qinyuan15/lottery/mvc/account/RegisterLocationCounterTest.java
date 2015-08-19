package com.qinyuan15.lottery.mvc.account;

import org.junit.Test;

public class RegisterLocationCounterTest {
    @Test
    public void testCount() throws Exception {
        System.out.println(new RegisterLocationCounter().count());
        System.out.println(new RegisterLocationCounter().count(true));
    }
}
