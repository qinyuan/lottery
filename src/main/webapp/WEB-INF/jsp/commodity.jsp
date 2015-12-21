<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@include file="inc-header.jsp" %>
<%@include file="commodity-header.jsp" %>
<div class="main-body page-width">
    <div class="snapshots">
        <div class="prev lightTransparent"></div>
        <c:forEach var="snapshot" items="${snapshots}">
            <div class="snapshot" title="单击切换到该商品" data-options="id:${snapshot.id}">
                <div class="image" style="background-image: url('${snapshot.snapshot}')"></div>
                <c:choose>
                    <c:when test="${snapshot.inLottery || snapshot.inSeckill}">
                        <div class="name in-activity">${snapshot.name}</div>
                    </c:when>
                    <c:otherwise>
                        <div class="name">${snapshot.name}</div>
                        <div class="price">${snapshot.price}元</div>
                    </c:otherwise>
                </c:choose>
                <c:if test="${snapshot.inLottery}">
                    <div class="in-lottery-icon mediumTransparent" title="抽奖中"><img
                            src="resources/css/images/commodity/in-lottery.png"/></div>
                </c:if>
                <c:if test="${snapshot.inSeckill}">
                    <div class="in-seckill-icon mediumTransparent" title="准备秒杀"><img
                            src="resources/css/images/commodity/in-seckill.png"/></div>
                </c:if>
            </div>
        </c:forEach>
        <div class="next lightTransparent"></div>
    </div>
</div>
<div class="main-body">
    <div class="summary page-width">
        <div class="right">
            <div class="remaining-time">
                <div class="title">距离活动结束：</div>
                <div class="clock"></div>
            </div>
            <div class="participant-count">已参与人数：<span class="participant-count"></span>人</div>
        </div>
    </div>
    <div class="details">
    </div>
</div>
<div id="commodityMaps">
</div>
<q:handlebars-template id="mapTemplate">
    {{#each commodityMaps}}
    <area shape="rect" coords="{{xStart}},{{yStart}},{{xEnd}},{{yEnd}}" href="{{href}}" alt="{{comment}}"
          target="_blank"/>
    {{/each}}
</q:handlebars-template>
<%@include file="commodity-lottery-result-float-panel.jsp" %>
<%@include file="subscribe-float-panel.jsp" %>
<%--<%@include file="commodity-seckill-result-float-panel.jsp" %>--%>
<div class="float-panel shadow" id="exceptionPrompt">
    <jsp:include page="commodity-float-panel-title.jsp">
        <jsp:param name="title" value="消息提示"/>
    </jsp:include>
    <div class="body">
        <div class="info"></div>

        <div class="links">
            <a target="_blank" href="activity-history.html">查看我的活动历史</a>
        </div>
    </div>
</div>
<div class="float-panel shadow" id="noTelPrompt">
    <jsp:include page="commodity-float-panel-title.jsp">
        <jsp:param name="title" value="消息提示"/>
    </jsp:include>
    <div class="body">
        <div class="info">
            尊敬的<span class="username"></span><br/>
            您的支持数已超过<span class="no-tel-liveness"></span>，按照抽奖规则需正确填写手机号<br/>
            <a target="_blank" href="setting.html?index=3">立刻填写手机号 &gt;&gt;</a>
        </div>
        <div class="bottom">
            手机号一年内只可修改<span class="max-tel-modification-times"></span>次，请正确填写
        </div>
    </div>
</div>
<%@include file="inc-footer.jsp" %>
