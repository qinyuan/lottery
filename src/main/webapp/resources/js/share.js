(function () {
    var $copyLinkInput = $('div.main-body div.left > div.copy input[type=text]');
    var $copySuccess = $('div.main-body div.left > div.copy span.info');
    $('div.main-body div.left > div.copy button').zclip({
        //path: "js/ZeroClipboard.swf",
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
})();