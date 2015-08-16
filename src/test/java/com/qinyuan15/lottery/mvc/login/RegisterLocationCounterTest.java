package com.qinyuan15.lottery.mvc.login;

import org.junit.Test;

public class RegisterLocationCounterTest {
    @Test
    public void testCount() throws Exception {
        System.out.println(new RegisterLocationCounter().count());
        System.out.println(new RegisterLocationCounter().count(true));
    }
}
