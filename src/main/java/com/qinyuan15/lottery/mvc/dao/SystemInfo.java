package com.qinyuan15.lottery.mvc.dao;

import com.qinyuan15.utils.DateUtils;
import com.qinyuan15.utils.database.hibernate.PersistObject;

public class SystemInfo extends PersistObject {
    private String buildTime;
    private String content;

    public String getBuildTime() {
        return DateUtils.trimMilliSecond(buildTime);
    }

    public void setBuildTime(String buildTime) {
        this.buildTime = buildTime;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
