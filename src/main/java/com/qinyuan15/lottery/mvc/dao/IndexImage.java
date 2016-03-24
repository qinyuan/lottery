package com.qinyuan15.lottery.mvc.dao;

import com.qinyuan.lib.database.hibernate.PersistObject;

/**
 * Image in index page
 * Created by qinyuan on 15-6-18.
 */
public class IndexImage extends PersistObject {

    private String path;
    private String backPath;
    private Integer rowIndex;

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

    public Integer getRowIndex() {
        return rowIndex;
    }

    public void setRowIndex(Integer rowIndex) {
        this.rowIndex = rowIndex;
    }

    public IndexImage copy() {
        IndexImage image = new IndexImage();
        image.setId(getId());
        image.setPath(path);
        image.setBackPath(backPath);
        image.setRowIndex(rowIndex);
        return image;
    }
}
