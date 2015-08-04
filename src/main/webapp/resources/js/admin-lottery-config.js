;
$('#commodityLotteryLink').addClass('emphasize');
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
    JSUtils.loadSelectFormEventsAndValue($('div.new-lottery-chance-mail-select'), window['currentNewLotteryChanceMailAccountId']);
})();
