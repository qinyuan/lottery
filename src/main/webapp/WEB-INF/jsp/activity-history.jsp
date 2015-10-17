<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@include file="inc-header.jsp" %>
<%@include file="index-header.jsp" %>
<div class="gray-back">
    <div class="page-width main-body">
        <div class="title">我参与的活动<c:if test="${email != null}"><a href="javascript:void(0)"
                                                                  class="subscribe">订阅活动</a></c:if></div>
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
                            <div class="status active">${activity.closed ? '等待开奖' : '活动进行中'}</div>
                            <div class="announcement">
                                <%@include file="activity-history-serial-number.jsp"%>
                                未开奖
                            </div>
                        </c:otherwise>
                    </c:choose>
                </div>
            </c:forEach>
        </div>
    </div>
</div>
<c:if test="${email != null}">
    <%@include file="subscribe-float-panel.jsp" %>
</c:if>
<%@include file="inc-footer.jsp" %>
