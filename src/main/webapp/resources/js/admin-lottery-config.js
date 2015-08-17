;
(function () {
    var $form = $('form');
    $('#submitButton').click(function (e) {
        var $newLotLiveness = $form.getInputByName('newLotLiveness');
        if (!JSUtils.isNumberString($newLotLiveness.val())) {
            e.preventDefault();
            alert('获得新抽奖机会所需要的爱心数必须为数字格式');
            $newLotLiveness.focusOrSelect();
            return false;
        } else {
            return true;
        }
    });
    $('table.email-template input[name=remindNewLotteryChanceByMail]').click(function () {
        var $mailConfig = $(this).getParentByTagName('table').find('tr.mail-config');
        if (this.checked) {
            $mailConfig.show(200);
        } else {
            $mailConfig.hide(200)
        }
    });
    $('table.email-template input[name=remindNewLotteryChanceBySystemInfo]').click(function () {
        var $systemInfoConfig = $(this).getParentByTagName('table').find('tr.system-info-config');
        if (this.checked) {
            $systemInfoConfig.show(200);
        } else {
            $systemInfoConfig.hide(200);
        }
    });
    JSUtils.loadSelectFormEventsAndValue($('div.new-lottery-chance-mail-select'),
        window['currentNewLotteryChanceMailAccountId']);
})();
$('#commodityLotteryLink').addClass('emphasize');
