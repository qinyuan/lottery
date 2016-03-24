package com.qinyuan15.lottery.mvc.dao;

import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.RandomUtils;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class CommodityTest {
    @Test
    public void testCopy() throws Exception {
        Commodity commodity = new Commodity();

        commodity.setId(RandomUtils.nextInt(0, 1000));
        commodity.setVisible(false);
        commodity.setSnapshot(RandomStringUtils.randomAlphanumeric(20));
        commodity.setOwn(true);
        commodity.setName(RandomStringUtils.randomAlphanumeric(20));
        commodity.setPrice(RandomUtils.nextDouble(0, 1000));
        commodity.setRanking(RandomUtils.nextInt(0, 1000));

        Commodity copy = commodity.copy();

        assertThat(copy).isNotSameAs(commodity);
        assertThat(copy.getId()).isEqualTo(commodity.getId());
        assertThat(copy.getVisible()).isEqualTo(commodity.getVisible());
        assertThat(copy.getSnapshot()).isEqualTo(commodity.getSnapshot());
        assertThat(copy.getOwn()).isEqualTo(commodity.getOwn());
        assertThat(copy.getName()).isEqualTo(commodity.getName());
        assertThat(copy.getPrice()).isEqualTo(commodity.getPrice());
        assertThat(copy.getRanking()).isEqualTo(commodity.getRanking());
    }
}
