<%@ page import="com.qinyuan15.lottery.mvc.AppConfig" %>
<%@ page import="com.qinyuan15.lottery.mvc.activity.dualcoloredball.DualColoredBallUtils" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@include file="inc-taglib.jsp" %>
<div class="float-panel shadow activity-result" id="lotteryResult">
    <jsp:include page="commodity-float-panel-title.jsp">
        <jsp:param name="title" value="<span class='text'></span>"/>
    </jsp:include>
    <div class="body">
        <div class="activity">
            <div class="image"><img/></div>
            <div class="description break-word"></div>
        </div>
        <div class="create-number conceal">
            <button class="manual blue" type="button">手动选号</button>
            <button class="auto blue" type="button">自动选号</button>
        </div>
        <div class="manual-number-creator conceal">
            <c:forEach var="i" begin="1" end="3">
                <select name="numberPart${i}">
                    <%
                        for (int j = DualColoredBallUtils.MIN_NUMBER; j <= DualColoredBallUtils.MAX_NUMBER; j++) {
                            String optionValue = j < 10 ? "0" + j : "" + j;
                    %>
                    <option value="<%=optionValue%>"><%=optionValue%>
                    </option>
                    <%
                        }
                    %>
                </select>
            </c:forEach>
            <div class="submit">
                <button class="blue" type="button">确定</button>
            </div>
        </div>
        <div class="lot conceal">
            <div class="serial-number">
                您的抽奖号： <span class="number"><span></span><span></span><span></span></span>
            </div>
        </div>
        <div class="same-lot-users conceal">
            <div class="left">
                <table>
                    <colgroup>
                        <col class="username"/>
                        <col class="number"/>
                        <col class="liveness"/>
                    </colgroup>
                    <thead>
                    <tr>
                        <td>昵称</td>
                        <td>抽奖号</td>
                        <td class="liveness">支持数</td>
                    </tr>
                    </thead>
                </table>
                <div class="data">
                    <table>
                        <colgroup>
                            <col class="username"/>
                            <col class="number"/>
                            <col class="liveness"/>
                        </colgroup>
                        <tbody></tbody>
                    </table>
                </div>
            </div>
            <div class="right">
                <div class="more-liveness">
                    <a class="blue" href="share.html" target="_blank">增加更多支持</a>
                </div>
                <div class="lottery-rule">
                    中奖号码若为该号码，中奖者为支持数最高的用户，详见
                    <a target="_blank" href="<%=AppConfig.getLotteryRuleLink()%>">抽奖规则</a>
                </div>
            </div>
        </div>
        <div class="activity-expire conceal">
            活动已结束，敬请留意下次活动开始时间
        </div>
    </div>
</div>
