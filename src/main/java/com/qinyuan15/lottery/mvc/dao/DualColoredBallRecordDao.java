package com.qinyuan15.lottery.mvc.dao;

import com.qinyuan15.utils.hibernate.HibernateListBuilder;
import com.qinyuan15.utils.hibernate.HibernateUtils;

public class DualColoredBallRecordDao {
    public Integer add(int fullTerm, String publishDate) {
        int year = fullTerm / 1000;
        int term = fullTerm % 1000;
        return add(year, term, publishDate);
    }

    public Integer add(int year, int term, String publishDate) {
        return add(year, term, publishDate, null);
    }

    public Integer add(int year, int term, String publishDate, String result) {
        DualColoredBallRecord record = new DualColoredBallRecord();
        record.setYear(year);
        record.setTerm(term);
        record.setPublishDate(publishDate);
        record.setResult(result);
        return HibernateUtils.save(record);
    }

    public DualColoredBallRecord getNearestInstance(int year, int term) {
        return new HibernateListBuilder().addEqualFilter("year", year)
                .addFilter("term<=:term").addArgument("term", term)
                .addOrder("term", false).getFirstItem(DualColoredBallRecord.class);
    }
}
