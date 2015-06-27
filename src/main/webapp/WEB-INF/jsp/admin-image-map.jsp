<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@include file="inc-header.jsp" %>
<%@include file="index-header.jsp" %>
<div class="page-width shadow main-body">
    <div class="link-list">
        <div class="title">已添加链接<span class="comment">(注：在图中拖动鼠标画出矩形区域，即可添加新链接。单击已添加链接，图中会显示该链接的位置)</span></div>
        <div class="content">
            <c:forEach var="imageMap" items="${imageMaps}">
                <div data-options="id: ${imageMap.id}">
                    <div class="comment" title="单击可显示链接对应的区域">${imageMap.comment}</div>
                    <div class="edit-image" title="编辑链接"><img class="link" src="resources/css/images/pencil.png"/></div>
                    <div class="close-image" title="删除"><img class="link" src="resources/css/images/close.gif"/></div>
                    <input type="hidden" name="href" value="${imageMap.href}"/>
                </div>
            </c:forEach>
        </div>
    </div>
    <div class="image">
        <img src="${image}"/>

        <div class="image-cover"></div>
        <div id="rectangle"></div>
    </div>
</div>
<div id="linkInputDiv" class="shadow">
    <input type="hidden" id="mapId"/>

    <div>
        <label>目标链接</label>
        <input type="text" id="mapHref" class="form-control"/>
    </div>
    <div>
        <label>备注</label>
        <input type="text" id="mapComment" class="form-control" value="新建链接"/>
    </div>
    <div>
        <button class="btn btn-primary" id="addSubmit">确定</button>
        <button class="btn btn-default" id="addCancel">取消</button>
    </div>
</div>
<%@include file="inc-footer.jsp" %>
