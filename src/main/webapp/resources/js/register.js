;
(function () {
    var email = $('div.main-body div.form div.email span.email').text();
    if (window['isMobile'] && JSUtils.validateEmail(email)) {
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

    var $form = $('div.main-body div.form').focusFirstTextInput();
    $form.setDefaultButtonByClass('ok');
    var $submit = $form.find('div.submit button.ok').click(function (e) {
        if (updateSubmitStatus()) {
            $.post('register-complete-user-info.json', {
                serialKey: $.url.param('serial'),
                username: $username.val(),
                password: $password.val()
            }, function (data) {
                if (data.success) {
                    if (!window['isMobile']) {
                        showSuccessInfo();
                    }
                } else {
                    alert(data.detail);
                }
            });
            return true;
        } else {
            e.preventDefault();
            return false;
        }
    });
    var $username = $form.getInputByName('username').blur(function () {
        validateUsername();
    });
    var $password = $form.getInputByName('password').blur(function () {
        validatePassword(function () {
            if ($password2.val() != '') {
                validatePassword2();
            }
        });
    });
    var $password2 = $form.getInputByName('password2').blur(function () {
        validatePassword2();
    });
    var $checkbox = $form.find('div.subscribe input[type=checkbox]').click(function () {
        updateSubmitStatus();
    });

    function showSuccessInfo() {
        var $registerSuccess = $('div.main-body div.register-success');
        var remainSeconds = 5;
        var $remain = $registerSuccess.find('span.remain').text(remainSeconds);
        $form.hide();
        $registerSuccess.show();
        setInterval(function () {
            remainSeconds--;
            $remain.text(remainSeconds);
            if (remainSeconds == 0) {
                location.href = 'index.html';
            }
        }, 1000);
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
