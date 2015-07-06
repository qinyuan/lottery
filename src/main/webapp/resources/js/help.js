;
(function () {
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
})();