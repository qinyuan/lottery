package com.qinyuan15.lottery.mvc.dao;

import com.qinyuan15.utils.hibernate.PersistObject;

public class SystemInfoSendRecord extends PersistObject {
    private Integer userId;
    private Integer infoId;
    private Boolean unread;

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getInfoId() {
        return infoId;
    }

    public void setInfoId(Integer infoId) {
        this.infoId = infoId;
    }

    public Boolean getUnread() {
        return unread;
    }

    public void setUnread(Boolean unread) {
        this.unread = unread;
    }

    // derived fields
    private SystemInfo infoCache;

    private SystemInfo getSystemInfo() {
        if (infoCache == null) {
            infoCache = new SystemInfoDao().getInstance(infoId);
        }
        return infoCache;
    }

    public String getContent() {
        SystemInfo info = getSystemInfo();
        return info == null ? null : info.getContent();
    }

    public String getBuildTime() {
        SystemInfo info = getSystemInfo();
        return info == null ? null : info.getBuildTime();
    }
}
