package com.qinyuan15.lottery.mvc.dao;

import com.qinyuan.lib.database.test.DatabaseTestCase;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class SubIndexImageDaoTest extends DatabaseTestCase {
    private SubIndexImageDao dao = new SubIndexImageDao();

    @Test
    public void testAdd() throws Exception {
        assertThat(dao.count()).isZero();
        dao.add(2, "path1", "path2");
        assertThat(dao.count()).isEqualTo(1);
    }

    @Test
    public void testUpdate() throws Exception {
        dao.add(2, "path1", "path2");
        dao.update(1, "new_path1", "new_path2");
        SubIndexImage image = dao.getInstance(1);
        assertThat(image.getPath()).isEqualTo("new_path1");
        assertThat(image.getBackPath()).isEqualTo("new_path2");
    }

    @Test
    public void testRankUp() throws Exception {
        dao.add(1, "path1", "backPath1");
        dao.add(1, "path2", "backPath2");
        dao.add(2, "path3", "backPath3");

        dao.rankUp(2);
        assertThat(dao.getInstance(1).getRanking()).isEqualTo(2);
        assertThat(dao.getInstance(2).getRanking()).isEqualTo(1);
        assertThat(dao.getInstance(3).getRanking()).isEqualTo(3);

        dao.rankUp(3);
        assertThat(dao.getInstance(3).getRanking()).isEqualTo(3);
    }

    @Test
    public void testRankDown() throws Exception {
        dao.add(1, "path1", "backPath1");
        dao.add(1, "path2", "backPath2");
        dao.add(2, "path3", "backPath3");

        dao.rankDown(1);
        assertThat(dao.getInstance(1).getRanking()).isEqualTo(2);
        assertThat(dao.getInstance(2).getRanking()).isEqualTo(1);
        assertThat(dao.getInstance(3).getRanking()).isEqualTo(3);

        dao.rankDown(1);
        assertThat(dao.getInstance(3).getRanking()).isEqualTo(3);
    }
}
