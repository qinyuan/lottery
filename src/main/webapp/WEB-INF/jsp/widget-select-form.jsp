<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@include file="inc-taglib.jsp" %>
<div class="dropdown<c:if test="${selectFormId != null}"> ${selectFormId}</c:if>">
    <input type="hidden" name="${selectFormName}" value="${selectFormItems[0].id}"/>
    <button class="btn btn-default dropdown-toggle" type="button"
            data-toggle="dropdown" aria-expanded="true">
        ${selectFormItems[0].value}
        <span class="caret"></span>
    </button>
    <ul class="dropdown-menu" role="menu" aria-labelledby="commodityDropdownMenu">
        <c:forEach var="selectFormItem" items="${selectFormItems}">
            <li role="presentation"><a role="menuitem" tabindex="-1" data-options="id: ${selectFormItem.id}"
                                       href="javascript:void(0)">${selectFormItem.value}</a></li>
        </c:forEach>
    </ul>
</div>
