<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@include file="inc-header.jsp" %>
<%@include file="index-header.jsp" %>
<div class="page-width form">
    <div>
        <div>
            <c:forEach var="indexImageGroup" items="${indexImageGroups}">
                <div class="image-row">
                    <c:forEach var="indexImage" items="${indexImageGroup.indexImages}">
                        <div class="image">
                            <input type="hidden" value="${indexImage.backPath}"/>
                                <a href="${indexImage.path}" target="_blank" title="单击打开">
                                    <img src="${indexImage.path}" onload="adjustImageHeight(this, 100);"/>
                                </a>
                            <button type="button" data-options="id: ${indexImage.id}"
                                    class="btn btn-xs btn-primary edit-image">编辑图片
                            </button>
                            <a href="admin-index-image-link.html?id=${indexImage.id}" target="_blank">
                                <button type="button" class="btn btn-xs btn-primary edit-link">编辑链接</button>
                            </a>
                            <button type="button" data-options="id: ${indexImage.id}"
                                    class="btn btn-xs btn-danger delete-image">删除
                            </button>
                        </div>
                    </c:forEach>
                    <div class="cycle">
                        <button type="button" data-options="rowIndex: ${indexImageGroup.rowIndex}"
                                class="btn btn-xs btn-success add-cycle-image">添加循环图片
                        </button>
                    </div>
                </div>
            </c:forEach>
        </div>
        <div style="padding: 10px 0;position: relative">
            <button type="button" id="addImage" class="btn btn-xs btn-success">添加图片</button>
            <div class="cycle-interval">
                <div>循环时间间隔(秒):</div>
                <div>
                    <input id="cycleIntervalInput" type="text" class="form-control" value="${cycleInterval}"/>
                </div>
                <div></div>
            </div>
        </div>
    </div>
</div>
<q:multipart-form id="addImageForm" action="admin-index-add-image">
    <input type="hidden" name="id"/>
    <input type="hidden" name="rowIndex"/>

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
    <div class="buttonDiv">
        <button type="submit" id="addImageSubmit" class="btn btn-success">确定</button>
        <button type="button" id="addImageCancel" class="btn btn-default">取消</button>
    </div>
</q:multipart-form>
<%@include file="inc-footer.jsp" %>
