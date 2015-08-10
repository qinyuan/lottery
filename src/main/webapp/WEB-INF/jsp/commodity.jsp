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
                <div class="name">${snapshot.name}</div>
                <div class="price"><c:if test="${!snapshot.inLottery}">${snapshot.price}元</c:if></div>
                <c:if test="${snapshot.inLottery}">
                    <div class="in-lottery-icon mediumTransparent" title="抽奖中"><img src="resources/css/images/commodity/in-lottery.png"/></div>
                </c:if>
            </div>
        </c:forEach>
        <div class="next lightTransparent"></div>
    </div>
    <!--<div class="detail">
        <div class="participant-count"><span class="participant-count"></span>人抽奖</div>
        <%--<img src="${commodity.detailImage}" usemap="#commodityMap"/>--%>
        <img usemap="#commodityMap"/>
    </div>-->
</div>
<div class="main-body">
    <%--<div class="detail" style="background-image:url('${commodity.backImage}')">--%>
    <div class="detail">
        <div class="participant-count"><span class="participant-count"></span>人抽奖</div>
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
<form class="float-panel" id="telInputForm">
    <jsp:include page="commodity-float-panel-title.jsp">
        <jsp:param name="title" value="补全信息"/>
    </jsp:include>
    <div class="body">
        <table>
            <tbody>
            <tr>
                <td class="title">手机号码</td>
                <td class="content">
                    <input style="width: 260px;" name="tel" type="text" class="form-control"
                           placeholder="在此输入11位数的手机号" maxlength="11"/>

                    <div class="comment">请输入常用手机号码，用于中奖后联系使用！</div>
                </td>
            </tr>
            <tr>
                <td class="title">验证码</td>
                <td class="content">
                    <jsp:include page="widget-identity-code.jsp">
                        <jsp:param name="placeholder" value="输入验证码"/>
                    </jsp:include>
                    <div class="comment">请输入图中的字母或数字，不区分大小写</div>
                </td>
            </tr>
            </tbody>
        </table>
        <div class="submit">
            <button class="green-submit-button" type="submit" name="ok">参与抽奖</button>
        </div>
    </div>
</form>
<div class="float-panel" id="noPrivilegePrompt">
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
<div class="float-panel" id="lotteryExceptionPrompt">
    <jsp:include page="commodity-float-panel-title.jsp">
        <jsp:param name="title" value="提示信息"/>
    </jsp:include>
    <div class="body">
        <div class="info"></div>

        <div class="links">
            <a target="_blank" href="lottery-history.html">查看我的抽奖历史</a>
        </div>
    </div>
</div>
<div class="float-panel" id="lotteryResult">
    <jsp:include page="commodity-float-panel-title.jsp">
        <jsp:param name="title" value="抽奖结果"/>
    </jsp:include>
    <div class="body">
        <div class="activity-info">
            <div class="participant-count">参与人数：<span></span></div>
            <div class="deadline">
                <span class="icon"></span>
                距活动结束还有：
                <span class="day">0</span>天
                <span class="hour">0</span>时
                <span class="minute">0</span>分
                <span class="second">0</span>秒
            </div>
        </div>
        <div class="my-lottery">
            <div class="number">
                <div class="text">我的抽奖号：</div>
                <div class="number-list"></div>
            </div>
            <div class="liveness">
                <div class="mine">
                    我的爱心<span class="icon"></span>：<span class="my-liveness"></span>
                </div>
                <div class="max">
                    最高爱心：<span class="max-liveness"></span>
                </div>
            </div>
        </div>
        <div class="prompt">
            <%--<div class="no-chance">
                您已抽过奖，需要通过积累爱心再次获取抽奖机会
            </div>--%>
            <div class="spread">
                您的爱心不足N，请努力传播！
                <div class="spread-method">
                    <a href="javascript:void(showLotteryRule('lotteryResult'))">如何提高爱心？</a>
                </div>
            </div>
        </div>
        <div class="lottery-again">
            <button class="green-submit-button" id="takeLotteryAgain">再抽一次</button>
            <span class="split"></span>
            <a href="javascript:void(showLotteryRule('lotteryResult'))">抽奖规则</a>
        </div>
        <div class="share">
            <a href="javascript:void(0)" target="_blank" class="sina"></a>
            <a href="javascript:void(0)" target="_blank" class="qq"></a>
            <a href="javascript:void(0)" target="_blank" class="qzone"></a>
        </div>
    </div>
</div>
<div class="float-panel" id="lotteryRule">
    <div class="title">抽奖规则<div class="close-icon"><span></span></div></div>
    <div class="body">${lotteryRule}</div>
    <div class="button"><button type="button">我已了解</button></div>
</div>
<%@include file="inc-footer.jsp" %>
