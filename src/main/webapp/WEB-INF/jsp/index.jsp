<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@include file="inc-header.jsp" %>
<%@include file="index-header.jsp" %>
<div class="posters">
    <script>var indexImageGroups = {};</script>
    <c:forEach var="indexImageGroup" items="${indexImageGroups}">
        <c:set var="indexImages" value="${indexImageGroup.indexImages}"/>
        <c:set var="firstIndexImage" value="${indexImages[0]}"/>
        <c:set var="multiImage" value="${fn:length(indexImages)>1}"/>
        <div class="poster" data-options="rowIndex:${indexImageGroup.rowIndex}">
            <div class="background" <c:if
                    test="${firstIndexImage.backPath != null}"> style='background-image:url("${firstIndexImage.backPath}");'</c:if>>
                <div class="image page-width">
                    <img src="${firstIndexImage.path}" usemap="#indexMap${firstIndexImage.id}"/>
                </div>
            </div>
            <c:if test="${multiImage}">
                <div class="dots">
                    <c:forEach var="indexImage" items="${indexImages}" varStatus="status">
                        <div class="dot<c:if test="${status.index == 0}"> selected</c:if>"
                             data-options="index:${status.index}"></div>
                    </c:forEach>
                </div>
            </c:if>
        </div>
        <c:if test="${multiImage}">
            <script>
                <c:forEach var="indexImage" items="${indexImages}" varStatus="status">
                <c:if test="${status.index==0}">
                indexImageGroups["${indexImageGroup.rowIndex}"] = [];
                </c:if>
                indexImageGroups["${indexImageGroup.rowIndex}"].push({index:${status.index}, id:${indexImage.id}, path: "${indexImage.path}", backPath: "${indexImage.backPath}"});
                </c:forEach>
            </script>
        </c:if>
    </c:forEach>
</div>
<c:forEach var="entry" items="${indexImageMaps}">
    <map name="indexMap${entry.key}">
        <c:forEach var="imageMap" items="${entry.value}">
            <area shape="rect" coords="${imageMap.xStart},${imageMap.yStart},${imageMap.xEnd},${imageMap.yEnd}"
                  href="${imageMap.href}" target="_blank" alt="${imageMap.comment}"/>
        </c:forEach>
    </map>
</c:forEach>
<%@include file="inc-footer.jsp" %>
