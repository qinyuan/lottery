package com.qinyuan15.lottery.mvc.lottery;

import com.qinyuan15.utils.http.HttpClient;

abstract public class AbstractDualColoredBallCrawler implements DualColoredBallCrawler {
    @Override
    public Result getResult(int fullTermNumber) {
        HttpClient httpClient = new HttpClient();
        String pageContent = httpClient.getContent(getPageUrl(fullTermNumber));
        DualColoredBallPageParser pageParser = getPageParser(pageContent, fullTermNumber);

        return new Result(pageParser.getResult(), pageParser.getDrawTime());
    }

    protected abstract String getPageUrl(int fullTermNumber);

    protected abstract DualColoredBallPageParser getPageParser(String pageContent, int fullTermNumber);
}
