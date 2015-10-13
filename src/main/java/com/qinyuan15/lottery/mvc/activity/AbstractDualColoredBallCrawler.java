package com.qinyuan15.lottery.mvc.activity;

import com.qinyuan.lib.network.http.HttpClient;

abstract public class AbstractDualColoredBallCrawler implements DualColoredBallCrawler {
    @Override
    public Result getResult(int fullPhase) {
        HttpClient httpClient = new HttpClient();
        String pageContent = httpClient.getContent(getPageUrl(fullPhase));
        DualColoredBallPageParser pageParser = getPageParser(pageContent, fullPhase);

        return new Result(pageParser.getResult(), pageParser.getDrawTime());
    }

    protected abstract String getPageUrl(int fullPhase);

    protected abstract DualColoredBallPageParser getPageParser(String pageContent, int fullPhase);
}
