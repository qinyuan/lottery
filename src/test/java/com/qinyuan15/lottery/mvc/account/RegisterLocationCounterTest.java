package com.qinyuan15.lottery.mvc.account;

import com.qinyuan.lib.database.test.DatabaseTestCase;
import org.apache.commons.lang3.tuple.Pair;
import org.junit.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class RegisterLocationCounterTest extends DatabaseTestCase {
    private RegisterLocationCounter counter = new RegisterLocationCounter();

    @Test
    public void testCount() throws Exception {
        List<Pair<String, Integer>> count = counter.count();
        assertThat(count).hasSize(3);
        assertThat(count.get(0)).isEqualTo(Pair.of("广州", 1));
        assertThat(count.get(1)).isEqualTo(Pair.of("深圳", 1));
        assertThat(count.get(2)).isEqualTo(Pair.of("注册但未登录", 1));

        count = counter.count(true);
        assertThat(count).hasSize(3);
        assertThat(count.get(0)).isEqualTo(Pair.of("深圳", 2));
        assertThat(count.get(1)).isEqualTo(Pair.of("广州", 1));
        assertThat(count.get(2)).isEqualTo(Pair.of("注册但未登录", 2));
    }
}
