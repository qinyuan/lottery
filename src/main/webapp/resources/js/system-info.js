;
(function () {
    var $unreadItems = $('div.main-body div.right div.system-info div.unread');
    var $readItems = $('div.main-body div.right div.system-info div.read');
    $('#infoTypeSelector').on('switch-change', function (e, data) {
        var unread = data.value;
        if (unread) {
            $readItems.hide();
            $unreadItems.fadeIn(500);
        } else {
            $unreadItems.hide();
            $readItems.fadeIn(500);
        }
    });

    function loadInfoItems($parent, items) {
        var $html = $(JSUtils.handlebars('infoItemTemplate', items));
        $html.appendTo($parent);
    }

    loadInfoItems($unreadItems, {'items': [
        {
            id: 1,
            content: 'AAAAAAAAAAAAAAAa',
            buildTime: '2012-12-12 15:15:15',
            unread: true
        },
        {

            id: 2,
            content: 'BBBBBBBBBBBBBBBBB',
            buildTime: '2012-12-13 15:15:15',
            unread: true
        }
    ]});
})();