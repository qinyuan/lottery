package com.qinyuan15.lottery.mvc.controller;

import com.qinyuan15.lottery.mvc.RichHelpGroup;
import com.qinyuan15.lottery.mvc.dao.HelpItem;
import com.qinyuan15.utils.mvc.controller.ImageController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
public class HelpController extends ImageController {

    @RequestMapping("/help")
    public String index() {
        setTitle("帮助中心");
        return getHelpView();
    }

    protected String getHelpView() {
        IndexHeaderUtils.setHeaderParameters(this);
        setAttribute("helpGroups", adaptRichHelpGroups(RichHelpGroup.getInstances()));
        addJs("resources/js/lib/handlebars.min-v1.3.0", false);
        addCssAndJs("help");
        return "help";
    }


    protected HelpItem adaptHelpItem(HelpItem helpItem) {
        helpItem.setIcon(pathToUrl(helpItem.getIcon()));
        return helpItem;
    }

    protected List<HelpItem> adaptHelpItems(List<HelpItem> helpItems) {
        for (HelpItem helpItem : helpItems) {
            adaptHelpItem(helpItem);
        }
        return helpItems;
    }

    protected RichHelpGroup adaptRichHelpGroup(RichHelpGroup richHelpGroup) {
        adaptHelpItems(richHelpGroup.getItems());
        return richHelpGroup;
    }

    protected List<RichHelpGroup> adaptRichHelpGroups(List<RichHelpGroup> richHelpGroups) {
        for (RichHelpGroup richHelpGroup : richHelpGroups) {
            adaptRichHelpGroup(richHelpGroup);
        }
        return richHelpGroups;
    }
}
