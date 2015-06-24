;
(function () {
    var snapshotDisplaySize = 6;
    var $snapshots = $('div.body div.snapshots div.snapshot');
    var snapshotCount = $snapshots.size();
    //var selectedId = $('div.body div.detail img').dataOptions()['id'];
    var selectedId = window['selectedCommodityId'];

    function loadDetailImageById(id) {
        var $img = $('div.body div.detail img');
        $img.hide();
        $.post('commodity-info.json', {
            id: id
        }, function (commodity) {
            $img.attr('src', commodity['detailImage']);
            $img.fadeIn(500);
        })
    }

    for (var i = 0; i < snapshotCount; i++) {
        var $snapshot = $snapshots.eq(i);
        if ($snapshot.dataOptions()['id'] == selectedId) {
            $snapshot.addClass('selected');
            var startIndex = i / snapshotDisplaySize;
            var endIndex = startIndex + 5;
            for (var j = startIndex; j <= endIndex; j++) {
                $snapshots.eq(j).show();
            }
            break;
        }
    }
    $snapshots.click(function () {
        $snapshots.removeClass('selected');
        var $this = $(this);
        $this.addClass('selected');
        var id = $this.dataOptions()['id'];
        loadDetailImageById(id);
    });
})();
