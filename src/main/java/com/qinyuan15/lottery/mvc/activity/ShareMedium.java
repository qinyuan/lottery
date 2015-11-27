package com.qinyuan15.lottery.mvc.activity;

import com.google.common.collect.Lists;
import org.springframework.util.StringUtils;

import java.util.List;

/**
 * Medium to share lottery url
 * Created by qinyuan on 15-7-18.
 */
public class ShareMedium {
    public final static ShareMedium SINA_WEIBO = new ShareMedium("sinaWeibo", "新浪微博");
    public final static ShareMedium QQ = new ShareMedium("qq", "QQ");
    public final static ShareMedium QZONE = new ShareMedium("qzone", "QQ空间");
    public final static ShareMedium COPY = new ShareMedium("copy", "复制");
    public final static ShareMedium INITIATIVE = new ShareMedium("initiative", "主动支持");

    public final String en;
    public final String cn;

    private ShareMedium(String en, String cn) {
        this.en = en;
        this.cn = cn;
    }

    public static String getCnByEn(String en) {
        if (!StringUtils.hasText(en)) {
            return "";
        }

        List<ShareMedium> mediums = Lists.newArrayList(SINA_WEIBO, QQ, QZONE);
        for (ShareMedium medium : mediums) {
            if (medium.en.equals(en)) {
                return medium.cn;
            }
        }
        return "未知途径";
    }
}
