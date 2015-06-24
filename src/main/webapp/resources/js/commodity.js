;
(function () {
    var snapshotDisplaySize = 6;
    var $snapshots = $('div.body div.snapshots div.snapshot');
    var snapshotCount = $snapshots.size();
    var selectedId = window['selectedCommodityId'];

    function loadDetailImageById(id) {
        var $img = $('div.body div.detail img');
        $img.hide();
        $.post('commodity-info.json', {
            id: id
        }, function (data) {
            $img.attr('src', data['commodity']['detailImage']);
            $img.fadeIn(500);
            loadCommodityMap(data['commodityMaps']);
        })
    }

    function getVisibleSnapshots() {
        return $snapshots.filter(function () {
            return $(this).css('display') == 'block';
        });
    }

    function loadCommodityMap(commodityMaps) {
        var mapHtml = JSUtils.handlebars('mapTemplate', {'commodityMaps': commodityMaps});
        $('#commodityMap').html(mapHtml);
    }

    var $prevArrow = $('div.body div.snapshots div.prev');
    var $nextArrow = $('div.body div.snapshots div.next');
    if (snapshotCount > snapshotDisplaySize) {
        $prevArrow.click(function () {
            var $visibleSnapshots = getVisibleSnapshots();
            var $firstVisibleSnapshot = $visibleSnapshots.first();
            var $lastVisibleSnapshot = $visibleSnapshots.last();

            for (var i = 0; i < snapshotDisplaySize; i++) {
                $firstVisibleSnapshot = $firstVisibleSnapshot.prev();
                if ($firstVisibleSnapshot.size() > 0 && $firstVisibleSnapshot.hasClass('snapshot')) {
                    $lastVisibleSnapshot.hide(500);
                    $lastVisibleSnapshot = $lastVisibleSnapshot.prev();
                    $firstVisibleSnapshot.show(500);
                    if (i == 0) {
                        $nextArrow.show();
                    }
                } else {
                    if (i > 0) {
                        $prevArrow.hide();
                    }
                    break;
                }
            }
        }).show();
        $nextArrow.click(function () {
            var $visibleSnapshots = getVisibleSnapshots();
            var $firstVisibleSnapshot = $visibleSnapshots.first();
            var $lastVisibleSnapshot = $visibleSnapshots.last();

            for (var i = 0; i < snapshotDisplaySize; i++) {
                $lastVisibleSnapshot = $lastVisibleSnapshot.next();
                if ($lastVisibleSnapshot.size() > 0 && $lastVisibleSnapshot.hasClass('snapshot')) {
                    $firstVisibleSnapshot.hide(500);
                    $firstVisibleSnapshot = $firstVisibleSnapshot.next();
                    $lastVisibleSnapshot.show(500);
                    if (i == 0) {
                        $prevArrow.show();
                    }
                } else {
                    if (i > 0) {
                        $nextArrow.hide();
                    }
                    break;
                }
            }
        }).show();

    }

    for (var i = 0; i < snapshotCount; i++) {
        var $snapshot = $snapshots.eq(i);
        if ($snapshot.dataOptions()['id'] == selectedId) {
            $snapshot.addClass('selected');
            var startIndex = parseInt(i / snapshotDisplaySize) * snapshotDisplaySize;
            var endIndex = startIndex + 5;
            while (endIndex >= snapshotCount) {
                endIndex--;
                startIndex--;
            }
            for (var j = startIndex; j <= endIndex && j < snapshotCount; j++) {
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

    loadCommodityMap(window['commodityMaps']);
})();
