package com.qinyuan15.lottery.mvc.dao;

import org.junit.Test;

/**
 * Test IndexImageMapDao
 * Created by qinyuan on 15-6-19.
 */
public class IndexImageMapDaoTest {
    private IndexImageMapDao dao = new IndexImageMapDao();

    @Test
    public void testAdd() throws Exception {
        dao.add(1, 1, 1, 2, 2, "href", "comment");
    }
}
