package com.qinyuan15.lottery.mvc;

import com.qinyuan15.lottery.mvc.dao.SubIndexImage;
import com.qinyuan15.lottery.mvc.dao.SubIndexImageDao;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/**
 * Class to group SubIndexImage by pageIndex
 * Created by qinyuan on 16-4-23.
 */
public class SubIndexImageGroup {
    private int pageIndex;
    private List<SubIndexImage> subIndexImages;

    private SubIndexImageGroup(int pageIndex, List<SubIndexImage> subIndexImages) {
        this.pageIndex = pageIndex;
        this.subIndexImages = subIndexImages;
    }

    public int getPageIndex() {
        return pageIndex;
    }

    public List<SubIndexImage> getSubIndexImages() {
        return subIndexImages;
    }

    /**
     * convert index image list to map that group index images by row index
     *
     * @param indexImages index image list
     * @return map whose keys are row indexes and values are related index images
     */
    private static Map<Integer, List<SubIndexImage>> buildMap(List<SubIndexImage> indexImages) {
        // use tree map to ensure map is in order
        Map<Integer, List<SubIndexImage>> map = new TreeMap<>();
        for (SubIndexImage subIndexImage : indexImages) {
            Integer pageIndex = subIndexImage.getPageIndex();
            if (!map.containsKey(pageIndex)) {
                map.put(pageIndex, new ArrayList<SubIndexImage>());
            }
            map.get(pageIndex).add(subIndexImage);
        }
        return map;
    }

    public static List<SubIndexImageGroup> build(List<SubIndexImage> subIndexImages) {
        List<SubIndexImageGroup> subIndexImageGroups = new ArrayList<>();

        for (Map.Entry<Integer, List<SubIndexImage>> entry : buildMap(subIndexImages).entrySet()) {
            subIndexImageGroups.add(new SubIndexImageGroup(entry.getKey(), entry.getValue()));
        }

        return subIndexImageGroups;
    }

    public static List<SubIndexImageGroup> build() {
        return build(new SubIndexImageDao().getInstances());
    }
}
