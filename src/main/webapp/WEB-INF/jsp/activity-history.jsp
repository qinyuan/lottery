<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@include file="inc-header.jsp" %>
<%@include file="index-header.jsp" %>
<div class="gray-back">
    <div class="page-width main-body">
        <table class="normal">
            <colgroup>
                <col class="index"/>
                <c:forEach var="alias" items="${lotteryHistoryTable.aliases}">
                    <col class="${alias}"/>
                </c:forEach>
            </colgroup>
            <thead>
            <th>序号</th>
            <c:forEach var="head" items="${lotteryHistoryTable.heads}" varStatus="status">
                <th class="${lotteryHistoryTable.aliases[status.index]}">${head}</th>
            </c:forEach>
            </thead>
            <tbody>
            <c:forEach var="history" items="${lotteryHistories}" varStatus="status">
                <tr data-options="id:${history.id}">
                    <td>${status.index + rowStartIndex}</td>
                    <c:forEach var="col" items="${history.cols}" varStatus="innerStatus">
                        <td class="${lotteryHistoryTable.aliases[innerStatus.index]}">${col}</td>
                    </c:forEach>
                </tr>
            </c:forEach>
            </tbody>
        </table>
        <%@include file="widget-pagination.jsp" %>
    </div>
</div>
<%@include file="inc-footer.jsp" %>
