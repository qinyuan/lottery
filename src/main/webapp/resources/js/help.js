;
(function () {
    function editHelpListTitle($titleDiv) {
        var $content = $titleDiv.find('span.content');
        var title = $titleDiv.text();
        $content.empty().html('<input type="text" class="form-control" value="' + title + '">')
            .after('<span class="info">按回车键完成输入</span>');
        $titleDiv.find('input').focusOrSelect().blur(function () {
            var $this = $(this);
            var text = $this.val();
            $.post('admin-add-help-group.json', {
                'title': text
            }, function (data) {
                console.log(data);
                if (data.success) {
                    $this.getParentByTagName('span').empty().html(text).next().remove();
                } else {
                    $this.getParentByTagNameAndClass('div', 'help-list').remove();
                    alert(data.detail);
                }
            });
        }).keydown(function (e) {
            if (e.keyCode == 13) {
                $(this).trigger('blur');
            }
        });
    }

    $('div.main-body > div > div.left div.add-help-group img').click(function () {
        var $div = $(this).getParentByTagName('div');
        var newHelpListHtml = '<div class="help-list">';
        newHelpListHtml += '<div class="title"><span class="icon"></span><span class="content">新建分组</span></div>';
        newHelpListHtml += '<ul></ul>';
        newHelpListHtml += '</div>';
        var $newHelpList = $(newHelpListHtml);
        $newHelpList.insertBefore($div);
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
    var helpGroupActions = $('div.main-body > div > div.left div.help-list div.title div.action');
    helpGroupActions.find('img.delete').click(function () {
        var $helpList = $(this).getParentByTagNameAndClass('div', 'help-list');
        var helpGroupId = $helpList.dataOptions()['helpGroupId'];
        $.post('admin-delete-help-group.json', {
            'id': helpGroupId
        }, function (data) {
            console.log(data);
            if (data.success) {
                $helpList.remove();
            } else {
                alert(data.detail);
            }
        });
    });
})();