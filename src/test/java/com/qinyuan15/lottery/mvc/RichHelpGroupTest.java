package com.qinyuan15.lottery.mvc;

import org.junit.Test;

import java.util.List;

public class RichHelpGroupTest {
    @Test
    public void testGetInstances() throws Exception {
        List<RichHelpGroup> groups = RichHelpGroup.getInstances();
        System.out.println(groups.size());
    }
}
