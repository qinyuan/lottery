<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@include file="inc-taglib.jsp" %>
<div class="help-list"<c:if test="${helpGroup.id != null}"> data-options="helpGroupId:${helpGroup.id}"</c:if>>
    <div class="title">
        <span class="icon"></span>
        <span class="content">${helpGroup.title == null ? '{{helpGroupTitle}}' : helpGroup.title }</span>
        <c:if test="${editMode}">
            <div class="action">
                <img class="link up" title="上移" onclick="rankUpHelpGroup(this);"
                     src="resources/css/images/arrow_up.png"/>
                <img class="link down" title="下移" onclick="rankDownHelpGroup(this);"
                     src="resources/css/images/arrow_down.png"/>
                <img class="link edit" title="编辑" onclick="editHelpGroup(this);"
                     src="resources/css/images/pencil.png"/>
                <img class="link delete" title="删除" onclick="deleteHelpGroup(this);"
                     src="resources/css/images/delete.png"/>
            </div>
        </c:if>
    </div>
    <ul>
        <c:forEach var="helpItem" items="${helpGroup.items}">
            <li data-options="helpItemId: ${helpItem.id}">
                <a href="#${helpItem.id}">${helpItem.title}</a>
                <c:if test="${editMode}">
                    <div class="action">
                        <img class="link up" title="上移" onclick="rankUpHelpItem(this);"
                             src="resources/css/images/arrow_up.png"/>
                        <img class="link down" title="下移" onclick="rankDownHelpItem(this);"
                             src="resources/css/images/arrow_down.png"/>
                        <img class="link edit" title="编辑" onclick="editHelpItem(this);"
                             src="resources/css/images/pencil.png"/>
                        <img class="link delete" title="删除" onclick="deleteHelpItem(this);"
                             src="resources/css/images/delete.png"/>
                    </div>
                </c:if>
            </li>
        </c:forEach>
    </ul>
    <c:if test="${editMode}">
        <div class="add-help-item">
            <img class="link" title="添加条目" onclick="addHelpItem(this);" src="resources/css/images/add.png"/>
        </div>
    </c:if>
</div>
