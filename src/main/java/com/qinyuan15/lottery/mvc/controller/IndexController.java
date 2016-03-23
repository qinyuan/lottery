package com.qinyuan15.lottery.mvc.controller;

import com.qinyuan.lib.image.CachedImageMapDao;
import com.qinyuan.lib.image.ImageMapDao;
import com.qinyuan.lib.mvc.controller.ImageController;
import com.qinyuan15.lottery.mvc.ImageMapType;
import com.qinyuan15.lottery.mvc.config.AppConfig;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class IndexController extends ImageController {
    private ImageMapDao dao = new CachedImageMapDao(ImageMapType.INDEX);

    @RequestMapping("/index")
    public String index() {
        IndexHeaderUtils.setHeaderParameters(this);
        IndexHeaderUtils.setIndexImageGroups(this);

        setAttribute("indexImageMaps", dao.getInstancesAndGroupByRelateId());
        addJavaScriptData("cycleInterval", AppConfig.index.getIndexImageCycleInterval());

        setTitle(AppConfig.props.getIndexPageTitle());
        addCssAndJs("index");
        return "index";
    }
}
