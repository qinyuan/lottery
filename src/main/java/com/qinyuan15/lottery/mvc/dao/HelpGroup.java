package com.qinyuan15.lottery.mvc.dao;

import com.qinyuan15.utils.hibernate.AbstractRanking;

public class HelpGroup extends AbstractRanking {
    private String title;
    private String icon;

    public String getTitle() {
        return title;
    }

    public String getIcon() {
        return icon;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }
}
