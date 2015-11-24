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
<div class="float-panel shadow activity-result" id="lotteryResult">
    <jsp:include page="commodity-float-panel-title.jsp">
        <jsp:param name="title" value="<span class='text'></span>"/>
    </jsp:include>
    <div class="body">
        <div class="activity">
            <div class="image"><img/></div>
            <div class="description break-word"></div>
        </div>
        <div class="create-number conceal">
            <button class="manual blue" type="button">手动选号</button>
            <button class="auto blue" type="button">自动选号</button>
        </div>
        <div class="manual-number-creator conceal">
            <c:forEach var="i" begin="1" end="3">
                <select name="numberPart${i}">
                    <%
                        for (int j = 1; j <= 33; j++) {
                            String optionValue = j < 10 ? "0" + j : "" + j;
                    %>
                    <option value="<%=optionValue%>"><%=optionValue%>
                    </option>
                    <%
                        }
                    %>
                </select>
            </c:forEach>
            <div class="submit">
                <button class="blue" type="button">确定</button>
            </div>
        </div>
        <div class="lot conceal">
            <div class="serial-number">
                您的抽奖号： <span class="number"><span></span><span></span><span></span></span>
            </div>
        </div>
        <%--<div class="lot">
            <button class="create-number">获取抽奖号</button>
            <div class="serial-number">
                我的抽奖号：<span class="number"></span>
            </div>
            <div class="success">
                参与成功，查看我的
                <a href="activity-history.html" target="_blank">抽奖历史</a>
            </div>
            <div class="tel">
                手机号码为中奖唯一联系方式，请正确
                <a href="setting.html?index=3" target="_blank">绑定手机</a>
            </div>
            <div class="insufficient-liveness">
                爱心数还不足<span class="min-liveness-to-participate"></span>，请继续努力
                <a href="setting.html?index=5" target="_blank">增加爱心数</a>
            </div>
        </div>--%>
        <div class="activity-expire conceal">
            活动已结束，敬请留意下次活动开始时间
            <%--本期抽奖已结束，请关注下期抽奖活动
            <a href="activity-history.html" target="_blank">抽奖结果请查看活动历史</a>--%>
        </div>
        <%--<div class="bottom">
            <jsp:include page="commodity-activity-share.jsp">
                <jsp:param name="title" value="分享到"/>
            </jsp:include>
        </div>--%>
    </div>
</div>
<%@include file="subscribe-float-panel.jsp" %>
<div class="float-panel shadow activity-result" id="seckillResult">
    <jsp:include page="commodity-float-panel-title.jsp">
        <jsp:param name="title" value="<span class='text'></span>"/>
    </jsp:include>
    <div class="body">
        <div class="activity">
            <div class="image"><img/></div>
            <div class="description break-word"></div>
        </div>
        <div class="lot">
            <div class="pokers">
                <c:forEach var="index" begin="1" end="3">
                    <div class="poker" data-options="index:1">
                        <img class="back delay" data-source="${pokerBackSide}"/>
                        <img class="front link delay" data-source="${pokerFrontSide}"/>
                    </div>
                </c:forEach>
            </div>
            <div class="success result">恭喜您，秒杀成功！</div>
            <div class="not-start fail result">秒杀尚未开始！</div>
            <div class="over fail result">很遗憾，速度了差一点，秒杀失败！</div>
            <div class="unknown fail result">出现未知错误！</div>
        </div>
        <div class="activity-expire">
            本期秒杀已结束，请关注下期秒杀活动
            <a href="activity-history.html" target="_blank">秒杀结果请查看活动历史</a>
        </div>
        <div class="remaining-time">
            距离活动开始
            <span class="day">00天</span>
            <span class="hour">00</span>:<span class="minute">00</span>:<span class="second">00</span>
        </div>
        <%--<div class="bottom">
            <jsp:include page="commodity-activity-share.jsp">
                <jsp:param name="title" value="告诉小伙伴一起抢"/>
            </jsp:include>
        </div>--%>
    </div>
</div>
<div class="float-panel shadow" id="exceptionPrompt">
    <jsp:include page="commodity-float-panel-title.jsp">
        <jsp:param name="title" value="提示信息"/>
    </jsp:include>
    <div class="body">
        <div class="info"></div>

        <div class="links">
            <a target="_blank" href="activity-history.html">查看我的活动历史</a>
        </div>
    </div>
</div>
<%@include file="inc-footer.jsp" %>
