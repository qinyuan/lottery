package com.qinyuan15.lottery.mvc.dao;

import com.qinyuan15.utils.hibernate.HibernateListBuilder;
import com.qinyuan15.utils.hibernate.HibernateUtils;
import com.qinyuan15.utils.hibernate.RankingDao;

import java.util.List;

public class HelpItemDao {

    public Integer add(Integer groupId, String content) {
        HelpItem item = new HelpItem();
        item.setGroupId(groupId);
        item.setContent(content);
        return new RankingDao().add(item);
    }

    public List<HelpItem> getInstancesByGroupId(Integer groupId) {
        return new HibernateListBuilder().addEqualFilter(GROUP_ID, groupId).build(HelpItem.class);
    }

    public HelpItem getInstance(Integer id) {
        return HibernateUtils.get(HelpItem.class, id);
    }

    public void update(Integer id, Integer groupId, String content) {
        HelpItem item = getInstance(id);
        if (item != null) {
            item.setGroupId(groupId);
            item.setContent(content);
            HibernateUtils.update(item);
        }
    }

    private final static String GROUP_ID = "groupId";

    public void rankUp(Integer id) {
        new RankingDao().rankUp(HelpItem.class, id, GROUP_ID);
    }

    public void rankDown(Integer id) {
        new RankingDao().rankDown(HelpItem.class, id, GROUP_ID);
    }
}
