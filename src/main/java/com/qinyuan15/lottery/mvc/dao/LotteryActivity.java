package com.qinyuan15.lottery.mvc.dao;

import com.qinyuan.lib.lang.time.DateUtils;
import com.qinyuan15.lottery.mvc.activity.lot.LotCounter;
import com.qinyuan15.lottery.mvc.activity.lot.LotteryLotCounter;

public class LotteryActivity extends AbstractActivity {
    private String expectEndTime;
    private String closeTime;
    private String endTime;
    private Integer virtualParticipants;
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

    public Integer getVirtualParticipants() {
        return virtualParticipants;
    }

    public void setVirtualParticipants(Integer virtualParticipants) {
        this.virtualParticipants = virtualParticipants;
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

    /*public String getWinningNumber() {
        DualColoredBallPhase phase = new DualColoredBallPhase(dualColoredBallTerm);
        String result = new DualColoredBallRecordDao().getResult(phase.year, phase.term);
        return StringUtils.isBlank(result) ? null : StringUtils.right(result, 6);
    }*/
}
