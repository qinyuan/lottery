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
        return true;
    });

    function clearError() {
        $security.find('span.error').hide();
    }
})();
(function () {
    // code about tel
    var $tel = $('#tel');
    var $newTel = $tel.getInputByName('newTel');

    $tel.setDefaultButtonByClass('ok');
    $tel.find('button.ok').click(function (e) {
        e.preventDefault();

        clearError();
        validateTel(function () {
            var $password = $tel.getInputByName('password');
            var password = $password.val();
            if (password == '') {
                showError($password, '密码不能为空');
                return;
            }

            $.post('setting-change-tel.json', {
                password: password,
                tel: $newTel.val()
            }, function (data) {
                if (data.success) {
                    alert('手机号码修改成功');
                    location.reload();
                } else {
                    alert(data.detail);
                }
            });
        }, function (info) {
            showError($newTel, info);
        });
    });

    $newTel.blur(function () {
        validateTel(function () {
            $newTel.next().hide();
        }, function (info) {
            $newTel.next().text(info).twinkle(3);
        });
    });

    function validateTel(successCallback, failCallback) {
        var $newTel = $tel.getInputByName('newTel');
        var newTel = $newTel.val();
        if ($.trim(newTel) == '') {
            failCallback && failCallback('新手机号不能为空');
            return;
        } else if (!JSUtils.validateTel(newTel)) {
            failCallback && failCallback('新手机号格式错误');
            return;
        }

        var oldTel = $tel.find('span.old-tel').text();
        if (oldTel == newTel) {
            failCallback && failCallback('新手机号未作修改');
            return false;
        }

        $.post('setting-validate-tel.json', {'tel': newTel}, function (data) {
            if (data.success) {
                successCallback && successCallback();
                $('#telValidate').slideUp(500);
            } else {
                failCallback && failCallback(data.detail);
                if (data.detail.indexOf('占用')) {
                    $('#telValidate').slideDown(500);
                }
            }
        });
        return true;
    }

    function clearError() {
        $tel.find('span.error').hide();
        $('#telValidate').slideUp(500);
    }
})();
(function () {
    // code about email
    var $email = $('#email').setDefaultButtonByClass('ok');
    var $newEmail = $email.getInputByName('email').blur(function () {
        validateEmail(function () {
            clearError();
        }, function (detail) {
            $newEmail.next().text(detail).twinkle(3);
        });
    });
    var $result = $email.find('span.result');
    var $button = $email.find('button.ok').click(function () {
        clearError();
        var $this = $(this);
        $this.text('正在处理...');
        validateEmail(function () {
            var $password = $email.getInputByName('password');
            var password = $password.val();
            if ($.trim(password) == '') {
                showError($password, '密码不能为空');
                recoverButtonText();
                return false;
            }

            $.post('setting-update-email.json', {
                password: password,
                email: $newEmail.val()
            }, function (data) {
                if (data.success) {
                    var newEmail = $newEmail.val();
                    var loginPage = JSUtils.getEmailLoginPage($newEmail.val());
                    $result.find('a').attr('href', loginPage);
                    $result.find('a.email').text(newEmail);
                    $result.show();
                } else {
                    alert(data.detail);
                    $result.hide();
                }
                $this.text('发送验证邮件');
            });
        }, function (detail) {
            showError($newEmail, detail);
        });
    });

    function recoverButtonText() {
        $button.text('发送验证邮件');
        $result.hide();
    }

    function validateEmail(successCallback, failCallback) {
        var newEmail = $newEmail.val();
        if ($.trim(newEmail) == '') {
            failCallback && failCallback('邮箱不能为空');
            return false;
        } else if (!JSUtils.validateEmail(newEmail)) {
            failCallback && failCallback('邮箱格式错误');
            return false;
        }

        var $oldEmail = $email.find('span.old-email');
        var oldEmail = $oldEmail.text();
        if (oldEmail == newEmail) {
            failCallback && failCallback('新邮箱与旧邮箱相同');
            return false;
        }

        $.post('setting-validate-email.json', {'email': $newEmail.val()}, function (data) {
            if (data.success) {
                successCallback && successCallback()
            } else {
                failCallback && failCallback(data.detail);
            }
        });
    }

    function clearError() {
        $result.hide();
        $email.find('span.error').hide();
    }
})();
(function () {
    // code about share
    var $liveness = $('#liveness');
    var $sharePanel = $liveness.find('div.share-panel');
    var $share = $liveness.find('span.share').click(function () {
        if ($sharePanel.css('display') == 'none') {
            $sharePanel.fadeIn(500);
        } else {
            $sharePanel.fadeOut(500, function () {
                // patch bug in linux chrome
                var color = $share.css('color');
                if (color == 'rgb(153, 153, 153)' || color == '#999999') {
                    $share.css('color', '#999998');
                } else {
                    $share.css('color', '#999999');
                }
            });
        }
    });
})();
function showError($input, errorInfo) {
    $input.focusOrSelect().next().text(errorInfo).twinkle(3);
}
if ($('div.main-body > div.right div.body input').size() > 0) {
    $('div.main-body > div.right div.body').focusFirstTextInput();
}
