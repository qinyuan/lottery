package com.qinyuan15.lottery.mvc.activity;

import com.qinyuan.lib.lang.test.TestFileUtils;
import org.junit.Before;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class BaiduLecaiPageParserTest {
    private BaiduLecaiPageParser pageParser;

    @Before
    public void setUp() throws Exception {
        pageParser = new BaiduLecaiPageParser(TestFileUtils.read("baiduLecai.json"));
    }

    @Test
    public void testGetResult() throws Exception {
        assertThat(pageParser.getResult()).isEqualTo("141725272830");
    }

    @Test
    public void testGetDrawTime(){
        assertThat(pageParser.getDrawTime()).isEqualTo("2015-07-12 21:15:00");
    }
}
