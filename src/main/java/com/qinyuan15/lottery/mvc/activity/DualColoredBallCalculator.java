package com.qinyuan15.lottery.mvc.activity;

import com.qinyuan.lib.lang.time.DateUtils;
import com.qinyuan.lib.lang.file.ClasspathFileUtils;
import com.qinyuan15.lottery.mvc.dao.DualColoredBallRecord;
import com.qinyuan15.lottery.mvc.dao.DualColoredBallRecordDao;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Date;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Properties;

/**
 * Class to calculate deadline of dual colored ball
 * Created by qinyuan on 15-7-14.
 */
public class DualColoredBallCalculator {
    private final static Logger LOGGER = LoggerFactory.getLogger(DualColoredBallCalculator.class);

    public final static int PUBLISH_HOUR;
    public final static int PUBLISH_MINUTE;
    public final static int PUBLISH_SECOND;

    static {
        int hour = 21;
        int minute = 15;
        int second = 0;
        try {
            Properties props = ClasspathFileUtils.getProperties("global-config.properties");
            hour = Integer.parseInt(props.getProperty("dualColoredBallPublishHour"));
            minute = Integer.parseInt(props.getProperty("dualColoredBallPublishMinute"));
            second = Integer.parseInt(props.getProperty("dualColoredBallPublishSecond"));
        } catch (Exception e) {
            LOGGER.error("Fail to load dual colored ball publish configuration, info: {}", e);
        }

        PUBLISH_HOUR = hour;
        PUBLISH_MINUTE = minute;
        PUBLISH_SECOND = second;
    }

    public Date getDateTimeByTermNumber(int termNumber) {
        Date date = getDateByTermNumber(termNumber);
        long milliSecondsToAdd = (PUBLISH_HOUR * 3600 + PUBLISH_MINUTE * 60 + PUBLISH_SECOND) * 1000;
        date.setTime(date.getTime() + milliSecondsToAdd);
        return date;
    }

    public Date getDateByTermNumber(int termNumber) {
        if (!new DualColoredBallTermValidator().validate(termNumber)) {
            throw new RuntimeException("Invalid term number: " + termNumber);
        }

        DualColoredBallTerm term = new DualColoredBallTerm(termNumber);
        DualColoredBallRecord record = new DualColoredBallRecordDao().getNearestInstance(term.year, term.term);
        if (record != null) {
            return getDateWithRecord(term.term, record);
        } else {
            return getDateWithoutRecord(term.year, term.term);
        }
    }

    private Date getDateWithRecord(int term, DualColoredBallRecord record) {
        int termDiff = term - record.getTerm();
        Calendar cal = DateUtils.newCalendar(record.getPublishDate());
        return getDateByCalendar(cal, termDiff);
    }

    private Date getDateWithoutRecord(int year, int term) {
        Calendar cal = getFirstTermDeadline(year);
        return getDateByCalendar(cal, term - 1);
    }

    private Date getDateByCalendar(Calendar cal, int termDiff) {
        cal.add(Calendar.DATE, (termDiff / 3) * 7);

        int dayOfWeek = cal.get((Calendar.DAY_OF_WEEK));
        int remainder = termDiff % 3;
        if (remainder == 1) {
            if (dayOfWeek == 1 || dayOfWeek == 3) { // Sunday or Tuesday
                cal.add(Calendar.DATE, 2);
            } else if (dayOfWeek == 5) { // Thursday
                cal.add(Calendar.DATE, 3);
            }
        } else if (remainder == 2) {
            if (dayOfWeek == 1) { // Sunday
                cal.add(Calendar.DATE, 4);
            } else if (dayOfWeek == 3 || dayOfWeek == 5) { // Tuesday or Thursday
                cal.add(Calendar.DATE, 5);
            }
        }

        return new Date(cal.getTimeInMillis());
    }

    private Calendar getFirstTermDeadline(int year) {
        Calendar cal = new GregorianCalendar(year, 0, 1);
        int dayOfWeek = cal.get(Calendar.DAY_OF_WEEK);
        if (dayOfWeek == 2 || dayOfWeek == 4 || dayOfWeek == 7) {
            cal.add(Calendar.DATE, 1);
        } else if (dayOfWeek == 6) {
            cal.add(Calendar.DATE, 2);
        }

        return cal;
    }
}
