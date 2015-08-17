package com.qinyuan15.lottery.mvc.dao;

import org.junit.Test;

/**
 * Test IndexImageDao
 * Created by qinyuan on 15-6-18.
 */
public class IndexImageDaoTest {
    private IndexImageDao dao = new IndexImageDao();

    @Test
    public void testGetInstances() throws Exception {
        System.out.println(dao.getInstances().size());
    }

    @Test
    public void testAdd() throws Exception {
        //dao.add("/var/www/html/activity/hello", "/var/www/html/activity/world");
    }
}
