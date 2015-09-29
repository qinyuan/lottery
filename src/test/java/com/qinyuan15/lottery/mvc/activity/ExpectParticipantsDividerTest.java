package com.qinyuan15.lottery.mvc.activity;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class ExpectParticipantsDividerTest {
    @Test
    public void testGetCurrentExpectValue() throws Exception {
        String startTime = "2015-07-14 00:00:00";
        String endTime = "2015-07-13 23:59:59";
        ExpectParticipantsDivider divider = new ExpectParticipantsDivider(startTime, endTime, 1000);
        assertThat(divider.getCurrentExpectValue()).isEqualTo(0);

        startTime = "1900-01-01 12:12:12";
        endTime = "1911-01-01 12:00:00";
        divider = new ExpectParticipantsDivider(startTime, endTime, 1000);
        assertThat(divider.getCurrentExpectValue()).isEqualTo(1000);

        startTime = "2100-01-01 12:00:00";
        endTime = "2100-01-02 08:00:00";
        divider = new ExpectParticipantsDivider(startTime, endTime, 1000);
        assertThat(divider.getCurrentExpectValue()).isEqualTo(0);

        startTime = "2015-09-28 10:00:00";
        endTime = "2015-09-29 22:00:00";
        divider = new ExpectParticipantsDivider(startTime, endTime, 1000);
        System.out.println(divider.getCurrentExpectValue());

        startTime = "2015-09-28 10:00:00";
        endTime = "2015-09-30 22:00:00";
        divider = new ExpectParticipantsDivider(startTime, endTime, 1000);
        System.out.println(divider.getCurrentExpectValue());
    }
}
