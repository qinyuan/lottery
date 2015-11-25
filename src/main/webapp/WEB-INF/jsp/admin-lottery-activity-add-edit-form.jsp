<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<form id="lotteryActivityForm" class="float-panel shadow activity">
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
                        <input type="text" name="startTime" class="form-control"
                               placeholder="格式如'2015-01-01 09:00:00'" disabled/>
                        <span style="padding-left:10px;">
                            <input type="checkbox" name="autoStartTime" tabindex="-1" checked>自动生成
                        </span>
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
                    <td class="title">绑定双色球<span class="required">*</span></td>
                    <td class="content">
                        第<input style="width:160px;margin:0 8px;" type="text" name="dualColoredBallTerm"
                                class="form-control" value="${nextDualColoredBallTerm}"
                                maxlength="7" placeholder="格式如'2015077'等"/>期
                    </td>
                </tr>
                <tr>
                    <td class="title">预计结束时间<span class="required">*</span></td>
                    <td class="content">
                        <input type="text" name="expectEndTime" class="form-control"
                               placeholder="格式如'2015-03-03 19:00:00'"/>
                    </td>
                    <td class="title">页面关闭时间</td>
                    <td class="content">
                        <input type="text" name="closeTime" class="form-control"
                               placeholder="格式如'2015-03-03 19:00:00'"/>
                    </td>

                </tr>
                <tr class="liveness">
                    <td class="title">最大爱心数</td>
                    <td class="content">
                        <input type="text" name="virtualLiveness" class="form-control"
                               placeholder="输入最大爱心数，格式为数字"/>
                    </td>
                    <td class="title">最大爱心用户</td>
                    <td class="content">
                        <input type="text" name="virtualLivenessUsers" class="form-control"
                               placeholder="若输入多个用户，以“,”作分隔"/>
                    </td>
                </tr>
                <tr>
                    <td class="title">最少需要的爱心数</td>
                    <td class="content">
                        <input type="text" name="minLivenessToParticipate" class="form-control"
                               value="${latestMinLivenessToParticipate}" placeholder="输入参与抽奖最少需要的爱心"/>
                    </td>
                    <td class="title">预计参加人数</td>
                    <td class="content">
                        <input type="text" name="expectParticipantCount" class="form-control"
                               value="10000" placeholder="输入预计参数人数"/>
                    </td>
                </tr>
                <tr>
                    <td></td>
                    <td></td>
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
            <h4>尚未添加商品，故无法添加抽奖</h4>
        </c:otherwise>
    </c:choose>
</form>
