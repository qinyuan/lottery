<%@ page import="com.qinyuan15.lottery.mvc.config.AppConfig" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<c:if test="${!noFooter}">
    <div class="footer-poster">
        <div class="page-width" style="background-image: url('${footerPoster}');"></div>
    </div>
</c:if>
<div class="${whiteFooter ? 'footer white' : 'footer'}">
    <span>${footerText}</span>
</div>
<%@include file="inc-register-login-panel.jsp" %>
</body>
<c:choose><c:when test="<%=AppConfig.props.isOffline()%>">
    <q:js src="resources/js/lib/jquery-1.11.3.min"/>
</c:when><c:otherwise>
    <q:js src="<%=CDNSource.JQUERY_JS%>"/>
</c:otherwise></c:choose>
<q:js src="lib/jquery.url"/>
<q:js src="lib/jquery.cookie"/>
<q:js src="lib/jquery-form-3.51.0"/>
<q:js src="lib/underscore-min"/>
<q:js src="lib/jsutils/jsutils" version="true"/>
<q:js src="common" version="true"/>
<%---
<!--[if IE]>
<script src="resources/js/lib/angular/html5shiv.js"></script>
<script src="resources/js/lib/angular/json2.js"></script>
<script src="resources/js/lib/ie-patch.js"></script>
<![endif]-->
--%>
<c:forEach var="js" items="${moreJs}"><q:js src="${js.href}" version="${js.version}"/></c:forEach>
<!--[if IE]>
<c:forEach var="js" items="${ieJs}"><q:js src="${js.href}" version="${js.version}"/></c:forEach>
<![endif]-->
</html>
