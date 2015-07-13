<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@include file="inc-taglib.jsp" %>
<table class="normal">
    <colgroup>
        <col class="index"/>
        <col class="commodity"/>
        <col class="start-time"/>
        <col class="expect-end-time"/>
        <col class="continuous-serial-limit"/>
        <col class="expect-participant-count"/>
        <col class="action"/>
    </colgroup>
    <thead>
    <th>序号</th>
    <th>奖品</th>
    <th>开始时间</th>
    <th>预计结束时间</th>
    <th>抽奖号码最大连续个数</th>
    <th>预计参加人数</th>
    <th></th>
    </thead>
    <tbody>
    <c:forEach var="activity" items="${activities}" varStatus="status">
        <tr data-options="id:${activity.id}">
            <td class="index">${status.index + rowStartIndex}</td>
            <td class="commodity"
                data-options="commodityId: ${activity.commodity.id}">${activity.commodity.name}</td>
            <td class="startTime">${activity.startTime}</td>
            <td class="expectEndTime">${activity.expectEndTime}</td>
            <td class="continuousSerialLimit">${activity.continuousSerialLimit}</td>
            <td class="expectParticipantCount">${activity.expectParticipantCount}</td>
            <td class="action">
                <img class="link stop" title="强行结束" src="resources/css/images/stop.png"/>
                <jsp:include page="widget-edit-delete.jsp"/>
            </td>
        </tr>
    </c:forEach>
    </tbody>
</table>
<%@include file="widget-pagination.jsp" %>

<%--
<table class="normal">
    <colgroup>
        <col class="index"/>
        <col class="commodity"/>
        <col class="start-time"/>
        <col class="expect-end-time"/>
        <col class="continuous-serial-limit"/>
        <col class="expect-participant-count"/>
        <col class="end-time"/>
        <col class="winners"/>
        <col class="announcement"/>
        <col class="action"/>
    </colgroup>
    <thead>
    <th>序号</th>
    <th>奖品</th>
    <th>开始时间</th>
    <th>预计结束时间</th>
    <th>抽奖号码最大连续个数</th>
    <th>预计参加人数</th>
    <th>实际结束时间</th>
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
            <td class="startTime">${activity.startTime}</td>
            <td class="expectEndTime">${activity.expectEndTime}</td>
            <td class="continuousSerialLimit">${activity.continuousSerialLimit}</td>
            <td class="expectParticipantCount">${activity.expectParticipantCount}</td>
            <td class="endTime">${activity.endTime}</td>
            <td class="winners">${activity.winnerSerialNumbers}</td>
            <td class="announcement">${activity.announcement}</td>
            <td class="action">
                <c:choose>
                    <c:when test="${activity.expire}">
                        <img class="link announce" title="编辑公告" src="resources/css/images/announcement.png"/>
                    </c:when>
                    <c:otherwise>
                        <img class="link stop" title="强行结束" src="resources/css/images/stop.png"/>
                        <jsp:include page="widget-edit-delete.jsp"/>
                    </c:otherwise>
                </c:choose>
            </td>
        </tr>
    </c:forEach>
    </tbody>
</table>
<%@include file="widget-pagination.jsp" %>
--%>