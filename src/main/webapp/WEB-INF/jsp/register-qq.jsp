<%@ page import="com.qinyuan15.lottery.mvc.AppConfig" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<div class="fetch-qq-info">
    <span class="waiting">正在获取个人信息 <span id="fetchInfoWaiting"></span></span>
    <span class="error">获取个人信息失败！！！</span>
</div>
<script type="text/javascript" src="http://qzonestyle.gtimg.cn/qzone/openapi/qc_loader.js"
        data-appid="<%=AppConfig.getQQConnectAppId()%>" data-redirecturi="<%=AppConfig.getQQConnectRedirectURI()%>"
        charset="utf-8"></script>
