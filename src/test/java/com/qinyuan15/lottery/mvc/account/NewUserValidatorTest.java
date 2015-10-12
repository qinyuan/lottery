package com.qinyuan15.lottery.mvc.account;

import com.qinyuan.lib.database.test.DatabaseTestCase;
import org.apache.commons.lang3.tuple.Pair;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class NewUserValidatorTest extends DatabaseTestCase {
    private NewUserValidator validator = new NewUserValidator();

    @Test
    public void testValidateUsername() throws Exception {
        Pair<Boolean, String> result = validator.validateUsername(null);
        assertThat(result.getLeft()).isFalse();
        assertThat(result.getRight()).isEqualTo(NewUserValidator.EMPTY);

        result = validator.validateUsername(" ");
        assertThat(result.getLeft()).isFalse();
        assertThat(result.getRight()).isEqualTo(NewUserValidator.EMPTY);

        result = validator.validateUsername("a");
        assertThat(result.getLeft()).isFalse();
        assertThat(result.getRight()).isEqualTo(NewUserValidator.TOO_SHORT);

        result = validator.validateUsername("12345@test.com");
        assertThat(result.getLeft()).isFalse();
        assertThat(result.getRight()).isEqualTo(NewUserValidator.INVALID_CHAR);

        result = validator.validateUsername("15000000000");
        assertThat(result.getLeft()).isFalse();
        assertThat(result.getRight()).isEqualTo(NewUserValidator.TEL);

        result = validator.validateUsername("admin-user1");
        assertThat(result.getLeft()).isFalse();
        assertThat(result.getRight()).isEqualTo(NewUserValidator.REGISTERED);

        result = validator.validateUsername("normal-user1");
        assertThat(result.getLeft()).isFalse();
        assertThat(result.getRight()).isEqualTo(NewUserValidator.REGISTERED);

        result = validator.validateUsername("virtual_user2");
        assertThat(result.getLeft()).isFalse();
        assertThat(result.getRight()).isEqualTo(NewUserValidator.REGISTERED);

        result = validator.validateUsername("helloWorld");
        assertThat(result.getLeft()).isTrue();
        assertThat(result.getRight()).isNull();
    }
}
