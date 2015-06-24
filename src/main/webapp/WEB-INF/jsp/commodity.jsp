<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@include file="inc-header.jsp" %>
<div class="header">
    <div class="back"></div>
    <div class="content page-width">
        <div class="left-logo">
            <a href="index.html" style=""><img src="${commodityHeaderLeftLogo}"/></a>
        </div>
        <div class="right-navigation">
            <%@include file="security-navigation.jsp" %>
        </div>
    </div>
</div>
<div class="body page-width">
    <div class="snapshots">
        <c:forEach var="snapshot" items="${snapshots}">
            <div class="snapshot" title="单击切换到该商品" data-options="id:${snapshot.id}">
                <div class="image" style="background-image: url('${snapshot.snapshot}')"></div>
                <div class="name">${snapshot.name}</div>
                <div class="price">${snapshot.price}元</div>
            </div>
        </c:forEach>
    </div>
    <div class="detail">
        <img src="${commodity.detailImage}"/>
    </div>
</div>
<%@include file="inc-footer.jsp" %>
