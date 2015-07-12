;
(function () {
    $('#commodityLotteryLink').addClass('emphasize');
    var $form = $('#lotteryActivityForm');
    var $startTime = $form.getInputByName('startTime');
    var $autoStartTime = $form.getInputByName('autoStartTime');
    var $expectEndTime = $form.getInputByName('expectEndTime');
    var $continuousSerialLimit = $form.getInputByName('continuousSerialLimit');
    var $expectParticipantCount = $form.getInputByName('expectParticipantCount');
    var $okButton = $form.getButtonByName('ok');
    var $cancelButton = $form.getButtonByName('cancel');
    var $deleteImages = $('table.normal img.delete');
    var $editImages = $('table.normal img.edit');
    var $stopImages = $('table.normal img.stop');
    var $announceImages = $('table.normal img.announce');
    var $announceEditForm = $('#announceEditForm');
    var $announceOkButton = $announceEditForm.getButtonByName('ok');
    var $announceCancelButton = $announceEditForm.getButtonByName('cancel');

    function showAnnounceEditForm(id, winners, announcement) {
        JSUtils.showTransparentBackground();
        $announceEditForm.setInputValue('id', id);
        $announceEditForm.setInputValue('winners', winners);
        $announceEditForm.find('textarea[name=announcement]').val(announcement);
        $announceEditForm.fadeIn(500).focusFirstTextInput();
    }

    function validateInputForm() {
        if (!$autoStartTime.get(0).checked) {
            if (!JSUtils.isDateOrDateTimeString($startTime.val())) {
                alert('开始时间格式错误！');
                $startTime.focusOrSelect();
                return false;
            }
        }

        var expectEndTime = $expectEndTime.val();
        if (expectEndTime != '' && !JSUtils.isDateOrDateTimeString(expectEndTime)) {
            alert("预期结束时间格式错误！");
            $expectEndTime.focusOrSelect();
            return false;
        }

        var continuousSerialLimit = $continuousSerialLimit.val();
        if (continuousSerialLimit != '' && !JSUtils.isNumberString(continuousSerialLimit)) {
            alert('抽奖号最大连接个数只能为数字格式！');
            $continuousSerialLimit.focusOrSelect();
            return false;
        }

        var expectParticipantCount = $expectParticipantCount.val();
        if (expectParticipantCount != '' && !JSUtils.isNumberString(expectParticipantCount)) {
            alert('预期参数人数只能为数字格式！');
            $expectParticipantCount.focusOrSelect();
            return false;
        }
        return true;
    }

    $autoStartTime.click(function () {
        var checked = this.checked;
        if (checked) {
            $startTime.attr('disabled', true);
        } else {
            $startTime.attr('disabled', false).focusOrSelect();
        }
    });
    $okButton.click(function (e) {
        e.preventDefault();

        if (!validateInputForm()) {
            return false;
        } else {
            $.post('admin-lottery-activity-add-edit.json', $form.serialize(), function (data) {
                if (!data.success) {
                    alert(data.detail);
                }
                if ($form.getInputByName('id').val()) {
                    location.reload();
                } else {
                    location.href = 'admin-lottery-activity.html';
                }
            });
        }

        return true;
    });
    $cancelButton.click(function () {
        $form.getInputByName('id').val(null);
        $okButton.text('添加抽奖');
        $cancelButton.hide();
    });
    $deleteImages.click(function () {
        if (confirm('确定删除？')) {
            var activityId = $(this).getParentByTagName('tr').dataOptions('id');
            $.post('admin-lottery-activity-delete.json', {
                'id': activityId
            }, JSUtils.normalAjaxCallback);
        }
    });
    $editImages.click(function () {
        var $tr = $(this).getParentByTagName('tr');
        $form.setInputValue('id', $tr.dataOptions('id'));
        $form.getInputByName('startTime').attr('disabled', false)
            .val($tr.find('td.startTime').text()).focusOrSelect();
        $form.getInputByName('autoStartTime').get(0).checked = false;
        $form.setInputValue('expectEndTime', $tr.find('td.expectEndTime').text());
        $form.setInputValue('continuousSerialLimit', $tr.find('td.continuousSerialLimit').text());
        $form.setInputValue('expectParticipantCount', $tr.find('td.expectParticipantCount').text());

        var commodityId = $tr.find('td.commodity').dataOptions('commodityId');
        $form.find('div.commodity-select li a').each(function () {
            var $this = $(this);
            if ($this.dataOptions('id') == commodityId) {
                $this.trigger('click');
                return false;
            }
            return true;
        });

        $cancelButton.show();
        $okButton.text("保存修改");
        JSUtils.scrollTop($form);
    });
    $stopImages.click(function () {
        if (confirm('确定强行结束该抽奖活动？')) {
            var activityId = $(this).getParentByTagName('tr').dataOptions('id');
            $.post('admin-lottery-activity-stop.json', {
                'id': activityId
            }, JSUtils.normalAjaxCallback);
        }
    });
    $announceImages.click(function () {
        var $tr = $(this).getParentByTagName('tr');
        showAnnounceEditForm(
            $tr.dataOptions('id'),
            $tr.find('td.winners').trimText(),
            $tr.find('td.announcement').trimText()
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
            'admin-lottery-activity-update-announcement.json',
            $announceEditForm.serialize(),
            JSUtils.normalAjaxCallback
        );
        return false;
    });
})();