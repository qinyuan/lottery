<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<div id="security">
    <div class="text">修改密码</div>
    <div class="text old">
        <span class="title">原始密码</span>
        <input type="password" class="form-control"/>
        <span class="error"></span>
    </div>
    <div class="text new">
        <span class="title">新密码</span>
        <input type="password" class="form-control" maxlength="20"/>
        <span class="error"></span>
    </div>
    <div class="text new2">
        <span class="title">确认密码</span>
        <input type="password" class="form-control" maxlength="20"/>
        <span class="error"></span>
    </div>
    <div class="edit-submit" style="margin-left:100px;">
        <button class="ok" type="button">确定</button>
    </div>
</div>
<div class="history content">
    <div class="title history">
        我们通过IP推测出您的大概位置，请通过登录记录判断是否存在异常，如有异常，请及时修改密码
    </div>
    <table class="normal">
        <thead>
        <tr>
            <th>序号</th>
            <th>登录时间</th>
            <th>IP地址</th>
            <th>参考地点</th>
            <th>操作系统</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach var="loginRecord" items="${loginRecords}" varStatus="status">
            <tr>
                <td>${status.index + 1}</td>
                <td>${loginRecord.loginTime}</td>
                <td>${loginRecord.ip}</td>
                <td>${loginRecord.location}</td>
                <td>${loginRecord.platform}</td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
</div>
