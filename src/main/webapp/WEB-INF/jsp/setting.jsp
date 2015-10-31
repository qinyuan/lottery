<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@include file="inc-header.jsp" %>
<%@include file="index-header.jsp" %>
<div class="white-back">
    <div class="page-width main-body">
        <div class="left">
            <div class="title">设置</div>
            <div class="nav">
                <c:forEach var="title" items="${titles}" varStatus="status">
                    <div>
                        <c:choose>
                            <c:when test="${status.index == pageIndex}">
                                <span>${title}</span>
                            </c:when>
                            <c:otherwise>
                                <a href="setting.html?index=${status.index}">${title}</a>
                            </c:otherwise>
                        </c:choose>
                    </div>
                </c:forEach>
            </div>
        </div>
        <div class="right">
            <div class="title">${titles[pageIndex]}</div>
            <div class="body">
                <c:if test="${pageIndex == 0}">
                    <%@include file="setting-personal-info.jsp" %>
                </c:if>
                <c:if test="${pageIndex == 1}">
                    <%@include file="setting-system-info.jsp" %>
                </c:if>
                <c:if test="${pageIndex == 2}">
                    <%@include file="setting-security.jsp" %>
                </c:if>
                <c:if test="${pageIndex == 3}">
                    <%@include file="setting-tel.jsp" %>
                </c:if>
                <c:if test="${pageIndex == 4}">
                    <%@include file="setting-email.jsp" %>
                </c:if>
                <c:if test="${pageIndex == 5}">
                    <%@include file="setting-liveness.jsp" %>
                </c:if>
            </div>
        </div>
    </div>
</div>
<%@include file="inc-footer.jsp" %>
