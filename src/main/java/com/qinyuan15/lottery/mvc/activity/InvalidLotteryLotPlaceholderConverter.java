package com.qinyuan15.lottery.mvc.activity;

import org.apache.commons.lang3.StringUtils;

public class InvalidLotteryLotPlaceholderConverter {
    public final static String PHASE_PLACEHOLDER = "{{phase}}";
    public final static String LIVENESS_PLACEHOLDER = "{{liveness}}";
    public final static String MIN_LIVENESS_PLACEHOLDER = "{{min_liveness}}";

    private final int fullPhase;
    private final int liveness;
    private final int minLiveness;

    InvalidLotteryLotPlaceholderConverter(int fullPhase, int liveness, int minLiveness) {
        this.fullPhase = fullPhase;
        this.liveness = liveness;
        this.minLiveness = minLiveness;
    }

    String convert(String template) {
        if (StringUtils.isBlank(template)) {
            return template;
        }

        template = template.replace(PHASE_PLACEHOLDER, String.valueOf(fullPhase));
        template = template.replace(LIVENESS_PLACEHOLDER, String.valueOf(liveness));
        template = template.replace(MIN_LIVENESS_PLACEHOLDER, String.valueOf(minLiveness));

        return template;
    }
}
