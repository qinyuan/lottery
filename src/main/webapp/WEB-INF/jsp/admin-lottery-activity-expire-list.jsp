<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@include file="inc-taglib.jsp" %>
<table class="normal">
    <colgroup>
        <col class="index"/>
        <col class="commodity"/>
        <col class="start-time"/>
        <col class="end-time"/>
        <col class="expect-participant-count"/>
        <col class="participant-count"/>
        <col class="real-participant-count"/>
        <col class="winners"/>
        <col class="announcement"/>
        <col class="action"/>
    </colgroup>
    <thead>
    <th>序号</th>
    <th>奖品</th>
    <th>开始时间</th>
    <th>实际结束时间</th>
    <th>预计参加人数</th>
    <th>总参加人数(包括虚拟部分)</th>
    <th>实际参加人数</th>
    <th>中奖号</th>
    <th>中奖公告</th>
    <th></th>
    </thead>
    <tbody>
    <c:forEach var="activity" items="${activities}" varStatus="status">
        <tr data-options="id:${activity.id}">
            <td class="index">${status.index + rowStartIndex}</td>
            <td class="commodity"
                data-options="commodityId: ${activity.commodity.id}">${activity.commodity.name}</td>
            <td class="start-time">${activity.startTime}</td>
            <td class="end-time">${activity.endTime}</td>
            <td class="expect-participant-count">${activity.expectParticipantCount}</td>
            <td class="participant-count">${activity.participantCount}</td>
            <td class="real-participant-count">${activity.realParticipantCount}</td>
            <td class="winners">${activity.winnerSerialNumbers}</td>
            <td class="announcement">${activity.announcement}</td>
            <td class="action">
                <img class="link announce" title="编辑公告" src="resources/css/images/announcement.png"/>
            </td>
        </tr>
    </c:forEach>
    </tbody>
</table>
<%@include file="widget-pagination.jsp" %>
