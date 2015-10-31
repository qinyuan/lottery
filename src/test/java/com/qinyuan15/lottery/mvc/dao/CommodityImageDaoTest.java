package com.qinyuan15.lottery.mvc.dao;

import com.qinyuan.lib.database.test.DatabaseTestCase;
import org.junit.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class CommodityImageDaoTest extends DatabaseTestCase {
    private CommodityImageDao dao = new CommodityImageDao();

    @Test
    public void testAdd() throws Exception {
        assertThat(dao.count()).isEqualTo(3);
        dao.add(1, "path_new", "back_path_new");
        assertThat(dao.count()).isEqualTo(4);

        CommodityImage image = dao.getInstance(4);
        assertThat(image.getId()).isEqualTo(4);
        assertThat(image.getPath()).isEqualTo("path_new");
        assertThat(image.getBackPath()).isEqualTo("back_path_new");
        assertThat(image.getRanking()).isEqualTo(4);
    }

    @Test
    public void testUpdate() throws Exception {
        dao.update(1, 3, "path11", "backPath11");
        CommodityImage image = dao.getInstance(1);
        assertThat(image.getCommodityId()).isEqualTo(3);
        assertThat(image.getPath()).isEqualTo("path11");
        assertThat(image.getBackPath()).isEqualTo("backPath11");
    }

    @Test
    public void testGetInstancesByCommodityId() throws Exception {
        List<CommodityImage> images = dao.getInstancesByCommodityId(1);
        assertThat(images).hasSize(2);
        assertThat(images.get(0).getId()).isEqualTo(2);
        assertThat(images.get(1).getId()).isEqualTo(1);

        images = dao.getInstancesByCommodityId(2);
        assertThat(images).hasSize(1);
        assertThat(images.get(0).getId()).isEqualTo(3);

        assertThat(dao.getInstancesByCommodityId(333)).isEmpty();
    }
}
