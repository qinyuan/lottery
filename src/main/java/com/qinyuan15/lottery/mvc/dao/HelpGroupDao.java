package com.qinyuan15.lottery.mvc.dao;

import com.qinyuan15.utils.hibernate.HibernateDeleter;
import com.qinyuan15.utils.hibernate.HibernateUtils;
import com.qinyuan15.utils.hibernate.RankingDao;

import java.util.List;

public class HelpGroupDao {
    public void add(String title) {
        HelpGroup helpGroup = new HelpGroup();
        helpGroup.setTitle(title);

        new RankingDao().add(helpGroup);
    }

    public List<HelpGroup> getInstances() {
        return new RankingDao().getInstances(HelpGroup.class);
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

    public void delete(Integer id) {
        HibernateDeleter.deleteById(HelpGroup.class, id);
        new HelpItemDao().deleteByGroupId(id);
    }

    public void rankUp(int id) {
        new RankingDao().rankUp(HelpGroup.class, id);
    }

    public void rankDown(int id) {
        new RankingDao().rankDown(HelpGroup.class, id);
    }
}
