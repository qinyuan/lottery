<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@include file="inc-header.jsp" %>
<%@include file="index-header.jsp" %>
<div class="page-width form">
    <div>
        <div class="add">
            <button class="btn btn-success" type="button">添加新商品</button>
        </div>
        <table class="normal">
            <thead>
            <th>序号</th>
            <th>名称</th>
            <th>价格</th>
            <th>缩略图</th>
            <th>是否显示</th>
            <th></th>
            </thead>
            <tbody>
            <c:forEach var="commodity" items="${commodities}" varStatus="status">
                <tr data-options="id:${commodity.id}">
                    <c:set var="rankIndex" value="${status.index + rowStartIndex}"/>
                    <td class="index">
                        <div data-options="rankIndex:${rankIndex}">${rankIndex}
                            <img title="编辑" class="link" src="resources/css/images/note_edit.png">
                        </div>
                    </td>
                    <td class="name">${commodity.name}</td>
                    <td class="price">${commodity.price}</td>
                    <td class="snapshot">
                        <a href="${commodity.snapshot}" target="_blank" title="单击预览">
                            <img src="${commodity.snapshot}" onload="adjustImage(this, 100, 100);"/>
                        </a>
                    </td>
                        <%--<td class="detailImage">
                            <a href="${commodity.detailImage}" target="_blank" title="单击预览">
                                <img src="${commodity.detailImage}" onload="adjustImage(this, 100, 100);"/></a>
                        </td>
                        <td class="backImage">
                            <c:choose>
                                <c:when test="${commodity.backImage != null && fn:length(commodity.backImage) > 0}">
                                    <a href="${commodity.backImage}" target="_blank" title="单击预览">
                                        <img src="${commodity.backImage}" onload="adjustImage(this, 100, 100);"/></a>
                                </c:when>
                                <c:otherwise><span class="no-back">(无)</span></c:otherwise>
                            </c:choose>
                        </td>--%>
                    <td class="visible">
                        <div class="switch switch-mini" data-on-label="是" data-off-label="否">
                            <input type="checkbox"<c:if test="${commodity.visible}"> checked</c:if>/>
                        </div>
                    </td>
                    <td class="action">
                        <jsp:include page="widget-ranking.jsp"/>
                        <a href="commodity.html?id=${commodity.id}" target="_blank"><img
                                title="预览" src="resources/css/images/preview.gif"/></a>
                        <a href="admin-commodity-link.html?id=${commodity.id}" target="_blank"><img
                                title="编辑图片上的链接" src="resources/css/images/link.png"/></a>
                        <jsp:include page="widget-edit-delete.jsp"/>
                    </td>
                </tr>
            </c:forEach>
            </tbody>
        </table>
        <%@include file="widget-pagination.jsp" %>
    </div>
</div>
<%@include file="admin-commodity-add-edit-form.jsp" %>
<%@include file="inc-footer.jsp" %>
