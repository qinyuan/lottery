package com.qinyuan15.lottery.mvc.dao;

import org.junit.Test;

public class HelpItemDaoTest {
    private HelpItemDao dao = new HelpItemDao();

    @Test
    public void testGetInstancesByGroupId() throws Exception {
        System.out.println(dao.getInstancesByGroupId(5).size());
    }
}
