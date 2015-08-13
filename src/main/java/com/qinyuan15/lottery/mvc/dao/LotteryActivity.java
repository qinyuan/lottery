package com.qinyuan15.lottery.mvc.dao;

import com.qinyuan15.lottery.mvc.lottery.LotteryLotCounter;
import com.qinyuan15.utils.DateUtils;
import com.qinyuan15.utils.hibernate.PersistObject;

public class LotteryActivity extends PersistObject {
    private Integer commodityId;
    private Integer term;
    private String startTime;
    private String expectEndTime;
    private String endTime;
    private Integer continuousSerialLimit;
    private Boolean expire;
    private Integer expectParticipantCount;
    private String announcement;
    private Integer virtualParticipants;
    private Integer virtualLiveness;
    private String virtualLivenessUsers;
    private Integer maxSerialNumber;
    private Integer dualColoredBallTerm;
    private String winners;

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

    public String getExpectEndTime() {
        return DateUtils.trimMilliSecond(expectEndTime);
    }

    public void setExpectEndTime(String expectEndTime) {
        this.expectEndTime = expectEndTime;
    }

    public String getEndTime() {
        return DateUtils.trimMilliSecond(endTime);
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public Integer getContinuousSerialLimit() {
        return continuousSerialLimit;
    }

    public void setContinuousSerialLimit(Integer continuousSerialLimit) {
        this.continuousSerialLimit = continuousSerialLimit;
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

    public Integer getVirtualParticipants() {
        return virtualParticipants;
    }

    public void setVirtualParticipants(Integer virtualParticipants) {
        this.virtualParticipants = virtualParticipants;
    }

    public Integer getVirtualLiveness() {
        return virtualLiveness;
    }

    public void setVirtualLiveness(Integer virtualLiveness) {
        this.virtualLiveness = virtualLiveness;
    }

    public String getVirtualLivenessUsers() {
        return virtualLivenessUsers;
    }

    public void setVirtualLivenessUsers(String virtualLivenessUsers) {
        this.virtualLivenessUsers = virtualLivenessUsers;
    }

    public Integer getMaxSerialNumber() {
        return maxSerialNumber;
    }

    public void setMaxSerialNumber(Integer maxSerialNumber) {
        this.maxSerialNumber = maxSerialNumber;
    }

    public Integer getDualColoredBallTerm() {
        return dualColoredBallTerm;
    }

    public void setDualColoredBallTerm(Integer dualColoredBallTerm) {
        this.dualColoredBallTerm = dualColoredBallTerm;
    }

    public String getWinners() {
        return winners;
    }

    public void setWinners(String winners) {
        this.winners = winners;
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
            realParticipantCountCache = new LotteryLotCounter().countReal(this.getId());
        }
        return realParticipantCountCache;
    }

    public int getParticipantCount() {
        return virtualParticipants == null ? getRealParticipantCount() :
                getRealParticipantCount() + virtualParticipants;
    }
}
