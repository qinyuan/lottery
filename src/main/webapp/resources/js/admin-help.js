;
(function () {
    function get$HelpListBySubElement($element) {
        return $element.getParentByTagNameAndClass('div', 'help-list');
    }

    function getIdByHelpGroupList($helpGroupList) {
        return $helpGroupList.dataOptions('helpGroupId');
    }

    function showGroupItemByItemId(id) {
        $.post('query-help-item.json', {
            'id': id
        }, function (item) {
            showGroupItemForm(item['groupId'], id, item['icon'], item['title'], item['content']);
        });
    }

    function showGroupItemForm(groupId, id, icon, title, content) {
        if (groupId == null) {
            return;
        }
        var top = (JSUtils.getWindowHeight() - $groupItemFormDiv.height()) / 2;
        if (top < 0) {
            top = 0;
        }
        top = top + $(window).scrollTop();
        $groupItemFormDiv.css('top', top);

        $groupItemFormDiv.find('input[name=groupId]').val(groupId);
        $groupItemFormDiv.find('input[name=id]').val(id);
        $groupItemFormDiv.find('input[name=icon]').val(icon);
        $groupItemFormDiv.find('input[name=iconFile]').val(null);
        $groupItemFormDiv.find('input[name=title]').val(title);
        groupItemFormContentEditor.setData(content);
        JSUtils.showTransparentBackground();
        $groupItemFormDiv.fadeIn(300).focusFirstTextInput();
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
    };
    var $groupItemFormDiv = $('#groupItemFormDiv');
    var groupItemFormContentEditor = CKEDITOR.replace("helpItemContent");
    $groupItemFormDiv.find('button[name=cancelButton]').click(function () {
        $groupItemFormDiv.fadeOut(300, function () {
            JSUtils.hideTransparentBackground();
        });
    });
    $groupItemFormDiv.get$Title = function () {
        return $groupItemFormDiv.find('input[name=title]');
    };
    $groupItemFormDiv.find('button[name=submitButton]').click(function (e) {
        var $title = $groupItemFormDiv.get$Title();
        if ($title.trimVal() == '') {
            alert('标题不能为空');
            $title.focusOrSelect();
            e.preventDefault();
            return false;
        }

        if ($.trim(groupItemFormContentEditor.getData()) == '') {
            alert('正文不能为空');
            groupItemFormContentEditor.focus();
            e.preventDefault();
            return false;
        }
        return true;
    });


    editHelpItemByRightDiv = function (target) {
        var $itemDiv = $(target).getParentByTagNameAndClass('div', 'item');
        var id = $itemDiv.attr('id').replace(/\D/g, '');
        showGroupItemByItemId(id);
    };
    addHelpItem = function (target) {
        var $helpList = get$HelpListBySubElement($(target));
        var groupId = getIdByHelpGroupList($helpList);
        showGroupItemForm(groupId);
    };
    editHelpItem = function (target) {
        var $target = $(target);
        var id = $target.getParentByTagName('li').dataOptions('helpItemId');
        showGroupItemByItemId(id);
    };
    rankUpHelpItem = function (target) {
        var $li = $(target).getParentByTagName('li');
        var $prev = $li.prev();
        if (!($prev.is('li'))) {
            return;
        }

        var helpItemId = $li.dataOptions('helpItemId');
        $.post('admin-rank-up-help-item.json', {
            'id': helpItemId
        }, function (data) {
            if (data.success) {
                $li.moveToPrev();
            } else {
                alert(data.detail);
            }
        });
    };
    rankDownHelpItem = function (target) {
        var $li = $(target).getParentByTagName('li');
        var $next = $li.next();
        if (!($next.is('li'))) {
            return;
        }

        var helpItemId = $li.dataOptions('helpItemId');
        $.post('admin-rank-down-help-item.json', {
            'id': helpItemId
        }, function (data) {
            if (data.success) {
                $li.moveToNext();
            } else {
                alert(data.detail);
            }
        });
    };
    deleteHelpItem = function (target) {
        var $li = $(target).getParentByTagName('li');
        var helpItemId = $li.dataOptions('helpItemId');
        $.post('admin-delete-help-item.json', {
            'id': helpItemId
        }, function (data) {
            if (data.success) {
                $li.remove();
            } else {
                alert(data.detail);
            }
        });
    };
})();
var deleteHelpGroup, editHelpGroup, rankUpHelpGroup, rankDownHelpGroup,
    addHelpItem, deleteHelpItem, editHelpItem, rankUpHelpItem, rankDownHelpItem,
    editHelpItemByRightDiv;
