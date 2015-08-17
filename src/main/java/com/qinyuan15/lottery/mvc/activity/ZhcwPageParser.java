package com.qinyuan15.lottery.mvc.activity;

import com.google.common.base.Joiner;
import com.qinyuan15.utils.html.JavaScriptExecutor;
import org.mozilla.javascript.NativeObject;

import java.text.DecimalFormat;
import java.util.Arrays;

import static com.qinyuan15.lottery.mvc.activity.DualColoredBallCalculator.*;

public class ZhcwPageParser implements DualColoredBallPageParser {
    private NativeObject data;

    public ZhcwPageParser(String pageContent, int fullTermNumber) {
        Object kjData1 = new JavaScriptExecutor().evaluate(pageContent + ";kjData_1");
        if (kjData1 instanceof NativeObject) {
            Object dataObject = ((NativeObject) kjData1).get(fullTermNumber);
            if (dataObject instanceof NativeObject) {
                data = (NativeObject) dataObject;
            }
        }
    }

    @Override
    public String getResult() {
        if (data == null) {
            return null;
        }
        Object number = data.get("kjZNum");
        if (number == null || !(number instanceof String)) {
            return null;
        }

        String[] numbers = ((String) number).split("\\s");
        Arrays.sort(numbers);
        return Joiner.on(" ").join(numbers);
    }

    @Override
    public String getDrawTime() {
        if (data == null) {
            return null;
        }
        Object date = data.get("kjDate");
        if (date == null || !(date instanceof String)) {
            return null;
        }
        DecimalFormat format = new DecimalFormat("00");
        return date + " " + format.format(PUBLISH_HOUR) + ":" + format.format(PUBLISH_MINUTE)
                + ":" + format.format(PUBLISH_SECOND);
    }
}
