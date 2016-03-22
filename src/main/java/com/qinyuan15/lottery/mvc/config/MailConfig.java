package com.qinyuan15.lottery.mvc.config;

public class MailConfig extends DatabaseConfig {
    ///////////////////////////////////// activate mail start ///////////////////////////////////
    private final static String ACTIVATE_MAIL_SUBJECT_TEMPLATE_KEY = "activateMailSubjectTemplate";

    public String getActivateMailSubjectTemplate() {
        return getValue(ACTIVATE_MAIL_SUBJECT_TEMPLATE_KEY);
    }

    public void updateActivateMailSubjectTemplate(String template) {
        saveToDatabase(ACTIVATE_MAIL_SUBJECT_TEMPLATE_KEY, template);
    }

    private final static String ACTIVATE_MAIL_CONTENT_TEMPLATE_KEY = "activateMailContentTemplate";

    public String getActivateMailContentTemplate() {
        return getValue(ACTIVATE_MAIL_CONTENT_TEMPLATE_KEY);
    }

    public void updateActivateMailContentTemplate(String template) {
        saveToDatabase(ACTIVATE_MAIL_CONTENT_TEMPLATE_KEY, template);
    }

    private final static String ACTIVATE_MAIL_ACCOUNT_ID_KEY = "activateMailAccountId";

    public Integer getActivateMailAccountId() {
        return parsePositiveInteger(getValue(ACTIVATE_MAIL_ACCOUNT_ID_KEY));
    }

    public void updateActivateMailAccountId(Integer mailAccountId) {
        saveToDatabase(ACTIVATE_MAIL_ACCOUNT_ID_KEY, mailAccountId);
    }
    ///////////////////////////////////// activate mail end /////////////////////////////////////

    ///////////////////////////////////// register mail start ////////////////////////////////////
    private final static String REGISTER_MAIL_SUBJECT_TEMPLATE_KEY = "registerMailSubjectTemplate";

    public String getRegisterMailSubjectTemplate() {
        return getValue(REGISTER_MAIL_SUBJECT_TEMPLATE_KEY);
    }

    public void updateRegisterMailSubjectTemplate(String template) {
        saveToDatabase(REGISTER_MAIL_SUBJECT_TEMPLATE_KEY, template);
    }

    private final static String REGISTER_MAIL_CONTENT_TEMPLATE_KEY = "registerMailContentTemplate";

    public String getRegisterMailContentTemplate() {
        return getValue(REGISTER_MAIL_CONTENT_TEMPLATE_KEY);
    }

    public void updateRegisterMailContentTemplate(String template) {
        saveToDatabase(REGISTER_MAIL_CONTENT_TEMPLATE_KEY, template);
    }

    private final static String REGISTER_MAIL_ACCOUNT_ID_KEY = "registerMailAccountId";

    public Integer getRegisterMailAccountId() {
        return parsePositiveInteger(getValue(REGISTER_MAIL_ACCOUNT_ID_KEY));
    }

    public void updateRegisterMailAccountId(Integer mailAccountId) {
        saveToDatabase(REGISTER_MAIL_ACCOUNT_ID_KEY, mailAccountId);
    }
    ///////////////////////////////////// register mail end //////////////////////////////////////

    //////////////////////////////// reset password mail start //////////////////////////
    private final static String RESET_PASSWORD_MAIL_SUBJECT_TEMPLATE_KEY = "resetPasswordMailSubjectTemplate";

    public String getResetPasswordMailSubjectTemplate() {
        return getValue(RESET_PASSWORD_MAIL_SUBJECT_TEMPLATE_KEY);
    }

    public void updateResetPasswordMailSubjectTemplate(String template) {
        saveToDatabase(RESET_PASSWORD_MAIL_SUBJECT_TEMPLATE_KEY, template);
    }

    private final static String RESET_PASSWORD_MAIL_CONTENT_TEMPLATE_KEY = "resetPasswordMailContentTemplate";

    public String getResetPasswordMailContentTemplate() {
        return getValue(RESET_PASSWORD_MAIL_CONTENT_TEMPLATE_KEY);
    }

    public void updateResetPasswordMailContentTemplate(String template) {
        saveToDatabase(RESET_PASSWORD_MAIL_CONTENT_TEMPLATE_KEY, template);
    }

    private final static String RESET_PASSWORD_MAIL_ACCOUNT_ID_KEY = "resetPasswordMailAccountId";

    public Integer getResetPasswordMailAccountId() {
        return parsePositiveInteger(getValue(RESET_PASSWORD_MAIL_ACCOUNT_ID_KEY));
    }

    public void updateResetPasswordMailAccountId(Integer mailAccountId) {
        saveToDatabase(RESET_PASSWORD_MAIL_ACCOUNT_ID_KEY, mailAccountId);
    }
    //////////////////////////////// reset password mail end /////////////////////////////

    //////////////////////////////// reset email mail start /////////////////////////////
    private final static String RESET_EMAIL_MAIL_SUBJECT_TEMPLATE_KEY = "resetEmailMailSubjectTemplate";

    public String getResetEmailMailSubjectTemplate() {
        return getValue(RESET_EMAIL_MAIL_SUBJECT_TEMPLATE_KEY);
    }

    public void updateResetEmailMailSubjectTemplate(String template) {
        saveToDatabase(RESET_EMAIL_MAIL_SUBJECT_TEMPLATE_KEY, template);
    }

    private final static String RESET_EMAIL_MAIL_CONTENT_TEMPLATE_KEY = "resetEmailMailContentTemplate";

    public String getResetEmailMailContentTemplate() {
        return getValue(RESET_EMAIL_MAIL_CONTENT_TEMPLATE_KEY);
    }

    public void updateResetEmailMailContentTemplate(String template) {
        saveToDatabase(RESET_EMAIL_MAIL_CONTENT_TEMPLATE_KEY, template);
    }

    private final static String RESET_EMAIL_MAIL_ACCOUNT_ID_KEY = "resetEmailMailAccountId";

    public Integer getResetEmailMailAccountId() {
        return parsePositiveInteger(getValue(RESET_EMAIL_MAIL_ACCOUNT_ID_KEY));
    }

    public void updateResetEmailMailAccountId(Integer mailAccountId) {
        saveToDatabase(RESET_EMAIL_MAIL_ACCOUNT_ID_KEY, mailAccountId);
    }
    ///////////////////////////// reset email mail end /////////////////////////////
}
