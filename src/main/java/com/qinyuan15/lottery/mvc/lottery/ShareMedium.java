package com.qinyuan15.lottery.mvc.lottery;

/**
 * Medium to share lottery url
 * Created by qinyuan on 15-7-18.
 */
public class ShareMedium {
    public final static ShareMedium SINA_WEIBO = new ShareMedium("sinaWeibo", "新浪微博");
    public final static ShareMedium QQ = new ShareMedium("qq", "QQ");
    public final static ShareMedium QZONE = new ShareMedium("qzone", "QQ空间");

    public final String en;
    public final String cn;

    private ShareMedium(String en, String cn) {
        this.en = en;
        this.cn = cn;
    }
}
