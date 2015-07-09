;
(function () {
    var $div = $('div.commodity-select');
    $div.find('ul.dropdown-menu a').click(function () {
        var $this = $(this);
        var commodityId = $this.dataOptions('id');
        var text = $this.text();
        $div.find('button').html(text + ' <span class="caret"></span>');
        $div.find('input[name=commodityId]').val(commodityId);
    });
})();
