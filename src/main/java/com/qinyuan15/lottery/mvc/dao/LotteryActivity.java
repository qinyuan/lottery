package com.qinyuan15.lottery.mvc.dao;

import com.qinyuan.lib.lang.IntegerRange;
import com.qinyuan.lib.lang.time.DateUtils;
import com.qinyuan15.lottery.mvc.activity.LotCounter;
import com.qinyuan15.lottery.mvc.activity.LotteryLotCounter;

public class LotteryActivity extends AbstractActivity {
    private String expectEndTime;
    private String closeTime;
    private String endTime;
    private Integer continuousSerialLimit;
    private Integer virtualParticipants;
    private Integer virtualLiveness;
    private String virtualLivenessUsers;
    private Integer maxSerialNumber;
    private Integer minSerialNumber;
    private Integer dualColoredBallTerm;
    private Integer minLivenessToParticipate;

    public String getExpectEndTime() {
        return DateUtils.trimMilliSecond(expectEndTime);
    }

    public void setExpectEndTime(String expectEndTime) {
        this.expectEndTime = expectEndTime;
    }

    public String getCloseTime() {
        return closeTime;
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

    public Integer getMinSerialNumber() {
        return minSerialNumber;
    }

    public void setMinSerialNumber(Integer minSerialNumber) {
        this.minSerialNumber = minSerialNumber;
    }

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

    public String getSerialNumberRange() {
        String range = IntegerRange.SEPARATOR;

        if (minSerialNumber != null) {
            range = minSerialNumber + range;
        }
        if (maxSerialNumber != null) {
            range = range + maxSerialNumber;
        }

        return range;
    }
}
