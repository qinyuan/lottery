<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@include file="inc-header.jsp" %>
<%@include file="index-header.jsp" %>
<div class="page-width form">
    <div>
        <table class="normal">
            <colgroup>
                <col class="select"/>
                <col class="index"/>
                <c:forEach var="alias" items="${table.aliases}">
                    <col class="${alias}"/>
                </c:forEach>
            </colgroup>
            <thead>
            <th>序号</th>
            <c:forEach var="head" items="${table.heads}" varStatus="status">
                <th class="${table.aliases[status.index]}">${head}
                    <c:if test="${status.index != 2}">
                        <div title="排序筛选" class="filter">
                            <button class="${table.headStyles[status.index]}"></button>
                        </div>
                    </c:if>
                </th>
            </c:forEach>
            </thead>
            <tbody>
            <c:forEach var="infoRecord" items="${paginationItems}" varStatus="status">
                <tr data-options="id:${infoRecord.id}">
                    <td>${status.index + rowStartIndex}</td>
                    <c:forEach var="col" items="${infoRecord.cols}" varStatus="innerStatus">
                        <td class="${table.aliases[innerStatus.index]}">${col}</td>
                    </c:forEach>
                </tr>
            </c:forEach>
            </tbody>
        </table>
        <%@include file="widget-pagination.jsp" %>
    </div>
</div>
<%@include file="inc-footer.jsp" %>
