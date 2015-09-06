package com.qinyuan15.lottery.mvc.dao;

import com.qinyuan.lib.database.hibernate.PersistObject;
import com.qinyuan.lib.lang.DateUtils;
import com.qinyuan15.lottery.mvc.activity.LotCounter;

abstract class AbstractActivity extends PersistObject {
    private Integer commodityId;
    private Integer term;
    private String startTime;
    private Boolean expire;
    private Integer expectParticipantCount;
    private String announcement;
    private String winners;
    private String description;

    public Integer getCommodityId() {
        return commodityId;
    }

    public void setCommodityId(Integer commodityId) {
        this.commodityId = commodityId;
    }

    public Integer getTerm() {
        return term;
    }

    public void setTerm(Integer term) {
        this.term = term;
    }

    public String getStartTime() {
        return DateUtils.trimMilliSecond(startTime);
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public Boolean getExpire() {
        return expire;
    }

    public void setExpire(Boolean expire) {
        this.expire = expire;
    }

    public Integer getExpectParticipantCount() {
        return expectParticipantCount;
    }

    public void setExpectParticipantCount(Integer expectParticipantCount) {
        this.expectParticipantCount = expectParticipantCount;
    }

    public String getAnnouncement() {
        return announcement;
    }

    public void setAnnouncement(String announcement) {
        this.announcement = announcement;
    }

    public String getWinners() {
        return winners;
    }

    public void setWinners(String winners) {
        this.winners = winners;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    //////////////////////////// derivative fields /////////////////////////////////
    private Commodity commodityCache;

    public Commodity getCommodity() {
        if (commodityCache == null) {
            commodityCache = new CommodityDao().getInstance(this.commodityId);
        }
        return commodityCache;
    }

    private Integer realParticipantCountCache;

    public int getRealParticipantCount() {
        if (realParticipantCountCache == null) {
            realParticipantCountCache = getLotCounter().countReal(this.getId());
        }
        return realParticipantCountCache;
    }

    public abstract int getParticipantCount();

    protected abstract LotCounter getLotCounter();
}
