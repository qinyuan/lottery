package com.qinyuan15.lottery.mvc.dao;

import org.junit.Test;

public class LotteryActivityDaoTest {
    private LotteryActivityDao dao = new LotteryActivityDao();

    @Test
    public void testFactory() {
        LotteryActivityDao.Factory factory = LotteryActivityDao.factory();
        System.out.println(factory.getCount());
        System.out.println(factory.getInstances().size());
        System.out.println(factory.getFirstInstance().getId());
    }

    /*@Test
    public void testGetMaxSerialNumber() throws Exception {
        System.out.println(dao.getMaxSerialNumber(25));
        System.out.println(dao.getMaxSerialNumber(26));
    }*/

    @Test
    public void testIncreaseMaxSerialNumber() {
        //dao.increaseMaxSerialNumber(25, 2);
    }

    @Test
    public void testGetMaxTerm() {
        System.out.println(dao.getMaxTerm());
    }

    @Test
    public void testGetLatestDescription() {
        System.out.println(dao.getLatestDescription());
    }

    @Test
    public void testGetLatestMinLivenessToParticipate() {
        System.out.println(dao.getLatestMinLivenessToParticipate());
    }
}
