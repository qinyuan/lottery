<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@include file="inc-taglib.jsp" %>
<table class="normal">
    <colgroup>
        <col class="index"/>
        <col class="commodity"/>
        <col class="start-time"/>
        <col class="${listExpire ? 'end-time' : 'expect-end-time'}"/>
        <col class="expect-participant-count"/>
        <col class="participant-count"/>
        <col class="real-participant-count"/>
        <c:choose>
            <c:when test="${listExpire}">
                <col class="winners"/>
                <col class="announcement"/>
            </c:when>
            <c:otherwise>
                <col class="continuous-serial-limit"/>
            </c:otherwise>
        </c:choose>
        <col class="action"/>
    </colgroup>
    <thead>
    <th>序号</th>
    <th>奖品</th>
    <th>开始时间</th>
    <th>${listExpire ? '实际' : '预计'}结束时间</th>
    <th>预计参与人数</th>
    <th>总参与人数(包含虚拟部分)</th>
    <th>实际参与人数</th>
    <c:choose>
        <c:when test="${listExpire}">
            <th>中奖号</th>
            <th>中奖公告</th>
        </c:when>
        <c:otherwise>
            <th>抽奖号码最大连续个数</th>
        </c:otherwise>
    </c:choose>
    <th></th>
    </thead>
    <tbody>
    <c:forEach var="activity" items="${activities}" varStatus="status">
        <tr data-options="id:${activity.id}">
            <td>${status.index + rowStartIndex}</td>
            <td data-options="commodityId: ${activity.commodity.id}">${activity.commodity.name}</td>
            <td>${activity.startTime}</td>
            <td>${listExpire ? activity.endTime : activity.expectEndTime}</td>
            <td>${activity.expectParticipantCount}</td>
            <td>${activity.participantCount}</td>
            <td>${activity.realParticipantCount}</td>
            <c:choose>
                <c:when test="${listExpire}">
                    <td class="winners">${activity.winnerSerialNumbers}</td>
                    <td class="announcement">${activity.announcement}</td>
                    <td>
                        <img class="link announce" title="编辑公告" src="resources/css/images/announcement.png"/>
                    </td>
                </c:when>
                <c:otherwise>
                    <td>${activity.continuousSerialLimit}</td>
                    <td>
                        <img class="link stop" title="强行结束" src="resources/css/images/stop.png"/>
                        <jsp:include page="widget-edit-delete.jsp"/>
                    </td>
                </c:otherwise>
            </c:choose>
        </tr>
    </c:forEach>
    </tbody>
</table>
<%@include file="widget-pagination.jsp" %>
