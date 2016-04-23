<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@include file="inc-taglib.jsp" %>
<table class="normal">
    <thead>
    <tr>
        <th class="index">序号</th>
        <th class="image">前景图</th>
        <th class="back-image">背景图</th>
        <th class="action">操作</th>
    </tr>
    </thead>
    <tbody>
    <c:if test="${subIndexImageGroup != null}">
        <c:forEach var="image" items="${subIndexImageGroup.subIndexImages}" varStatus="status">
            <tr data-id="${image.id}">
                <td class="index">${status.index + 1}</td>
                <td class="image">
                    <c:if test="${image.path != null}">
                        <a href="${image.path}" target="_blank" title="单击预览">
                            <img src="${image.path}" onload="adjustImage(this, 200, 200);"/>
                        </a>
                    </c:if>
                    <c:if test="${image.path == null}">
                        <span class="no-image">(无)</span>
                    </c:if>
                </td>
                <td class="back-image">
                    <c:if test="${image.backPath != null}">
                        <a href="${image.backPath}" target="_blank" title="单击预览">
                            <img src="${image.backPath}" onload="adjustImage(this, 200, 200);"/>
                        </a>
                    </c:if>
                    <c:if test="${image.backPath == null}">
                        <span class="no-image">(无)</span>
                    </c:if>
                </td>
                <td class="action">
                    <jsp:include page="widget-ranking.jsp"/>
                    <a href="admin-sub-index-link.html?id=${image.id}" target="_blank"><img
                            title="编辑图片上的链接" src="resources/css/images/link.png"/></a>
                    <jsp:include page="widget-edit-delete.jsp"/>
                </td>
            </tr>
        </c:forEach>
    </c:if>
    </tbody>
</table>
<button class="btn btn-success btn-xs add-image">添加</button>
