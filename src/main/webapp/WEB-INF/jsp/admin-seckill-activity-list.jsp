<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<table class="normal">
    <colgroup>
        <%--<col class="index"/>--%>
        <col class="term"/>
        <col class="commodity"/>
        <col class="start-time"/>
        <col class="expect-participant-count"/>
        <col class="real-participant-count"/>
        <c:choose>
            <c:when test="${listExpire}">
                <col class="winners"/>
                <col class="announcement"/>
            </c:when>
            <c:otherwise>
                <col class="description"/>
            </c:otherwise>
        </c:choose>
        <col class="action"/>
    </colgroup>
    <thead>
    <%--<th>序号</th>--%>
    <th>期数</th>
    <th>奖品</th>
    <th>开始时间</th>
    <th>预计参与人数</th>
    <th>真实参与人数</th>
    <c:choose>
        <c:when test="${listExpire}">
            <th>获胜者</th>
            <th>中奖公告</th>
        </c:when>
        <c:otherwise>
            <th>获胜者(虚拟)</th>
            <th>活动说明</th>
        </c:otherwise>
    </c:choose>
    <th></th>
    </thead>
    <tbody>
    <c:forEach var="activity" items="${activities}" varStatus="status">
        <tr data-options="id:${activity.id}">
            <input type="hidden" class="description" value="${activity.description}"/>
            <%--<td>${status.index + rowStartIndex}</td>--%>
            <td class="term">${activity.term}</td>
            <td class="commodity" data-options="commodityId: ${activity.commodity.id}">${activity.commodity.name}</td>
            <td class="start-time">${activity.startTime}</td>
            <td class="expect-participant-count">${activity.expectParticipantCount}</td>
            <td>${activity.realParticipantCount}</td>
            <td class="winners">${activity.winners}</td>
            <c:choose>
                <c:when test="${listExpire}">
                    <td class="announcement">${activity.announcement}</td>
                    <td>
                        <img class="link announce" title="编辑公告" src="resources/css/images/announcement.png"/>
                    </td>
                </c:when>
                <c:otherwise>
                    <td class="description">${activity.description}</td>
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
