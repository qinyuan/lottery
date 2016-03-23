package com.qinyuan15.lottery.mvc.config;

public class LivenessConfig extends DatabaseConfig {
    ///////////////////////// share succeed liveness start /////////////////////
    private final static String SHARE_SUCCEED_LIVENESS_KEY = "shareSucceedLiveness";

    public Integer getShareSucceedLiveness() {
        return parseInteger(getValue(SHARE_SUCCEED_LIVENESS_KEY));
    }

    public void updateShareSucceedLiveness(Integer shareSucceedLiveness) {
        saveToDatabase(SHARE_SUCCEED_LIVENESS_KEY, shareSucceedLiveness);
    }
    ///////////////////////// share succeed liveness end /////////////////////

    ////////////////////////////// liveness increase start ////////////////////////////////
    private final static String REMIND_LIVENESS_INCREASE_BY_SYSTEM_INFO_KEY = "remindLivenessIncreaseBySystemInfo";

    public Boolean getRemindIncreaseBySystemInfo() {
        return parseBoolean(getValue(REMIND_LIVENESS_INCREASE_BY_SYSTEM_INFO_KEY));
    }

    public void updateRemindIncreaseBySystemInfo(Boolean remindLivenessIncreaseBySystemInfo) {
        saveToDatabase(REMIND_LIVENESS_INCREASE_BY_SYSTEM_INFO_KEY, remindLivenessIncreaseBySystemInfo);
    }

    private final static String LIVENESS_INCREASE_SYSTEM_INFO_TEMPLATE_KEY = "livenessIncreaseSystemInfoTemplate";

    public String getIncreaseSystemInfoTemplate() {
        return getValue(LIVENESS_INCREASE_SYSTEM_INFO_TEMPLATE_KEY);
    }

    public void updateIncreaseSystemInfoTemplate(String template) {
        saveToDatabase(LIVENESS_INCREASE_SYSTEM_INFO_TEMPLATE_KEY, template);
    }
    ////////////////////////////// liveness increase end /////////////////////////////////
}
