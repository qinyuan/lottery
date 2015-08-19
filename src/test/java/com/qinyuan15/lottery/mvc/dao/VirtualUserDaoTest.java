package com.qinyuan15.lottery.mvc.dao;

import org.junit.Test;

public class VirtualUserDaoTest {
    @Test
    public void testHasUsername() throws Exception {
        System.out.println(new VirtualUserDao().hasUsername("test"));
    }
}
