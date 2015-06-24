;
(function () {
    $('div.body div.snapshots div.snapshot').each(function (index) {
        if (index >= 6) {
            $(this).hide();
        }
    });
})();
