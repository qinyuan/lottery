<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<div id="liveness">
    <div class="text">
        <span class="liveness-icon lightTransparent"></span>： ${liveness}
        <span class="share"><img src="resources/css/images/help.png"/>通过转发增加支持数</span>

        <div class="share-panel">
            <div class="list">
                <ul>
                    <li><a class="sina" href="${sinaWeiboShareUrl}" target="_blank"
                            ><span class="icon">&nbsp;</span>新浪微博</a></li>
                    <li><a class="qq" href="${qqShareUrl}" target="_blank"
                            ><span class="icon">&nbsp;</span>QQ</a></li>
                    <li><a class="qzone" href="${qzoneShareUrl}" target="_blank"
                            ><span class="icon">&nbsp;</span>QQ空间</a></li>
                </ul>
            </div>
            <div class="triangle outer"></div>
            <div class="triangle inner"></div>
        </div>
    </div>
</div>
