package com.qinyuan15.lottery.mvc.dao;

import com.qinyuan15.utils.database.hibernate.PersistObject;

/**
 * Class about navigation link
 * Created by qinyuan on 15-6-17.
 */
public class NavigationLink extends PersistObject {
    private String title;
    private String href;

    public String getTitle() {
        return title;
    }

    public String getHref() {
        return href;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setHref(String href) {
        this.href = href;
    }
}
