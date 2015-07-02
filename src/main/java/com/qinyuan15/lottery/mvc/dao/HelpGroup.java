package com.qinyuan15.lottery.mvc.dao;

import com.qinyuan15.utils.hibernate.AbstractRanking;

public class HelpGroup extends AbstractRanking {
    private String title;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
