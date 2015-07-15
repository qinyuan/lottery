package com.qinyuan15.lottery.mvc.lottery;

import com.qinyuan15.utils.http.HttpClient;

abstract public class AbstractDualColoredBallCrawler implements DualColoredBallCrawler {
    @Override
    public String getResult(int fullTermNumber) {
        HttpClient httpClient = new HttpClient();
        String pageContent = httpClient.getContent(getPageUrl(fullTermNumber));
        DualColoredBallPageParser pageParser = getPageParser(pageContent);
        return pageParser.getResult();
    }

    protected abstract String getPageUrl(int fullTermNumber);

    protected abstract DualColoredBallPageParser getPageParser(String pageContent);
}
