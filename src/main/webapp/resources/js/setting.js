(function () {
    $('#editUsername').click(function () {
        var $div = $(this).getParentByTagName('div');
        $div.find('span.text').hide();
        $div.find('span.input').show().find('input').focusOrSelect();
        $div.next().slideDown(200);
    });
    var $editSubmit = $('div.main-body > div.right div.body div.edit-submit');
    $editSubmit.find('button.ok').click(function () {
        var $username = $('#usernameInput');
        var username = $username.val();
        if ($.trim(username) == '') {
            alert('昵称不能为空');
            $username.focusOrSelect();
            return;
        } else if (username.indexOf(' ') >= 0) {
            alert('昵称不能包含空格');
            $username.focusOrSelect();
            return;
        }
        var oldUsername = $.trim($editSubmit.prev().find('span.text').text());
        if (oldUsername == username) {
            alert('昵称未作修改');
            $username.focusOrSelect();
            return;
        }

        $.post("setting-change-username.json", {username: username}, function (data) {
            if (!data.success) {
                alert(data.detail);
                $username.focusOrSelect();
            } else {
                location.reload();
            }
        });
    });
    $editSubmit.find('a.cancel').click(function () {
        $editSubmit.slideUp(200);
        var $prev = $editSubmit.prev();
        $prev.find('span.input').hide();
        $prev.find('span.text').show();
    });
    $('div.main-body > div.right div.body').setDefaultButtonByClass('ok');

    var $pagination = $('ul.pagination');
    if ($pagination.size() > 0) {
        var width = $pagination.width();
        $pagination.css('margin-left', '-' + (width / 2) + 'px').show();
    }
})();
