package com.qinyuan15.lottery.mvc.dao;

import com.qinyuan.lib.database.hibernate.HibernateListBuilder;
import com.qinyuan.lib.database.hibernate.HibernateUtils;
import com.qinyuan15.lottery.mvc.activity.DualColoredBallTerm;

import java.util.List;

public class DualColoredBallRecordDao {
    public Integer add(int fullTerm, String publishDate, String result) {
        DualColoredBallTerm term = new DualColoredBallTerm(fullTerm);
        return add(term.year, term.term, publishDate, result);
    }

    public Integer add(int year, int term, String publishDate, String result) {
        if (hasTerm(year, term)) {
            return null;
        }
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

    public DualColoredBallRecord getLatestInstance() {
        return new HibernateListBuilder().addFilter("result IS NOT NULL").addOrder("publishDate", false)
                .getFirstItem(DualColoredBallRecord.class);
    }

    public List<DualColoredBallRecord> getInstancesByYear(int year) {
        return new HibernateListBuilder().addEqualFilter("year", year).build(DualColoredBallRecord.class);
    }

    public boolean hasTerm(int year, int term) {
        return new HibernateListBuilder().addEqualFilter("year", year)
                .addEqualFilter("term", term).count(DualColoredBallRecord.class) > 0;
    }
}
