package com.qinyuan15.lottery.mvc.dao;

import com.qinyuan.lib.database.test.DatabaseTestCase;
import com.qinyuan.lib.mvc.security.UserRole;
import org.apache.commons.lang3.RandomStringUtils;
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
        assertThat(userDao.getInstanceByName("username")).isNull();
        userDao.addAdmin("username", "password");
        assertThat(userDao.getInstanceByName("username")).isNotNull();
        assertThat(userDao.getInstanceByName("username").getRole()).isEqualTo(UserRole.ADMIN);
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
        assertThat(userDao.getInstanceByName("username")).isNull();
        userDao.addNormal("username", "password", "123456@qq.com");
        assertThat(userDao.getInstanceByName("username")).isNotNull();
        assertThat(userDao.getInstanceByName("username").getRole()).isEqualTo(UserRole.NORMAL);
    }

    @Test
    public void testAddNormal2() {
        assertThat(userDao.getInstanceByName("username")).isNull();
        userDao.addNormal("username", "password", "123456@qq.com", "testOpenId");
        User user = userDao.getInstanceByName("username");
        assertThat(user).isNotNull();
        assertThat(user.getRole()).isEqualTo(UserRole.NORMAL);
        assertThat(user.getQqOpenId()).isEqualTo("testOpenId");
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
    public void testGetInstanceByName() {
        User user = userDao.getInstanceByName("12345@qq.com");
        assertThat(user).isNotNull();
        assertThat(user.getUsername()).isEqualTo("admin-user1");

        assertThat(userDao.getInstanceByName("user1")).isNotNull();

        user = userDao.getInstanceByName("User1");
        assertThat(user).isNotNull();
        assertThat(user.getUsername()).isEqualTo("user1");

        assertThat(userDao.getInstanceByName("12345test@qq.com")).isNull();
    }

    @Test
    public void testGetInstanceByEmail() {
        assertThat(userDao.getInstanceByEmail("12345@qq.com")).isNotNull();
        assertThat(userDao.getInstanceByEmail("12345@QQ.COM")).isNotNull();
        assertThat(userDao.getInstanceByEmail("123456@QQ.COM")).isNull();
        assertThat(userDao.getInstanceByEmail("user1")).isNull();
    }

    @Test
    public void testGetNameById() {
        assertThat(userDao.getNameById(1)).isEqualTo("user1");
        assertThat(userDao.getNameById(2)).isEqualTo("admin-user1");
        assertThat(userDao.getNameById(200)).isNull();
    }

    private final static String VALID_SERIAL_KEY = "adjfklsajfd";

    @Test
    public void testGetUserIdBySerialKey() {
        assertThat(userDao.getIdBySerialKey(VALID_SERIAL_KEY)).isNull();
        assertThat(userDao.getIdBySerialKey("abdafdipsuap")).isEqualTo(1);
    }

    @Test
    public void testUpdateTel() {
        assertThat(userDao.getInstance(1).getTel()).isNull();

        String tel = RandomStringUtils.randomNumeric(11);
        userDao.updateTel(1, tel);
        assertThat(userDao.getInstance(1).getTel()).isEqualTo(tel);
    }

    @Test
    public void testUpdateEmail() {
        assertThat(userDao.getInstance(1).getEmail()).isNull();

        userDao.updateEmail(1, "test");
        assertThat(userDao.getInstance(1).getEmail()).isNull();
        userDao.updateEmail(1, "hello@test.com");
        assertThat(userDao.getInstance(1).getEmail()).isEqualTo("hello@test.com");

        assertThat(userDao.getInstance(2).getEmail()).isEqualTo("12345@qq.com");
        userDao.updateEmail(2, "test123@sina.com");
        assertThat(userDao.getInstance(2).getEmail()).isEqualTo("test123@sina.com");
    }
}
