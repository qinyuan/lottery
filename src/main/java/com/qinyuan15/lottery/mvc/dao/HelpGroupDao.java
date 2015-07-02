package com.qinyuan15.lottery.mvc.dao;

import com.qinyuan15.utils.hibernate.HibernateListBuilder;
import com.qinyuan15.utils.hibernate.HibernateUtils;
import com.qinyuan15.utils.hibernate.RankingDao;

import java.util.List;

public class HelpGroupDao {
    public void add(String title, String icon) {
        HelpGroup helpGroup = new HelpGroup();
        helpGroup.setTitle(title);
        helpGroup.setIcon(icon);

        new RankingDao().add(helpGroup);
    }

    public List<HelpGroup> getInstances() {
        return new HibernateListBuilder().build(HelpGroup.class);
    }

    public HelpGroup getInstance(Integer id) {
        return HibernateUtils.get(HelpGroup.class, id);
    }

    public void update(Integer id, String title, String icon) {
        HelpGroup helpGroup = getInstance(id);
        if (helpGroup != null) {
            helpGroup.setTitle(title);
            helpGroup.setIcon(icon);
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
