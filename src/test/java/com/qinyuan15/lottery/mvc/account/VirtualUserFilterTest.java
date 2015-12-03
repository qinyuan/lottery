package com.qinyuan15.lottery.mvc.account;

import com.google.common.collect.Lists;
import org.junit.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class VirtualUserFilterTest {
    @Test
    public void testFilter() throws Exception {
        VirtualUserFilter filter = new VirtualUserFilter();

        List<String> usernames = Lists.newArrayList("hello", "world", "一", "二", "三", "四");
        filter.setAsciiRate(0.5);
        assertThat(filter.filter(usernames)).hasSize(4).contains("hello", "world");

        usernames = Lists.newArrayList("hello", "world", "一", "二", "三");
        filter.setAsciiRate(0.25);
        assertThat(filter.filter(usernames)).hasSize(4).contains("一", "二", "三");

        filter.setAsciiRate(0.4);
        assertThat(filter.filter(usernames)).hasSize(5).containsAll(usernames).isNotEqualTo(usernames);

        filter.setAsciiRate(0.39);
        assertThat(filter.filter(usernames)).hasSize(4).contains("一", "二", "三");
    }
}
