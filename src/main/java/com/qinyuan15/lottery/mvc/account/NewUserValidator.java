package com.qinyuan15.lottery.mvc.account;

import com.qinyuan.lib.contact.tel.TelValidator;
import com.qinyuan15.lottery.mvc.dao.UserDao;
import com.qinyuan15.lottery.mvc.dao.VirtualUserDao;
import org.apache.commons.lang3.tuple.Pair;
import org.springframework.util.StringUtils;

public class NewUserValidator {
    /*
    /**
     * Validate if certain username exists
     *
     * @param name name that can be used to login, including username, email, tel or virtual username
     * @return true if user
     */
    /*public boolean hasUsername(String name) {
        return new UserDao().hasUsername(name) || new VirtualUserDao().hasUsername(name);
    }*/

    public final static String EMPTY = "用户名不能为空！";
    public final static String TOO_SHORT = "用户名至少使用2个字符！";
    public final static String INVALID_CHAR = "用户名不能包含'@'字符！";
    public final static String TEL = "用户名不能为电话号码！";
    public final static String REGISTERED = "该用户名已经被注册！";

    public Pair<Boolean, String> validateUsername(String username) {
        if (!StringUtils.hasText(username)) {
            return Pair.of(false, EMPTY);
        }
        if (username.length() < 2) {
            return Pair.of(false, TOO_SHORT);
        }

        if (username.contains("@")) {
            return Pair.of(false, INVALID_CHAR);
        }

        if (new TelValidator().validate(username)) {
            return Pair.of(false, TEL);
        }

        if (new UserDao().hasUsername(username) || new VirtualUserDao().hasUsername(username)) {
            return Pair.of(false, REGISTERED);
        }

        return Pair.of(true, null);
    }
}
