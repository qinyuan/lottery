package com.qinyuan15.lottery.mvc.dao;

import com.qinyuan.lib.database.hibernate.AbstractDao;
import com.qinyuan.lib.database.hibernate.HibernateListBuilder;
import com.qinyuan.lib.database.hibernate.HibernateUtils;
import com.qinyuan.lib.lang.time.DateUtils;
import org.apache.commons.lang3.BooleanUtils;
import org.apache.commons.lang3.tuple.Pair;

import java.util.ArrayList;
import java.util.List;

public class LotteryWinnerLivenessDao extends AbstractDao<LotteryWinnerLiveness> {
    public Integer add(Integer activityId, Integer userId, Boolean virtual, Integer liveness, String recordTime) {
        LotteryWinnerLiveness l = new LotteryWinnerLiveness();

        l.setActivityId(activityId);
        l.setUserId(userId);
        l.setVirtual(virtual);
        l.setLiveness(liveness);
        l.setRecordTime(recordTime);

        return HibernateUtils.save(l);
    }

    public Pair<String, List<LotterySameLotDao.SimpleUser>> query(Integer activityId) {
        String sql = "SELECT u.username AS u_name,vu.username AS vu_name,virtual,l.liveness,l.record_time " +
                "FROM lottery_winner_liveness AS l " +
                "LEFT JOIN user AS u ON (l.user_id=u.id AND (l.virtual IS NULL or l.virtual=false)) " +
                "LEFT JOIN virtual_user AS vu ON (l.user_id=vu.id AND (l.virtual=true))";
        List<Object[]> list = new HibernateListBuilder().addEqualFilter("activity_id", activityId)
                .addOrder("l.liveness", false)
                .buildBySQL(sql);

        String recordTime = null;
        List<LotterySameLotDao.SimpleUser> users = new ArrayList<>();
        for (Object[] objects : list) {
            boolean virtual = BooleanUtils.isTrue((Boolean) objects[2]);
            String username = virtual ? (String) objects[1] : (String) objects[0];
            LotterySameLotDao.SimpleUser user = new LotterySameLotDao.SimpleUser(username, (Integer) objects[3]);
            recordTime = DateUtils.trimMilliSecond(objects[4].toString());
            users.add(user);
        }

        return Pair.of(recordTime, users);
    }
}
