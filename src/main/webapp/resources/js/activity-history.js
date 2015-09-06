;
(function () {
    /*$('div.main-body tbody tr').each(function () {
        var $this = $(this);
        var $td = $this.find('td.status');
        if ($td.text().indexOf('结束') > 0) {
            $this.addClass('expire');
        }
    });*/
    $('div.main-body div.filter input').iCheck({
        checkboxClass: 'icheckbox_minimal',
        radioClass: 'iradio_minimal'
    }).on('ifChecked', function () {
        location.href = JSUtils.updateUrlParam('activityType', this.value);
    });
})();
$('#lotteryHistoryNavigation').addClass('emphasize');
