<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@include file="inc-taglib.jsp" %>
<c:if test="${activity.serials != null}">抽奖号：${activity.serials}
    <c:if test="${activity.invalid}"><span style="color:#ff0000">(无效)</span></c:if><br/></c:if>
