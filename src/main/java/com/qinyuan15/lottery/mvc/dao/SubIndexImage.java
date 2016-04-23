package com.qinyuan15.lottery.mvc.dao;

import com.qinyuan.lib.database.hibernate.AbstractRanking;

public class SubIndexImage extends AbstractRanking {
    private Integer pageIndex;
    private String path;
    private String backPath;

    public Integer getPageIndex() {
        return pageIndex;
    }

    public void setPageIndex(Integer pageIndex) {
        this.pageIndex = pageIndex;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getBackPath() {
        return backPath;
    }

    public void setBackPath(String backPath) {
        this.backPath = backPath;
    }

    public SubIndexImage copy() {
        SubIndexImage image = new SubIndexImage();
        image.setId(getId());
        image.setPageIndex(pageIndex);
        image.setPath(path);
        image.setBackPath(backPath);
        image.setRanking(getRanking());
        return image;
    }
}
