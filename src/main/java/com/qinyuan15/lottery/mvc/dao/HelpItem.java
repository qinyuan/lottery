package com.qinyuan15.lottery.mvc.dao;

import com.qinyuan.lib.database.hibernate.AbstractRanking;

public class HelpItem extends AbstractRanking {
    private Integer groupId;
    private String content;
    private String title;
    private String icon;

    public Integer getGroupId() {
        return groupId;
    }

    public String getContent() {
        return content;
    }

    public String getTitle() {
        return title;
    }

    public String getIcon() {
        return icon;
    }

    public void setGroupId(Integer groupId) {
        this.groupId = groupId;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }
}
