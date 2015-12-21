<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@include file="inc-taglib.jsp" %>
<div class="float-panel shadow activity-result" id="seckillResult">
    <jsp:include page="commodity-float-panel-title.jsp">
        <jsp:param name="title" value="<span class='text'></span>"/>
    </jsp:include>
    <div class="body">
        <div class="activity">
            <div class="image"><img/></div>
            <div class="description break-word"></div>
        </div>
        <div class="lot">
            <div class="pokers">
                <c:forEach var="index" begin="1" end="3">
                    <div class="poker" data-options="index:1">
                        <img class="back delay" data-source="${pokerBackSide}"/>
                        <img class="front link delay" data-source="${pokerFrontSide}"/>
                    </div>
                </c:forEach>
            </div>
            <div class="success result">恭喜您，秒杀成功！</div>
            <div class="not-start fail result">秒杀尚未开始！</div>
            <div class="over fail result">很遗憾，速度了差一点，秒杀失败！</div>
            <div class="unknown fail result">出现未知错误！</div>
        </div>
        <div class="activity-expire">
            本期秒杀已结束，请关注下期秒杀活动
            <a href="activity-history.html" target="_blank">秒杀结果请查看活动历史</a>
        </div>
        <div class="remaining-time">
            距离活动开始
            <span class="day">00天</span>
            <span class="hour">00</span>:<span class="minute">00</span>:<span class="second">00</span>
        </div>
    </div>
</div>
