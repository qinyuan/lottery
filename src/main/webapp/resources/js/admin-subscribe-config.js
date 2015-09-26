;
(function () {
    var $form = $('#mainForm');
    var $qqlistId = $form.getInputByName('qqlistId');
    var descriptionEditor = CKEDITOR.replace("qqlistDescription");
    $('#submitButton').click(function () {
        var qqlistId = $qqlistId.val();
        if ($.trim(qqlistId) == '') {
            alert('QQList的ID不能为空');
            $qqlistId.focusOrSelect();
            return false;
        }

        $form.find('textarea[name=qqlistDescription]').val(descriptionEditor.getData());
        $.post('admin-subscribe-config-submit.json', $form.serialize(), JSUtils.normalAjaxCallback);
    });
})();
$('#systemEditLink').addClass('emphasize');
