package com.qinyuan15.lottery.mvc.dao;

import com.qinyuan.lib.database.test.DatabaseTestCase;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class HelpGroupDaoTest extends DatabaseTestCase {
    private HelpGroupDao dao = new HelpGroupDao();

    @Test
    public void testGetFirstInstance() {
        HelpGroup helpGroup = dao.getFirstInstance();
        assertThat(helpGroup.getTitle()).isEqualTo("title3");
        assertThat(helpGroup.getRanking()).isEqualTo(5);
    }

    @Test
    public void testGetInstance() throws Exception {
        assertThat(dao.getInstance(1).getTitle()).isEqualTo("title1");
        assertThat(dao.getInstance(111)).isNull();
    }

    @Test
    public void testAdd() {
        assertThat(dao.count()).isEqualTo(3);
        dao.add("title_added");
        assertThat(dao.count()).isEqualTo(4);
    }
}
