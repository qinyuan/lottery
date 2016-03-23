package com.qinyuan15.lottery.mvc.config;

public class QQListConfig extends DatabaseConfig {
    ////////////////////////////// qqlist start ////////////////////////////////////
    private final static String QQ_LIST_ID_KEY = "qqlistId";

    public String getId() {
        return getValue(QQ_LIST_ID_KEY);
    }

    public void updateId(String qqlistId) {
        saveToDatabase(QQ_LIST_ID_KEY, qqlistId);
    }

    private final static String QQ_LIST_DESCRIPTION_KEY = "qqlistDescription";

    public String getDescription() {
        return getValue(QQ_LIST_DESCRIPTION_KEY);
    }

    public void updateDescription(String qqlistDescription) {
        saveToDatabase(QQ_LIST_DESCRIPTION_KEY, qqlistDescription);
    }
    ////////////////////////////// qqlist end //////////////////////////////////////
}
