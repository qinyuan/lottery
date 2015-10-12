package com.qinyuan15.lottery.mvc.account;

import com.qinyuan.lib.database.test.DatabaseTestCase;
import com.qinyuan15.lottery.mvc.dao.PreUserDao;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class PreUserSerialKeyBuilderTest extends DatabaseTestCase {
    @Test
    public void testBuild() {
        PreUserSerialKeyBuilder builder = new PreUserSerialKeyBuilder();
        String serialKey = builder.build();
        assertThat(new PreUserDao().hasSerialKey(serialKey)).isFalse();
    }
}
