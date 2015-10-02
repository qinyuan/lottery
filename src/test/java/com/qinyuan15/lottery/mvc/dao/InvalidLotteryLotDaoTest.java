package com.qinyuan15.lottery.mvc.dao;

import org.junit.Test;

public class InvalidLotteryLotDaoTest {
    private InvalidLotteryLotDao dao = new InvalidLotteryLotDao();

    @Test
    public void testCount() throws Exception {
        System.out.println(dao.count(15));
    }

    @Test
    public void testGetSerialNumbers() {
        System.out.println(dao.getSerialNumbers(15));
    }

    @Test
    public void getGetNoTelUserIds() {
        System.out.println(dao.getNoTelUserIds(15));
    }

    @Test
    public void getInsufficientLivenessUserIds() {
        System.out.println(dao.getInsufficientLivenessUserIds(15));
    }
}
