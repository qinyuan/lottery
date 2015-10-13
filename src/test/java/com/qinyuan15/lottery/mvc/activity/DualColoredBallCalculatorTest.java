package com.qinyuan15.lottery.mvc.activity;

import com.qinyuan.lib.lang.time.DateUtils;
import org.junit.Test;

import java.sql.Date;

import static org.assertj.core.api.Assertions.assertThat;

public class DualColoredBallCalculatorTest {

    @Test
    public void testPublishConfiguration() {
        assertThat(DualColoredBallCalculator.PUBLISH_HOUR).isEqualTo(21);
        assertThat(DualColoredBallCalculator.PUBLISH_MINUTE).isEqualTo(15);
        assertThat(DualColoredBallCalculator.PUBLISH_SECOND).isEqualTo(0);
    }

    @Test
    public void testGetDateTimeByTermNumber() {
        DualColoredBallCalculator calculator = new DualColoredBallCalculator();
        assertThat(DateUtils.toLongString(calculator.getDateTimeByPhase(2015001)))
                .isEqualTo("2015-01-01 21:15:00");
    }

    @Test
    public void testGetDateByTermNumber() throws Exception {
        DualColoredBallCalculator calculator = new DualColoredBallCalculator();
        assertDate(calculator.getDateByPhase(2015001), "2015-01-01");
        assertDate(calculator.getDateByPhase(2014001), "2014-01-02");
        assertDate(calculator.getDateByPhase(2013001), "2013-01-01");
        assertDate(calculator.getDateByPhase(2012001), "2012-01-01");
        assertDate(calculator.getDateByPhase(2011001), "2011-01-02");
        assertDate(calculator.getDateByPhase(2010001), "2010-01-03");
        assertDate(calculator.getDateByPhase(2009001), "2009-01-01");
        assertDate(calculator.getDateByPhase(2008001), "2008-01-01");
        assertDate(calculator.getDateByPhase(2007001), "2007-01-02");
        assertDate(calculator.getDateByPhase(2006001), "2006-01-01");

        assertDate(calculator.getDateByPhase(2015080), "2015-07-12");
        assertDate(calculator.getDateByPhase(2015079), "2015-07-09");
        assertDate(calculator.getDateByPhase(2015078), "2015-07-07");
        assertDate(calculator.getDateByPhase(2015077), "2015-07-05");
        assertDate(calculator.getDateByPhase(2015076), "2015-07-02");
        assertDate(calculator.getDateByPhase(2015075), "2015-06-30");
        assertDate(calculator.getDateByPhase(2015074), "2015-06-28");
        assertDate(calculator.getDateByPhase(2015073), "2015-06-25");

        assertDate(calculator.getDateByPhase(2015007), "2015-01-15");
        assertDate(calculator.getDateByPhase(2015006), "2015-01-13");
        assertDate(calculator.getDateByPhase(2015005), "2015-01-11");
        assertDate(calculator.getDateByPhase(2015004), "2015-01-08");
    }

    private void assertDate(Date date, String dateString) {
        assertThat(date.toString()).isEqualTo(dateString);
    }
}
