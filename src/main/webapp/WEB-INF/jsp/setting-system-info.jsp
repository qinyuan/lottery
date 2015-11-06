<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<c:choose>
    <c:when test="${fn:length(systemInfoItems) > 0}">
        <c:forEach var="systemInfo" items="${systemInfoItems}">
            <div>
                <div class="system-info">
                    <div class="info break-word">${systemInfo.content}</div>
                    <div class="time">${systemInfo.buildTime}</div>
                </div>
            </div>
        </c:forEach>
        <%@include file="widget-pagination.jsp" %>
    </c:when>
    <c:otherwise>
        <div class="no-info">暂时没有系统通知</div>
    </c:otherwise>
</c:choose>

