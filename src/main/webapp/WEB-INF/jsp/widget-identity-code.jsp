<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@include file="inc-taglib.jsp" %>
<input name="identityCode" type="text" class="form-control identity-code"
       <c:if test="${param.placeholder != null}">placeholder="输入验证码" </c:if>maxlength="4" <c:if
        test="${param.tabindex != null}"> tabindex="5"</c:if>/>
<img style="height:34px;width:98px;margin-bottom:3px;" class="link identity-code" src="identity-code"
     title="单击刷新"/><a href="javascript:void(0)">换一张</a>