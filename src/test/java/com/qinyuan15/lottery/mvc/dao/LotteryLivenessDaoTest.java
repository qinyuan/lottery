package com.qinyuan15.lottery.mvc.dao;

import org.junit.Test;

public class LotteryLivenessDaoTest {
    private LotteryLivenessDao dao = new LotteryLivenessDao();

    @Test
    public void testGetLiveness() {
        System.out.println(dao.getLiveness(6, 27));
    }

    @Test
    public void testGetMaxLivenessUsernames() {
        System.out.println(dao.getMaxLivenessUsernames(27));
    }

    @Test
    public void testAdd() {
        //dao.add(6, 1, 2, "xina", true);
    }
}