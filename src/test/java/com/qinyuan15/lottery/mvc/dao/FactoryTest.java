package com.qinyuan15.lottery.mvc.dao;

import org.junit.Test;

/**
 * Test LotteryActivityDao
 * Created by qinyuan on 15-7-11.
 */
public class FactoryTest {
    //private LotteryActivityDao dao = new LotteryActivityDao();

    @Test
    public void testGetCount() throws Exception {
        System.out.println(LotteryActivityDao.factory().getCount());
        System.out.println(LotteryActivityDao.factory().setExpire(true).getCount());
    }

    @Test
    public void testGetInstances() throws Exception {
        System.out.println(LotteryActivityDao.factory().getInstances().size());
    }
}
