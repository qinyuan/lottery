;
(function () {
    $('table.normal td.content').each(function(){
        JSUtils.limitTextLength($(this), 30);
    });
})();