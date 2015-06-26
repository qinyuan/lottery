<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@include file="inc-taglib.jsp" %>
<!DOCTYPE html>
<html>
<head lang="zh-ch">
    <meta charset="UTF-8">
    <meta http-equiv="Content-Type" content="text/html;charset=utf-8">
    <meta name="renderer" content="webkit">
    <meta http-equiv="X-UA-Compatible" content="IE=Edge,chrome=1">
    <title>${title}</title>
    <%--
    <meta name="keywords" content="<%=seoKeyword==null?"":seoKeyword.getWord()%>">
    <meta name="description" content="<%=seoKeyword==null?"":seoKeyword.getDescription()%>">
    --%>
    <c:if test="${favicon != null}">
        <link rel="icon" href="${favicon}" type="image/x-icon"/>
        <link rel="shortcut icon" href="${favicon}" type="image/x-icon"/>
    </c:if>
    <q:css href="resources/js/lib/bootstrap/css/bootstrap.min"/>
    <q:css href="common" version="true"/>
    <c:forEach var="css" items="${moreCss}"><q:css href="${css.href}" version="${css.version}"/></c:forEach>
    <c:forEach var="js" items="${headJs}"><q:js src="${js.href}" version="${js.version}"/></c:forEach>
</head>
<c:if test="${javascriptDatas != null}">
<script>
    <c:forEach var="entry" items="${javascriptDatas}">
    var ${entry.key}=${entry.value};
    </c:forEach>
</script>
</c:if>
<body class="ng-app:main" ng-app="main" id="ng-app">
