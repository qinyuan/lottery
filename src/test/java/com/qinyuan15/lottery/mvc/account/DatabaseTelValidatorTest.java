package com.qinyuan15.lottery.mvc.account;

import com.qinyuan.lib.contact.tel.TelValidator;
import com.qinyuan.lib.database.test.DatabaseTestCase;
import com.qinyuan15.lottery.mvc.dao.UserDao;
import org.apache.commons.lang3.tuple.Pair;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class DatabaseTelValidatorTest extends DatabaseTestCase {
    private TelValidator validator = new DatabaseTelValidator();

    @Test
    public void testValidate() throws Exception {
        Pair<Boolean, String> result = validator.validate("15000000000x");
        assertThat(result.getLeft()).isFalse();
        assertThat(result.getRight()).isEqualTo("电话号码必须为11位数字");

        result = validator.validate("15000000000");
        assertThat(result.getLeft()).isTrue();
        assertThat(result.getRight()).isNull();

        new UserDao().updateTel(1, "15000000000");
        result = validator.validate("15000000000");
        assertThat(result.getLeft()).isFalse();
        assertThat(result.getRight()).isEqualTo("号码已被使用");
    }
}
