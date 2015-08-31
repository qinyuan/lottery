;
(function () {
    $('div.main-body tbody tr').each(function () {
        var $this = $(this);
        var $td = $this.find('td.status');
        if ($td.text().indexOf('结束') > 0) {
            $this.addClass('expire');
        }
    });
})();
$('#lotteryHistoryNavigation').addClass('emphasize');
