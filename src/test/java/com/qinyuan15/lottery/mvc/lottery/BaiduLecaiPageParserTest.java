package com.qinyuan15.lottery.mvc.lottery;

import com.qinyuan15.utils.test.TestFileUtils;
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
}
