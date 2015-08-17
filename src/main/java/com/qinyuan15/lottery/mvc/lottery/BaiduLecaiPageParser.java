package com.qinyuan15.lottery.mvc.lottery;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Map;

public class BaiduLecaiPageParser implements DualColoredBallPageParser {

    private final static Logger LOGGER = LoggerFactory.getLogger(BaiduLecaiPageParser.class);
    private Map<String, Object> data;

    @SuppressWarnings("unchecked")
    public BaiduLecaiPageParser(String pageContent) {
        if (!StringUtils.hasText(pageContent)) {
            LOGGER.error("pageContent is empty: {}", pageContent);
            return;
        }
        if (pageContent.contains("出错啦！彩期不存在！")) {
            LOGGER.error("invalid phase");
            return;
        }

        ObjectMapper objectMapper = new ObjectMapper();
        try {
            Map<String, Object> object = (Map) objectMapper.readValue(pageContent, Object.class);
            this.data = (Map) object.get("data");
        } catch (Exception e) {
            LOGGER.error("Fail to get data from page pageContent, pageContent: {}, info: {}", pageContent, e);
        }
    }

    @SuppressWarnings("unchecked")
    public String getResult() {
        if (data == null) {
            return null;
        }
        try {
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
            LOGGER.error("Fail to parse baidu lecai page, data: {}, info: {}", data, e);
            return null;
        }
    }

    @SuppressWarnings("unchecked")
    @Override
    public String getDrawTime() {
        if (data == null) {
            return null;
        }
        Object drawTime = data.get("time_draw");
        return drawTime == null ? null : drawTime.toString();
    }
}
