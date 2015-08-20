package com.qinyuan15.lottery.mvc.share;

import com.qinyuan.lib.lang.test.TestFileUtils;
import com.qinyuan.lib.network.url.UrlUtils;
import org.junit.Test;

public class XinaWeiboShareUrlBuilderTest {
    @Test
    public void testBuild() throws Exception {
        String content = TestFileUtils.read("temp.txt");
        System.out.println(UrlUtils.decode(content));
    }
}
