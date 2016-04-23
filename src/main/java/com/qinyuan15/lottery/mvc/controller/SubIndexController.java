package com.qinyuan15.lottery.mvc.controller;

import com.qinyuan.lib.image.CachedImageMapDao;
import com.qinyuan.lib.image.ImageMap;
import com.qinyuan.lib.image.ImageMapDao;
import com.qinyuan.lib.mvc.controller.ImageController;
import com.qinyuan15.lottery.mvc.ImageMapType;
import com.qinyuan15.lottery.mvc.SubIndexImageGroup;
import com.qinyuan15.lottery.mvc.dao.SubIndexImage;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.List;

@Controller
public class SubIndexController extends ImageController {
    private ImageMapDao mapDao = new CachedImageMapDao(ImageMapType.SUB_INDEX);

    @RequestMapping("/affiliate")
    public String index1() {
        return index("布迪联盟", 1);
    }

    @RequestMapping("/join")
    public String index2() {
        return index("加入我们", 2);
    }

    public String index(String title, int pageIndex) {
        IndexHeaderUtils.setHeaderParameters(this);
        RegisterHeaderUtils.setParameters(this);

        List<SubIndexImageGroup> groups = SubIndexHeaderUtils.adapt(this, SubIndexImageGroup.build());
        SubIndexImageGroup group = SubIndexHeaderUtils.getGroupByPageIndex(groups, pageIndex);
        setAttribute("subIndexImageGroup", group);

        List<List<ImageMap>> maps = new ArrayList<>();
        for (SubIndexImage image : group.getSubIndexImages()) {
            maps.add(mapDao.getInstancesByRelateId(image.getId()));
        }
        setAttribute("maps", maps);

        setTitle(title);
        addJs("lib/handlebars.min-v1.3.0", false);
        addCssAndJs("sub-index");
        return "sub-index";
    }
}
