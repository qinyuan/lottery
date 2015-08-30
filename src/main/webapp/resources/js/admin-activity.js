(function () {
    if ($('#announcementEditor').size() == 0) {
        return;
    }

    // codes about announcement edit form
    var $stopImages = $('table.normal img.stop');
    var $announceImages = $('table.normal img.announce');
    var $announceEditForm = $('#announceEditForm');
    var $announceOkButton = $announceEditForm.getButtonByName('ok');
    var $announceCancelButton = $announceEditForm.getButtonByName('cancel');
    var $announceEditor = CKEDITOR.replace('announcementEditor', {'width': 650});

    function showAnnounceEditForm(id, winners, announcement) {
        JSUtils.showTransparentBackground(1);
        $announceEditForm.setInputValue('id', id);
        $announceEditForm.setInputValue('winners', winners);
        $announceEditor.setData(announcement);
        $announceEditForm.fadeIn(500).focusFirstTextInput();
        JSUtils.scrollToVerticalCenter($announceEditForm);
    }

    $stopImages.click(function () {
        if (confirm('确定强行结束该秒杀活动？')) {
            var activityId = $(this).getParentByTagName('tr').dataOptions('id');
            $.post(stopUrl, {'id': activityId}, JSUtils.normalAjaxCallback);
        }
    });
    $announceImages.click(function () {
        var $tr = $(this).getParentByTagName('tr');
        showAnnounceEditForm(
            $tr.dataOptions('id'),
            $tr.find('td.winners').trimText(),
            $.trim($tr.find('td.announcement').html())
        );
    });
    $announceCancelButton.click(function () {
        $announceEditForm.fadeOut(300, function () {
            JSUtils.hideTransparentBackground();
        });
    });
    $announceOkButton.click(function (e) {
        e.preventDefault();
        var data = {
            id: $announceEditForm.find('input[name=id]').val(),
            winners: $announceEditForm.find('input[name=winners]').val(),
            announcement: $announceEditor.getData()
        };
        $.post(announcementUpdateUrl, data, JSUtils.normalAjaxCallback);
        return false;
    });
})();
(function () {
    var $listType = $('div.list-type');

    $listType.find('input[type=radio]').click(function () {
        location.href = location.origin + location.pathname + '?listType=' + $(this).val();
    });
    var $deleteImages = $('table.normal img.delete');
    $deleteImages.click(function () {
        if (confirm('确定删除？')) {
            var activityId = $(this).getParentByTagName('tr').dataOptions('id');
            $.post(deleteUrl, {'id': activityId}, JSUtils.normalAjaxCallback);
        }
    });
})();
$('#commoditySeckillLink').addClass('emphasize');
