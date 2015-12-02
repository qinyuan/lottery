package com.qinyuan15.lottery.mvc.account;

import com.qinyuan.lib.contact.tel.TelValidator;
import com.qinyuan15.lottery.mvc.dao.UserDao;
import com.qinyuan15.lottery.mvc.dao.VirtualUserDao;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.tuple.Pair;

public class NewUserValidator {
    public final static int MAX_LENGTH = 14;
    public final static int MIN_LENGTH = 2;
    public final static String EMPTY = "用户名不能为空！";
    public final static String TOO_SHORT = "用户名至少使用2个字符！";
    public final static String INVALID_CHAR = "用户名不能包含'@'字符！";
    public final static String TEL = "用户名不能为电话号码！";
    public final static String SPACE = "用户名不能包含空格！";
    public final static String REGISTERED = "该用户名已经被注册！";

    public Pair<Boolean, String> validateUsername(String username) {
        if (StringUtils.isBlank(username)) {
            return Pair.of(false, EMPTY);
        } else if (username.contains(" ")) {
            return Pair.of(false, SPACE);
        } else if (username.length() < MIN_LENGTH) {
            return Pair.of(false, TOO_SHORT);
        } else if (username.contains("@")) {
            return Pair.of(false, INVALID_CHAR);
        } else if (new TelValidator().validate(username).getLeft()) {
            return Pair.of(false, TEL);
        } else if (new UserDao().hasUsername(username) || new VirtualUserDao().hasUsername(username)) {
            return Pair.of(false, REGISTERED);
        } else {
            return Pair.of(true, null);
        }
    }
}
