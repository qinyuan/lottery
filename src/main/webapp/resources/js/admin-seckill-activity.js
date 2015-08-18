;
(function () {
    // codes about seckill activity form
    var $form = $('#seckillActivityForm');
    var $term = $form.getInputByName('term');
    var $startTime = $form.getInputByName('startTime');
    var $expectParticipantCount = $form.getInputByName('expectParticipantCount');
    var $winners = $form.getInputByName('winners');
    var $description = $form.find('textarea[name=description]');
    var $okButton = $form.getButtonByName('ok');
    var $cancelButton = $form.getButtonByName('cancel');
    var $editImages = $('table.normal img.edit');
    var $addButton = $('#addSeckillActivityButton');

    function showForm() {
        JSUtils.showTransparentBackground(1);
        $form.fadeIn(300, function () {
            $term.focusOrSelect();
        });
    }

    function validateInputForm() {
        if ($term.trimVal() == '') {
            alert('期数不能为空！');
            $term.focusOrSelect();
            return false;
        }

        if (!JSUtils.isNumberString($term.val())) {
            alert('期数必须为数字格式！');
            $term.focusOrSelect();
            return false;
        }

        var startTime = $startTime.trimVal();
        if (startTime == '') {
            alert('开始时间未填写！');
            $startTime.focusOrSelect();
            return false;
        }
        if (!JSUtils.isDateOrDateTimeString(startTime)) {
            alert('开始时间格式错误！');
            $startTime.focusOrSelect();
            return false;
        }

        var expectParticipantCount = $expectParticipantCount.val();
        if (expectParticipantCount != '' && !JSUtils.isNumberString(expectParticipantCount)) {
            alert('预期参与人数只能为数字格式！');
            $expectParticipantCount.focusOrSelect();
            return false;
        }
        return true;
    }

    $addButton.click(function () {
        showForm();
    });
    $okButton.click(function (e) {
        e.preventDefault();

        if (!validateInputForm()) {
            return false;
        } else {
            $.post('admin-seckill-activity-add-edit.json', $form.serialize(), function (data) {
                if (!data.success) {
                    alert(data.detail);
                    return;
                }
                if ($form.getInputByName('id').val()) {
                    // if edit, just reload
                    location.reload();
                } else {
                    // if add, go to first page
                    location.href = 'admin-seckill-activity.html';
                }
            });
            return false;
        }
    });
    $cancelButton.click(function () {
        $form.getInputByName('id').val(null);
        $form.hide();
        JSUtils.hideTransparentBackground();
    });
    $editImages.click(function () {
        var $tr = $(this).getParentByTagName('tr');
        $form.setInputValue('id', $tr.dataOptions('id'));
        $term.val($tr.find('td.term').text());
        $startTime.attr('disabled', false).val($tr.find('td.start-time').text());
        $expectParticipantCount.val($tr.find('td.expect-participant-count').text());
        $winners.val($tr.find('td.winners').text());
        $description.val($tr.find('td.description').text());
        JSUtils.loadSelectFormValue($commoditySelect, $tr.find('td.commodity').dataOptions('commodityId'));
        showForm();
    });
    var $commoditySelect = $form.find('div.commodity-select');
    JSUtils.loadSelectFormEvents($commoditySelect);
})();
(function () {
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
            $.post('admin-seckill-activity-stop.json', {
                'id': activityId
            }, JSUtils.normalAjaxCallback);
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
        $.post(
            'admin-seckill-activity-update-announcement.json',
            $announceEditForm.serialize(),
            JSUtils.normalAjaxCallback
        );
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
            $.post('admin-seckill-activity-delete.json', {
                'id': activityId
            }, JSUtils.normalAjaxCallback);
        }
    });
})();
$('#commoditySeckillLink').addClass('emphasize');
