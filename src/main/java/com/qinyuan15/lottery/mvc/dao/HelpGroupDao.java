package com.qinyuan15.lottery.mvc.dao;

import com.qinyuan15.utils.hibernate.HibernateUtils;
import com.qinyuan15.utils.hibernate.RankingDao;

public class HelpGroupDao {
    public void add(String title) {
        HelpGroup helpGroup = new HelpGroup();
        helpGroup.setTitle(title);

        new RankingDao().add(helpGroup);
    }

    public HelpGroup getInstance(Integer id) {
        return HibernateUtils.get(HelpGroup.class, id);
    }

    public void update(Integer id, String title) {
        HelpGroup helpGroup = getInstance(id);
        if (helpGroup != null) {
            helpGroup.setTitle(title);
            HibernateUtils.update(helpGroup);
        }
    }

    public void rankUp(int id) {
        new RankingDao().rankUp(HelpGroup.class, id);
    }

    public void rankDown(int id) {
        new RankingDao().rankDown(HelpGroup.class, id);
    }
}
