package com.qinyuan15.lottery.mvc.activity.tracker;

import java.util.List;

class TrackerFactory {
    private int activityId;

    TrackerFactory(int activityId) {
        this.activityId = activityId;
    }

    List<PreTracker> createActivePreTrackers() {
        return null;
    }

    List<PreTracker> createInactivePreTrackers() {
        return null;
    }

    List<Tracker> getOnJobActiveTrackers() {
        return null;
    }
}
