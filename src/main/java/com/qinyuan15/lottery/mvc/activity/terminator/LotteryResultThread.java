package com.qinyuan15.lottery.mvc.activity.terminator;

import com.qinyuan.lib.lang.IntegerUtils;
import com.qinyuan.lib.lang.time.DateUtils;
import com.qinyuan15.lottery.mvc.config.AppConfig;
import com.qinyuan15.lottery.mvc.activity.LotteryActivityUtils;
import com.qinyuan15.lottery.mvc.activity.dualcoloredball.DualColoredBallCrawler;
import com.qinyuan15.lottery.mvc.activity.dualcoloredball.DualColoredBallPhaseValidator;
import com.qinyuan15.lottery.mvc.activity.tracker.WinnerManager;
import com.qinyuan15.lottery.mvc.dao.DualColoredBallRecordDao;
import com.qinyuan15.lottery.mvc.dao.LotteryActivity;
import com.qinyuan15.lottery.mvc.dao.LotteryActivityDao;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * Thread to crawl lottery result
 * Created by qinyuan on 16-1-24.
 */
public class LotteryResultThread extends LotteryHandlerThread {
    private final static Logger LOGGER = LoggerFactory.getLogger(LotteryResultThread.class);
    private List<DualColoredBallCrawler> crawlers;

    LotteryResultThread(LotteryActivity activity, List<DualColoredBallCrawler> crawlers) {
        super(activity);
        this.crawlers = crawlers;
    }

    @Override
    protected boolean doLoop() {
        if (!DateUtils.isDateOrDateTime(activity.getExpectEndTime())) {
            return true;
        }

        if (!AppConfig.properties.isOffline()) {
            try {
                DualColoredBallCrawler.Result result = getResult();
                if (result != null) {
                    new DualColoredBallRecordDao().add(activity.getDualColoredBallTerm(),
                            result.drawTime, result.result);
                    new LotteryActivityDao().updateResult(activity.getId(),
                            WinnerManager.getWinnerNumber(result.result));
                    return false;
                }
            } catch (Throwable e) {
                e.printStackTrace();
                LOGGER.error("Fail to crawl result of activity whose id is {}, info: {}", activity.getId(), e);
            }
        }
        return true;
    }

    @Override
    protected long getSleepTime() {
        return LotteryActivityUtils.getRemainingTimeToEnd(activity);
    }

    private DualColoredBallCrawler.Result getResult() {
        if (crawlers == null) {
            return null;
        }
        if (!new DualColoredBallPhaseValidator().validate(activity.getDualColoredBallTerm())) {
            return null;
        }

        for (DualColoredBallCrawler crawler : crawlers) {
            Integer dualColoredBallTerm = activity.getDualColoredBallTerm();
            if (!IntegerUtils.isPositive(dualColoredBallTerm)) {
                continue;
            }

            DualColoredBallCrawler.Result result = crawler.getResult(dualColoredBallTerm);
            if (result != null && result.result != null && result.result.matches("^\\d{12}$")) {
                return result;
            } else {
                LOGGER.error("Fail to parse result of dualColoredBall {}", dualColoredBallTerm);
            }
        }
        return null;
    }
}
