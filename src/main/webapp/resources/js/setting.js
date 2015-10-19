(function () {
    var $navigations = $('div.main-body > div.left div.nav div a').click(function () {
        var nav = this.href.replace(/^.*\=/, '');
        toPage(nav);
    });

    var nav = JSUtils.getUrlHash('nav');
    if (nav == null) {
        nav = 1;
    }
    toPage(nav);

    function toPage(navIndex) {
        $navigations.each(function (index) {
            var currentIndex = index + 1;
            var $this = $(this);
            var text = $this.text();
            var $parent = $this.parent();
            $parent.empty();
            if (currentIndex == navIndex) {
                $parent.html('<span>' + text + '</span>');
            } else {
                $parent.html('<a href="#nav=' + currentIndex + '">' + text + '</a>');
            }
        });
    }
})();
