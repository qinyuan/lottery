package com.qinyuan15.lottery.mvc.dao;

import com.google.common.collect.Lists;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class LotteryWinnerDaoTest {
    private LotteryWinnerDao dao = new LotteryWinnerDao();

    @Test
    public void testUpdate() {
        //dao.update(1, Lists.newArrayList(1, 2, 3));
        //dao.update(1, new ArrayList<Integer>());
    }

    @Test
    public void testGetUserIdsByActivityId() throws Exception {
        List<Integer> userIds = dao.getUserIdsByActivityId(1);
        System.out.println(userIds);
    }

    @Test
    public void testGetActivityIdsByUserId() throws Exception {
        List<Integer> activityIds = dao.getActivityIdsByUserId(1);
        System.out.println(activityIds);
    }
}
