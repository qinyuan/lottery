package com.qinyuan15.lottery.mvc.dao;

import com.qinyuan.lib.contact.mail.MailSerialKey;
import com.qinyuan.lib.database.hibernate.HibernateListBuilder;
import com.qinyuan.lib.database.hibernate.HibernateUtils;
import com.qinyuan.lib.database.test.DatabaseTestCase;
import com.qinyuan.lib.lang.time.DateUtils;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.Test;

import java.sql.Date;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Test ActivateRequestDao
 * Created by qinyuan on 15-7-1.
 */
public class ActivateRequestDaoTest extends DatabaseTestCase {
    private ActivateRequestDao dao = new ActivateRequestDao();

    private int count() {
        return new HibernateListBuilder().addEqualFilter("mailType", ActivateRequestDao.MAIL_TYPE)
                .count(MailSerialKey.class);
    }

    private int countAll() {
        return new HibernateListBuilder().count(MailSerialKey.class);
    }

    @Test
    public void testAdd() throws Exception {
        assertThat(countAll()).isEqualTo(2);
        assertThat(count()).isEqualTo(1);
        dao.add(4);
        assertThat(countAll()).isEqualTo(3);
        assertThat(count()).isEqualTo(2);
    }

    @Test
    public void testGetInstanceBySerialKey() {
        String serialKey = "fjkdasipaifjdsaoij";
        updateSendTime(1, System.currentTimeMillis() - 3600 * 1000 * 25);
        assertThat(dao.getInstanceBySerialKey(serialKey)).isNull();

        updateSendTime(1, System.currentTimeMillis() - 3600 * 1000 * 23);
        assertThat(dao.getInstanceBySerialKey(serialKey)).isNotNull();

        serialKey = RandomStringUtils.randomAlphanumeric(20);
        assertThat(dao.getInstanceBySerialKey(serialKey)).isNull();

        serialKey = "jfdajkjesfioafpakjdsjkfasj";
        updateSendTime(2, System.currentTimeMillis() - 3600 * 1000 * 25);
        assertThat(dao.getInstanceBySerialKey(serialKey)).isNull();
    }

    @Test
    public void testGetInstance() {
        updateSendTime(1, System.currentTimeMillis() - 3600 * 1000 * 25);
        assertThat(dao.getInstance(1)).isNull();

        updateSendTime(1, System.currentTimeMillis() - 3600 * 1000 * 23);
        assertThat(dao.getInstance(1)).isNotNull();

        updateSendTime(2, System.currentTimeMillis() - 3600 * 1000 * 25);
        assertThat(dao.getInstance(2)).isNull();
    }

    private void updateSendTime(int id, long sendTimestamp) {
        String sendTime = DateUtils.toLongString(new Date(sendTimestamp));
        updateSendTime(id, sendTime);
    }

    private void updateSendTime(int id, String sendTime) {
        MailSerialKey serialKey = HibernateUtils.get(MailSerialKey.class, id);
        serialKey.setSendTime(sendTime);
        HibernateUtils.update(serialKey);
    }

    @Test
    public void testResponse() {
        assertThat(dao.getInstance(1).getResponseTime()).isNull();

        dao.response(1);

        long now = System.currentTimeMillis();
        String responseTime = dao.getInstance(1).getResponseTime();
        assertThat(responseTime).isNotNull();
        long responseTimestamp = DateUtils.newDate(DateUtils.trimMilliSecond(responseTime)).getTime();
        assertThat(now - responseTimestamp).isLessThan(1000);
        System.out.println(now - responseTimestamp);
    }
}
