package com.qinyuan15.lottery.mvc.dao;

import org.junit.Test;

public class HelpGroupDaoTest {
    private HelpGroupDao dao = new HelpGroupDao();

    @Test
    public void testGetInstance() throws Exception {
        System.out.println(dao.getInstance(1));
    }

    @Test
    public void testAdd() {
        //dao.add("helloWorld");
    }
}
