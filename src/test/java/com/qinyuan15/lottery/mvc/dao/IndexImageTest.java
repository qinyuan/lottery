package com.qinyuan15.lottery.mvc.dao;

import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.RandomUtils;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class IndexImageTest {
    @Test
    public void testCopy() throws Exception {
        IndexImage indexImage = new IndexImage();
        indexImage.setId(RandomUtils.nextInt(0, 1000));
        indexImage.setRowIndex(RandomUtils.nextInt(0, 1000));
        indexImage.setPath(RandomStringUtils.randomAlphanumeric(20));
        indexImage.setBackPath(RandomStringUtils.randomAlphanumeric(20));

        IndexImage copy = indexImage.copy();
        assertThat(copy).isNotSameAs(indexImage);
        assertThat(copy.getId()).isEqualTo(indexImage.getId());
        assertThat(copy.getRowIndex()).isEqualTo(indexImage.getRowIndex());
        assertThat(copy.getPath()).isEqualTo(indexImage.getPath());
        assertThat(copy.getBackPath()).isEqualTo(indexImage.getBackPath());
    }
}
