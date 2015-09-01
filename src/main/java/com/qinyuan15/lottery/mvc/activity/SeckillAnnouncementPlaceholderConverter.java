package com.qinyuan15.lottery.mvc.activity;

import org.springframework.util.StringUtils;

public class SeckillAnnouncementPlaceholderConverter {
    public final static String WINNERS_PLACEHOLDER = "{{winners}}";
    public final static String PARTICIPANT_COUNT_PLACEHOLDER = "{{p_count}}";

    private final String winners;
    private final int participantCount;

    SeckillAnnouncementPlaceholderConverter(String winners, int participantCount) {
        this.winners = winners == null ? "" : winners;
        this.participantCount = participantCount;
    }

    String convert(String announcement) {
        if (!StringUtils.hasText(announcement)) {
            return announcement;
        }

        announcement = announcement.replace(WINNERS_PLACEHOLDER, winners);
        announcement = announcement.replace(PARTICIPANT_COUNT_PLACEHOLDER, String.valueOf(participantCount));

        return announcement;
    }
}
