package com.qinyuan15.lottery.mvc.lottery;

public class DualColoredBallTerm {
    public final int year;
    public final int term;

    public DualColoredBallTerm(int fullTerm) {
        year = fullTerm / 1000;
        term = fullTerm % 1000;
    }

    public static int toFullTerm(int year, int term) {
        return year * 1000 + term;
    }
}
