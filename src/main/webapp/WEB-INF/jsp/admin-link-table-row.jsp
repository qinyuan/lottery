<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@include file="inc-taglib.jsp" %>
<tr>
    <td><input type="type" name="headerLinkTitles" class="form-control" value="${param.title}"/></td>
    <td><input type="type" name="headerLinkHrefs" class="form-control" value="${param.href}"/></td>
    <td>
        <img class="link" title="上移" onclick="return rankUpLink(this);"
             src="resources/css/images/arrow_up.png"/>
        <img class="link" title="下移" onclick="return rankDownLink(this);"
             src="resources/css/images/arrow_down.png"/>
        <img class="link" title="删除" onclick="return deleteLink(this);"
             src="resources/css/images/delete.png"/>
    </td>
</tr>
