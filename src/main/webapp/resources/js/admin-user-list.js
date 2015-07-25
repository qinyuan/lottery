;
(function () {
    var filterPanel = {
        getDistinctItems: function () {
            var items = [];
            items.push({
                'text': 'item1',
                'checked': true
            });
            items.push({
                'text': 'item2',
                'checked': false
            });
            return items;
        },
        clear: function ($parent) {
            $parent.find('div').remove();
        },
        build: function ($parent) {
            var html = '<div class="filter-menu">';
            html += '<div class="rank"><div class="asc">升序</div><div class="desc">降序</div>';
            html += '</div>';

            html += '<div class="filter-items">';
            html += '<div><input type="checkbox" name="select-all"/>(全选)</div>';
            var distinctItems = this.getDistinctItems();
            for (var i = 0, len = distinctItems.length; i < len; i++) {
                var item = distinctItems[i];
                html += '<div><input type="checkbox" name="selectedItems" value="' + item['text'] + '"';
                if (item.checked) {
                    html += ' checked';
                }
                html += '/>' + item['text'] + '</div>'
            }
            html += '</div>';

            var $html = $(html).css({
                'position': 'absolute',
                'background-color': '#ffffff',
                'top': '100%',
                'right': 0,
                'width': '100px',
                'border': '1px solid #cccccc',
                'cursor': 'default',
                'text-align': 'left',
                'z-index': 1
            }).appendTo($parent);
            $html.find('div.rank').css({
                'border-bottom': '1px solid #cccccc'
            }).find('>div').click(function () {
                var $this = $(this);
                var $th = $this.getParentByTagName('th');
                location.href = JSUtils.updateUrlParam({
                    'orderField': $th.attr('class'),
                    'orderType': $this.attr('class')
                });
            });
            $html.find('>div >div').css({
                'width': '100%',
                'cursor': 'pointer',
                'padding': '4px'
            }).hover(function () {
                $(this).css('background-color', '#cccccc');
            }, function () {
                $(this).css('background-color', '#ffffff');
            }).find('input').css({'vertical-align': '-10%', 'margin-right': '3px'});
        }
    };

    $('table div.filter button.filter').click(function () {
        filterPanel.build($(this).parent());
    }).blur(function () {
        var $parent = $(this).parent();
        setTimeout(function () {
            filterPanel.clear($parent);
        }, 200);
    });

    $('#statisticLink').addClass('emphasize');
})();
