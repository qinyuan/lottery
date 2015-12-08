package com.qinyuan15.lottery.mvc.dao;

import com.qinyuan.lib.database.test.DatabaseTestCase;
import org.junit.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class LotterySameLotDaoTest extends DatabaseTestCase {
    private LotterySameLotDao dao = new LotterySameLotDao();

    @Test
    public void testGetUsers() {
        List<LotterySameLotDao.User> users = dao.getUsers(2, 10257);
        assertThat(users).hasSize(1);
        assertThat(users.get(0).username).isEqualTo("normal-user1");
        assertThat(users.get(0).liveness).isEqualTo(25);
        assertThat(users.get(0).virtual).isFalse();
        assertThat(users.get(0).lotId).isEqualTo(1);

        // add a virtual lot
        new LotteryLotDao().add(2, 1, 10257, true);

        users = dao.getUsers(2, 10257);
        assertThat(users).hasSize(2);
        assertThat(users.get(0).username).isEqualTo("normal-user1");
        assertThat(users.get(0).liveness).isEqualTo(25);
        assertThat(users.get(0).virtual).isFalse();
        assertThat(users.get(0).lotId).isEqualTo(1);
        assertThat(users.get(1).username).isEqualTo("virtual_user1");
        assertThat(users.get(1).liveness).isEqualTo(0);
        assertThat(users.get(1).virtual).isTrue();
        assertThat(users.get(1).lotId).isEqualTo(3);

        // change liveness of virtual user
        VirtualUserDao virtualUserDao = new VirtualUserDao();
        virtualUserDao.changeLiveness(virtualUserDao.getInstance(1), 2);
        users = dao.getUsers(2, 10257);
        assertThat(users.get(1).liveness).isEqualTo(2);
    }

    @Test
    public void testGetSimpleUsers() {
        List<LotterySameLotDao.SimpleUser> simpleUsers = dao.getSimpleUsers(2, 10257);
        assertThat(simpleUsers).hasSize(1);
        assertThat(simpleUsers.get(0).username).isEqualTo("normal-user1");
        assertThat(simpleUsers.get(0).liveness).isEqualTo(25);

        new LotteryLotDao().add(2, 4, 10257);

        simpleUsers = dao.getSimpleUsers(2, 10257);
        assertThat(simpleUsers).hasSize(2);
        assertThat(simpleUsers.get(0).username).isEqualTo("normal-user1");
        assertThat(simpleUsers.get(0).liveness).isEqualTo(25);
        assertThat(simpleUsers.get(1).username).isEqualTo("normal-user2");
        assertThat(simpleUsers.get(1).liveness).isEqualTo(13);

        assertThat(dao.getUsers(2, 10258)).isEmpty();
        assertThat(dao.getUsers(1, 10257)).isEmpty();
    }

    @Test
    public void testGetSimpleUsers2() {
        assertThat(dao.getUsers(2, "010257")).hasSize(1);
        assertThat(dao.getUsers(2, "10257")).hasSize(1);
        assertThat(dao.getUsers(2, "10258")).isEmpty();
    }
}
