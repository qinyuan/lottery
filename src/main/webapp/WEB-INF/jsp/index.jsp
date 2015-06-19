<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@include file="inc-header.jsp" %>
<%@include file="index-header.jsp" %>
<div class="posters">
    <script>var indexImageGroups = {};</script>
    <c:forEach var="indexImageGroup" items="${indexImageGroups}">
        <c:set var="indexImages" value="${indexImageGroup.indexImages}"/>
        <c:set var="firstIndexImage" value="${indexImages[0]}"/>
        <div class="poster" style='background-image: url("${firstIndexImage.backPath}");'>
            <div class="image page-width">
                <img src="${firstIndexImage.path}" usemap="#indexMap${firstIndexImage.id}"/>
            </div>
        </div>
        <c:if test="${fn:length(indexImages)>1}">
            <c:forEach var="indexImage" items="${indexImages}" varStatus="status">
                <script>
                    <c:if test="${status.index==0}">
                    indexImageGroups["${indexImageGroup.rowIndex}"] = [];
                    </c:if>
                    indexImageGroups["${indexImageGroup.rowIndex}"].push({id:${indexImage.id}, path: "${indexImage.path}", backPath: "${indexImage.backPath}"});
                </script>
            </c:forEach>
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
