package com.qinyuan15.lottery.mvc.dao;

import com.qinyuan.lib.database.test.DatabaseTestCase;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class HelpItemDaoTest extends DatabaseTestCase {
    private HelpItemDao dao = new HelpItemDao();

    @Test
    public void testGetInstancesByGroupId() throws Exception {
        assertThat(dao.getInstancesByGroupId(1)).isEmpty();
        assertThat(dao.getInstancesByGroupId(2)).hasSize(2);
        assertThat(dao.getInstancesByGroupId(3)).hasSize(1);
    }
}
