package com.qinyuan15.lottery.mvc.config;

import org.apache.commons.lang3.RandomUtils;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class IndexConfigTest {
    private IndexConfig config = new IndexConfig();

    @Test
    public void testIndexImageCycleInterval() {
        assertThat(config.getIndexImageCycleInterval()).isEqualTo(10);  // default interval is 10

        int interval = RandomUtils.nextInt(20, 1000);
        config.updateIndexImageCycleInterval(interval);
        assertThat(config.getIndexImageCycleInterval()).isEqualTo(interval);
    }
}
