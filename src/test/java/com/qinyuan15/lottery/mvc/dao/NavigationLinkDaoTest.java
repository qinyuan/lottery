package com.qinyuan15.lottery.mvc.dao;

import com.qinyuan.lib.database.test.DatabaseTestCase;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Test NavigationLinkDao
 * Created by qinyuan on 15-6-17.
 */
public class NavigationLinkDaoTest extends DatabaseTestCase {
    private NavigationLinkDao dao = new NavigationLinkDao();

    @Test
    public void testGetInstances() throws Exception {
        assertThat(dao.getInstances()).hasSize(3);
        for (NavigationLink navigationLink : dao.getInstances()) {
            assertThat(navigationLink).isExactlyInstanceOf(NavigationLink.class);
        }
    }

    @Test
    public void testClearAndSave() {
        assertThat(dao.count()).isEqualTo(3);
        for (int i = 1; i <= 3; i++) {
            assertThat(dao.getInstance(i)).isNotNull();
        }

        dao.clearAndSave(null);
        assertThat(dao.count()).isZero();

        List<NavigationLink> links = new ArrayList<>();
        NavigationLink link = new NavigationLink();
        link.setTitle("testTitle1");
        link.setHref("testHref1");
        links.add(link);
        link = new NavigationLink();
        link.setTitle("testTitle2");
        link.setHref("testHref3");
        links.add(link);
        dao.clearAndSave(links);
        assertThat(dao.count()).isEqualTo(2);
        assertThat(dao.getInstance(4)).isNotNull();
        assertThat(dao.getInstance(5)).isNotNull();
    }
}
