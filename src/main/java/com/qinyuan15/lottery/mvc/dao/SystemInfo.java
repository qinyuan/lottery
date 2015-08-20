package com.qinyuan15.lottery.mvc.dao;

import com.qinyuan.lib.database.hibernate.PersistObject;
import com.qinyuan.lib.lang.DateUtils;

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
