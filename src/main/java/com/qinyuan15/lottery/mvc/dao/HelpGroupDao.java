package com.qinyuan15.lottery.mvc.dao;

import com.qinyuan15.utils.hibernate.AbstractRankingDao;
import com.qinyuan15.utils.hibernate.HibernateUtils;
import com.qinyuan15.utils.hibernate.RankingDao;

public class HelpGroupDao extends AbstractRankingDao<HelpGroup> {
    public Integer add(String title) {
        HelpGroup helpGroup = new HelpGroup();
        helpGroup.setTitle(title);

        return new RankingDao().add(helpGroup);
    }

    public void update(Integer id, String title) {
        HelpGroup helpGroup = getInstance(id);
        if (helpGroup != null) {
            helpGroup.setTitle(title);
            HibernateUtils.update(helpGroup);
        }
    }

    @Override
    public void delete(Integer id) {
        super.delete(id);
        new HelpItemDao().deleteByGroupId(id);
    }
}
