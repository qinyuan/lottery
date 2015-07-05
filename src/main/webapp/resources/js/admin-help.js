;
(function () {
    function get$HelpListBySubElement($element) {
        return $element.getParentByTagNameAndClass('div', 'help-list');
    }

    function getIdByHelpGroupList($helpGroupList) {
        return $helpGroupList.dataOptions('helpGroupId');
    }

    function editHelpListTitle($titleDiv) {
        var $content = $titleDiv.find('span.content');
        var title = $.trim($titleDiv.text());
        $content.empty().html('<input type="text" class="form-control" value="' + title + '">')
            .after('<span class="info">按回车键完成输入</span>');
        $titleDiv.find('input').focusOrSelect().blur(function () {
            var $this = $(this);
            var text = $this.val();
            var $helpList = get$HelpListBySubElement($this);
            var helpGroupId = getIdByHelpGroupList($helpList);
            if (helpGroupId) {
                $.post('admin-edit-help-group.json', {
                    'id': helpGroupId,
                    'title': text
                }, function (data) {
                    if (data.success) {
                        $this.getParentByTagName('span').empty().html(text).next().remove();
                    } else {
                        $this.getParentByTagName('span').empty().html(data['oldText']).next().remove();
                        alert(data.detail);
                    }
                });
            } else {
                $.post('admin-add-help-group.json', {
                    'title': text
                }, function (data) {
                    if (data.success) {
                        $this.getParentByTagName('span').empty().html(text).next().remove();
                        $helpList.dataOptions('helpGroupId', data.detail);
                    } else {
                        get$HelpListBySubElement($this).remove();
                        alert(data.detail);
                    }
                });
            }
        }).keydown(function (e) {
            if (JSUtils.isEnterKeyCode(e.keyCode)) {
                $(this).trigger('blur');
            }
        });
    }

    $('div.main-body > div > div.left div.add-help-group img').click(function () {
        var newHelpListHtml = JSUtils.handlebars("help-group-template", {'helpGroupTitle': '新建分组'});
        var $newHelpList = $(newHelpListHtml);
        $newHelpList.insertBefore($(this).getParentByTagName('div'));
        editHelpListTitle($newHelpList.find('div.title'));
    });
    $('div.main-body > div > div.left div.help-list ul li').hover(function () {
        var $this = $(this);
        if (!$this.hasClass('selected')) {
            $this.addClass('hover');
        }
    }, function () {
        var $this = $(this);
        if ($this.hasClass('hover')) {
            $this.removeClass('hover');
        }
    });
    deleteHelpGroup = function (target) {
        var $helpList = get$HelpListBySubElement($(target));
        var helpGroupId = getIdByHelpGroupList($helpList);
        $.post('admin-delete-help-group.json', {
            'id': helpGroupId
        }, function (data) {
            if (data.success) {
                $helpList.remove();
            } else {
                alert(data.detail);
            }
        });
    };
    editHelpGroup = function (target) {
        var $helpList = get$HelpListBySubElement($(target));
        editHelpListTitle($helpList.find('div.title'));
    };
    rankUpHelpGroup = function (target) {
        var $helpList = get$HelpListBySubElement($(target));
        var $prev = $helpList.prev();
        if (!($prev.is('div') && $prev.hasClass('help-list'))) {
            return;
        }

        var helpGroupId = getIdByHelpGroupList($helpList);
        $.post('admin-rank-up-help-group.json', {
            'id': helpGroupId
        }, function (data) {
            if (data.success) {
                $helpList.moveToPrev();
            } else {
                alert(data.detail);
            }
        });
    };
    rankDownHelpGroup = function (target) {
        var $helpList = get$HelpListBySubElement($(target));
        var $next = $helpList.next();
        if (!($next.is('div') && $next.hasClass('help-list'))) {
            return;
        }

        var helpGroupId = getIdByHelpGroupList($helpList);
        $.post('admin-rank-down-help-group.json', {
            'id': helpGroupId
        }, function (data) {
            if (data.success) {
                $helpList.moveToNext();
            } else {
                alert(data.detail);
            }
        });
    }
})();
var deleteHelpGroup, editHelpGroup, rankUpHelpGroup, rankDownHelpGroup;
