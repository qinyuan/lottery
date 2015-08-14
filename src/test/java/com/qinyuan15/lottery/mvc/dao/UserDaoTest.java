package com.qinyuan15.lottery.mvc.dao;

import org.junit.Test;

/**
 * Test UserDao
 * Created by qinyuan on 15-6-29.
 */
public class UserDaoTest {
    private UserDao userDao = new UserDao();

    @Test
    public void testAddAdmin() {
        //userDao.addAdmin("username", "password");
    }

    @Test
    public void testCount() {
        System.out.println(userDao.countNormalUsers());
        System.out.println(userDao.countActiveNormalUsers());
        System.out.println(userDao.countDirectlyRegisterNormalUsers());
        System.out.println(userDao.countInvitedRegisterNormalUsers());
    }

    @Test
    public void testAddNormal() {
        //userDao.addNormal("hello", "world", "qinyuan15@sina.com", "12345");
    }


    @Test
    public void testDelete() {
        //userDao.delete(2);
    }

    @Test
    public void testHasUsername() {
        System.out.println(userDao.hasUsername("username"));
        System.out.println(userDao.hasUsername("username2"));
        System.out.println(userDao.hasUsername("username3"));
        System.out.println(userDao.hasUsername("testMe"));
    }

    @Test
    public void testHasEmail() {
        System.out.println(userDao.hasEmail("qinyuan15@sina.com"));
        System.out.println(userDao.hasEmail("qinyuan150@sina.com"));
        System.out.println(userDao.hasEmail("qinyuan15@163.com"));
        System.out.println(userDao.hasEmail("qinyuan15@qq.com"));
        System.out.println(userDao.hasEmail("QINYUAN15@qq.com"));
    }

    @Test
    public void testGetInstance() {
        User user = userDao.getInstance(1);
        System.out.println(user.getId());
        System.out.println(user.getUsername());
    }

    @Test
    public void testGetInstances() {
        System.out.println(userDao.getInstances().size());
    }

    @Test
    public void testGetUserIdBySerialKey() {
        System.out.println(userDao.getIdBySerialKey("adjfklsajfd"));
        System.out.println(userDao.getIdBySerialKey("mIJGXjen64YICnzsfjMhP6vpe4V2kI83mIFTd4h7PyMqbmA8411VAu3fZlBoMJvzXjwSx0Y1L4QYIkUi3m9UDVNnFOt0EfmpdKyf"));
    }
}
