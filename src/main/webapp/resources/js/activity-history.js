;
(function () {
    $('div.main-body div.filter input').iCheck({
        checkboxClass: 'icheckbox_minimal',
        radioClass: 'iradio_minimal'
    }).on('ifChecked', function () {
        location.href = JSUtils.updateUrlParam('activityType', this.value);
    });

    subscribe.setEmail(window['email']);
    $('div.main-body div.title a.subscribe').click(function () {
        subscribe.show();
    });

    var $activities = $('div.main-body div.activities');
    var height = $activities.height();
    $activities.css({
        'height': height,
        'overflow': 'visible'
    });
})();
$('#lotteryHistoryNavigation').addClass('emphasize');
