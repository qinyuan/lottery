var angularUtils = {
    _module: null,
    /**
     * Usage:
     * controller(controllerName, func)
     * or
     * controller(func)
     */
    controller: function () {
        if (!this._module) {
            this._module = angular.module('main', []);
        }
        var argSize = arguments.length;
        if (argSize == 1) {
            this._module.controller('ContentController', ['$scope', '$http', arguments[0]]);
        } else if (argSize >= 2) {
            this._module.controller(arguments[0], ['$scope', '$http', arguments[1]]);
        }
        return this;
    }
};
(function () {
    // to show error information
    var errorInfo = $.url.param('errorInfo');
    if (errorInfo) {
        alert(errorInfo);
    }
})();
(function () {
    // actions about bootstrap style select form
    var $div = $('div.dropdown');
    $div.find('ul.dropdown-menu a').click(function () {
        var $this = $(this);
        var commodityId = $this.dataOptions('id');
        var text = $this.text();
        $div.find('button').html(text + ' <span class="caret"></span>');
        $div.find('input[type=hidden]:first').val(commodityId);
    });
})();
(function () {
    function showRegisterForm() {
        $registerForm.get$Inputs().each(function () {
            resetRegisterInput(this);
        });
        $registerForm.find('form').get(0).reset();
        $registerForm.find('img.identity-code').trigger('click');
        $registerForm.fadeIn(500).focusFirstTextInput();
    }

    function resetRegisterInput(element) {
        var $this = $(element);
        $this.next().removeClass('success').removeClass('fail');
        var $comment = $this.parent().next();
        var $info = $comment.find('span.info');
        var $error = $comment.find('span.error');
        $error.hide();
        $info.show();
    }

    function showRegisterSuccess(email) {
        $registerSuccess.find('span.email').text(email);
        var mailLoginPage = 'http://mail.' + email.replace(/^.*\@/, '');
        $registerSuccess.find('a.to-mail-page').attr('href', mailLoginPage);
        $registerSuccess.fadeIn(500);
    }

    function showActivateRemind(email) {
        JSUtils.showTransparentBackground();
        $activateRemind.find('span.email').text(email);
        var mailLoginPage = 'http://mail.' + email.replace(/^.*\@/, '');
        $activateRemind.find('a.to-mail-page').attr('href', mailLoginPage);
        $activateRemind.fadeIn(500);
    }

    showLoginForm = function (loginSuccessCallback) {
        $springLoginForm.find('form').get(0).reset();
        $springLoginForm.get$ErrorInfo().hide();
        $springLoginForm.fadeIn(500).focusFirstTextInput();
        $springLoginForm.loginSuccessCallback = loginSuccessCallback;
    };

    hideLoginForm = function () {
        $springLoginForm.hide();
        $springLoginForm.loginSuccessCallback = null;
    };

    // actions of navigation link
    $('#loginNavigationLink').click(function () {
        JSUtils.showTransparentBackground(1);
        showLoginForm();
    });
    $('#registerNavigationLink').click(function () {
        JSUtils.showTransparentBackground();
        showRegisterForm();
    });

    // actions of login form
    var $springLoginForm = $('#springLoginForm');
    $springLoginForm.find('div.body div.rememberLogin span').click(function () {
        var checkBox = $(this).parent().find('input[type=checkbox]').get(0);
        checkBox.checked = !checkBox.checked;
    });
    $springLoginForm.find('div.title div.close-icon').click(function () {
        hideLoginForm();
        JSUtils.hideTransparentBackground();
    });
    $springLoginForm.find('#switchToRegister').click(function () {
        hideLoginForm();
        showRegisterForm();
    });
    $springLoginForm.get$SubmitButton = function () {
        return $springLoginForm.find('button[name=loginSubmit]');
    };
    $springLoginForm.get$ErrorInfo = function () {
        return $springLoginForm.find('div.error-info');
    };
    $springLoginForm.get$SubmitButton().click(function (e) {
        e.preventDefault();
        var $username = $springLoginForm.find('input[name=j_username]');
        if ($username.trimVal() == '') {
            alert('帐号未填写');
            $username.focusOrSelect();
            return false;
        }
        var $password = $springLoginForm.find('input[name=j_password]');
        if ($password.trimVal() == '') {
            alert('密码未填写');
            $password.focusOrSelect();
            return false;
        }

        var formData = $springLoginForm.find('form').serialize();
        $.post('j_spring_security_ajax_check', formData, function (data) {
            if (data.success) {
                if ($springLoginForm.loginSuccessCallback) {
                    $springLoginForm.loginSuccessCallback();
                } else {
                    location.reload();
                }
            } else {
                $springLoginForm.get$ErrorInfo().twinkle(3);
            }
        });
        return false;
    });

    // actions of registerForm
    var $registerForm = $('#registerForm');
    $registerForm.showValidationByInput = function ($input, errorInfo) {
        var $parent = $input.parent();
        var $iconSpan = $parent.find('span.validate');
        var $comment = $parent.next();
        var $info = $comment.find('span.info');
        var $error = $comment.find('span.error');
        if (errorInfo) {
            $info.hide();
            $error.html(errorInfo).show();
            $iconSpan.removeClass('success').addClass('fail');
        } else {
            $error.hide();
            $info.show();
            $iconSpan.removeClass('fail').addClass('success');
        }
    };
    $registerForm.get$Email = function () {
        return $registerForm.find('input[name=email]');
    };
    $registerForm.validateEmail = function (callback) {
        var $email = $registerForm.get$Email();
        var email = $email.val();
        if (!email) {
            $registerForm.showValidationByInput($email, '请输入您的常用邮箱');
            $registerForm.valid = false;
            typeof(callback) == 'function' && callback();
        } else if (!JSUtils.validateEmail(email)) {
            $registerForm.showValidationByInput($email, '请输入有效有邮箱地址');
            $registerForm.valid = false;
            typeof(callback) == 'function' && callback();
        } else {
            $.get('validate-email.json', {
                'email': email
            }, function (data) {
                if (data.success) {
                    $registerForm.showValidationByInput($email);
                } else {
                    $registerForm.showValidationByInput($email, data.detail);
                }
                typeof(callback) == 'function' && callback();
            });
        }
    };
    $registerForm.get$Email().blur($registerForm.validateEmail);
    $registerForm.get$Username = function () {
        return $registerForm.find('input[name=username]');
    };
    $registerForm.validateUsername = function (callback) {
        var $username = $registerForm.get$Username();
        var username = $username.val();
        if (!username) {
            $registerForm.showValidationByInput($username, '请输入您的用户名');
            $registerForm.valid = false;
            typeof(callback) == 'function' && callback();
        } else if (username.length < 2) {
            $registerForm.showValidationByInput($username, '用户名至少使用2个字符');
            $registerForm.valid = false;
            typeof(callback) == 'function' && callback();
        } else {
            $.get('validate-username.json', {
                'username': username
            }, function (data) {
                if (data.success) {
                    $registerForm.showValidationByInput($username);
                } else {
                    $registerForm.showValidationByInput($username, data.detail);
                }
                typeof(callback) == 'function' && callback();
            });
        }
    };
    $registerForm.get$Username().blur($registerForm.validateUsername);
    $registerForm.get$Password = function () {
        return $registerForm.find('input[name=password]');
    };
    $registerForm.validatePassword = function (callback) {
        var $password = $registerForm.get$Password();
        var password = $password.val();
        if (!password || password.length < 6) {
            $registerForm.showValidationByInput($password, '密码应为6-20个字符，区分大小写');
            $registerForm.valid = false;
        } else {
            $registerForm.showValidationByInput($password);
        }
        typeof(callback) == 'function' && callback();
    };
    $registerForm.get$Password().blur($registerForm.validatePassword);
    $registerForm.get$Password2 = function () {
        return $registerForm.find('input[name=password2]');
    };
    $registerForm.validatePassword2 = function (callback) {
        var $password2 = $registerForm.get$Password2();
        var password2 = $password2.val();
        if (!password2) {
            $registerForm.showValidationByInput($password2, '确认密码不能为空');
            $registerForm.valid = false;
        } else if (password2 != $registerForm.get$Password().val()) {
            $registerForm.showValidationByInput($password2, '两次输入的密码不一样，请重新输入');
            $registerForm.valid = false;
        } else {
            $registerForm.showValidationByInput($password2);
        }
        typeof(callback) == 'function' && callback();
    };
    $registerForm.get$Password2().blur($registerForm.validatePassword2);
    $registerForm.get$IdentityCode = function () {
        return $registerForm.find('input[name=identityCode]');
    };
    $registerForm.validateIdentityCode = function (callback) {
        var $identityCode = $registerForm.get$IdentityCode();
        var identityCode = $identityCode.val();
        if (!identityCode || identityCode.length != 4) {
            $registerForm.showValidationByInput($identityCode, '请正确填写验证码');
            $registerForm.valid = false;
        } else {
            $registerForm.showValidationByInput($identityCode);
        }
        typeof(callback) == 'function' && callback();
    };
    $registerForm.get$IdentityCode().blur($registerForm.validateIdentityCode);
    $registerForm.get$Inputs = function () {
        return $registerForm.find('input[type=text],input[type=password]');
    };
    $registerForm.get$Inputs().focus(function () {
        resetRegisterInput(this);
    });
    $registerForm.get$Inputs().trigger('focus');
    $registerForm.find('button[name=loginSubmit]').click(function () {
        $registerForm.valid = true;
        $registerForm.validateEmail(function () {
            $registerForm.validateUsername(function () {
                $registerForm.validatePassword(function () {
                    $registerForm.validatePassword2(function () {
                        $registerForm.validateIdentityCode(function () {
                            if ($registerForm.valid) {
                                registerSubmit();
                            }
                        })
                    });
                })
            });
        });
        function registerSubmit() {
            $registerForm.find('form').ajaxSubmit({
                success: function (data) {
                    if (data['success']) {
                        JSUtils.showTransparentBackground();
                        $registerForm.hide();
                        showRegisterSuccess($registerForm.get$Email().val());
                        $registerForm.find('form').get(0).reset();
                    } else {
                        alert(data['detail']);
                        $registerForm.find('div.body div.right div.input.identity-code img').trigger('click');
                    }
                },
                resetForm: false,
                dataType: 'json'
            });
        }
    });
    $registerForm.find('form').submit(function () {
        return false;
    });
    $registerForm.find('div.title div.close-icon').click(function () {
        $registerForm.hide();
        JSUtils.hideTransparentBackground();
    });
    switchToLogin = function () {
        $registerForm.hide();
        showLoginForm();
    };
    $registerForm.find('#switchToLogin').click(switchToLogin);

    // actions of registerSuccess
    var $registerSuccess = $('#registerSuccess');
    $registerSuccess.find('div.title div.close-icon').click(function () {
        $registerSuccess.hide();
        JSUtils.hideTransparentBackground();
    });

    // remind activation
    $('div.activate-remind div.body a.resend').click(function () {
        var $body = $(this).parent();
        if ($body.size() > 0 && !$body.hasClass('body')) {
            $body = $body.parent();
        }
        var email = $body.find('span.email').text();
        $.get('resend-activate-email.json', {
            'email': email
        }, function (data) {
            if (data.success) {
                $body.find('span.resend-success').showForAWhile(1500);
            } else {
                $body.find('span.resend-fail').text(data.detail + '！').showForAWhile(1500);
            }
        });
    });

    var $activateRemind = $('#activateRemind');
    $activateRemind.find('div.title div.close-icon').click(function () {
        $activateRemind.hide();
        JSUtils.hideTransparentBackground();
    });
    if (window['unactivatedEmail']) {
        showActivateRemind(window['unactivatedEmail']);
    }

    // refresh identity code
    $('img.identity-code').click(function () {
        this.src = 'identity-code?id=' + new Date().getTime();
        $(this).prev().val('').focus();
    }).next().click(function () {
        $(this).prev().trigger('click');
    });
})();
var switchToLogin, showLoginForm, hideLoginForm;
