package com.qinyuan15.lottery.mvc.lottery;

import com.qinyuan15.lottery.mvc.dao.DualColoredBallRecord;
import com.qinyuan15.lottery.mvc.dao.DualColoredBallRecordDao;
import com.qinyuan15.utils.DateUtils;

import java.sql.Date;
import java.util.Calendar;
import java.util.GregorianCalendar;

/**
 * Class to calculate deadline of dual colored ball
 * Created by qinyuan on 15-7-14.
 */
public class DualColoredBallCalculator {
    public Date getDateByTermNumber(int termNumber) {
        if (termNumber < 1900000 || termNumber > 3000000) {
            throw new RuntimeException("Invalid term number: " + termNumber);
        }

        int year = termNumber / 1000;
        int termNumberInYear = termNumber % 1000;

        DualColoredBallRecord record = new DualColoredBallRecordDao().getNearestInstance(year, termNumberInYear);
        if (record != null) {
            return getDateWithRecord(termNumberInYear, record);
        } else {
            return getDateWithoutRecord(year, termNumberInYear);
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
