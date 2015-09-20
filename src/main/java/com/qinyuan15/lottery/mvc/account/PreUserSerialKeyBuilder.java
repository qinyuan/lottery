package com.qinyuan15.lottery.mvc.account;

import com.qinyuan15.lottery.mvc.dao.PreUserDao;
import org.apache.commons.lang3.RandomStringUtils;

/**
 * Class to build a unique PreUser serialKey
 * Created by qinyuan on 15-9-21.
 */
public class PreUserSerialKeyBuilder {
    public synchronized String build() {
        PreUserDao dao = new PreUserDao();
        String serialKey;
        do {
            serialKey = RandomStringUtils.randomAlphanumeric(100);
        } while (dao.hasSerialKey(serialKey));
        return serialKey;
    }
}
