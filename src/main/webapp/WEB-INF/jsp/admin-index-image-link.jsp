<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@include file="inc-header.jsp" %>
<%@include file="index-header.jsp" %>

<div class="page-width shadow main-body">
    <div class="link-list">
        <div class="title">已添加链接<span class="comment">(注：在图中画出矩形区域，即可添加新链接。单击已添加链接，图中会显示该链接的位置)</span></div>
        <div class="content">
            <c:forEach var="indexImageMap" items="${indexImageMaps}">
                <div>
                    <div class="comment">${indexImageMap.comment}</div>
                    <div class="close-image"><img class="link" src="resources/css/images/close.gif"/></div>
                </div>
            </c:forEach>
        </div>
    </div>
    <div class="image">
        <img src="${indexImage.path}"/>
    </div>
</div>

<%@include file="inc-footer.jsp" %>
