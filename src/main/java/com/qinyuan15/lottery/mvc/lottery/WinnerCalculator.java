package com.qinyuan15.lottery.mvc.lottery;

/**
 * Class to calculate winner
 * Created by qinyuan on 15-8-5.
 */
public class WinnerCalculator {
    public int run(long dualColoredBallResult, long participantCount)  {
        return (int) (dualColoredBallResult % participantCount);
    }
}
