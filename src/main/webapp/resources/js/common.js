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

    function showLoginForm() {
        $springLoginForm.fadeIn(500).focusFirstTextInput();
    }

    function showRegisterForm() {
        $registerForm.fadeIn(500).focusFirstTextInput();
    }

    function showRegisterSuccess(email) {
        $registerSuccess.find('span.email').text(email);
        var mailLoginPage = 'http://mail.' + email.replace(/^.*\@/, '');
        $registerSuccess.find('a.to-mail-page').attr('href', mailLoginPage);
        $registerSuccess.fadeIn(500);
    }

    // actions of navigation link
    $('#loginNavigationLink').click(function () {
        JSUtils.showTransparentBackground();
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
        $springLoginForm.hide();
        JSUtils.hideTransparentBackground();
    });
    $springLoginForm.find('#switchToRegister').click(function () {
        $springLoginForm.hide();
        showRegisterForm();
    });

    // actions of registerForm
    var $registerForm = $('#registerForm').ajaxForm(function (data) {
        alert($registerForm.get$Email().val());
        if (data['success']) {
            JSUtils.showTransparentBackground();
            $registerForm.hide();
            showRegisterSuccess($registerForm.get$Email().val());
            $registerForm.find('form').get(0).reset();
        } else {
            alert(data['detail']);
            $identityCodeImage.trigger('click');
        }
    });
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
    $registerForm.validateEmail = function () {
        var $email = $registerForm.get$Email();
        var email = $email.val();
        if (!email) {
            $registerForm.showValidationByInput($email, '请输入您的常用邮箱');
            return false;
        } else if (!JSUtils.validateEmail(email)) {
            $registerForm.showValidationByInput($email, '请输入有效有邮箱地址');
            return false;
        } else {
            $registerForm.showValidationByInput($email);
            return true;
        }
    };
    $registerForm.get$Email().blur($registerForm.validateEmail);
    $registerForm.get$Username = function () {
        return $registerForm.find('input[name=username]');
    };
    $registerForm.validateUsername = function () {
        var $username = $registerForm.get$Username();
        var username = $username.val();
        if (!username) {
            $registerForm.showValidationByInput($username, '请输入您的用户名');
            return false;
        } else if (username.length < 2) {
            $registerForm.showValidationByInput($username, '用户名至少使用2个字符');
            return false;
        } else {
            $registerForm.showValidationByInput($username);
            return true;
        }
    };
    $registerForm.get$Username().blur($registerForm.validateUsername);
    $registerForm.get$Password = function () {
        return $registerForm.find('input[name=password]');
    };
    $registerForm.validatePassword = function () {
        var $password = $registerForm.get$Password();
        var password = $password.val();
        if (!password || password.length < 6) {
            $registerForm.showValidationByInput($password, '密码应为6-20个字符，区分大小写');
            return false;
        } else {
            $registerForm.showValidationByInput($password);
            return true;
        }
    };
    $registerForm.get$Password().blur($registerForm.validatePassword);
    $registerForm.get$Password2 = function () {
        return $registerForm.find('input[name=password2]');
    };
    $registerForm.validatePassword2 = function () {
        var $password2 = $registerForm.get$Password2();
        var password2 = $password2.val();
        if (!password2) {
            $registerForm.showValidationByInput($password2, '确认密码不能为空');
            return false;
        } else if (password2 != $registerForm.get$Password().val()) {
            $registerForm.showValidationByInput($password2, '两次输入的密码不一样，请重新输入');
            return false;
        } else {
            $registerForm.showValidationByInput($password2);
            return true;
        }
    };
    $registerForm.get$Password2().blur($registerForm.validatePassword2);
    $registerForm.get$IdentityCode = function () {
        return $registerForm.find('input[name=identityCode]');
    };
    $registerForm.validateIdentityCode = function () {
        var $identityCode = $registerForm.get$IdentityCode();
        var identityCode = $identityCode.val();
        if (!identityCode || identityCode.length != 4) {
            $registerForm.showValidationByInput($identityCode, '请正确填写验证码');
            return false;
        } else {
            $registerForm.showValidationByInput($identityCode);
            return true;
        }
    };
    $registerForm.get$IdentityCode().blur($registerForm.validateIdentityCode);
    $registerForm.find('input[type=text],input[type=password]').focus(function () {
        var $this = $(this);
        $this.next().removeClass('success').removeClass('fail');
        var $comment = $this.parent().next();
        var $info = $comment.find('span.info');
        var $error = $comment.find('span.error');
        $error.hide();
        $info.show();
    });
    $registerForm.find('button[name=loginSubmit]').click(function (e) {
        var valid = true;
        valid = $registerForm.validateEmail() && valid;
        valid = $registerForm.validateUsername() && valid;
        valid = $registerForm.validatePassword() && valid;
        valid = $registerForm.validatePassword2() && valid;
        valid = $registerForm.validateIdentityCode() && valid;
        if (!valid) {
            e.preventDefault();
            return false;
        }
        e.preventDefault();
        return false;
    });
    $registerForm.find('div.title div.close-icon').click(function () {
        $registerForm.hide();
        JSUtils.hideTransparentBackground();
    });
    $registerForm.find('#switchToLogin').click(function () {
        $registerForm.hide();
        showLoginForm();
    });
    var $identityCodeImage = $registerForm.find('div.body div.right div.input.identity-code img').click(function () {
        this.src = 'identity-code?id=' + new Date().getTime();
        $registerForm.get$IdentityCode().val('').focus();
    });
    $registerForm.find('div.body div.right div.input.identity-code a').click(function () {
        $identityCodeImage.trigger('click');
    });

    // actions of registerSuccess
    var $registerSuccess = $('#registerSuccess');
    $registerSuccess.find('div.title div.close-icon').click(function () {
        $registerSuccess.hide();
        JSUtils.hideTransparentBackground();
    });
    $registerSuccess.find('div.body a.resend').click(function () {
        // TODO need to implement resend function
        $registerSuccess.find('div.body span.resend-success').showForAWhile(1500);
    });

    // TODO remove this someday
    showRegisterForm();
    JSUtils.showTransparentBackground();
})();
