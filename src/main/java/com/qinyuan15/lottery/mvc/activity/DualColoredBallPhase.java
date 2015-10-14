package com.qinyuan15.lottery.mvc.activity;

public class DualColoredBallPhase {
    public final int year;
    public final int term;

    public DualColoredBallPhase(int fullPhase) {
        year = fullPhase / 1000;
        term = fullPhase % 1000;
    }

    public static int toFullPhase(int year, int phase) {
        return year * 1000 + phase;
    }
}
