package com.qinyuan15.lottery.mvc.dao;

import org.junit.Test;

public class DualColoredBallRecordDaoTest {
    private DualColoredBallRecordDao dao = new DualColoredBallRecordDao();

    @Test
    public void testAdd() throws Exception {
        //dao.add(2015073, "2015-06-25");
        //dao.add(2015001, "2015-01-01");
    }

    @Test
    public void testGetNearestInstance() throws Exception {
        DualColoredBallRecord record = dao.getNearestInstance(2015, 80);
        System.out.println(record);
        if (record != null) {
            System.out.println(record.getYear() + " " + record.getTerm() + " " + record.getPublishDate());
        }
    }
}
