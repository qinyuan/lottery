;
(function () {
    function validateUploadFile(id, name) {
        var $textInput = $('#' + id);
        if ($textInput.val().trim() == '' && $('#' + id + 'File').val().trim() == '') {
            alert(name + '未设置');
            $textInput.focusOrSelect();
            return false;
        } else {
            return true;
        }
    }

    $('#submitButton').click(function (e) {
        if ((!validateUploadFile('indexHeaderLeftLogo', '左图标'))
            || (!validateUploadFile('indexHeaderRightLogo', '右图标'))
            || (!validateUploadFile('indexHeaderSlogan', '右侧宣传图片'))) {
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
