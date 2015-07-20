package com.qinyuan15.lottery.mvc.dao;

import org.junit.Test;

/**
 * Test ActivateRequestDao
 * Created by qinyuan on 15-7-1.
 */
public class MailSerialKeyDaoTest {
    private ActivateRequestDao dao = new ActivateRequestDao();

    @Test
    public void testAdd() throws Exception {
        //dao.add(1);
    }

    @Test
    public void testGetInstanceBySerialKey() {
        String serialKey = "HrbOdjAhGkQzyTZwhIITjFyOYnFxIdjIEFzWprulgtAJqUSNtuouVRDKzjyieYQGXJvZcecSNgSZhDwytASXzMYWukHHfOayzsyf";
        System.out.println(dao.getInstanceBySerialKey(serialKey));

        serialKey = "ArbOdjAhGkQzyTZwhIITjFyOYnFxIdjIEFzWprulgtAJqUSNtuouVRDKzjyieYQGXJvZcecSNgSZhDwytASXzMYWukHHfOayzsyf";
        System.out.println(dao.getInstanceBySerialKey(serialKey));
    }

    @Test
    public void testResponse() {
        //dao.response(1);
    }
}
