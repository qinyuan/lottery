package com.qinyuan15.lottery.mvc.dao;

import org.junit.Test;

/**
 * Test NavigationLinkDao
 * Created by qinyuan on 15-6-17.
 */
public class NavigationLinkDaoTest {
    @Test
    public void testGetInstances() throws Exception {
        System.out.println(new NavigationLinkDao().getInstances().size());
    }
}
