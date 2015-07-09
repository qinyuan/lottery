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
    var $deleteImages = $('table.normal img.delete');
    var $editImages = $('table.normal img.edit');

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
        if (!validateInputForm()) {
            e.preventDefault();
            return false;
        }
        return true;
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
        JSUtils.scrollTop($form);
    });
})();