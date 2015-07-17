;
(function () {
    $('#submitButton').click(function (e) {
        if ((!JSUtils.validateUploadFile('indexHeaderLeftLogo', '左图标未设置'))
            || (!JSUtils.validateUploadFile('indexHeaderRightLogo', '右图标未设置'))
            || (!JSUtils.validateUploadFile('indexHeaderSlogan', '右侧宣传图片未设置'))) {
            e.preventDefault();
            return false;
        } else {
            return true;
        }
    });
    $('#addLink').click(function () {
        var $linkTable = $('#linkTable');
        $linkTable.append(JSUtils.handlebars("link-template"));
        $linkTable.find('tr:last').find('input:first').focus();
    });
    $('#systemEditLink').addClass('emphasize');
})();

function rankUpLink(target) {
    $(target).getParentByTagName('tr').moveToPrev();
}

function rankDownLink(target) {
    $(target).getParentByTagName('tr').moveToNext();
}

function deleteLink(target) {
    $(target).getParentByTagName('tr').remove();
}
