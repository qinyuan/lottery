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
                <%--<div class="title">剩余时间：</div>--%>
                <div class="title">距离活动结束：</div>
                <div class="clock"></div>
            </div>
            <div class="participant-count">已参与人数：<span class="participant-count"></span></div>
        </div>
    </div>
    <div class="detail">
        <!--<div class="participant-count">已参与人数：<span class="participant-count"></span></div>-->
        <img usemap="#commodityMap"/>
    </div>
</div>
<map name="commodityMap" id="commodityMap"></map>
<q:handlebars-template id="mapTemplate">
    {{#each commodityMaps}}
    <area shape="rect" coords="{{xStart}},{{yStart}},{{xEnd}},{{yEnd}}" href="{{href}}" alt="{{comment}}"
          target="_blank"/>
    {{/each}}
</q:handlebars-template>
<div class="float-panel shadow" id="noPrivilegePrompt">
    <jsp:include page="commodity-float-panel-title.jsp">
        <jsp:param name="title" value="提示信息"/>
    </jsp:include>
    <div class="body">
        <h4>对不起，您当前的帐户没有权限参与抽奖！</h4>

        <div>
            您可以：
            <a class="toLogin" href="javascript:void(0)">切换登录帐号</a>
            或
            <a href="j_spring_security_logout">直接退出</a>
        </div>
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
<div class="float-panel shadow activity-result" id="lotteryResult">
    <jsp:include page="commodity-float-panel-title.jsp">
        <jsp:param name="title" value="<span class='text'></span>"/>
    </jsp:include>
    <div class="body">
        <%--<div class="remind-me top">
            <span class="update-fail"></span>
            <span class="update-success">更新成功</span>
            <input type="checkbox" name="remindMe"/>
            开启抽奖邮件提醒
        </div>--%>
        <div class="activity">
            <div class="image"><img/></div>
            <div class="description break-word"></div>
        </div>
        <div class="lot">
            <div class="tel">
                联系手机号码：<input type="text" class="form-control" name="tel" maxlength="11"/>

                <div class="comment">
                    手机号码是兑奖的唯一依据，请填写正确的号码
                </div>
                <div class="modify"><a href="javascript:void(0)">修改联系号码 ></a></div>
                <div class="validate-error"></div>
                <div class="buttons">
                    <div class="submit">
                        <button type="submit" class="btn btn-success ok">确定</button>
                        <button type="button" class="btn btn-default cancel">取消</button>
                    </div>
                    <div class="conflict">
                        <button type="button" class="btn btn-default clear">重新输入</button>
                        <a href="${telValidateDescriptionPage ? 'javascript:void(0)' : telValidateDescriptionPage}"
                           target="_blank" class="validate">
                            <button type="button" class="btn btn-default validate">验证</button>
                        </a>
                    </div>
                </div>
            </div>
            <div class="liveness">爱心数：<span class="liveness"></span><span class="icon lightTransparent"></span></div>
            <div class="serial">抽奖号：<span class="serial"></span></div>
        </div>
        <div class="insufficient-liveness">
            您的爱心数不足，无法参与抽奖！！！
            <div class="status">
                参加活动需要的爱心为：
                <span class="min-liveness-to-participate"></span>
                ，您目前的爱心为：
                <span class="liveness"></span>
            </div>
        </div>
        <div class="activity-expire">
            本期抽奖已结束，请关注下期抽奖活动
            <a href="activity-history.html" target="_blank">抽奖结果请查看活动历史</a>
        </div>
        <div class="bottom">
            <jsp:include page="commodity-activity-share.jsp">
                <jsp:param name="title" value="分享到"/>
            </jsp:include>
            <div class="rule">
                <a href="javascript:void(0)">抽奖规则>>></a>
            </div>
        </div>
    </div>
</div>

<div class="float-panel shadow info" id="lotteryRule">
    <div class="title">抽奖规则
        <div class="close-icon"><span></span></div>
    </div>
    <div class="body">${lotteryRule}</div>
    <div class="button">
        <button type="button">我已了解</button>
    </div>
</div>

<%@include file="subscribe-float-panel.jsp"%>

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
                        <img class="back" src="${pokerBackSide}"/>
                        <img class="front link" src="${pokerFrontSide}"/>
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
        <div class="bottom">
            <jsp:include page="commodity-activity-share.jsp">
                <jsp:param name="title" value="告诉小伙伴一起抢"/>
            </jsp:include>
        </div>
    </div>
</div>
<%@include file="inc-footer.jsp" %>
