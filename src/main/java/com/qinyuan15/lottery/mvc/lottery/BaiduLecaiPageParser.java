package com.qinyuan15.lottery.mvc.lottery;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Map;

public class BaiduLecaiPageParser implements DualColoredBallPageParser {

    private final static Logger LOGGER = LoggerFactory.getLogger(BaiduLecaiPageParser.class);
    private final String pageContent;

    public BaiduLecaiPageParser(String pageContent) {
        this.pageContent = pageContent;
    }

    @SuppressWarnings("unchecked")
    public String getResult() {
        if (!StringUtils.hasText(this.pageContent)) {
            return null;
        }

        ObjectMapper objectMapper = new ObjectMapper();
        try {
            Map<String, Object> object = (Map) objectMapper.readValue(this.pageContent, Object.class);
            Map<String, Object> data = (Map) object.get("data");
            Map<String, Object> result = (Map) data.get("result");
            if (result == null) {
                LOGGER.warn("Dual colored ball haven's been drawn");
                return null;
            }

            List resultList = (List) result.get("result");
            Map<String, Object> map = (Map) resultList.get(0);
            List<String> dataList = (List) map.get("data");

            String resultString = "";
            for (String str : dataList) {
                if (str.length() == 1) {
                    resultString += "0" + str;
                } else if (str.length() >= 2) {
                    resultString += str.substring(0, 2);
                }
            }
            return resultString;
        } catch (Exception e) {
            LOGGER.error("Fail to parse baidu lecai page, page content: {}, info: {}", this.pageContent, e);
            return null;
        }
    }
}
