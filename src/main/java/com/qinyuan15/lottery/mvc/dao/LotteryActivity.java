package com.qinyuan15.lottery.mvc.dao;

import com.qinyuan.lib.lang.time.DateUtils;
import com.qinyuan15.lottery.mvc.activity.LotCounter;
import com.qinyuan15.lottery.mvc.activity.LotteryLotCounter;

public class LotteryActivity extends AbstractActivity {
    private String expectEndTime;
    private String closeTime;
    private String endTime;
    private Integer continuousSerialLimit;
    private Integer virtualParticipants;
/*    private Integer virtualLiveness;
    private String virtualLivenessUsers;*/
    private Integer dualColoredBallTerm;
    private Integer minLivenessToParticipate;
    private Boolean closed;

    public String getExpectEndTime() {
        return DateUtils.trimMilliSecond(expectEndTime);
    }

    public void setExpectEndTime(String expectEndTime) {
        this.expectEndTime = expectEndTime;
    }

    public String getCloseTime() {
        return DateUtils.trimMilliSecond(closeTime);
    }

    public void setCloseTime(String closeTime) {
        this.closeTime = closeTime;
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

    public Integer getVirtualParticipants() {
        return virtualParticipants;
    }

    public void setVirtualParticipants(Integer virtualParticipants) {
        this.virtualParticipants = virtualParticipants;
    }

    /*public Integer getVirtualLiveness() {
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
    }*/

    public Integer getDualColoredBallTerm() {
        return dualColoredBallTerm;
    }

    public void setDualColoredBallTerm(Integer dualColoredBallTerm) {
        this.dualColoredBallTerm = dualColoredBallTerm;
    }

    public Integer getMinLivenessToParticipate() {
        return minLivenessToParticipate;
    }

    public void setMinLivenessToParticipate(Integer minLivenessToParticipate) {
        this.minLivenessToParticipate = minLivenessToParticipate;
    }

    public Boolean getClosed() {
        return closed;
    }

    public void setClosed(Boolean closed) {
        this.closed = closed;
    }

    //////////////////////////// derivative fields /////////////////////////////////
    @Override
    public int getParticipantCount() {
        return virtualParticipants == null ? getRealParticipantCount() :
                getRealParticipantCount() + virtualParticipants;
    }

    @Override
    protected LotCounter getLotCounter() {
        return new LotteryLotCounter();
    }
}
