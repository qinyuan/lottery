package com.qinyuan15.lottery.mvc;

import com.qinyuan.lib.lang.IntegerUtils;
import com.qinyuan.lib.mvc.security.LoginRecordDao;
import com.qinyuan.lib.mvc.security.SecurityUtils;
import com.qinyuan15.lottery.mvc.dao.UserDao;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Class to add login record by new thread to prevent from blocking
 * Created by qinyuan on 15-7-28.
 */
public class LoginRecordAdder {
    private final static Logger LOGGER = LoggerFactory.getLogger(LoginRecordAdder.class);

    public void add(String ip) {
        new AddThread(ip, new UserDao().getIdByName(SecurityUtils.getUsername())).start();
    }

    private class AddThread extends Thread {
        final String ip;
        final Integer userId;

        private AddThread(String ip, Integer userId) {
            this.ip = ip;
            this.userId = userId;
        }

        @Override
        public void run() {
            try {
                if (IntegerUtils.isPositive(userId)) {
                    new LoginRecordDao().add(userId, ip);
                }
            } catch (Exception e) {
                LOGGER.error("Fail to save login record, ip: {}, info: {}", ip, e);
            }
        }
    }

}
