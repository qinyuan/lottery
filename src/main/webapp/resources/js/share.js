(function () {
    var $copyLinkInput = $('div.main-body div.left > div.copy input[type=text]');
    var $copySuccess = $('div.main-body div.left > div.copy span.info');
    $('div.main-body div.left > div.copy button').zclip({
        path: "resources/js/lib/jquery-zclip/ZeroClipboard.swf",
        copy: function () {
            return $copyLinkInput.val();
        },
        afterCopy: function () {
            $copySuccess.fadeIn(300);
            setTimeout(function () {
                $copySuccess.fadeOut(1000);
            }, 2000);
        }
    });
    $('div.main-body div.right div.body table tbody td').hover(function () {
        $(this).parent().addClass('hover');
    }, function () {
        $(this).parent().removeClass('hover');
    }).click(function () {
        var $this = $(this);
        if ($this.hasClass('action')) {
            return;
        }

        var $parent = $this.parent();
        if ($parent.hasClass('selected')) {
            $parent.removeClass('selected');
        } else {
            $parent.parent().find('tr.selected').removeClass('selected');
            $parent.addClass('selected');
        }
    });
})();