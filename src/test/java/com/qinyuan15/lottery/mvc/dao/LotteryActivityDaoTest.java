package com.qinyuan15.lottery.mvc.dao;

import org.junit.Test;

public class LotteryActivityDaoTest {
    private LotteryActivityDao dao = new LotteryActivityDao();

    @Test
    public void testGetMaxSerialNumber() throws Exception {
        System.out.println(dao.getMaxSerialNumber(25));
        System.out.println(dao.getMaxSerialNumber(26));
    }
}
