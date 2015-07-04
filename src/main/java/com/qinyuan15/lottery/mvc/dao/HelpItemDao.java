package com.qinyuan15.lottery.mvc.dao;

import com.qinyuan15.utils.hibernate.HibernateDeleter;
import com.qinyuan15.utils.hibernate.HibernateListBuilder;
import com.qinyuan15.utils.hibernate.HibernateUtils;
import com.qinyuan15.utils.hibernate.RankingDao;

import java.util.List;

public class HelpItemDao {

    public Integer add(Integer groupId, String icon, String title, String content) {
        HelpItem item = new HelpItem();
        item.setGroupId(groupId);
        item.setIcon(icon);
        item.setContent(content);
        item.setTitle(title);
        return new RankingDao().add(item);
    }

    public List<HelpItem> getInstancesByGroupId(Integer groupId) {
        return new HibernateListBuilder().addOrder("ranking", true)
                .addEqualFilter(GROUP_ID, groupId).build(HelpItem.class);
    }

    public HelpItem getInstance(Integer id) {
        return HibernateUtils.get(HelpItem.class, id);
    }

    public void deleteByGroupId(Integer groupId) {
        new HibernateDeleter().addEqualFilter("groupId", groupId);
    }

    public void update(Integer id, Integer groupId, String icon, String title, String content) {
        HelpItem item = getInstance(id);
        if (item != null) {
            item.setGroupId(groupId);
            item.setIcon(icon);
            item.setTitle(title);
            item.setContent(content);
            HibernateUtils.update(item);
        }
    }

    public void updateContent(Integer id, String content) {
        HelpItem item = getInstance(id);
        if (item != null) {
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
