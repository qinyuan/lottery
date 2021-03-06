<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<form id="seckillActivityForm" class="float-panel shadow activity">
    <c:choose>
        <c:when test="${fn:length(allCommodities)>0}">
            <input type="hidden" name="id"/>
            <table>
                <tbody>
                <tr>
                    <td class="title">期数<span class="required">*</span></td>
                    <td class="input">
                        第<input style="width:160px;margin:0 8px;" type="text" name="term" class="form-control"
                                value="${nextTerm}" placeholder="数字格式，如'123'等"/>期
                    </td>
                    <td class="title">开始时间<span class="required">*</span></td>
                    <td class="content" style="width:300px;">
                        <input type="text" name="startTime" class="form-control" value="${defaultStartTime}"
                               placeholder="格式如'2015-01-01 09:00:00'"/>
                    </td>
                </tr>
                <tr>
                    <td class="title">奖品<span class="required">*</span></td>
                    <td class="input">
                        <c:set var="selectFormItems" value="${allCommodities}"/>
                        <c:set var="selectFormId" value="commodity-select"/>
                        <c:set var="selectFormName" value="commodityId"/>
                        <%@include file="widget-select-form.jsp" %>
                    </td>

                    <td class="title">预计参加人数</td>
                    <td class="content">
                        <input type="text" name="expectParticipantCount" class="form-control"
                               value="1000" placeholder="输入预计参数人数"/>
                    </td>
                </tr>
                <tr>
                    <td class="title">获胜者</td>
                    <td class="content">
                        <input type="text" name="winners" class="form-control" placeholder="输入虚拟的最终获胜者"/>
                    </td>
                    <%@include file="admin-activity-description-input.jsp" %>
                </tr>
                </tbody>
            </table>
            <div class="submit">
                <button type="submit" class="btn btn-primary ok">确定</button>
                <button type="button" class="btn btn-cancel cancel">取消</button>
                <span style="color: #4EB13F">注：带“<span class="required">*</span>”的为必填项</span>
            </div>
        </c:when>
        <c:otherwise>
            <h4>尚未添加商品，故无法添加秒杀活动</h4>
        </c:otherwise>
    </c:choose>
</form>
