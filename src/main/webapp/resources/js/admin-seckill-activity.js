;
(function () {
    // codes about seckill activity form
    var seckillActivity = buildAddEditFloatPanel({
        $floatPanel: $('#seckillActivityForm'),
        get$Winners: function () {
            return this.$floatPanel.getInputByName('winners');
        },
        addOrEditUrl: 'admin-seckill-activity-add-edit.json'
    });

    $('#addSeckillActivityButton').click(function () {
        seckillActivity.$floatPanel.setInputValue('id', null);
        seckillActivity.show();
    });
    $('table.normal img.edit').click(function () {
        seckillActivity.show();

        var $tr = $(this).getParentByTagName('tr');
        seckillActivity.get$Id().val($tr.dataOptions('id'));
        seckillActivity.get$Term().val($tr.find('td.term').text()).focusOrSelect();
        seckillActivity.get$StartTime().attr('disabled', false).val($tr.find('td.start-time').text());
        seckillActivity.get$ExpectParticipantCount().val($tr.find('td.expect-participant-count').text());
        seckillActivity.get$Winners().val($tr.find('td.winners').text());
        seckillActivity.setDescriptionHtml($tr.find('input.description').val());

        JSUtils.loadSelectFormValue(seckillActivity.get$CommoditySelect(), $tr.find('td.commodity')
            .dataOptions('commodityId'));
    });

    /*
     return;
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
     JSUtils.loadSelectFormEvents($commoditySelect);*/
})();
var stopUrl = 'admin-seckill-activity-stop.json';
var announcementUpdateUrl = 'admin-seckill-activity-update-announcement.json';
var deleteUrl = 'admin-seckill-activity-delete.json';
