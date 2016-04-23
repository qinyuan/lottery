package com.qinyuan15.lottery.mvc.dao;

import com.qinyuan.lib.database.hibernate.AbstractRankingDao;
import com.qinyuan.lib.database.hibernate.HibernateUtils;
import com.qinyuan.lib.database.hibernate.RankingDao;

public class SubIndexImageDao extends AbstractRankingDao<SubIndexImage> {
    private final static String PAGE_INDEX = "pageIndex";

    public Integer add(Integer pageIndex, String path, String backPath) {
        SubIndexImage image = new SubIndexImage();
        image.setPageIndex(pageIndex);
        image.setPath(path);
        image.setBackPath(backPath);

        return new RankingDao().add(image);
    }

    public void update(Integer id, String path, String backPath) {
        SubIndexImage image = getInstance(id);
        if (image != null) {
            image.setPath(path);
            image.setBackPath(backPath);
            HibernateUtils.update(image);
        }
    }

    @Override
    public void rankUp(int id) {
        new RankingDao().rankUp(getPersistClass(), id, PAGE_INDEX);
    }

    @Override
    public void rankDown(int id) {
        new RankingDao().rankDown(getPersistClass(), id, PAGE_INDEX);
    }
}
