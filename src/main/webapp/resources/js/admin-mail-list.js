;
(function () {
    $('table.normal td.subject').each(function () {
        var $this = $(this);
        var receiver = $this.getParentByTagName('tr').find('td.receiver').text();
        $this.text($this.text().replace('{{user}}', receiver));
    });
    $('table.normal td.content').each(function () {
        var $this = $(this);
        var receiver = $this.getParentByTagName('tr').find('td.receiver').text();
        $this.text($this.text().replace('{{user}}', receiver));
    }).each(function () {
        JSUtils.limitTextLength($(this), 30);
    });

    JSUtils.loadTableFilterEvents('admin-mail-list-distinct-values.json', 'admin-mail-list-filter.json',
        'admin-mail-list-filter-remove.json');
})();
$('#statisticLink').addClass('emphasize');