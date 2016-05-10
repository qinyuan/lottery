<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@include file="inc-header.jsp" %>
<%@include file="register-header.jsp" %>
<div class="main-body">
    <div class="images">
        <c:if test="${subIndexImageGroup != null}">
            <c:forEach var="image" items="${subIndexImageGroup.subIndexImages}" varStatus="status">
                <div class="image"<c:if
                        test="${image.backPath != null}"> style="background-image:url('${image.backPath}')"</c:if>>
                    <div class="page-width">
                        <img src="${image.path}" usemap="#imageMap${status.index}"/>
                    </div>
                </div>
            </c:forEach>
        </c:if>
    </div>
</div>
<div id="imageMaps">
    <c:forEach var="imageMap" items="${maps}" varStatus="status">
        <map name="imageMap${status.index}" id="imageMap${status.index}">
            <c:forEach var="mapItem" items="${imageMap}">
                <area shape="rect" coords="${mapItem.xStart},${mapItem.yStart},${mapItem.xEnd},${mapItem.yEnd}"
                      href="${mapItem.href}" alt="${mapItem.comment}" target="_blank"/>
            </c:forEach>
        </map>
    </c:forEach>
</div>
<%@include file="inc-footer.jsp" %>
