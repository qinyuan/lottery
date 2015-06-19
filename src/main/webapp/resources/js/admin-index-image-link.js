;
(function () {
    $('div.main-body div.image').mousemove(function (e) {
        var offset = JSUtils.getOffsetByEvent(e);
        var x = offset.offsetX;
        var y = offset.offsetY;
        console.log(x + ',' + y);
    });
})();