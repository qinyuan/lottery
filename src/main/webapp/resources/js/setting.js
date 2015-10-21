(function () {
    // code about personal information
    $('#editUsername').click(function () {
        var $div = $(this).getParentByTagName('div');
        $div.find('span.text').hide();
        $div.find('span.input').show().find('input').focusOrSelect();
        $div.next().slideDown(200);
    });
    var $personalInfo = $('#personalInfo');
    $personalInfo.find('button.ok').click(function (e) {
        e.preventDefault();

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
        var oldUsername = $.trim($personalInfo.find('old-username').text());
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
    $personalInfo.find('a.cancel').click(function () {
        $personalInfo.find('edit-submit').slideUp(200);
        $personalInfo.find('span.input').hide();
        $personalInfo.find('span.text').show();
    });
    $personalInfo.setDefaultButtonByClass('ok');

    var $pagination = $('ul.pagination');
    if ($pagination.size() > 0) {
        var width = $pagination.width();
        $pagination.css('margin-left', '-' + (width / 2) + 'px').show();
    }
})();
(function () {
    // code about security
    if ($.url.param('index') == 2) {
        $('body').focusFirstTextInput();
    }
    var $security = $('#security');
    $security.setDefaultButtonByClass('ok');
    $security.find('input').blur(function () {
        clearError();
    });
    $security.find('button.ok').click(function (e) {
        e.preventDefault();
        clearError();

        var $oldPassword = $security.find('div.old input');
        var oldPassword = $oldPassword.val();
        if (oldPassword == '') {
            showError($oldPassword, '原密码不能为空');
            return false;
        }

        var $newPassword = $security.find('div.new input');
        var newPassword = $newPassword.val();
        if ($.trim(newPassword) == '') {
            showError($newPassword, '新密码不能为空');
            return false;
        } else if (newPassword.length < 6) {
            showError($newPassword, '新密码不能少于6位字符');
            return false;
        } else if (newPassword == oldPassword) {
            showError($newPassword, '新密码不能与原密码相同');
            return false;
        }

        var $newPassword2 = $security.find('div.new2 input');
        var newPassword2 = $newPassword2.val();
        if (newPassword != newPassword2) {
            showError($newPassword2, '两次输入的密码不一致');
            return false;
        }

        $.post('setting-change-password.json', {
            oldPassword: oldPassword,
            newPassword: newPassword
        }, function (data) {
            if (data.success) {
                alert('密码修改成功');
                location.reload();
            } else {
                alert(data.detail);
            }
        });
    });

    function clearError() {
        $security.find('span.error').hide();
    }

    function showError($input, errorInfo) {
        $input.focusOrSelect().next().text(errorInfo).twinkle(3);
    }
})();
