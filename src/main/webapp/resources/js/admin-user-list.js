;
(function () {
    var filterPanel = {
        getDistinctItems: function (alias, callback) {
            var href = JSUtils.updateUrlParam('alias', alias);
            href = 'admin-user-list-distinct-values.json?' + href.replace(/^.*\?/, '');
            $.get(href, function (data) {
                callback(data);
            });
        },
        clear: function ($parent) {
            $parent.find('div').remove();
        },
        build: function ($parent) {
            function setInsertHtmlCss($html) {
                $html.find('>div >div').css({
                    'width': '100%',
                    'cursor': 'pointer',
                    'padding': '4px'
                }).hover(function () {
                    $(this).css('background-color', '#cccccc');
                }, function () {
                    $(this).css('background-color', '#ffffff');
                });
            }

            var html = '<div><div class="filter-menu">';
            html += '<div class="rank"><div class="asc">升序</div><div class="desc">降序</div>';
            html += '</div></div>';
            var $html = $(html).appendTo($parent);
            $html.css({
                'position': 'absolute',
                'background-color': '#ffffff',
                'top': '100%',
                'right': 0,
                'width': '100px',
                'border': '1px solid #cccccc',
                'cursor': 'default',
                'text-align': 'left',
                'z-index': 1
            });
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
            setInsertHtmlCss($html.find('>div'));

            this.getDistinctItems($parent.getParentByTagName('th').attr('class'), function (distinctItems) {
                var html = '<div class="filter-items">';
                html += '<div><input type="checkbox" name="select-all"/>(全选)</div>';

                for (var i = 0, len = distinctItems.length; i < len; i++) {
                    var item = distinctItems[i];
                    html += '<div><input type="checkbox" name="selectedItems" value="' + item['text'] + '"';
                    if (item.checked) {
                        html += ' checked';
                    }
                    html += '/>' + item['text'] + '</div>'
                }
                html += '</div>';
                var $html = $(html).appendTo($parent.find('>div'));
                setInsertHtmlCss($html);
                $html.find('input').css({'vertical-align': '-10%', 'margin-right': '3px'});
            });
        }
    };

    $('table div.filter button.filter').click(function () {
        var $parent = $(this).parent();
        if ($parent.find('div').size() > 0) {
            filterPanel.clear($parent);
        } else {
            filterPanel.build($parent);
        }
    })/*.blur(function () {
     var $parent = $(this).parent();
     setTimeout(function () {
     filterPanel.clear($parent);
     }, 200);
     })*/;

    $('#statisticLink').addClass('emphasize');
})();
