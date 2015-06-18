package com.qinyuan15.lottery.mvc;

import com.qinyuan15.lottery.mvc.dao.IndexImage;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Test IndexImageGroup
 * Created by qinyuan on 15-6-18.
 */
public class IndexImageGroupTest {
    @Test
    public void testBuild() throws Exception {
        List<IndexImage> indexImages = new ArrayList<>();

        IndexImage indexImage = new IndexImage();
        indexImage.setId(11);
        indexImage.setRowIndex(1);
        indexImages.add(indexImage);

        indexImage = new IndexImage();
        indexImage.setId(12);
        indexImage.setRowIndex(1);
        indexImages.add(indexImage);

        indexImage = new IndexImage();
        indexImage.setId(21);
        indexImage.setRowIndex(2);
        indexImages.add(indexImage);

        indexImage = new IndexImage();
        indexImage.setId(31);
        indexImage.setRowIndex(3);
        indexImages.add(indexImage);

        indexImage = new IndexImage();
        indexImage.setId(32);
        indexImage.setRowIndex(3);
        indexImages.add(indexImage);

        List<IndexImageGroup> indexImageGroups = IndexImageGroup.build(indexImages);
        assertThat(indexImageGroups).hasSize(3);

        assertThat(indexImageGroups.get(0).getRowIndex()).isEqualTo(1);
        assertThat(indexImageGroups.get(0).getIndexImages()).hasSize(2);
        assertThat(indexImageGroups.get(0).getIndexImages().get(0).getId()).isEqualTo(11);
        assertThat(indexImageGroups.get(0).getIndexImages().get(1).getId()).isEqualTo(12);

        assertThat(indexImageGroups.get(1).getRowIndex()).isEqualTo(2);
        assertThat(indexImageGroups.get(1).getIndexImages()).hasSize(1);
        assertThat(indexImageGroups.get(1).getIndexImages().get(0).getId()).isEqualTo(21);

        assertThat(indexImageGroups.get(2).getRowIndex()).isEqualTo(3);
        assertThat(indexImageGroups.get(2).getIndexImages()).hasSize(2);
        assertThat(indexImageGroups.get(2).getIndexImages().get(0).getId()).isEqualTo(31);
        assertThat(indexImageGroups.get(2).getIndexImages().get(1).getId()).isEqualTo(32);
    }
}
