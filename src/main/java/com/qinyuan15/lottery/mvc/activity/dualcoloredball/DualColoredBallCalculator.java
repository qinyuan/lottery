package com.qinyuan15.lottery.mvc.activity.dualcoloredball;

import com.qinyuan.lib.lang.file.ClasspathFileUtils;
import com.qinyuan.lib.lang.time.DateUtils;
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

    /**
     * Get date time of certain phase
     *
     * @param phase target phase
     * @return date time of target phase
     */
    public Date getDateTimeByPhase(int phase) {
        Date date = getDateByPhase(phase);
        long milliSecondsToAdd = (PUBLISH_HOUR * 3600 + PUBLISH_MINUTE * 60 + PUBLISH_SECOND) * 1000;
        date.setTime(date.getTime() + milliSecondsToAdd);
        return date;
    }

    /**
     * get date of certain phase
     *
     * @param phase target phase
     * @return date of target phase, whose time part is '00:00:00'
     */
    public Date getDateByPhase(int phase) {
        if (!new DualColoredBallPhaseValidator().validate(phase)) {
            throw new RuntimeException("Invalid term number: " + phase);
        }

        DualColoredBallPhase phaseObject = new DualColoredBallPhase(phase);
        DualColoredBallRecord record = new DualColoredBallRecordDao().getNearestInstance(phaseObject.year, phaseObject.term);
        if (record != null) {
            return getDateWithRecord(phaseObject.term, record);
        } else {
            return getDateWithoutRecord(phaseObject.year, phaseObject.term);
        }
    }

    /**
     * Calculate date by consulting exists record of same year
     *
     * @param phase  target phase
     * @param record exists record to consult
     * @return date of target phase
     */
    private Date getDateWithRecord(int phase, DualColoredBallRecord record) {
        int phaseDiff = phase - record.getTerm();
        Calendar cal = DateUtils.newCalendar(record.getPublishDate());
        return getDateByCalendar(cal, phaseDiff);
    }

    /**
     * Calculate date by pure calculating without consulting exists record
     *
     * @param year  year of target phase
     * @param phase target phase
     * @return date of target phase
     */
    private Date getDateWithoutRecord(int year, int phase) {
        Calendar cal = getFirstPhaseDeadline(year);
        return getDateByCalendar(cal, phase - 1);
    }

    /**
     * Calculate date of phase by calendar of referential phase and
     * phase difference between referential phase and target phase
     *
     * @param cal       calendar of referential phase
     * @param phaseDiff phase difference between referential phase and target phase
     * @return date object of target phase
     */
    private Date getDateByCalendar(Calendar cal, int phaseDiff) {
        int weekDiff = phaseDiff / 3;
        cal.add(Calendar.DATE, weekDiff * 7);

        int dayOfWeek = cal.get((Calendar.DAY_OF_WEEK));
        int remainder = phaseDiff % 3;
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

    /**
     * Get deadline of first phase in certain year
     *
     * @param year certain year such as 2015
     * @return deadline of first phase
     */
    private Calendar getFirstPhaseDeadline(int year) {
        Calendar cal = new GregorianCalendar(year, 0, 1);
        int dayOfWeek = cal.get(Calendar.DAY_OF_WEEK);
        if (dayOfWeek == 2 || dayOfWeek == 4 || dayOfWeek == 7) { // Monday, Wednesday, Saturday
            cal.add(Calendar.DATE, 1);
        } else if (dayOfWeek == 6) {    // Friday
            cal.add(Calendar.DATE, 2);
        }

        return cal;
    }
}
