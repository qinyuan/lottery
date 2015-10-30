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
        $personalInfo.find('div.edit-submit').slideUp(200);
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
    var $oldPassword = $security.find('div.old input').blur(function () {
        validateOldPassword();
    });
    var $newPassword = $security.find('div.new input').blur(function () {
        validateNewPassword();
    });
    var $newPassword2 = $security.find('div.new2 input').blur(function () {
        validateNewPassword2();
    });
    $security.find('button.ok').click(function (e) {
        e.preventDefault();

        if (!validateOldPassword()) {
            $oldPassword.focusOrSelect();
            return false;
        } else if (!validateNewPassword()) {
            $newPassword.focusOrSelect();
            return false;
        } else if (!validateNewPassword2()) {
            $newPassword2.focusOrSelect();
            return false;
        }

        $.post('setting-change-password.json', {
            oldPassword: $oldPassword.val(),
            newPassword: $newPassword.val()
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

    function validateOldPassword() {
        var oldPassword = $oldPassword.val();
        if (oldPassword == '') {
            $oldPassword.showValidation(false, '原密码不能为空');
            return false;
        }

        $oldPassword.showValidation(true);
        return true;
    }

    function validateNewPassword() {
        var newPassword = $newPassword.val();
        if ($.trim(newPassword) == '') {
            $newPassword.showValidation(false, '新密码不能为空');
            return false;
        } else if (newPassword.length < 6) {
            $newPassword.showValidation(false, '新密码不能少于6位字符');
            return false;
        } else if (newPassword == $oldPassword.val()) {
            $newPassword.showValidation(false, '新密码不能与原密码相同');
            return false;
        }

        $newPassword.showValidation(true);
        return true;
    }

    function validateNewPassword2() {
        var newPassword2 = $newPassword2.val();
        if ($newPassword.val() != newPassword2) {
            $newPassword2.showValidation(false, '两次输入的密码不一致');
            return false;
        }

        $newPassword2.showValidation(true);
        return true;
    }
})();
(function () {
    // code about tel
    var $tel = $('#tel');
    var $newTel = $tel.getInputByName('newTel').blur(function () {
        validateTel();
    });
    var $password = $tel.getInputByName('password').blur(function () {
        validatePassword();
    });
    var $telValidate = $('#telValidate');

    $tel.setDefaultButtonByClass('ok');
    $tel.find('button.ok').click(function (e) {
        e.preventDefault();

        $telValidate.slideUp(500);
        validateTel(function () {
            validatePassword(function () {
                $.post('setting-change-tel.json', {
                    password: $password.val(),
                    tel: $newTel.val()
                }, function (data) {
                    if (data.success) {
                        alert('手机号码修改成功');
                        location.reload();
                    } else {
                        alert(data.detail);
                    }
                });
            });
        });
    });

    function validatePassword(successCallback, failCallback) {
        var password = $password.val();
        if (password == '') {
            $password.showValidation(false, '密码不能为空');
            JSUtils.invokeIfIsFunction(failCallback);
            return;
        }

        $password.showValidation(true);
        JSUtils.invokeIfIsFunction(successCallback);
    }

    function validateTel(successCallback, failCallback) {
        var newTel = $newTel.val();
        if ($.trim(newTel) == '') {
            $newTel.showValidation(false, '新手机号不能为空');
            JSUtils.invokeIfIsFunction(failCallback);
            return;
        } else if (!JSUtils.validateTel(newTel)) {
            $newTel.showValidation(false, '新手机号格式错误');
            JSUtils.invokeIfIsFunction(failCallback);
            return;
        }

        var oldTel = $tel.find('span.old-tel').text();
        if (oldTel == newTel) {
            $newTel.showValidation(false, '新手机号未作修改');
            JSUtils.invokeIfIsFunction(failCallback);
            return false;
        }

        $.post('setting-validate-tel.json', {'tel': newTel}, function (data) {
            if (data.success) {
                $newTel.showValidation(true);
                JSUtils.invokeIfIsFunction(successCallback);
                $telValidate.slideUp(500);
            } else {
                $newTel.showValidation(false, data.detail);
                JSUtils.invokeIfIsFunction(failCallback);
                if (data.detail.indexOf('占用')) {
                    $telValidate.slideDown(500);
                }
            }
        });
        return true;
    }
})();
(function () {
    // code about email
    var $email = $('#email').setDefaultButtonByClass('ok');
    var $newEmail = $email.getInputByName('email').blur(function () {
        validateEmail();
    });
    var $password = $email.getInputByName('password').blur(function () {
        validatePassword();
    });
    var $result = $email.find('span.result');
    var $button = $email.find('button.ok').click(function () {
        //clearError();
        $result.hide();
        var $this = $(this);
        $this.text('正在处理...');
        validateEmail(function () {
            validatePassword(function () {
                $.post('setting-update-email.json', {
                    password: $password.val(),
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
            }, function () {
                recoverButtonText();
            });
        }, function () {
            recoverButtonText();
        });
    });

    function recoverButtonText() {
        $button.text('发送验证邮件');
        $result.hide();
    }

    function validateEmail(successCallback, failCallback) {
        var newEmail = $newEmail.val();
        if ($.trim(newEmail) == '') {
            $newEmail.showValidation(false, '邮箱不能为空');
            JSUtils.invokeIfIsFunction(failCallback);
            return false;
        } else if (!JSUtils.validateEmail(newEmail)) {
            $newEmail.showValidation(false, '邮箱格式错误');
            JSUtils.invokeIfIsFunction(failCallback);
            return false;
        }

        var $oldEmail = $email.find('span.old-email');
        var oldEmail = $oldEmail.text();
        if (oldEmail == newEmail) {
            $oldEmail.showValidation(false, '新邮箱与旧邮箱相同');
            JSUtils.invokeIfIsFunction(failCallback);
            return false;
        }

        $.post('setting-validate-email.json', {'email': $newEmail.val()}, function (data) {
            if (data.success) {
                $newEmail.showValidation(true);
                JSUtils.invokeIfIsFunction(successCallback);
            } else {
                $newEmail.showValidation(false, data.detail);
                JSUtils.invokeIfIsFunction(failCallback);
            }
        });
    }

    function validatePassword(successCallback, failCallback) {
        var password = $password.val();
        if ($.trim(password) == '') {
            $password.showValidation(false, '密码不能为空');
            recoverButtonText();
            failCallback && failCallback();
            return;
        }

        $password.showValidation(true);
        successCallback && successCallback();
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
