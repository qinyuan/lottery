package com.qinyuan15.lottery.mvc.dao;

import com.google.common.collect.Lists;
import org.junit.Test;

public class SystemInfoSendRecordDaoTest {
    @Test
    public void testRead() throws Exception {
        SystemInfoSendRecordDao dao = new SystemInfoSendRecordDao();
        dao.read(2, Lists.newArrayList(1, 2, 3, 4, 5));
    }
}
