package com.qinyuan15.lottery.mvc.account;

import com.qinyuan.lib.contact.tel.TelValidator;
import com.qinyuan15.lottery.mvc.dao.UserDao;
import org.apache.commons.lang3.tuple.Pair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * class to validate tel from database,
 * not only validate format but also validate duplication
 */
public class DatabaseTelValidator extends TelValidator {
    private final static Logger LOGGER = LoggerFactory.getLogger(DatabaseTelValidator.class);

    @Override
    public Pair<Boolean, String> validate(String tel) {
        Pair<Boolean, String> validation = super.validate(tel);
        if (!validation.getLeft()) {
            return validation;
        }

        try {
            if (new UserDao().hasTel(tel)) {
                return Pair.of(false, "号码已被使用");
            } else {
                return Pair.of(true, null);
            }
        } catch (Exception e) {
            LOGGER.error("Fail to validate tel, tel: {}, info: {}", tel, e);
            return Pair.of(false, "数据库查询失败");
        }
    }
}
