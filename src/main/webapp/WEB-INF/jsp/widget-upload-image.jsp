<%@ page import="java.util.Random" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@include file="inc-taglib.jsp" %>
<input type="text" id="${param.id}" name="${param.id}" class="form-control" style="float:left;width:200px;"
       placeholder="输入图片链接或上传图片"
       <c:if test="${param.value!=null}">value="${param.value}"</c:if>  />
<input type="file" id="${param.id}File" name="${param.id}File" style="display:block;float:left;" tabindex="-1"/>
<c:if test="${param.snapshot && param.value!=null && fn:length(param.value)>0}">
    <a target="_blank" href="${param.value}?t=<%=new Random().nextInt()%>" title="单击预览">
        <img src="${param.value}" onload="adjustImage(this, 250, 200);"/>
    </a>
</c:if>
