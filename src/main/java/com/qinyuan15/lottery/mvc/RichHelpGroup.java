package com.qinyuan15.lottery.mvc;

import com.qinyuan15.lottery.mvc.dao.HelpGroup;
import com.qinyuan15.lottery.mvc.dao.HelpGroupDao;
import com.qinyuan15.lottery.mvc.dao.HelpItem;
import com.qinyuan15.lottery.mvc.dao.HelpItemDao;

import java.util.ArrayList;
import java.util.List;

/**
 * Class to wrap HelpGroup and HelpItem
 * Created by qinyuan on 15-7-2.
 */
public class RichHelpGroup extends HelpGroup {
    private List<HelpItem> items;

    private RichHelpGroup(HelpGroup helpGroup, List<HelpItem> items) {
        this.setId(helpGroup.getId());
        this.setTitle(helpGroup.getTitle());
        this.setRanking(helpGroup.getRanking());
        this.items = items;
    }

    public List<HelpItem> getItems() {
        return items;
    }

    public static RichHelpGroup getInstance(HelpGroup group) {
        if (group == null || group.getId() == null) {
            return null;
        }
        return new RichHelpGroup(group, new HelpItemDao().getInstancesByGroupId(group.getId()));
    }

    public static List<RichHelpGroup> getInstances() {
        List<RichHelpGroup> groups = new ArrayList<>();
        for (HelpGroup group : new HelpGroupDao().getInstances()) {
            groups.add(getInstance(group));
        }
        return groups;
    }
}
