package com.qinyuan15.lottery.mvc.activity;

import org.springframework.util.StringUtils;

class LotteryAnnouncementPlaceholderConverter {
    public final static String BALL_PHASE_PLACEHOLDER = "{{b_phase}}";
    public final static String BALL_NUMBER = "{{b_number}}";
    public final static String WINNER_NUMBER = "{{p_number}}";
    public final static String PARTICIPANT_COUNT = "{{p_count}}";

    private final int fullTerm;
    private final String dualColoredBallResult;
    private final String winner;
    private final int participantCount;

    LotteryAnnouncementPlaceholderConverter(int fullTerm, String dualColoredBallResult, String winner, int participantCount) {
        this.fullTerm = fullTerm;
        this.dualColoredBallResult = dualColoredBallResult;
        this.winner = winner;
        this.participantCount = participantCount;
    }

    String convert(String announcement) {
        if (!StringUtils.hasText(announcement)) {
            return announcement;
        }

        announcement = announcement.replace(BALL_PHASE_PLACEHOLDER, String.valueOf(fullTerm));
        announcement = announcement.replace(BALL_NUMBER, dualColoredBallResult);
        announcement = announcement.replace(WINNER_NUMBER, winner);
        announcement = announcement.replace(PARTICIPANT_COUNT, String.valueOf(participantCount));

        return announcement;
    }
}
