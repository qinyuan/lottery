;
(function () {
    $('div.main-body > div > div.left div.help-list ul li').hover(function () {
        var $this = $(this);
        if (!$this.hasClass('selected')) {
            $this.addClass('hover');
        }
    }, function () {
        var $this = $(this);
        if ($this.hasClass('hover')) {
            $this.removeClass('hover');
        }
    });
})();