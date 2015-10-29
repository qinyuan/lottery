<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<c:forEach var="systemInfo" items="${systemInfoItems}">
    <div>
        <div class="system-info">
            <div class="info break-word">${systemInfo.content}</div>
            <div class="time">${systemInfo.buildTime}</div>
        </div>
    </div>
</c:forEach>
<%@include file="widget-pagination.jsp" %>
