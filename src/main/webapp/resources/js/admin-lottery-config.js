;
(function () {
    //var $form = $('form');
    $('#submitButton').click(function (e) {
        var $shareSucceedLiveness = $('input[name=shareSucceedLiveness]');
        var shareSucceedLiveness = $shareSucceedLiveness.val();

        if (shareSucceedLiveness == '') {
            e.preventDefault();
            alert('分享成功增加的爱心数不能为空');
            $shareSucceedLiveness.focusOrSelect();
            return false;
        }

        if (!JSUtils.isNumberString(shareSucceedLiveness)) {
            e.preventDefault();
            alert('分享成功增加的爱心数必须为数字格式');
            $shareSucceedLiveness.focusOrSelect();
            return false;
        }
        return true;

        /*var $newLotLiveness = $form.getInputByName('newLotLiveness');
         if (!JSUtils.isNumberString($newLotLiveness.val())) {
         e.preventDefault();
         alert('获得新抽奖机会所需要的爱心数必须为数字格式');
         $newLotLiveness.focusOrSelect();
         return false;
         } else {
         return true;
         }*/
    });
    $('input[name=remindNewLotteryChanceByMail]').click(function () {
        var $mailConfig = $(this).getParentByTagName('table').find('tr.mail-config');
        if (this.checked) {
            $mailConfig.show(200);
        } else {
            $mailConfig.hide(200)
        }
    });
    $('input[name=remindNewLotteryChanceBySystemInfo],input[name=remindLivenessIncreaseBySystemInfo]').click(function () {
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
$('#commoditySeckillLink').addClass('emphasize');
