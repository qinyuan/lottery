;
(function () {
    $('table.normal td.content').each(function () {
        var $this = $(this);
        var receiver = $this.getParentByTagName('tr').find('td.receiver').text();
        $this.text($this.text().replace('{{user}}', receiver));
    }).each(function () {
        JSUtils.limitTextLength($(this), 30);
    });
})();