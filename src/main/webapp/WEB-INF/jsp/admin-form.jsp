<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@include file="inc-taglib.jsp" %>
<div class="edit-unit">
    <div class="title">网站设置</div>
    <div class="content">
        <table>
            <tbody>
            <tr>
                <td class="title">网站图标</td>
                <td class="input">
                    <jsp:include page="widget-upload-image.jsp">
                        <jsp:param name="id" value="favicon"/>
                        <jsp:param name="value" value="${favicon}"/>
                        <jsp:param name="snapshot" value="true"/>
                    </jsp:include>
                </td>
            </tr>
            </tbody>
        </table>
    </div>
</div>
<div class="edit-unit">
    <div class="title">主页页头设置</div>
    <div class="content">
        <table>
            <tbody>
            <tr>
                <td class="title">左图标</td>
                <td class="input">
                    <jsp:include page="widget-upload-image.jsp">
                        <jsp:param name="id" value="indexHeaderLeftLogo"/>
                        <jsp:param name="value" value="${indexHeaderLeftLogo}"/>
                        <jsp:param name="snapshot" value="true"/>
                    </jsp:include>
                </td>
            </tr>
            <%--<tr>
                <td class="title">右图标</td>
                <td class="input">
                    <jsp:include page="widget-upload-image.jsp">
                        <jsp:param name="id" value="indexHeaderRightLogo"/>
                        <jsp:param name="value" value="${indexHeaderRightLogo}"/>
                        <jsp:param name="snapshot" value="true"/>
                    </jsp:include>
                </td>
            </tr>--%>
            <tr>
                <td class="title">右侧宣传图片</td>
                <td class="input">
                    <jsp:include page="widget-upload-image.jsp">
                        <jsp:param name="id" value="indexHeaderSlogan"/>
                        <jsp:param name="value" value="${indexHeaderSlogan}"/>
                        <jsp:param name="snapshot" value="true"/>
                    </jsp:include>
                </td>
            </tr>
            <tr>
                <td class="title">链接</td>
                <td class="input">
                    <table id="linkTable">
                        <colgroup>
                            <col class="text"/>
                            <col class="target"/>
                            <col class="action"/>
                        </colgroup>
                        <thead>
                        <tr>
                            <th>内容</th>
                            <th>链接目标</th>
                            <th>操作</th>
                        </tr>
                        </thead>
                        <tbody>
                        <c:forEach var="indexHeaderLink" items="${indexHeaderLinks}">
                            <jsp:include page="admin-link-table-row.jsp">
                                <jsp:param name="title" value="${indexHeaderLink.title}"/>
                                <jsp:param name="href" value="${indexHeaderLink.href}"/>
                            </jsp:include>
                        </c:forEach>
                        </tbody>
                    </table>
                    <img class="link" title="添加" id="addLink" src="resources/css/images/add.png"/>
                </td>
            </tr>
            </tbody>
        </table>
    </div>
</div>
<div class="edit-unit">
    <div class="title">商品页页头设置</div>
    <div class="content">
        <table>
            <tbody>
            <tr>
                <td class="title">左图标</td>
                <td class="input">
                    <jsp:include page="widget-upload-image.jsp">
                        <jsp:param name="id" value="commodityHeaderLeftLogo"/>
                        <jsp:param name="value" value="${commodityHeaderLeftLogo}"/>
                        <jsp:param name="snapshot" value="true"/>
                    </jsp:include>
                </td>
            </tr>
            </tbody>
        </table>
    </div>
</div>
<div class="edit-unit">
    <div class="title">注册页页头设置</div>
    <div class="content">
        <table>
            <tbody>
            <tr>
                <td class="title">左图标</td>
                <td class="input">
                    <jsp:include page="widget-upload-image.jsp">
                        <jsp:param name="id" value="registerHeaderLeftLogo"/>
                        <jsp:param name="value" value="${registerHeaderLeftLogo}"/>
                        <jsp:param name="snapshot" value="true"/>
                    </jsp:include>
                </td>
            </tr>
            <tr>
                <td class="title">右图标</td>
                <td class="input">
                    <jsp:include page="widget-upload-image.jsp">
                        <jsp:param name="id" value="registerHeaderRightLogo"/>
                        <jsp:param name="value" value="${registerHeaderRightLogo}"/>
                        <jsp:param name="snapshot" value="true"/>
                    </jsp:include>
                </td>
            </tr>
            </tbody>
        </table>
    </div>
</div>
<div class="edit-unit">
    <div class="title">页尾设置</div>
    <div class="content">
        <table>
            <tbody>
            <tr>
                <td class="title">页尾图片</td>
                <td class="input">
                    <jsp:include page="widget-upload-image.jsp">
                        <jsp:param name="id" value="footerPoster"/>
                        <jsp:param name="value" value="${footerPoster}"/>
                        <jsp:param name="snapshot" value="true"/>
                    </jsp:include>
                </td>
            </tr>
            <tr>
                <td class="title">页尾文字</td>
                <td class="input">
                    <input type="text" class="form-control" name="footerText" value="${footerText}"
                           placeholder="在此输入页面底部的文字"/>
                </td>
            </tr>
            </tbody>
        </table>
    </div>
</div>
<div class="edit-unit">
    <div class="title">手机号验证说明页面设置</div>
    <div class="content">
        <table>
            <tbody>
            <tr>
                <td class="title">页面链接</td>
                <td class="input">
                    <input type="text" class="form-control" name="telValidateDescriptionPage"
                           value="${telValidateDescriptionPage}"/>
                </td>
            </tr>
            </tbody>
        </table>
    </div>
</div>
<div class="edit-unit">
    <div class="title">邮箱帐户</div>
    <div class="content" id="emailList">
        <div>
            <c:forEach var="mail" items="${mails}">
                <div class="mail-item" data-options="id: ${mail.id}">
                        <%--<input type="hidden" class="host" value="${mail.host}"/>
                        <input type="hidden" class="password" value="${mail.password}"/>

                        <div class="username" title="单击修改">${mail.username}</div>
                        <div class="edit-image" title="单击修改"><img class="link"
                                                                  src="resources/css/images/pencil.png"/></div>
                        <div class="close-image" title="删除"><img class="link" src="resources/css/images/close.gif"/>
                        </div>--%>
                    <input type="hidden" class="type" value="${mail.type}"/>

                    <div class="username" title="单击修改">${mail.username}</div>
                    <div class="edit-image" title="单击修改">
                        <img class="link" src="resources/css/images/pencil.png"/>
                    </div>
                    <div class="close-image" title="删除">
                        <img class="link" src="resources/css/images/close.gif"/>
                    </div>
                </div>
            </c:forEach>
        </div>
        <div class="button">
            <button type="button" class="btn btn-primary btn-sm" id="addEmailButton">添加</button>
        </div>
    </div>
</div>
<div class="edit-unit">
    <div class="title">新用户注册邮件设置</div>
    <div class="content">
        <table class="email-template">
            <tbody>
            <tr>
                <td class="title">发件箱</td>
                <td class="input">
                    <c:set var="selectFormItems" value="${mailSelectFormItems}"/>
                    <c:set var="selectFormId" value="register-mail-select"/>
                    <c:set var="selectFormName" value="registerMailAccountId"/>
                    <%@include file="widget-select-form.jsp" %>
                </td>
            </tr>
            <tr>
                <td class="title">邮件标题</td>
                <td class="input">
                    <input type="text" name="registerMailSubjectTemplate" class="form-control"
                           value="${registerMailSubjectTemplate}" placeholder="在此输入邮件的标题"/>
                </td>
            </tr>
            <tr>
                <td class="title">邮件正文模板
                    <div class="comment">
                        注：<br/>
                        {{url}}指代激活链接。
                    </div>
                </td>
                <td class="input">
                    <textarea class="ckeditor"
                              name="registerMailContentTemplate">${registerMailContentTemplate}</textarea>
                </td>
            </tr>
            </tbody>
        </table>
    </div>
</div>
<div class="edit-unit">
    <div class="title">用户重置密码邮件设置</div>
    <div class="content">
        <table class="email-template">
            <tbody>
            <tr>
                <td class="title">发件箱</td>
                <td class="input">
                    <c:set var="selectFormItems" value="${mailSelectFormItems}"/>
                    <c:set var="selectFormId" value="reset-password-mail-select"/>
                    <c:set var="selectFormName" value="resetPasswordMailAccountId"/>
                    <%@include file="widget-select-form.jsp" %>
                </td>
            </tr>
            <tr>
                <td class="title">邮件标题</td>
                <td class="input">
                    <input type="text" name="resetPasswordMailSubjectTemplate" class="form-control"
                           value="${resetPasswordMailSubjectTemplate}" placeholder="在此输入邮件的标题"/>
                </td>
            </tr>
            <tr>
                <td class="title">邮件正文模板
                    <div class="comment">
                        注：<br/>
                        {{user}}指代用户名；<br/>
                        {{url}}指代重置链接。
                    </div>
                </td>
                <td class="input">
                    <textarea name="resetPasswordMailContentTemplate"
                              class="ckeditor">${resetPasswordMailContentTemplate}</textarea>
                </td>
            </tr>
            </tbody>
        </table>
    </div>
</div>
<div class="edit-unit">
    <div class="title">用户修改绑定邮箱的邮件设置</div>
    <div class="content">
        <table class="email-template">
            <tbody>
            <tr>
                <td class="title">发件箱</td>
                <td class="input">
                    <c:set var="selectFormItems" value="${mailSelectFormItems}"/>
                    <c:set var="selectFormId" value="reset-email-mail-select"/>
                    <c:set var="selectFormName" value="resetEmailMailAccountId"/>
                    <%@include file="widget-select-form.jsp" %>
                </td>
            </tr>
            <tr>
                <td class="title">邮件标题</td>
                <td class="input">
                    <input type="text" name="resetEmailMailSubjectTemplate" class="form-control"
                           value="${resetEmailMailSubjectTemplate}" placeholder="在此输入邮件的标题"/>
                </td>
            </tr>
            <tr>
                <td class="title">邮件正文模板
                    <div class="comment">
                        注：<br/>
                        {{user}}指代用户名；<br/>
                        {{url}}指代重置链接。
                    </div>
                </td>
                <td class="input">
                    <textarea name="resetEmailMailContentTemplate"
                              class="ckeditor">${resetEmailMailContentTemplate}</textarea>
                </td>
            </tr>
            </tbody>
        </table>
    </div>
</div>
<div class="submit">
    <button id="submitButton" type="submit" class="btn btn-success">确定</button>
</div>
