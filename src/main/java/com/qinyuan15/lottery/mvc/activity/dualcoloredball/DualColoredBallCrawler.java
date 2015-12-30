package com.qinyuan15.lottery.mvc.activity.dualcoloredball;

public interface DualColoredBallCrawler {
    Result getResult(int fullTermNumber);

    public static class Result {
        public final String result;
        public final String drawTime;

        public Result(String result, String drawTime) {
            this.result = result;
            this.drawTime = drawTime;
        }
    }
}