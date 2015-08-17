package com.qinyuan15.lottery.mvc.lottery;

import org.junit.Test;

public class VirtualParticipantCreatorTest {
    @Test
    public void testCreate() throws Exception {
        VirtualParticipantCreator creator = new VirtualParticipantCreator();
        creator.create(26, 3);
    }
}
