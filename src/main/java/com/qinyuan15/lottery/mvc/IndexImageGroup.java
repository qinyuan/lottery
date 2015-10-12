package com.qinyuan15.lottery.mvc;

import com.qinyuan15.lottery.mvc.dao.IndexImage;
import com.qinyuan15.lottery.mvc.dao.IndexImageDao;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/**
 * Class to group IndexImage by rowIndex
 * Created by qinyuan on 15-6-18.
 */
public class IndexImageGroup {
    private int rowIndex;
    private List<IndexImage> indexImages;

    private IndexImageGroup(int rowIndex, List<IndexImage> indexImages) {
        this.rowIndex = rowIndex;
        this.indexImages = indexImages;
    }

    public int getRowIndex() {
        return rowIndex;
    }

    public List<IndexImage> getIndexImages() {
        return indexImages;
    }

    /**
     * convert index image list to map that group index images by row index
     *
     * @param indexImages index image list
     * @return map whose keys are row indexes and values are related index images
     */
    private static Map<Integer, List<IndexImage>> buildMap(List<IndexImage> indexImages) {
        // use tree map to ensure map is in order
        Map<Integer, List<IndexImage>> map = new TreeMap<>();
        for (IndexImage indexImage : indexImages) {
            Integer rowIndex = indexImage.getRowIndex();
            if (!map.containsKey(rowIndex)) {
                map.put(rowIndex, new ArrayList<IndexImage>());
            }
            map.get(rowIndex).add(indexImage);
        }
        return map;
    }

    public static List<IndexImageGroup> build(List<IndexImage> indexImages) {
        List<IndexImageGroup> indexImageGroups = new ArrayList<>();

        for (Map.Entry<Integer, List<IndexImage>> entry : buildMap(indexImages).entrySet()) {
            indexImageGroups.add(new IndexImageGroup(entry.getKey(), entry.getValue()));
        }

        return indexImageGroups;
    }

    public static List<IndexImageGroup> build() {
        return build(new IndexImageDao().getInstances());
    }
}
