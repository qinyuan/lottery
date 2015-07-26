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
            function setSelectRowCss($html) {
                $html.css({
                    'width': '100%',
                    'padding': '4px'
                }).filter(function () {
                    var $this = $(this);
                    return $this.find('button').size() == 0 && $this.find('a').size() == 0;
                }).css({'cursor': 'pointer'}).hover(function () {
                    $(this).css('background-color', '#cccccc');
                }, function () {
                    $(this).css('background-color', '#ffffff');
                });
            }

            function adjustPanelWidth($filterDiv) {
                var maxWidth = 0;
                $filterDiv.find('>div >div').each(function () {
                    maxWidth = Math.max(maxWidth, JSUtils.getChineseStringLength($(this).text()));
                });
                $filterDiv.css('width', 10 + maxWidth * 9);
            }

            var html = '<div class="filter-menu">';
            html += '<div class="rank"><div class="asc">升序</div><div class="desc">降序</div>';
            html += '</div>';
            var $html = $(html).appendTo($parent);
            $html.css({
                'position': 'absolute',
                'background-color': '#ffffff',
                'top': '100%',
                'right': 0,
                'min-width': '100px',
                'width': 'auto',
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
            setSelectRowCss($html.find('>div >div'));

            this.getDistinctItems($parent.getParentByTagName('th').attr('class'), function (distinctItems) {
                var html = '<div class="filter-items">';

                // checkboxes
                for (var i = 0, len = distinctItems.length; i < len; i++) {
                    var item = distinctItems[i];
                    //var text = item.hasOwnProperty('text') ? item['text'] : '(空白)';
                    var text = item['text'];
                    html += '<div><input type="checkbox" value="' + text + '"';
                    if (item.checked) {
                        html += ' checked';
                    }
                    html += '/><span class="checkbox-label">' + text + '</span></div>'
                }

                // select or deselect all
                html += '<div><a href="javascript:void(0)" onclick="return selectAllValues(this);">全选</a>';
                html += '&nbsp;<a href="javascript:void(0)" onclick="return unSelectAllValues(this);">全不选</a></div>';

                // submit or cancel
                html += '<div><button type="button" class="btn btn-success btn-xs" onclick="return submitFilterValues(this);">确定</button>';
                html += '&nbsp;<button type="button" class="btn btn-default btn-xs" onclick="return cancelFilterValues(this);">取消</button></div>';

                html += '</div>';

                var $html = $(html).appendTo($parent.find('>div'));
                setSelectRowCss($html.find('>div'));
                $html.click(function () {
                    var $filterButton = $(this).getParentByTagNameAndClass('div', 'filter').find('>button');
                    $filterButton.attr('prevent-blur', 'true');
                });
                $html.find('input').css({'vertical-align': '-10%', 'margin-right': '3px'});
                $html.find('span.checkbox-label').click(function () {
                    $(this).parent().find('input[type=checkbox]').trigger('click');
                });
                adjustPanelWidth($parent.find('>div'));
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
    }).blur(function () {
        var $this = $(this);
        var $parent = $this.parent();
        setTimeout(function () {
            if ($this.attr('prevent-blur')) {
                $this.attr('prevent-blur', null);
                $this.focus();
            } else {
                filterPanel.clear($parent);
            }
        }, 200);
    });
    cancelFilterValues = function (target) {
        $(target).getParentByTagNameAndClass('div', 'filter').find('>button').trigger('blur');
    };
    submitFilterValues = function (target) {
        var $filter = $(target).getParentByTagNameAndClass('div', 'filter');
        var filterField = $filter.parent().attr('class');
        var filterValues = [];
        var allChecked = true;
        $filter.find('input[type=checkbox]').each(function () {
            if (this.checked) {
                filterValues.push($(this).val());
            } else {
                allChecked = false;
            }
        });

        if (allChecked) {
            $.post('admin-user-list-filter-remove.json', {
                filterField: filterField
            }, JSUtils.normalAjaxCallback);
        }

        JSUtils.postArrayParams('admin-user-list-filter.json', {
            filterField: filterField,
            filterValues: filterValues
        }, JSUtils.normalAjaxCallback);
    };

    $('#statisticLink').addClass('emphasize');
})();
function selectAllValues(target) {
    $(target).parent().parent().find('input[type=checkbox]').each(function () {
        this.checked = true;
    });
}

function unSelectAllValues(target) {
    $(target).parent().parent().find('input[type=checkbox]').each(function () {
        this.checked = false;
    });
}

var submitFilterValues, cancelFilterValues;
