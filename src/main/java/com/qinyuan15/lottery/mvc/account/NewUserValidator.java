package com.qinyuan15.lottery.mvc.account;

import com.qinyuan15.lottery.mvc.dao.UserDao;
import com.qinyuan15.lottery.mvc.dao.VirtualUserDao;

public class NewUserValidator {
    /**
     * Validate is certain username exists
     *
     * @param name name that can be used to login, including username, email, tel or virtual username
     * @return true if user
     */
    public boolean hasUsername(String name) {
        return new UserDao().hasUsername(name) || new VirtualUserDao().hasUsername(name);
    }
}
