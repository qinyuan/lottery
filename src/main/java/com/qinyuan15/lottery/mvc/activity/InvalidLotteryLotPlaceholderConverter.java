package com.qinyuan15.lottery.mvc.activity;

import org.apache.commons.lang3.StringUtils;

public class InvalidLotteryLotPlaceholderConverter {
    public final static String PHASE_PLACEHOLDER = "{{phase}}";
    public final static String LIVENESS_PLACEHOLDER = "{{liveness}}";
    public final static String MIN_LIVENESS_PLACEHOLDER = "{{min_liveness}}";

    private final int fullTerm;
    private final int liveness;
    private final int minLiveness;

    InvalidLotteryLotPlaceholderConverter(int fullTerm, int liveness, int minLiveness) {
        this.fullTerm = fullTerm;
        this.liveness = liveness;
        this.minLiveness = minLiveness;
    }

    String convert(String announcement) {
        if (StringUtils.isBlank(announcement)) {
            return announcement;
        }

        announcement = announcement.replace(PHASE_PLACEHOLDER, String.valueOf(fullTerm));
        announcement = announcement.replace(LIVENESS_PLACEHOLDER, String.valueOf(liveness));
        announcement = announcement.replace(MIN_LIVENESS_PLACEHOLDER, String.valueOf(minLiveness));

        return announcement;
    }
}
