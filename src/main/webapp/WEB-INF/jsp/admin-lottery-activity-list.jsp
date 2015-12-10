<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@include file="inc-taglib.jsp" %>
<table class="normal">
    <colgroup>
        <col class="term"/>
        <col class="commodity"/>
        <col class="start-time"/>
        <c:if test="${!listExpire}">
            <col class="close-time"/>
        </c:if>
        <col class="${listExpire ? 'end-time' : 'expect-end-time'}"/>
        <col class="expect-participant-count"/>
        <col class="participant-count"/>
        <col class="real-participant-count"/>
        <col class="winners"/>
        <c:if test="${listExpire}">
            <%--<col class="winners"/>--%>
            <col class="announcement"/>
        </c:if>
        <col class="action"/>
    </colgroup>
    <thead>
    <%--<th>序号</th>--%>
    <th>期数</th>
    <th>奖品</th>
    <th>开始时间</th>
    <c:if test="${!listExpire}">
        <th>关闭时间</th>
    </c:if>
    <th>${listExpire ? '实际' : '预计'}结束时间</th>
    <th>预计参与人数</th>
    <th>总参与人数<br/><span style="font-size: 9pt;">(包含虚拟)</span></th>
    <th>真实参与人数</th>
    <th>中奖号</th>
    <c:if test="${listExpire}">
        <th>中奖公告</th>
    </c:if>
    <th></th>
    </thead>
    <tbody>
    <c:forEach var="activity" items="${activities}" varStatus="status">
        <tr data-options="id:${activity.id}">
            <q:hidden cssClass="description" value="${activity.description}"/>
            <td class="term">${activity.term}</td>
            <td class="commodity" data-options="commodityId: ${activity.commodity.id}">${activity.commodity.name}</td>
            <td class="start-time">${fn:replace(activity.startTime, ' ', '<br/> ')}</td>
            <c:if test="${!listExpire}">
                <td class="close-time">${fn:replace(activity.closeTime, ' ', '<br/> ')}</td>
            </c:if>
            <td class="${listExpire ? 'end-time' : 'expect-end-time'}"
                <c:if test="${!listExpire}">data-options="dualColoredBallTerm:${activity.dualColoredBallTerm}"</c:if>
                    >${fn:replace((listExpire ? activity.endTime : activity.expectEndTime), ' ', '<br/> ')}</td>
            <td class="expect-participant-count">${activity.expectParticipantCount}</td>
            <td>${activity.participantCount}</td>
            <td>${activity.realParticipantCount}</td>
            <td class="winner"><span class="number">${activity.winners}</span>
                <c:if test="${activity.winners != null}">
                    <a href="javascript:void(0)" class="winner-ranking">排名</a>

                    <div class="winner-ranking shadow">
                        <div class="no-user">无人抽中此号码</div>
                        <table>
                            <colgroup>
                                <col class="username"/>
                                <col class="liveness"/>
                                <col class="virtual"/>
                            </colgroup>
                            <thead>
                            <tr>
                                <td>昵称</td>
                                <td>支持数</td>
                                <td>类型</td>
                            </tr>
                            </thead>
                            <tbody></tbody>
                        </table>
                    </div>
                </c:if>
            </td>
            <c:choose>
                <c:when test="${listExpire}">
                    <%--<td class="winners">${activity.winners}</td>--%>
                    <td class="announcement">${activity.announcement}</td>
                    <td>
                        <img class="link announce" title="编辑公告" src="resources/css/images/announcement.png"/>
                    </td>
                </c:when>
                <c:otherwise>
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
