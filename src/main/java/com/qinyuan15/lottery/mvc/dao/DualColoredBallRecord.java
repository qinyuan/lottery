package com.qinyuan15.lottery.mvc.dao;

import com.qinyuan15.utils.DateUtils;
import com.qinyuan15.utils.hibernate.PersistObject;

public class DualColoredBallRecord extends PersistObject {
    private Integer year;
    private Integer term;
    private String publishDate;
    private String result;

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    public Integer getTerm() {
        return term;
    }

    public void setTerm(Integer term) {
        this.term = term;
    }

    public String getPublishDate() {
        return DateUtils.adjustDateStringFromDB(publishDate);
    }

    public void setPublishDate(String publishDate) {
        this.publishDate = publishDate;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }
}
