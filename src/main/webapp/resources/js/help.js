;
(function () {
    function get$SelectedList() {
        return  $('div.main-body div.left div.help-list li.selected');
    }

    function getGroupId() {
        var $selectedList = get$SelectedList();
        if ($selectedList.size() == 0) {
            return null;
        } else {
            return $selectedList.getParentByTagNameAndClass('div', 'help-list').dataOptions('helpGroupId');
        }
    }

    var pageUrl = location.href.toString().replace(/\#.*$/, '');
    $('div.main-body div.left div.help-list li a').each(function () {
        var $this = $(this);
        var href = $this.attr('href');
        if (href.substr(0, 1) == '#') {
            if (href == location.hash.toString()) {
                $this.getParentByTagName('li').addClass('selected');
            }
            $this.attr('href', pageUrl + href);
        }
    }).click(function () {
        var $this = $(this);
        if ($this.getParentByTagName('ul').find('li.selected').size() == 0) {
            location.href = $this.attr('href');
            location.reload();
        } else {
            $this.getParentByTagNameAndClass('div', 'help-list').parent()
                .find('li.selected').removeClass('selected');
            $this.getParentByTagName('li').addClass('selected');
        }
    });
    $.post('query-help-items.json', {
        'id': getGroupId()
    }, function (data) {
        var html = JSUtils.handlebars('help-group-content-template', {
            'groupTitle': data.title,
            'helpItems': data.items
        });
        $('div.main-body > div > div.right').html(html);
        if (location.hash != '') {
            JSUtils.scrollTop($(location.hash), 0);
        }
    });
    $('#loginNavigationLink').removeClass('emphasize');
    if (location.pathname.indexOf('/admin') < 0) {
        $('#helpNavigationLink').addClass('emphasize');
    } else {
        $('#helpNavigationLink').prev().addClass('emphasize');
    }
})();