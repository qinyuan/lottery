package com.qinyuan15.lottery.mvc.activity;

import com.qinyuan.lib.database.hibernate.HibernateUtils;

/**
 * Class to create virtual participant
 * Created by qinyuan on 15-7-14.
 */
public class VirtualParticipantCreator {
    /**
     * create several virtual participants
     *
     * @param activityId        id of lottery activity to create participants
     * @param participantNumber how many participants to create
     */
    public void create(Integer activityId, int participantNumber) {
        String hql = "UPDATE LotteryActivity SET virtualParticipants=virtualParticipants+" + participantNumber;
        hql += " WHERE id=" + activityId;
        HibernateUtils.executeUpdate(hql);
        /*
        String hql = "UPDATE LotteryActivity SET maxSerialNumber=maxSerialNumber+" + participantNumber;
        hql += ",virtualParticipants=virtualParticipants+" + participantNumber;
        hql += " WHERE id=" + activityId;
        HibernateUtils.executeUpdate(hql);
        */
    }
}
