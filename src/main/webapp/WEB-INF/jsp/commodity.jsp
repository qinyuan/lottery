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
        <div class="prev lightTransparent"></div>
        <c:forEach var="snapshot" items="${snapshots}">
            <div class="snapshot" title="单击切换到该商品" data-options="id:${snapshot.id}">
                <div class="image" style="background-image: url('${snapshot.snapshot}')"></div>
                <div class="name">${snapshot.name}</div>
                <div class="price">${snapshot.price}元</div>
            </div>
        </c:forEach>
        <div class="next lightTransparent"></div>
    </div>
    <div class="detail">
        <img src="${commodity.detailImage}" usemap="#commodityMap"/>
    </div>
</div>
<map name="commodityMap" id="commodityMap"></map>
<q:handlebars-template id="mapTemplate">
    {{#each commodityMaps}}
    <area shape="rect" coords="{{xStart}},{{yStart}},{{xEnd}},{{yEnd}}" href="{{href}}" alt="{{comment}}"
          target="_blank"/>
    {{/each}}
</q:handlebars-template>
<%@include file="inc-footer.jsp" %>
