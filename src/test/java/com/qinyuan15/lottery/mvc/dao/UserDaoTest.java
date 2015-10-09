package com.qinyuan15.lottery.mvc.dao;

import com.qinyuan.lib.database.test.DatabaseTestCase;
import com.qinyuan.lib.mvc.security.UserRole;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Test UserDao
 * Created by qinyuan on 15-6-29.
 */
public class UserDaoTest extends DatabaseTestCase {
    private UserDao userDao = new UserDao();

    @Test
    public void testAddAdmin() {
        assertThat(userDao.getInstanceByUsername("username")).isNull();
        userDao.addAdmin("username", "password");
        assertThat(userDao.getInstanceByUsername("username")).isNotNull();
        assertThat(userDao.getInstanceByUsername("username").getRole()).isEqualTo(UserRole.ADMIN);
    }

    @Test
    public void testCount() {
        assertThat(userDao.countAllUsers()).isEqualTo(5);
        assertThat(userDao.countNormalUsers()).isEqualTo(3);
        assertThat(userDao.countActiveNormalUsers()).isEqualTo(2);
        assertThat(userDao.countDirectlyRegisterNormalUsers()).isEqualTo(2);
        assertThat(userDao.countInvitedRegisterNormalUsers()).isEqualTo(1);
    }

    @Test
    public void testAddNormal() {
        assertThat(userDao.getInstanceByUsername("username")).isNull();
        userDao.addNormal("username", "password", "12345@qq.com");
        assertThat(userDao.getInstanceByUsername("username")).isNotNull();
        assertThat(userDao.getInstanceByUsername("username").getRole()).isEqualTo(UserRole.NORMAL);
    }

    @Test
    public void testDelete() {
        int count = userDao.countAllUsers();
        userDao.deleteNormal(1);
        userDao.deleteNormal(2);

        assertThat(userDao.countAllUsers()).isEqualTo(count);
        userDao.deleteNormal(3);
        assertThat(userDao.countAllUsers()).isEqualTo(count - 1);
    }

    @Test
    public void testHasUsername() {
        assertThat(userDao.hasUsername("user0")).isFalse();
        assertThat(userDao.hasUsername("user1")).isTrue();
        assertThat(userDao.hasUsername("admin-user1")).isTrue();
    }

    @Test
    public void testHasEmail() {
        assertThat(userDao.hasEmail("12345@qq.com")).isTrue();
        assertThat(userDao.hasEmail("123456@qq.com")).isFalse();
    }

    @Test
    public void testGetInstance() {
        User user = userDao.getInstance(1);
        assertThat(user.getId()).isEqualTo(1);
        assertThat(user.getUsername()).isEqualTo("user1");
        assertThat(user.getPassword()).isEqualTo("password");
    }

    @Test
    public void testGetInstances() {
        assertThat(userDao.getInstances()).isNotNull().isNotEmpty();
    }

    @Test
    public void testGetUserIdBySerialKey() {
        assertThat(userDao.getIdBySerialKey("adjfklsajfd")).isNull();
        assertThat(userDao.getIdBySerialKey("abdafdipsuap")).isEqualTo(1);
    }
}
