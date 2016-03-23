package com.qinyuan15.lottery.mvc;

import com.qinyuan.lib.lang.Cache;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class CacheFactoryTest {
    @Test
    public void testGetInstance() throws Exception {
        Cache cache1 = CacheFactory.getInstance();
        Cache cache2 = CacheFactory.getInstance();
        assertThat(cache1).isSameAs(cache2);
    }
}
