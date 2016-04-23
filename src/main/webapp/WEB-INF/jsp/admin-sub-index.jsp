<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@include file="inc-header.jsp" %>
<%@include file="index-header.jsp" %>
<div class="page-width form">
    <div>
        <div>
            <div class="sub-index" data-options="index:1">
                <div class="title">affiliate.html图片设置</div>
                <c:set var="subIndexImageGroup" value="${subIndexImageGroup1}"/>
                <%@include file="admin-sub-index-table.jsp" %>
            </div>
            <div class="sub-index" data-options="index:2">
                <div class="title">join.html图片设置</div>
                <c:set var="subIndexImageGroup" value="${subIndexImageGroup2}"/>
                <%@include file="admin-sub-index-table.jsp" %>
            </div>
        </div>
    </div>
</div>
<q:multipart-form id="addImageForm" action="admin-sub-index-add-image" cssClass="float-panel shadow">
    <input type="hidden" name="id"/>
    <input type="hidden" name="pageIndex"/>

    <div>
        <div class="title">前景图片</div>
        <div class="input">
            <jsp:include page="widget-upload-image.jsp">
                <jsp:param name="id" value="image"/>
            </jsp:include>
        </div>
    </div>
    <div>
        <div class="title">背景图片</div>
        <div class="input">
            <jsp:include page="widget-upload-image.jsp">
                <jsp:param name="id" value="backImage"/>
            </jsp:include>
        </div>
    </div>
    <div class="buttons">
        <button type="submit" id="addImageSubmit" class="btn btn-success ok">确定</button>
        <button type="button" id="addImageCancel" class="btn btn-default cancel">取消</button>
    </div>
</q:multipart-form>
<%@include file="inc-footer.jsp" %>
