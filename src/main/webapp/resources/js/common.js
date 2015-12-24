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
    var login = ({
        show: function (loginSuccessCallback) {
            JSUtils.showTransparentBackground(1);
            this.$div.find('form').get(0).reset();
            this.$errorInfo.hide();
            this.$div.fadeIn(500).focusFirstTextInput();
            this.loginSuccessCallback = loginSuccessCallback;
        },
        hide: function () {
            this.$div.hide();
            this.loginSuccessCallback = null;
        },
        init: function () {
            var self = this;

            this.$div = $('#springLoginForm');
            this.$div.find('div.body div.rememberLogin span').click(function () {
                var checkBox = $(this).parent().find('input[type=checkbox]').get(0);
                checkBox.checked = !checkBox.checked;
            });
            this.$div.find('div.title div.close-icon').click(function () {
                self.hide();
                JSUtils.hideTransparentBackground(1);
            });
            this.$div.find('#switchToRegister').click(function () {
                self.hide();
                register.show();
            });

            this.$submitButton = this.$div.find('button[name=loginSubmit]');
            this.$submitButton.click(function (e) {
                e.preventDefault();

                var $username = self.$div.find('input[name=j_username]');
                if ($username.trimVal() == '') {
                    alert('帐号未填写');
                    $username.focusOrSelect();
                    return false;
                }
                var $password = self.$div.find('input[name=j_password]');
                if ($password.trimVal() == '') {
                    alert('密码未填写');
                    $password.focusOrSelect();
                    return false;
                }

                var formData = self.$div.find('form').serialize();
                $.post('j_spring_security_ajax_check', formData, function (data) {
                    if (data.success) {
                        if (self.loginSuccessCallback && typeof(self.loginSuccessCallback) == 'function') {
                            $.get('navigation', function (data) {
                                if (data) {
                                    $('div.header > div.content > div.right-navigation').empty().html(data);
                                }
                            });
                            self.loginSuccessCallback();
                        } else {
                            location.reload();
                        }
                    } else {
                        self.$errorInfo.twinkle(3);
                    }
                });
                return false;
            });
            this.$errorInfo = this.$div.find('div.error-info');

            showLoginForm = function (callback) {
                self.show(callback);
            };
            hideLoginForm = function () {
                self.hide();
            };

            return this;
        }
    } ).init();

    $('#loginNavigationLink').click(function () {
        login.show();
    });

    // actions of registerForm
    var register = ({
        show: function () {
            JSUtils.showTransparentBackground(1);
            this.setTitle('欢迎注册');
            this.$registerSubmit.text('立即注册');
            this.$div.find('form').show();
            this.$div.find('div.result').hide();
            this.$div.find('img.identity-code').trigger('click');
            var self = this;
            this.$inputs.each(function () {
                self.resetInput(this);
            });
            this.$div.find('form').get(0).reset();
            this.$div.fadeIn(500).focusFirstTextInput();
        },
        showResult: function () {
            this.setTitle('消息提示');
            this.$div.find('form').hide();

            this.$result.find('>div.text').hide();

            var email;
            if (this.$div.getInputByName('registerType').val() == 'email') {
                this.$result.find('>div.email').show();
                email = this.$email.val();
            } else {
                this.$result.find('>div.qq').show();
                email = this.$qq.val() + '@qq.com';
            }

            this.$result.show();
            this.$resendResult.text('');
            this.$result.find('div.text span.email').text(email);
            var loginPage = JSUtils.getEmailLoginPage(email);
            this.$result.find('div.text a').attr('href', loginPage);
        },
        setTitle: function (title) {
            this.$div.find('div.title div.text').text(title);
        },
        resetInput: function (element) {
            var $this = $(element);
            $this.parent().find('span.validate').removeClass('success').removeClass('fail');
            var $comment = $this.parent().next();
            var $info = $comment.find('span.info');
            var $error = $comment.find('span.error');
            $error.hide();
            $info.show();
        },
        showValidationByInput: function ($input, errorInfo) {
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
        },
        validateEmail: function (callback) {
            var email = this.$email.val();
            var self = this;
            if (!email) {
                this.showValidationByInput(this.$email, '请输入您的常用邮箱');
                this.valid = false;
                JSUtils.invokeIfIsFunction(callback);
            } else if (!JSUtils.validateEmail(email)) {
                this.showValidationByInput(this.$email, '请输入有效的邮箱地址');
                this.valid = false;
                JSUtils.invokeIfIsFunction(callback);
            } else {
                $.get('validate-email.json', {'email': email}, function (data) {
                    if (data.success) {
                        self.showValidationByInput(self.$email);
                    } else {
                        self.showValidationByInput(self.$email, data.detail);
                    }
                    JSUtils.invokeIfIsFunction(callback);
                });
            }
        },
        validateQQ: function (callback) {
            var qq = this.$qq.val();
            var self = this;
            if (!qq) {
                this.showValidationByInput(this.$qq, '请输入您的常用QQ');
                this.valid = false;
                JSUtils.invokeIfIsFunction(callback);
            } else if (!qq.match(/^\d+$/g)) {
                this.showValidationByInput(this.$qq, '请输入有效的QQ号码');
                this.valid = false;
                JSUtils.invokeIfIsFunction(callback);
            } else {
                $.get('validate-qq.json', {'qq': qq}, function (data) {
                    if (data.success) {
                        self.showValidationByInput(self.$qq);
                    } else {
                        self.showValidationByInput(self.$qq, data.detail);
                    }
                    JSUtils.invokeIfIsFunction(callback);
                });
            }
        },
        validateUsername: function (callback) {
            var username = this.$username.val();
            var self = this;
            if (!username) {
                this.showValidationByInput(this.$username, '请输入您的用户名');
                this.valid = false;
                typeof(callback) == 'function' && callback();
            } else if (username.length < 2) {
                this.showValidationByInput(this.$username, '用户名至少使用2个字符');
                this.valid = false;
                typeof(callback) == 'function' && callback();
            } else {
                $.get('validate-username.json', {'username': username }, function (data) {
                    if (data.success) {
                        self.showValidationByInput(self.$username);
                    } else {
                        self.showValidationByInput(self.$username, data.detail);
                    }
                    typeof(callback) == 'function' && callback();
                });
            }
        },
        validatePassword: function (callback) {
            var password = this.$password.val();
            if (!password || password.length < 6) {
                this.showValidationByInput(this.$password, '密码应为6-20个字符，区分大小写');
                this.valid = false;
            } else {
                this.showValidationByInput(this.$password);
            }
            typeof(callback) == 'function' && callback();
        },
        validatePassword2: function (callback) {
            var password2 = this.$password2.val();
            if (!password2) {
                this.showValidationByInput(this.$password2, '确认密码不能为空');
                this.valid = false;
            } else if (password2 != this.$password.val()) {
                this.showValidationByInput(this.$password2, '两次输入的密码不一样，请重新输入');
                this.valid = false;
            } else {
                this.showValidationByInput(this.$password2);
            }
            typeof(callback) == 'function' && callback();
        },
        validateIdentityCode: function (callback) {
            var identityCode = this.$identityCode.val();
            if (!identityCode || identityCode.length != 4) {
                this.showValidationByInput(this.$identityCode, '请正确填写验证码');
                this.valid = false;
            } else {
                this.showValidationByInput(this.$identityCode);
            }
            typeof(callback) == 'function' && callback();
        },
        init: function () {
            var self = this;
            this.$div = $('#registerForm');

            this.$email = this.$div.find('input[name=email]');
            this.$email.blur(function () {
                setTimeout(function () {
                    self.validateEmail();
                }, 200); // user may has no email then click the link below input, so delay for 200 millisecond
            });

            this.$qq = this.$div.find('input[name=qq]');
            this.$qq.blur(function () {
                setTimeout(function () {
                    self.validateQQ();
                }, 200); // user may has no qq then click the link below input, so delay for 200 millisecond
            });

            this.$username = this.$div.find('input[name=username]');
            this.$username.blur(function () {
                self.validateUsername();
            });

            this.$password = this.$div.find('input[name=password]');
            this.$password.blur(function () {
                self.validatePassword();
            });

            this.$password2 = this.$div.find('input[name=password2]');
            this.$password2.blur(function () {
                self.validatePassword2();
            });

            this.$identityCode = this.$div.find('input[name=identityCode]');
            this.$identityCode.blur(function () {
                self.validateIdentityCode();
            });

            this.$inputs = this.$div.find('input[type=text],input[type=password]');
            this.$inputs.focus(function () {
                self.resetInput(this);
            });
            this.$inputs.trigger('focus');

            this.$registerSubmit = this.$div.find('button[name=registerSubmit]').click(function (e) {
                e.preventDefault();
                self.valid = true;
                /*self.validateEmail(function () {
                 self.validateUsername(function () {
                 self.validatePassword(function () {
                 self.validatePassword2(function () {
                 self.validateIdentityCode(function () {
                 if (self.valid) {
                 registerSubmit();
                 }
                 })
                 });
                 })
                 });
                 });*/
                if (self.$emailInput.css('display') != 'none') {
                    self.validateEmail(function () {
                        self.validateIdentityCode(function () {
                            if (self.valid) {
                                registerSubmit('email');
                            }
                        });
                    });
                } else if (self.$qqInput.css('display') != 'none') {
                    self.validateQQ(function () {
                        self.validateIdentityCode(function () {
                            if (self.valid) {
                                registerSubmit('qq');
                            }
                        });
                    });
                }

                function registerSubmit(registerType) {
                    self.$div.setInputValue('registerType', registerType);
                    var waiting = self.$registerSubmit.text('正在处理').buildWaitingText();
                    $.post('register-submit.json', self.$div.find('form').serialize(), function (data) {
                        waiting.stop();
                        self.$registerSubmit.text('立即注册');
                        if (data['success']) {
                            self.showResult();
                        } else {
                            alert(data['detail']);
                            self.$div.find('div.body div.right div.input.identity-code img').trigger('click');
                        }
                    });
                }
            });
            this.$div.find('form').submit(function () {
                return false;
            });
            this.$div.find('div.title div.close-icon').click(function () {
                self.$div.hide();
                JSUtils.hideTransparentBackground();
            });
            switchToLogin = function (loginSuccessCallback) {
                self.$div.hide();
                login.show(loginSuccessCallback);
            };
            this.$div.find('#switchToLogin').click(function () {
                switchToLogin();
            });
            this.$result = this.$div.find('div.body div.result');
            this.$resendResult = this.$result.find('div.exception span.resend-result');
            this.$result.find('div.exception a.resend').click(function () {
                var email = self.$result.find('span.email').eq(0).text();
                self.$resendResult.text('正在发送...');
                $.get('resend-register-email.json', {email: email}, function (data) {
                    if (data.success) {
                        self.$resendResult.text('发送成功！').showForAWhile(1500);
                    } else {
                        self.$resendResult.text(data.detail + '！').showForAWhile(1500);
                    }
                });
            });

            this.$qqInput = this.$div.find('div.body div.right div.qq-input');
            this.$emailInput = this.$div.find('div.body div.right div.email-input');
            this.$div.find('div.body div.register-type > div').click(function () {
                var $this = $(this);
                if ($this.hasClass('selected')) {
                    return;
                }

                $this.parent().find('>div.selected').removeClass('selected');
                $this.addClass('selected');

                if ($this.hasClass('by-email')) {
                    self.$qqInput.hide();
                    self.$emailInput.show().focusFirstTextInput();
                } else if ($this.hasClass('by-qq')) {
                    self.$emailInput.hide();
                    self.$qqInput.show().focusFirstTextInput();
                }
            });

            showRegisterForm = function () {
                register.show();
            };

            return this;
        }
    }).init();
    $('#registerNavigationLink').click(function () {
        register.show();
    });

    // refresh identity code
    $('img.identity-code').click(function () {
        this.src = 'identity-code?id=' + new Date().getTime();
        $(this).prev().val('').focus();
    }).next().click(function () {
        $(this).prev().trigger('click');
    });
})();
var switchToLogin, showLoginForm, hideLoginForm, showRegisterForm;
