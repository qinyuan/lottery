;
(function () {
    // codes about lottery activity form
    var $form = $('#lotteryActivityForm');
    var $startTime = $form.getInputByName('startTime');
    var $autoStartTime = $form.getInputByName('autoStartTime');
    var $expectEndTime = $form.getInputByName('expectEndTime');
    var $continuousSerialLimit = $form.getInputByName('continuousSerialLimit');
    var $expectParticipantCount = $form.getInputByName('expectParticipantCount');
    var $virtualLiveness = $form.getInputByName('virtualLiveness');
    var $virtualLivenessUsers = $form.getInputByName('virtualLivenessUsers');
    var $dualColoredBallTerm = $form.getInputByName(('dualColoredBallTerm'));
    var $okButton = $form.getButtonByName('ok');
    var $cancelButton = $form.getButtonByName('cancel');
    var $editImages = $('table.normal img.edit');
    var $addButton = $('#addLotteryActivityButton');
    var $livenessRow = $form.find('tr.liveness');

    function showForm(displayLivness) {
        JSUtils.showTransparentBackground(1);

        if (displayLivness) {
            $livenessRow.show();
        } else {
            $livenessRow.hide();
        }
        $form.fadeIn(300, function () {
            if ($autoStartTime.get(0).checked) {
                $dualColoredBallTerm.focusOrSelect();
            } else {
                $startTime.focusOrSelect();
            }
        });
    }

    function validateDualColoredBall() {
        var dualColoredBallTerm = $dualColoredBallTerm.val();
        if (dualColoredBallTerm.length != 7
            || !(dualColoredBallTerm.match(/^20\d{5}$/) || dualColoredBallTerm.match(/^19\d{5}$/))) {
            alert('双色球期数应为19或20开头的7位数字！');
            $dualColoredBallTerm.focusOrSelect();
            return false;
        }
        return true;
    }

    function validateInputForm() {
        if (!$autoStartTime.get(0).checked) {
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
        }

        if (!validateDualColoredBall()) {
            return false;
        }

        var expectEndTime = $expectEndTime.trimVal();
        if (expectEndTime == '') {
            alert("预期结束时间未填写！");
            $expectEndTime.focusOrSelect();
            return false;
        }
        if (!JSUtils.isDateOrDateTimeString(expectEndTime)) {
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

        if ($livenessRow.css('display') != 'none') {
            var virtualLiveness = $virtualLiveness.val();
            if (virtualLiveness != '') {
                if (!JSUtils.isNumberString(virtualLiveness)) {
                    alert('最大爱心数只能为数字格式！');
                    $virtualLiveness.focusOrSelect();
                    return false;
                }
                var virtualLivenessUsers = $virtualLivenessUsers.trimVal();
                if (virtualLivenessUsers == '') {
                    alert('如果填写了最大爱心，则最大爱心用户必须填写！');
                    $virtualLivenessUsers.focusOrSelect();
                    return false;
                }
            }
        }

        var expectParticipantCount = $expectParticipantCount.val();
        if (expectParticipantCount != '' && !JSUtils.isNumberString(expectParticipantCount)) {
            alert('预期参数人数只能为数字格式！');
            $expectParticipantCount.focusOrSelect();
            return false;
        }
        return true;
    }

    $addButton.click(function () {
        showForm(false);
        //$form.getInputByName('expectEndTime').focusOrSelect();
    });
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
                    // if edit, just reload
                    location.reload();
                } else {
                    // if add, go to first page
                    location.href = 'admin-lottery-activity.html';
                }
            });
        }

        return true;
    });
    $cancelButton.click(function () {
        $form.getInputByName('id').val(null);
        $form.hide();
        JSUtils.hideTransparentBackground();
    });
    function updateExpectEndDateByDualColoredBallTerm() {
        $.post('dual-colored-ball-query-date.json', {
            'fullTermNumber': $dualColoredBallTerm.val()
        }, function (data) {
            if (data['date']) {
                $expectEndTime.val(data['date']);
            }
        });
    }

    if ($dualColoredBallTerm.trimVal() != '') {
        updateExpectEndDateByDualColoredBallTerm();
    }
    $dualColoredBallTerm.blur(function () {
        if (validateDualColoredBall()) {
            updateExpectEndDateByDualColoredBallTerm();
        } else {
            setTimeout(function () {
                $dualColoredBallTerm.focusOrSelect();
            }, 100);
        }
    });
    $editImages.click(function () {
        showForm(true);
        var $tr = $(this).getParentByTagName('tr');
        $form.setInputValue('id', $tr.dataOptions('id'));
        $form.getInputByName('startTime').attr('disabled', false)
            .val($tr.find('td.start-time').text()).focusOrSelect();
        $form.getInputByName('autoStartTime').get(0).checked = false;
        var $expectEndTimeTd = $tr.find('td.expect-end-time');
        $form.setInputValue('expectEndTime', $expectEndTimeTd.text());
        $form.setInputValue('dualColoredBallTerm', $expectEndTimeTd.dataOptions('dualColoredBallTerm'));
        $form.setInputValue('virtualLiveness', $tr.find('td.virtual-liveness').text());
        $form.setInputValue('virtualLivenessUsers', $tr.find('td.virtual-liveness-users').text());
        $form.setInputValue('continuousSerialLimit', $tr.find('td.continuous-serial-limit').text());
        $form.setInputValue('expectParticipantCount', $tr.find('td.expect-participant-count').text());

        var commodityId = $tr.find('td.commodity').dataOptions('commodityId');
        $form.find('div.commodity-select li a').each(function () {
            var $this = $(this);
            if ($this.dataOptions('id') == commodityId) {
                $this.trigger('click');
                return false;
            }
            return true;
        });
    });
})();
(function () {
    // codes about announcement edit form
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
(function () {
    var $listType = $('div.list-type');

    $listType.find('input[type=radio]').click(function () {
        location.href = location.origin + location.pathname + '?listType=' + $(this).val();
    });
    var $deleteImages = $('table.normal img.delete');
    $deleteImages.click(function () {
        if (confirm('确定删除？')) {
            var activityId = $(this).getParentByTagName('tr').dataOptions('id');
            $.post('admin-lottery-activity-delete.json', {
                'id': activityId
            }, JSUtils.normalAjaxCallback);
        }
    });
})();
$('#commodityLotteryLink').addClass('emphasize');
