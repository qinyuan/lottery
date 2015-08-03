;
(function () {
    // codes about changing password
    var $changePassword = $('#changePassword');
    $changePassword.click(function () {
        var $tr = $(this).getParentByTagName('tr');
        if ($tr.next().hasClass('change-password')) {
            $tr.next().focusFirstTextInput();
            return;
        }

        var $html = $(JSUtils.handlebars("changePasswordTemplate"));
        $html.insertAfter($tr).focusFirstTextInput();
        $html.find('button[name=ok]').click(function () {
            var $oldPassword = $html.getInputByName('oldPassword');
            var oldPassword = $oldPassword.val();
            if ($.trim(oldPassword) == '') {
                alert('现密码不能为空！');
                $oldPassword.focusOrSelect();
                return;
            }

            var $newPassword = $html.getInputByName('newPassword');
            var newPassword = $newPassword.val();
            if ($.trim(newPassword) == '') {
                alert('新密码不能为空！');
                $newPassword.focusOrSelect();
                return;
            }

            if (newPassword.length < 6) {
                alert('密码应为6~20个字符，区分大小写！');
                $newPassword.focusOrSelect();
                return;
            }

            var $newPassword2 = $html.getInputByName('newPassword2');
            var newPassword2 = $newPassword2.val();
            if (newPassword != newPassword2) {
                alert('两次输入的新密码不一致！');
                $newPassword2.focusOrSelect();
                return;
            }

            $.post('personal-center-update-password.json', $html.find('form').serialize(), function (data) {
                if (data.success) {
                    $html.find('td').html('<h3>密码修改成功！</h3>');
                    setTimeout(function () {
                        hideAndClear($html);
                    }, 1000);
                } else {
                    alert(data.detail);
                }
            });
        });
        $html.find('button[name=cancel]').click(function () {
            hideAndClear($html);
        });
        $html.setDefaultButton('changePasswordSubmit');
    });
    $('#changePassword2').click(function () {
        $changePassword.trigger('click');
        JSUtils.scrollTop($changePassword);
    });
})();
(function () {
    // codes about changing tel
    $('#changeTel').click(function () {
        var $tr = $(this).getParentByTagName('tr');
        if ($tr.next().hasClass('change-tel')) {
            $tr.next().focusFirstTextInput();
            return;
        }

        var $html = $(JSUtils.handlebars("changeTelTemplate"));
        $html.insertAfter($tr).focusFirstTextInput();
        $html.find('button[name=ok]').click(function (e) {
            e.preventDefault();   // prevent normal submit

            var $tel = $html.getInputByName('tel');
            var tel = $tel.val();
            if ($.trim(tel) == '') {
                alert('手机号不能为空！');
                $tel.focusOrSelect();
                return false;
            }

            if (!JSUtils.validateTel(tel)) {
                alert('手机号必须为11位数字！');
                $tel.focusOrSelect();
                return false;
            }

            $.post('personal-center-update-tel.json', $html.find('form').serialize(), function (data) {
                if (data.success) {
                    $html.getParentByTagName('table').find('span.tel').text(tel);
                    $html.find('td').html('<h3>手机号修改成功！</h3>');
                    setTimeout(function () {
                        hideAndClear($html);
                    }, 1000);
                } else {
                    alert(data.detail);
                }
            });
            return false;
        });
        $html.find('button[name=cancel]').click(function () {
            hideAndClear($html);
        });
        $html.setDefaultButton('changeTelSubmit');
    });
})();
(function () {
    // codes about changing email
    $('#changeEmail').click(function () {
        var $tr = $(this).getParentByTagName('tr');
        if ($tr.next().hasClass('change-email')) {
            $tr.next().focusFirstTextInput();
            return;
        }

        var $html = $(JSUtils.handlebars("changeEmailTemplate"));
        $html.insertAfter($tr).focusFirstTextInput();
        $html.find('button[name=ok]').click(function (e) {
            e.preventDefault();   // prevent normal submit

            var $email = $html.getInputByName('email');
            var email = $email.val();
            if ($.trim(email) == '') {
                alert('邮箱地址不能为空！');
                $email.focusOrSelect();
                return false;
            }

            if (!JSUtils.validateEmail(email)) {
                alert('邮箱地址格式错误！');
                $email.focusOrSelect();
                return false;
            }

            var $this = $(this);
            var oldEmail = $this.getParentByTagName('table').find('span.email').text();
            if (oldEmail.toLowerCase() == email.toLowerCase()) {
                alert('新邮箱不能与原邮箱相同！');
                $email.focusOrSelect();
                return false;
            }

            $this.text('正在处理...');
            var url = 'personal-center-update-email.json';
            $.post(url, $html.find('form').serialize(), function (data) {
                if (data.success) {
                    var loginPage = JSUtils.getEmailLoginPage(email);
                    var info = JSUtils.handlebars('changeEmailResultTemplate', {
                        loginPage: loginPage,
                        email: email
                    });
                    $html.find('td').html(info).find('a.resend').click(function () {
                        $.post(url, {email: email}, function (data) {
                            if (data.success) {
                                $html.find('span.resend-success').showForAWhile(3000);
                            } else {
                                $html.find('span.resend-fail').text(data.detail).showForAWhile(3000);
                            }
                        });
                    });
                } else {
                    alert(data.detail);
                    $this.text('确定');
                }
            });
            return false;
        });
        $html.find('button[name=cancel]').click(function () {
            hideAndClear($html);
        });
        $html.setDefaultButton('changeTelSubmit');
    });
})();
function hideAndClear($element) {
    $element.hide(200, function () {
        $element.remove();
    });
}