<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<div class="fetch-qq-info">
    <span class="waiting">正在获取个人信息 <span id="fetchInfoWaiting"></span></span>
    <span class="error">获取个人信息失败！！！</span>
</div>
<div class="qq-account-created">
    <span>您已经创建过帐号，现在可以： </span>
    <a href="index.html">转到主页</a>
</div>
<div class="form-wrapper">
    <%@include file="register-form.jsp" %>
</div>
<script type="text/javascript" src="http://qzonestyle.gtimg.cn/qzone/openapi/qc_loader.js"
        data-appid="<%=AppConfig.getQQConnectAppId()%>" data-redirecturi="<%=AppConfig.getQQConnectRedirectURI()%>"
        charset="utf-8"></script>
