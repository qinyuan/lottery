<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@include file="inc-header.jsp" %>
<%@include file="index-header.jsp" %>
<div class="gray-back">
    <div class="page-width main-body">
        <div class="title">我参与的活动</div>
        <%--<div class="filter">
            <input type="radio" name="activityType" value="lottery"<c:if test="${activityType == 'lottery'}"> checked</c:if>/>
            <span class="text">抽奖</span>
            <input type="radio" name="activityType" value="seckill"<c:if test="${activityType == 'seckill'}"> checked</c:if>/>
            <span class="text">秒杀</span>
        </div>--%>
        <div class="activities">
            <c:forEach var="activity" items="${activities}">
                <div class="activity shadow">
                    <div class="term">${activity.term}期</div>
                    <div class="snapshot" style="background-image: url('${activity.snapshot}')"></div>
                    <div class="name">${activity.name}</div>
                    <div class="count">
                        <span class="commodity-count">产品数量：1</span>
                        <span class="participant-count">申请人数：${activity.participantCount}</span>
                    </div>
                        <%--<div class="icon lightTransparent">
                            <img src="resources/css/images/commodity/in-${activity.type}.png"/>
                        </div>--%>
                    <div class="border"></div>
                    <c:choose>
                        <c:when test="${activity.expire}">
                            <div class="status expire">${activity.type == 'lottery' ? '已开奖' : '秒杀结束'}</div>
                            <div class="announcement">
                                <c:if test="${activity.serials != null}">抽奖号：${activity.serials}<br/></c:if>
                                中奖信息：<br/>${activity.announcement}
                            </div>
                        </c:when>
                        <c:otherwise>
                            <div class="status active">活动进行中</div>
                            <div class="announcement">未开奖</div>
                        </c:otherwise>
                    </c:choose>
                </div>
            </c:forEach>
        </div>
        <%--
        <table class="normal">
            <colgroup>
                <col class="index"/>
                <c:forEach var="alias" items="${lotteryHistoryTable.aliases}">
                    <col class="${alias}"/>
                </c:forEach>
            </colgroup>
            <thead>
            <th>序号</th>
            <c:forEach var="head" items="${lotteryHistoryTable.heads}" varStatus="status">
                <th class="${lotteryHistoryTable.aliases[status.index]}">${head}</th>
            </c:forEach>
            </thead>
            <tbody>
            <c:forEach var="history" items="${lotteryHistories}" varStatus="status">
                <tr data-options="id:${history.id}">
                    <td>${status.index + rowStartIndex}</td>
                    <c:forEach var="col" items="${history.cols}" varStatus="innerStatus">
                        <td class="${lotteryHistoryTable.aliases[innerStatus.index]}">${col}</td>
                    </c:forEach>
                </tr>
            </c:forEach>
            </tbody>
        </table>
        <%@include file="widget-pagination.jsp" %>
        --%>
    </div>
</div>
<%@include file="inc-footer.jsp" %>
