package com.qinyuan15.lottery.mvc.activity;

import com.qinyuan.lib.lang.test.TestFileUtils;
import com.qinyuan15.lottery.mvc.activity.dualcoloredball.ZhcwPageParser;
import org.junit.Before;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class ZhcwPageParserTest {
    private ZhcwPageParser pageParser;

    @Before
    public void setUp() throws Exception {
        pageParser = new ZhcwPageParser(TestFileUtils.read("zhcw.js"), 2015090);
    }

    @Test
    public void testGetResult() throws Exception {
        assertThat(pageParser.getResult()).isEqualTo("10 12 14 22 25 33");
    }

    @Test
    public void testGetDrawTime() throws Exception {
        assertThat(pageParser.getDrawTime()).isEqualTo("2015-08-04 21:15:00");
    }
}
