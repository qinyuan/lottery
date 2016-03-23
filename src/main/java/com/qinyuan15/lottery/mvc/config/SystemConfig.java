package com.qinyuan15.lottery.mvc.config;

public class SystemConfig extends DatabaseConfig {
    //////////////////////// website introduction link start ///////////////////////////
    private final static String WEBSITE_INTRODUCTION_LINK_KEY = "websiteIntroductionLink";

    public String getWebsiteIntroductionLink() {
        return getValue(WEBSITE_INTRODUCTION_LINK_KEY);
    }

    public void updateWebsiteIntroductionLink(String websiteIntroductionLink) {
        saveToDatabase(WEBSITE_INTRODUCTION_LINK_KEY, websiteIntroductionLink);
    }
    //////////////////////// website introduction link end /////////////////////////////

    /////////////////////// index header left logo start //////////////////////////
    private final static String INDEX_HEADER_LEFT_LOGO_KEY = "indexHeaderLeftLogo";

    public String getIndexHeaderLeftLogo() {
        return getValue(INDEX_HEADER_LEFT_LOGO_KEY);
    }

    public void updateIndexHeaderLeftLogo(String indexHeaderLeftLogo) {
        saveToDatabase(INDEX_HEADER_LEFT_LOGO_KEY, indexHeaderLeftLogo);
    }
    /////////////////////// index header left logo end //////////////////////////

    /////////////////////// index header slogan start ////////////////////////////
    private final static String INDEX_HEADER_SLOGAN_KEY = "indexHeaderSlogan";

    public String getIndexHeaderSlogan() {
        return getValue(INDEX_HEADER_SLOGAN_KEY);
    }

    public void updateIndexHeaderSlogan(String indexHeaderSlogan) {
        saveToDatabase(INDEX_HEADER_SLOGAN_KEY, indexHeaderSlogan);
    }
    /////////////////////// index header slogan end ////////////////////////////

    ////////////////////// footer poster start //////////////////////////////
    private final static String FOOTER_POSTER_KEY = "footerPoster";

    public String getFooterPoster() {
        return getValue(FOOTER_POSTER_KEY);
    }

    public void updateFooterPoster(String footerPoster) {
        saveToDatabase(FOOTER_POSTER_KEY, footerPoster);
    }
    ////////////////////// footer poster end ///////////////////////////////

    ////////////////////// footer text start /////////////////////////////////
    private final static String FOOTER_TEXT_KEY = "footerText";

    public String getFooterText() {
        return getValue(FOOTER_TEXT_KEY);
    }

    public void updateFooterText(String footerText) {
        saveToDatabase(FOOTER_TEXT_KEY, footerText);
    }
    ////////////////////// footer text end /////////////////////////////////

    ////////////////////// commodity header left logo start ///////////////////////////
    private final static String COMMODITY_HEADER_LEFT_LOGO_KEY = "commodityHeaderLeftLogo";

    public String getCommodityHeaderLeftLogo() {
        return getValue(COMMODITY_HEADER_LEFT_LOGO_KEY);
    }

    public void updateCommodityHeaderLeftLogo(String commodityHeaderLeftLogo) {
        saveToDatabase(COMMODITY_HEADER_LEFT_LOGO_KEY, commodityHeaderLeftLogo);
    }
    ////////////////////// commodity header left logo end ///////////////////////////

    /////////////////////////////// favicon start ////////////////////////////////////
    private final static String FAVICON_KEY = "favicon";

    public String getFavicon() {
        return getValue(FAVICON_KEY);
    }

    public void updateFavicon(String favicon) {
        saveToDatabase(FAVICON_KEY, favicon);
    }
    ////////////////////////////// favicon end /////////////////////////////////////

    ///////////////////////////// tel validate description page start //////////////////////////
    private final static String TEL_VALIDATE_DESCRIPTION_PAGE_KEY = "telValidateDescriptionPage";

    public String getTelValidateDescriptionPage() {
        return getValue(TEL_VALIDATE_DESCRIPTION_PAGE_KEY);
    }

    public void updateTelValidateDescriptionPage(String telValidateDescriptionPage) {
        saveToDatabase(TEL_VALIDATE_DESCRIPTION_PAGE_KEY, telValidateDescriptionPage);
    }
    ///////////////////////////// tel validate description page end //////////////////////////

    //////////////////////////////////// forum image start ///////////////////////////////////////
    private final static String FORUM_IMAGE_KEY = "forumImage";

    public String getForumImage() {
        return getValue(FORUM_IMAGE_KEY);
    }

    public void updateForumImage(String forumImage) {
        saveToDatabase(FORUM_IMAGE_KEY, forumImage);
    }
    //////////////////////////////////// forum image end /////////////////////////////////////////

    ////////////////////////////////// register logo start ///////////////////////////////////
    private final static String REGISTER_HEADER_LEFT_LOGO_KEY = "registerHeaderLeftLogo";

    public String getRegisterHeaderLeftLogo() {
        return getValue(REGISTER_HEADER_LEFT_LOGO_KEY);
    }

    public void updateRegisterHeaderLeftLogo(String registerHeaderLeftLogo) {
        saveToDatabase(REGISTER_HEADER_LEFT_LOGO_KEY, registerHeaderLeftLogo);
    }

    private final static String REGISTER_HEADER_RIGHT_LOGO_KEY = "registerHeaderRightLogo";

    public String getRegisterHeaderRightLogo() {
        return getValue(REGISTER_HEADER_RIGHT_LOGO_KEY);
    }

    public void updateRegisterHeaderRightLogo(String registerHeaderRightLogo) {
        saveToDatabase(REGISTER_HEADER_RIGHT_LOGO_KEY, registerHeaderRightLogo);
    }
    ////////////////////////////////// register logo end /////////////////////////////////////
}
