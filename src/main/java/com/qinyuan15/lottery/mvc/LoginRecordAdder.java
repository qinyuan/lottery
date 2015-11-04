package com.qinyuan15.lottery.mvc;

import com.qinyuan.lib.lang.IntegerUtils;
import com.qinyuan.lib.mvc.controller.RequestUtils;
import com.qinyuan.lib.mvc.controller.UserAgent;
import com.qinyuan.lib.mvc.security.LoginRecordDao;
import com.qinyuan.lib.mvc.security.SecurityUtils;
import com.qinyuan15.lottery.mvc.dao.UserDao;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;

/**
 * Class to add login record by new thread to prevent from blocking
 * Created by qinyuan on 15-7-28.
 */
public class LoginRecordAdder {
    private final static Logger LOGGER = LoggerFactory.getLogger(LoginRecordAdder.class);

    public void add(HttpServletRequest request) {
        add(RequestUtils.getRealRemoteAddress(request), new UserAgent(request).getOS().toString());
    }

    public void add(String ip, String platform) {
        new AddThread(ip, new UserDao().getIdByName(SecurityUtils.getUsername()), platform).start();
    }

    private class AddThread extends Thread {
        final String ip;
        final Integer userId;
        final String platform;

        private AddThread(String ip, Integer userId, String platform) {
            this.ip = ip;
            this.userId = userId;
            this.platform = platform;
        }

        @Override
        public void run() {
            try {
                if (IntegerUtils.isPositive(userId)) {
                    new LoginRecordDao().add(userId, ip, platform);
                }
            } catch (Exception e) {
                LOGGER.error("Fail to save login record, ip: {}, userId: {}, platform: {}, info: {}",
                        ip, userId, platform, e);
            }
        }
    }

}
