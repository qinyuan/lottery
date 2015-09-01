package com.qinyuan15.lottery.mvc.activity;

import org.springframework.util.StringUtils;

class SeckillAnnouncementPlaceholderConverter {
    public final static String WINNER_NUMBER = "{{p_number}}";
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

        announcement = announcement.replace(WINNER_NUMBER, winners);
        announcement = announcement.replace(PARTICIPANT_COUNT, String.valueOf(participantCount));

        return announcement;
    }
}
