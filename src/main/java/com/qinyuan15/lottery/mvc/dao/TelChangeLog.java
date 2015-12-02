package com.qinyuan15.lottery.mvc.dao;

import com.qinyuan.lib.database.hibernate.PersistObject;
import com.qinyuan.lib.lang.time.DateUtils;

public class TelChangeLog extends PersistObject {
    private Integer userId;
    private String oldTel;
    private String newTel;
    private String changeTime;

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getOldTel() {
        return oldTel;
    }

    public void setOldTel(String oldTel) {
        this.oldTel = oldTel;
    }

    public String getNewTel() {
        return newTel;
    }

    public void setNewTel(String newTel) {
        this.newTel = newTel;
    }

    public String getChangeTime() {
        return DateUtils.trimMilliSecond(changeTime);
    }

    public void setChangeTime(String changeTime) {
        this.changeTime = changeTime;
    }
}
