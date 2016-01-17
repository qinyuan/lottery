;
(function () {
    (function () {
        // patch mobile agent
        var email = $('div.main-body div.form div.email span.email').text();
        if (window['isMobileUserAgent'] && JSUtils.validateEmail(email)) {
            var validateLogin = function () {
                $.post("isLogin.json", {email: email }, function (data) {
                    if (data.result) {
                        showSuccessInfo();
                    }
                });
            };
            setInterval(function () {
                validateLogin();
            }, 1000);
            validateLogin();
        }
    })();

    var $form = $('div.main-body div.form').focusFirstTextInput();
    $form.setDefaultButtonByClass('ok');
    var $submit = $form.find('div.submit button.ok').click(function (e) {
        e.preventDefault();
        if (updateSubmitStatus()) {
            submitUserInfo();
            return true;
        } else {
            //e.preventDefault();
            return false;
        }
    });
    var $email = $form.getInputByName('email').blur(function () {
        validateEmail();
    });
    var $qqNumber = $form.getInputByName('qqNumber').blur(function () {
        validateQQNumber();
    });
    var $username = $form.getInputByName('username').blur(function () {
        validateUsername();
    }).keyup(function () {
        validateUsername();
    });
    var $password = $form.getInputByName('password').blur(function () {
        validateAllPassword();
    }).keyup(function () {
        validateAllPassword();
    });
    var $password2 = $form.getInputByName('password2').blur(function () {
        validatePassword2();
    }).keyup(function () {
        validatePassword2();
    });
    var $checkbox = $form.find('div.subscribe input[type=checkbox]').click(function () {
        updateSubmitStatus();
    });

    function showSuccessInfo() {
        var $registerSuccess = $('div.main-body div.register-success');
        $form.hide();
        $registerSuccess.show();
    }

    function validateUsername(callback) {
        var username = $username.val();
        if ($.trim(username) == '') {
            $username.showValidation(false, '用户名不能为空！');
        } else if (JSUtils.validateTel(username)) {
            $username.showValidation(false, '用户名不能为手机号！');
        } else if (username.indexOf('@') >= 0) {
            $username.showValidation(false, '用户名不能包含"@"字符！');
        } else {
            $.get('validate-username.json', {'username': username, 'withoutLink': true}, function (data) {
                if (data.success) {
                    $username.showValidation(true);
                    JSUtils.invokeIfIsFunction(callback);
                } else {
                    $username.showValidation(false, data.detail);
                }
                updateSubmitStatus();
            });
        }
        updateSubmitStatus();
    }

    function validatePassword(callback) {
        var password = $password.val();
        if ($.trim(password) == '') {
            $password.showValidation(false, '密码不能为空！');
        } else if (password.length < 6) {
            $password.showValidation(false, '密码不能少于6位字符！');
        } else if (password.indexOf(' ') >= 0) {
            $password.showValidation(false, '密码不能包含空格！');
        } else {
            $password.showValidation(true);
            JSUtils.invokeIfIsFunction(callback);
        }
        updateSubmitStatus();
    }

    function validatePassword2(callback) {
        var password = $password.val();
        var password2 = $password2.val();
        if ($.trim(password2) == '') {
            $password2.showValidation(false, '确认密码不能为空！');
        } else if (password != password2) {
            $password2.showValidation(false, '两次输入的密码需要一致！');
        } else {
            $password2.showValidation(true);
            JSUtils.invokeIfIsFunction(callback);
        }
        updateSubmitStatus();
    }

    function validateAllPassword() {
        validatePassword(function () {
            if ($password2.val() != '') {
                validatePassword2();
            }
        });
    }

    function validateEmail(callback) {
        var email = $email.val();
        if ($.trim(email) == '') {
            $email.showValidation(false, '邮箱地址不能为空！');
        } else if (!JSUtils.validateEmail(email)) {
            $email.showValidation(false, '邮箱地址格式错误！');
        } else {
            $email.showValidation(true);
            $form.setInputValue('to', email);
            JSUtils.invokeIfIsFunction(callback);
        }
        updateSubmitStatus();
    }

    function validateQQNumber(callback) {
        var qqNumber = $qqNumber.val();
        if ($.trim(qqNumber) == '') {
            $qqNumber.showValidation(false, 'QQ号码不能为空！');
        } else if (!JSUtils.isNumberString(qqNumber)) {
            $qqNumber.showValidation(false, 'QQ号码必须为数字！');
        } else {
            $qqNumber.showValidation(true);
            $form.setInputValue('to', qqNumber + "@qq.com");
            JSUtils.invokeIfIsFunction(callback);
        }
    }

    function validateSubscribe() {
        return $checkbox.get(0).checked;
    }

    function updateSubmitStatus() {
        var inputSize = $form.find('div.input').size();
        var validInputSize = $form.find('span.valid').filter(function () {
            return $(this).css('display') != 'none';
        }).size();

        if (validateSubscribe() && validInputSize >= inputSize) {
            $submit.attr('disabled', false);
            return true;
        } else {
            $submit.attr('disabled', true);
            return false;
        }
    }

    function submitUserInfo() {
        var serialKey = $.url.param('serial');
        if (serialKey) {
            $.post('register-complete-user-info.json', {
                serialKey: serialKey,
                username: $username.val(),
                password: $password.val()
            }, function (data) {
                postCallback(data);
            });
        } else {
            var params = {
                qqOpenId: $form.getInputByName('qqOpenId').val(),
                username: $username.val()/*,
                 password: $password.val()*/
            };
            if (window['numberMode']) {
                params.qqNumber = $qqNumber.val();
                $.post('register-complete-user-info-by-qq-number.json', params, function (data) {
                    postCallback(data);
                });
            } else {
                params.email = $email.val();
                $.post('register-complete-user-info-by-qq.json', params, function (data) {
                    postCallback(data);
                });
            }
        }

        function postCallback(data) {
            if (data.success) {
                if (!window['isMobileUserAgent']) {
                    showSuccessInfo();
                }
            } else {
                alert(data.detail);
            }
        }
    }

    (function () {
        // login by qq
        if (!window['byQQ']) {
            return;
        }

        var $fetchQQInfo = $('div.main-body div.fetch-qq-info');
        var waiting = $('#fetchInfoWaiting').text('').buildWaitingText();

        if (!JSUtils.getUrlHash('access_token')) {
            setTimeout(function () {
                showQueryError();
            }, 1000);
            return;
        }

        var nickname;
        QC.api("get_user_info", {}).success(function (s) {
            nickname = s.data['nickname'];
            QC.Login.getMe(function (openId, accessToken) {
                $.get('try-to-login-by-qq-open-id.json', {qqOpenId: openId}, function (data) {
                    if (data.result) {
                        toIndex();
                    } else {
                        showUserInfoForm(nickname, openId);
                    }
                });
            });
        }).error(function () {
            showQueryError();
        }).complete(function () {
        });

        function showQueryError() {
            waiting.stop();
            $fetchQQInfo.find('span.waiting').hide();
            $fetchQQInfo.find('span.error').show();
        }

        function toIndex() {
            $fetchQQInfo.hide();
            $('div.main-body div.qq-account-created').show();
            setTimeout(function () {
                location.href = "index.html";
            }, 500);
        }

        function showUserInfoForm(nickname, qqOpenId) {
            $fetchQQInfo.hide();
            var $root = $('div.main-body div.form-wrapper').show();
            $root.setInputValue('username', nickname);
            $root.setInputValue('qqOpenId', qqOpenId);
            validateUsername(function () {
                $root.focusFirstTextInput();
            });
            setTimeout(function () {
                $root.focusFirstTextInput();
            }, 500);
        }
    })();
})();
(function () {
    // code about exception
    $('div.main-body div.re-register').click(function () {
        showRegisterForm();
    });
    $('div.main-body div.completed-link a.to-login').click(function () {
        showRegisterForm();
    });
})();
