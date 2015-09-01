package com.qinyuan15.lottery.mvc.activity;

import org.springframework.util.StringUtils;

public class SeckillAnnouncementPlaceholderConverter {
    public final static String WINNERS = "{{winners}}";
    public final static String PARTICIPANT_COUNT = "{{p_count}}";

    private final String winners;
    private final int participantCount;

    SeckillAnnouncementPlaceholderConverter(String winners, int participantCount) {
        this.winners = winners;
        this.participantCount = participantCount;
    }

    String convert(String announcement) {
        if (!StringUtils.hasText(announcement)) {
            return announcement;
        }

        announcement = announcement.replace(WINNERS, winners);
        announcement = announcement.replace(PARTICIPANT_COUNT, String.valueOf(participantCount));

        return announcement;
    }
}
