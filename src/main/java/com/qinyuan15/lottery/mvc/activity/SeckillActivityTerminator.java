package com.qinyuan15.lottery.mvc.activity;

import com.qinyuan.lib.lang.concurrent.ThreadUtils;
import com.qinyuan.lib.lang.time.DateUtils;
import com.qinyuan15.lottery.mvc.dao.SeckillActivity;
import com.qinyuan15.lottery.mvc.dao.SeckillActivityDao;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SeckillActivityTerminator {
    private final static Logger LOGGER = LoggerFactory.getLogger(SeckillActivityTerminator.class);
    private Map<Integer, TerminateThread> threads = new HashMap<>();

    public void init() {
        new LoadActivityThread().start();
    }

    private class LoadActivityThread extends Thread {
        final static int INTERVAL = 60; // reload each minute

        @Override
        public void run() {
            while (true) {
                // build crawl thread for each active lottery activity
                List<SeckillActivity> activities = SeckillActivityDao.factory().setExpire(false).getInstances();
                for (SeckillActivity activity : activities) {
                    TerminateThread thread = threads.get(activity.getId());

                    if (thread == null) {
                        // if related thread not exists, create and run it
                        thread = new TerminateThread(activity);
                        threads.put(activity.getId(), thread);
                        thread.start();
                    } else {
                        // if related thread already exists, just update activity data of it
                        thread.activity = activity;
                    }
                }
                ThreadUtils.sleep(INTERVAL);
            }
        }
    }

    private class TerminateThread extends Thread {
        SeckillActivity activity;

        TerminateThread(SeckillActivity activity) {
            this.activity = activity;
        }

        @Override
        public void run() {
            while (true) {
                if (!DateUtils.isDateOrDateTime(activity.getStartTime())) {
                    break;
                }

                Date startTime = DateUtils.newDate(activity.getStartTime());
                long timeDiff = startTime.getTime() - System.currentTimeMillis();
                try {
                    if (timeDiff <= 0) {
                        new SeckillActivityDao().end(activity.getId());
                        new SeckillResultUpdater().update(activity.getId());

                        // remove related thread after success
                        threads.remove(activity.getId());
                        break;
                    }
                } catch (Throwable e) {
                    e.printStackTrace();
                    LOGGER.error("Fail to terminate activity whose id is {}, info: {}", activity.getId(), e);
                }

                ActivityTerminatorUtils.sleep(timeDiff);
            }
        }
    }
}
