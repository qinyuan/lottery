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
        var $html = $(JSUtils.handlebars('infoItemTemplate', {'items': items}));
        $html.find('a.mark-as-read').click(function () {
            var $infoItem = $(this).getParentByTagNameAndClass('div', 'info-item');
            var id = $infoItem.dataOptions('id');
            $.post('system-info-mark-as-read.json', {id: id}, JSUtils.normalAjaxCallback);
        });
        $html.appendTo($parent);
    }

    loadInfoItems($unreadItems, window['unreadSystemInfoItems']);
    loadInfoItems($readItems, window['readSystemInfoItems']);
})();