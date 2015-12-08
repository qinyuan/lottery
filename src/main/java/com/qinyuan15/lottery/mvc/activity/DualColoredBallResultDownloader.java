package com.qinyuan15.lottery.mvc.activity;

import com.qinyuan.lib.lang.time.DateUtils;
import com.qinyuan15.lottery.mvc.activity.dualcoloredball.DualColoredBallPhase;
import com.qinyuan15.lottery.mvc.dao.DualColoredBallRecordDao;

public class DualColoredBallResultDownloader {
    private DualColoredBallRecordDao dao = new DualColoredBallRecordDao();

    /**
     * Download results of all terms in certain year
     *
     * @param year the year that should download result
     */
    public void download(int year) {
        int phase = 1;
        while (dao.hasTerm(year, phase) || downloadResult(year, phase)) {
            phase++;
        }
    }

    /**
     * Download results of all phase in current year
     */
    public void download() {
        download(DateUtils.currentYear());
    }

    private boolean downloadResult(int year, int phase) {
        DualColoredBallCrawler.Result result = new BaiduLecaiCrawler().getResult(DualColoredBallPhase.toFullPhase(year, phase));
        if (result == null || result.result == null || result.drawTime == null) {
            return false;
        } else {
            dao.add(year, phase, result.drawTime, result.result);
            return true;
        }
    }
}
