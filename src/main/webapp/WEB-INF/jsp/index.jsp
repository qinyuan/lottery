<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@include file="inc-header.jsp" %>
<%@include file="index-header.jsp" %>
<div class="posters">
    <c:forEach var="indexImageGroup" items="${indexImageGroups}">
        <c:set var="indexImages" value="${indexImageGroup.indexImages}"/>
        <div class="poster" style='background-image: url("${indexImages[0].backPath}");'>
            <div class="image page-width"><img src="${indexImages[0].path}"/></div>
        </div>
    </c:forEach>
</div>
<%@include file="inc-footer.jsp" %>
