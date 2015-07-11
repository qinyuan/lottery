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
            <button type="submit" name="ok">参与抽奖</button>
        </div>
    </div>
</form>
<div class="float-panel" id="noPrivilegePrompt">
    <jsp:include page="commodity-float-panel-title.jsp">
        <jsp:param name="title" value="提示信息"/>
    </jsp:include>
    <div class="body">
        <h4>对不起，您当前的帐户没有权限参与抽奖</h4>

        <div>
            您可以：
            <a class="toLogin" href="javascript:void(0)">切换登录帐号</a>
            或
            <a href="j_spring_security_logout">直接退出</a>
        </div>
    </div>
</div>
<%@include file="inc-footer.jsp" %>
