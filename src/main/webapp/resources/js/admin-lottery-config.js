;
(function () {
    $('#submitButton').click(function (e) {
        function focus($target) {
            e.preventDefault();
            $target.focusOrSelect();
            JSUtils.scrollTop($target);
        }

        var $shareSucceedLiveness = $('input[name=shareSucceedLiveness]');
        var shareSucceedLiveness = $shareSucceedLiveness.val();

        if (shareSucceedLiveness == '') {
            focus($shareSucceedLiveness);
            alert('分享成功增加的爱心数不能为空');
            return false;
        }

        if (!JSUtils.isNumberString(shareSucceedLiveness)) {
            focus($shareSucceedLiveness);
            alert('分享成功增加的爱心数必须为数字格式');
            return false;
        }

        var $noTelLotteryLotCount = $('input[name=noTelLotteryLotCount]');
        var noTelLotteryLotCount = $noTelLotteryLotCount.val();
        if (!JSUtils.isNumberString(noTelLotteryLotCount)) {
            focus($noTelLotteryLotCount);
            alert('未填手机时最多可参与的抽奖次数必须为数字格式');
            return false;
        }

        var $noTelLotteryLotPrice = $('input[name=noTelLotteryLotPrice]');
        var noTelLotteryLotPrice = $noTelLotteryLotPrice.val();
        if (!JSUtils.isNumberString(noTelLotteryLotPrice)) {
            focus($noTelLotteryLotPrice);
            alert('未填手机时最多可参与的抽奖金额必须为数字格式');
            return false;
        }

        return true;
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
