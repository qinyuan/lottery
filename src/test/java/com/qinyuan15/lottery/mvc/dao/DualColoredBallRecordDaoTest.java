package com.qinyuan15.lottery.mvc.dao;

import com.qinyuan.lib.database.test.DatabaseTestCase;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class DualColoredBallRecordDaoTest extends DatabaseTestCase {
    private DualColoredBallRecordDao dao = new DualColoredBallRecordDao();

    @Test
    public void testCount() {
        assertThat(dao.count()).isEqualTo(2);
    }

    @Test
    public void testAdd() {
        assertThat(dao.count()).isEqualTo(2);
        dao.add(2015073, "2015-06-25", "010217222627");
        assertThat(dao.count()).isEqualTo(3);
    }

    @Test
    public void testGetNearestInstance() {
        DualColoredBallRecord record = dao.getNearestInstance(2015, 81);
        assertThat(record.getYear()).isEqualTo(2015);
        assertThat(record.getTerm()).isEqualTo(80);

        record = dao.getNearestInstance(2015, 84);
        assertThat(record.getTerm()).isEqualTo(80);

        record = dao.getNearestInstance(2015, 85);
        assertThat(record.getTerm()).isEqualTo(85);

        record = dao.getNearestInstance(2015, 86);
        assertThat(record.getTerm()).isEqualTo(85);

        record = dao.getNearestInstance(2014, 86);
        assertThat(record).isNull();
    }

    @Test
    public void testGetLatestInstance() {
        DualColoredBallRecord record = new DualColoredBallRecordDao().getLatestInstance();
        assertThat(record.getYear()).isEqualTo(2015);
        assertThat(record.getTerm()).isEqualTo(85);
        assertThat(record.getFullTerm()).isEqualTo("2015085");
        assertThat(record.getPublishDate()).isEqualTo("2015-07-23");
        assertThat(record.getResult()).isEqualTo("020825272829");
    }
}
