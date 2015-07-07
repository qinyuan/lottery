package com.qinyuan15.lottery.mvc.dao;

import org.junit.Test;

public class HelpGroupDaoTest {
    private HelpGroupDao dao = new HelpGroupDao();

    @Test
    public void testGetFirstInstance() {
        HelpGroup helpGroup = dao.getFirstInstance();
        if (helpGroup != null) {
            System.out.println(helpGroup.getId());
            System.out.println(helpGroup.getTitle());
        }
    }

    @Test
    public void testGetInstance() throws Exception {
        System.out.println(dao.getInstance(1));
    }

    @Test
    public void testAdd() {
        //dao.add("helloWorld");
    }
}
