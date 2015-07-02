package com.qinyuan15.lottery.mvc.dao;

import com.qinyuan15.utils.hibernate.AbstractRanking;

public class HelpItem extends AbstractRanking {
    private Integer groupId;
    private String content;

    public Integer getGroupId() {
        return groupId;
    }

    public String getContent() {
        return content;
    }

    public void setGroupId(Integer groupId) {
        this.groupId = groupId;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
