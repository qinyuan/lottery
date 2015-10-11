package com.qinyuan15.lottery.mvc.dao;

import com.google.common.collect.Lists;
import com.qinyuan.lib.database.test.DatabaseTestCase;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class SystemInfoSendRecordDaoTest extends DatabaseTestCase {
    private SystemInfoSendRecordDao dao = new SystemInfoSendRecordDao();

    @Test
    public void testFactory() {
        assertThat(SystemInfoSendRecordDao.factory().getCount()).isEqualTo(3);
        assertThat(SystemInfoSendRecordDao.factory().setUnread(false).getCount()).isEqualTo(0);
    }

    @Test
    public void testRead() {
        assertThat(SystemInfoSendRecordDao.factory().setUnread(true).getCount()).isEqualTo(3);

        dao.read(2, Lists.newArrayList(1, 2, 3, 4, 5));
        assertThat(SystemInfoSendRecordDao.factory().setUnread(true).getCount()).isEqualTo(3);

        dao.read(3, Lists.newArrayList(1, 2, 3, 4, 5));
        assertThat(SystemInfoSendRecordDao.factory().setUnread(true).getCount()).isEqualTo(2);
    }
}
