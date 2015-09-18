(function () {
    $('div.main-body div.completed-link a.to-login').click(function () {
        window['switchToLogin'](function () {
            location.href = 'index.html';
        });
    });

    $('div.main-body div.re-register a').click(function () {
        $('#registerNavigationLink').trigger('click');
    });

    var validation = ({
        _get$Validation: function ($target) {
            var $validation = $target.next();
            if ($validation.size() == 0 || !$validation.is('span')) {
                $validation = $('<span><span class="validate"></span><span class="error-info"></span></span>');
                $validation.appendTo($target.getParentByTagNameAndClass('span', 'right'));
            }
            return $validation;
        },
        showSuccessIcon: function ($target, callback) {
            var $validation = this._get$Validation($target);
            $validation.find('span.validate').removeClass('fail').addClass('success');
            $validation.find('span.error-info').text('');
            $validation.show();
            typeof(callback) == 'function' && callback();
        },
        showErrorInfo: function ($target, content) {
            var $validation = this._get$Validation($target);
            $validation.find('span.validate').removeClass('success').addClass('fail');
            $validation.find('span.error-info').html(content);
            $validation.twinkle(3);
        },
        init: function () {
            return this;
        }
    }).init();

    $('#submitButton').click(function (e) {
        e.preventDefault();

        validateUsername(function () {
            validatePassword(function () {
                validatePassword2(function () {
                    validateTel(function () {
                        $.post('register-complete-user-info.json', $form.serialize(), function (data) {
                            if (data.success) {
                                location.reload();
                            } else {
                                alert(data.detail);
                            }
                        });
                    })
                });
            });
        });
    });

    var $form = $('#userInfo');
    var $username = $form.getInputByName('username').blur(function () {
        validateUsername();
    });
    var $password = $form.getInputByName('password').blur(function () {
        validatePassword();
    });
    var $password2 = $form.getInputByName('password2').blur(function () {
        validatePassword2();
    });
    var $tel = $form.getInputByName('tel').blur(function () {
        validateTel();
    });

    setNormalInputBlurEvent($form.getInputByName('realName'));
    setNormalInputBlurEvent($form.find('select[name=gender]'));
    setNormalInputBlurEvent($form.find('select[name=birthdayDay]'));
    setNormalInputBlurEvent($form.find('select[name=constellation]'));
    setNormalInputBlurEvent($form.getInputByName('hometown'));
    setNormalInputBlurEvent($form.getInputByName('residence'));

    function validateUsername(successCallback) {
        var username = $username.val();
        if ($.trim(username) == '') {
            validation.showErrorInfo($username, '用户名不能为空！');
        } else if (JSUtils.validateTel(username)) {
            validation.showErrorInfo($username, '用户名不能为手机号！');
        } else if (username.indexOf('@') >= 0) {
            validation.showErrorInfo($username, '用户名不能包含"@"字符！');
        } else {
            $.get('validate-username.json', {'username': username, 'withoutLink': true}, function (data) {
                if (data.success) {
                    validation.showSuccessIcon($username, successCallback);
                } else {
                    validation.showErrorInfo($username, data.detail);
                }
            });
        }
    }

    function validatePassword(successCallback) {
        var password = $password.val();
        if ($.trim(password) == '') {
            validation.showErrorInfo($password, '密码不能为空！');
        } else if (password.length < 6) {
            validation.showErrorInfo($password, '密码不能少于6位字符！');
        } else if (password.indexOf(' ') >= 0) {
            validation.showErrorInfo($password, '密码不能包含空格！');
        } else {
            validation.showSuccessIcon($password, successCallback);
        }
    }

    function validatePassword2(successCallback) {
        var password = $password.val();
        var password2 = $password2.val();
        if ($.trim(password2) == '') {
            validation.showErrorInfo($password2, '确认密码不能为空！');
        } else if (password != password2) {
            validation.showErrorInfo($password2, '两次输入的密码需要一致！');
        } else {
            validation.showSuccessIcon($password2, successCallback);
        }
    }

    function validateTel(successCallback) {
        var tel = $tel.val();
        if (tel.length == 0) {
            validation.showSuccessIcon($tel, successCallback);
        }

        if (JSUtils.validateTel(tel)) {
            $.post('validate-tel-without-login.json', {tel: tel}, function (data) {
                if (data.success) {
                    validation.showSuccessIcon($tel, successCallback);
                } else {
                    var text = data.detail;
                    if (text.indexOf('被使用') > 0) {
                        text += '<a style="margin-left:10px;" href="' + window['telValidateDescriptionPage']
                            + '" target="_blank">验证</a>';
                    }
                    validation.showErrorInfo($tel, text);
                }
            });
        } else {
            validation.showErrorInfo($tel, '号码必须为11位数字');
        }
    }

    function setNormalInputBlurEvent($target) {
        $target.blur(function () {
            validation.showSuccessIcon($(this));
        });
    }
})();
$('div.main-body div.right').focusFirstTextInput();
