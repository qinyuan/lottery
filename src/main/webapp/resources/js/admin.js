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
        var html = JSUtils.handlebars("link-template");
        $('#linkTable').append(html).find('tr:last-child').find('input').eq(0).focus();
    });
})();

function rankUpLink(target) {
    console.log(target);
}

function rankDownLink(target) {
    console.log(target);
}

function deleteLink(target) {
    console.log(target);
}
