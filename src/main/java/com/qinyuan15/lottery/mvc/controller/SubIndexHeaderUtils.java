package com.qinyuan15.lottery.mvc.controller;

import com.qinyuan.lib.mvc.controller.ImageController;
import com.qinyuan15.lottery.mvc.SubIndexImageGroup;
import com.qinyuan15.lottery.mvc.dao.SubIndexImage;

import java.util.List;

/**
 * Utility class of IndexHeader
 * Created by qinyuan on 15-6-21.
 */
public class SubIndexHeaderUtils {
    static void setSubIndexImageGroups(ImageController controller) {
        controller.setAttribute("subIndexImageGroups", adapt(controller, SubIndexImageGroup.build()));
    }

    static List<SubIndexImageGroup> adapt(ImageController controller, List<SubIndexImageGroup> subIndexImageGroups) {
        for (SubIndexImageGroup subIndexImageGroup : subIndexImageGroups) {
            for (SubIndexImage indexImage : subIndexImageGroup.getSubIndexImages()) {
                adapt(controller, indexImage);
            }
        }
        return subIndexImageGroups;
    }

    static SubIndexImage adapt(ImageController controller, SubIndexImage subIndexImage) {
        subIndexImage.setPath(controller.pathToUrl(subIndexImage.getPath()));
        subIndexImage.setBackPath(controller.pathToUrl(subIndexImage.getBackPath()));
        return subIndexImage;
    }
}
