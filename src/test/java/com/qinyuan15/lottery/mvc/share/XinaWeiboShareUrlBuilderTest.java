package com.qinyuan15.lottery.mvc.share;

import com.qinyuan15.utils.mvc.UrlUtils;
import com.qinyuan15.utils.test.TestFileUtils;
import org.junit.Test;

public class XinaWeiboShareUrlBuilderTest {
    @Test
    public void testBuild() throws Exception {
        String content = TestFileUtils.read("temp.txt");
        System.out.println(UrlUtils.decode(content));
    }
}
