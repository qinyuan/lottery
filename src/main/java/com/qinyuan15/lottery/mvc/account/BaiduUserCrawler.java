package com.qinyuan15.lottery.mvc.account;

import com.qinyuan.lib.contact.tel.TelValidator;
import com.qinyuan.lib.lang.concurrent.ThreadUtils;
import com.qinyuan.lib.network.html.HtmlParser;
import com.qinyuan.lib.network.http.HttpClient;
import org.apache.commons.lang3.StringUtils;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class BaiduUserCrawler {
    private final static Logger LOGGER = LoggerFactory.getLogger(BaiduUserCrawler.class);
    private final static String WEB_ROOT = "http://tieba.baidu.com";
    private final static int MAX_STORE_PAGE_SIZE = 100000;
    private final static double DEFAULT_INTERVAL = 3;

    private Map<String, Boolean> urls;
    private Set<String> usernames = new HashSet<>();
    private int crawledPageSize = 0;
    private UsernameHandler usernameHandler;
    private boolean running;
    private double interval = DEFAULT_INTERVAL;

    public BaiduUserCrawler(UsernameHandler usernameHandler) {
        this.usernameHandler = usernameHandler;
    }

    public void setInterval(double interval) {
        this.interval = interval;
    }

    public void run() {
        running = true;
        urls = new HashMap<>();
        urls.put(WEB_ROOT, false);

        while (running) {
            ThreadUtils.sleep(interval);

            String url = getOneUnCrawledUrl();
            if (StringUtils.isBlank(url)) continue;

            try {
                crawlPage(url);
                urls.put(url, true);
                LOGGER.info("already store page size: {}", urls.size());
                LOGGER.info("already crawled page size: {}", (++crawledPageSize));
                LOGGER.info("already crawled user count: {}", usernames.size());
            } catch (Throwable e) {
                urls.remove(url);
                LOGGER.warn("error in crawling {}, information: {}", url, e);
            }

            if (urls.size() > MAX_STORE_PAGE_SIZE) {
                break;
            }
        }
    }

    public void stop() {
        running = false;
    }

    private String getOneUnCrawledUrl() {
        for (Map.Entry<String, Boolean> entry : urls.entrySet()) {
            if (!entry.getValue()) { // false means haven't crawled
                return entry.getKey();
            }
        }
        return null;
    }

    private void crawlPage(String url) {
        if (url.startsWith("/")) {
            url = WEB_ROOT + url;
        }
        HttpClient client = new HttpClient();
        String content = client.getContent(url);
        handleAnchors(content);
    }

    private void handleAnchors(String html) {
        if (StringUtils.isBlank(html)) {
            return;
        }
        HtmlParser parser = new HtmlParser(html);
        Elements elements = parser.getElements("a");
        if (elements != null) {
            for (Element element : elements) {
                String username = parseUsername(element);
                if (StringUtils.isNotBlank(username)) {
                    if (!usernames.contains(username)) {
                        usernames.add(username);
                        usernameHandler.handle(parseUsername(element));
                    }
                    continue;
                }

                String href = element.attr("href");
                if (isValidUrl(href) && !urls.containsKey(href)) {
                    urls.put(href, false);
                }
            }
        }

        Elements pageletHtmls = parser.getElements("code", "pagelet_html");
        if (pageletHtmls != null) {
            for (Element element : pageletHtmls) {
                String innerHtml = element.html().trim().replace("<!--", "").replace("-->", "");
                handleAnchors("<div>" + innerHtml + "</div>");
            }
        }
    }

    private String parseUsername(Element element) {
        if (!element.hasClass("j_user_card")) {
            return null;
        }

        String username = element.attr("username");
        if (StringUtils.isBlank(username)) {
            username = element.text();
        }

        if (username != null) {
            username = username.replace(" ", "");
        }

        return isValidUsername(username) ? username : null;
    }

    private boolean isValidUsername(String username) {
        if (StringUtils.isBlank(username) || username.contains("页") || username.contains("丶")
                || username.contains("、") || username.contains(".") || username.contains("·")
                || new TelValidator().validate(username).getLeft() || username.contains("@")
                || username.length() > NewUserValidator.MAX_LENGTH
                || username.length() < NewUserValidator.MIN_LENGTH) {
            return false;
        }

        // someday more conditions may be added here
        return true;
    }

    private boolean isValidUrl(String url) {
        if (StringUtils.isBlank(url)) {
            return false;
        }

        String[] validStarts = {"http://tieba.baidu.com/f?", "/p/", "/f?"};
        for (String start : validStarts) {
            if (url.startsWith(start)) {
                return true;
            }
        }
        return false;
    }

    public static interface UsernameHandler {
        void handle(String username);
    }
}
