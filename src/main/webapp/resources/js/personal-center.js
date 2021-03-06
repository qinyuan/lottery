;
(function () {
    // code about editing real name
    function getOldRealName() {
        var $span = $('span.real-name');
        return $span.size() > 0 ? $span.eq(0).text() : null;
    }

    function submitRealName(input) {
        if ($.trim(input) == '') {
            alert('姓名不能为空');
            JSUtils.focusPrompt();
        } else if (getOldRealName() == input) {
            alert('新姓名与原姓名相同，未作修改');
            JSUtils.focusPrompt();
        } else {
            $.post(realNameUpdateUrl, {'realName': input}, JSUtils.normalAjaxCallback);
        }
    }

    $('#addRealName').click(function () {
        JSUtils.showPrompt('请输入真实姓名：', null, submitRealName);
    });
    $('#editRealName').click(function () {
        JSUtils.showPrompt('请输入修改后的真实姓名：', getOldRealName(), submitRealName);
    });
})();
(function () {
    // code about editing password
    var password = JSUtils.buildFloatPanel({
        $floatPanel: $('#passwordEditForm'),
        validateInput: function () {
            var $oldPassword = this.$floatPanel.getInputByName('oldPassword');
            var oldPassword = $oldPassword.val();
            if (oldPassword == '') {
                alert('原密码不能为空');
                $oldPassword.focusOrSelect();
                return false;
            }

            var $newPassword = this.$floatPanel.getInputByName('newPassword');
            var newPassword = $newPassword.val();
            if (newPassword == '') {
                alert('新密码不能为空');
                $newPassword.focusOrSelect();
                return false;
            }

            if (newPassword.length < 6) {
                alert('新密码不得少于6位字符');
                $newPassword.focusOrSelect();
                return false;
            }

            if (newPassword == oldPassword) {
                alert('新密码不能与原密码相同');
                $newPassword.focusOrSelect();
                return false;
            }

            var $newPassword2 = this.$floatPanel.getInputByName('newPassword2');
            var newPassword2 = $newPassword2.val();
            if (newPassword != newPassword2) {
                alert('两次输入的密码不相等');
                $newPassword2.focusOrSelect();
                return false;
            }
            return true;
        },
        doSubmit: function () {
            $.post(passwordUpdateUrl, this.$floatPanel.serialize(), JSUtils.normalAjaxCallback);
        }
    });
    $('#editPassword').click(function () {
        password.show();
    });
    $('#editPassword2').click(function () {
        password.show();
    });
})();
(function () {
    // code about editing email
    function sendResetEmail(email, successCallback, failCallback) {
        $.post(emailUpdateUrl, {'email': email}, function (data) {
            if (data['success']) {
                successCallback();
            } else {
                failCallback(data['detail']);
            }
        });
    }

    $('#editEmail').click(function () {
        JSUtils.showPrompt('请输入新的邮箱地址：', '', function (input, $div) {
            if ($.trim(input) == '') {
                alert('邮箱不能为空');
                JSUtils.focusPrompt();
                return;
            }
            if (!JSUtils.validateEmail(input)) {
                alert('邮箱格式错误');
                JSUtils.focusPrompt();
                return;
            }
            var oldEmail = $('span.email').text();
            if (oldEmail == input) {
                alert('新邮箱不能与原邮箱相同');
                JSUtils.focusPrompt();
                return;
            }

            var $okButton = $div.find('button.ok');
            $okButton.text('正在处理...');

            sendResetEmail(input, function () {
                JSUtils.hidePrompt(true);
                changeEmailResult.show(input);
            }, function (info) {
                $okButton.text('确定');
                alert(info);
                JSUtils.focusPrompt();
            });
        });
    });
    var changeEmailResult = ({
        $floatPanel: $('#changeEmailResult'),
        get$TargetEmail: function () {
            return  this.$floatPanel.find('a.target-email');
        },
        show: function (email) {
            JSUtils.showTransparentBackground(1);
            JSUtils.scrollToVerticalCenter(this.$floatPanel.fadeIn(200));
            var loginPage = JSUtils.getEmailLoginPage(email);
            this.get$TargetEmail().text(email).attr('href', loginPage);
            this.$floatPanel.find('a.to-login').attr('href', loginPage);
        },
        hide: function () {
            this.$floatPanel.fadeOut(200, function () {
                JSUtils.hideTransparentBackground();
            });
        },
        init: function () {
            var self = this;
            this.$floatPanel.find('a.resend').click(function () {
                var email = self.get$TargetEmail().trimText();
                sendResetEmail(email, function () {
                    self.$floatPanel.find('span.resend-success').showForAWhile(3000);
                }, function (info) {
                    self.$floatPanel.find('span.resend-fail').text(info).showForAWhile(3000);
                });
            });
            this.$floatPanel.find('div.submit button').click(function () {
                self.hide();
            });
            return this;
        }
    }).init();
})();
(function () {
    // codes about editing tel
    var tel = JSUtils.buildFloatPanel({
        $floatPanel: $('#changeTelForm'),
        get$Input: function () {
            return this.$floatPanel.find('input');
        },
        get$Submit: function () {
            return this.$floatPanel.find('div.submit');
        },
        get$Conflict: function () {
            return this.$floatPanel.find('div.conflict');
        },
        beforeShow: function () {
            this.toSubmitMode();
            this.get$Input().val('');
            this.get$OkButton().attr('disabled', true);
        },
        postInit: function () {
            var self = this;
            this.get$Input().monitorValue(function (newTel) {
                self.toSubmitMode();
                if (JSUtils.validateTel(newTel)) {
                    self._validateDuplicateTel(newTel);
                } else {
                    self.get$OkButton().attr('disabled', true);
                    self.get$WaitForValidation().hide();
                    self.get$ValidateError().hide();
                }
            });
            this.get$Conflict().find('button.clear').click(function () {
                self.get$Input().val('').focusOrSelect();
                self.toSubmitMode();
            });

            var telValidateDescriptionPage = window['telValidateDescriptionPage'];
            if (telValidateDescriptionPage != null && $.trim(telValidateDescriptionPage) != '') {
                this.$floatPanel.find('a.validate').attr('href', telValidateDescriptionPage);
            }
        },
        toConflictMode: function () {
            this.get$Submit().hide();
            this.get$Conflict().show();
        },
        toSubmitMode: function () {
            this.get$Conflict().hide();
            this.get$Submit().show();
        },
        _validateDuplicateTel: function (newTel) {
            var $oldTel = $('span.tel');
            if ($oldTel.size() > 0) {
                var oldTel = $oldTel.eq(0).text();
                if (oldTel == newTel) {
                    this.get$ValidateError().text('与原号码相同').show();
                    return;
                }
            }

            var url = 'tel-validate.json';
            var self = this;
            this.get$WaitForValidation().show();
            $.post(url, {'tel': newTel}, function (data) {
                self.get$WaitForValidation().hide();
                if (data['success']) {
                    self.get$OkButton().attr('disabled', false);
                } else {
                    self.get$OkButton().attr('disabled', true);
                    var detail = data['detail'];
                    self.get$ValidateError().text(detail).show();
                    if (detail.indexOf('被使用') >= 0) {
                        self.toConflictMode();
                    }
                }
            });
        },
        get$WaitForValidation: function () {
            return this.$floatPanel.find('div.wait-for-validation');
        },
        get$ValidateError: function () {
            return this.$floatPanel.find('div.validate-error');
        },
        validateInput: function () {
            var newTel = this.get$Input().val();
            if (!JSUtils.validateTel(newTel)) {
                alert('电话号码应为11位数字');
                this.get$Input().focusOrSelect();
                this.toSubmitMode();
                return false;
            }
            return true;
        },
        doSubmit: function () {
            var newTel = this.get$Input().val();
            $.post(telUpdateUrl, {'tel': newTel}, JSUtils.normalAjaxCallback);
        },
        get$Title: function () {
            return this.$floatPanel.find('span.title');
        }
    });
    $('#editTel').click(function () {
        tel.get$Title().text('请输入新的联系手机号码：');
        tel.show();
    });
    $('#addTel').click(function () {
        tel.get$Title().text('请输入联系手机号码：');
        tel.show();
    });
})();
var realNameUpdateUrl = 'personal-center-update-real-name.json';
var passwordUpdateUrl = 'personal-center-update-password.json';
var emailUpdateUrl = 'personal-center-update-email.json';
var telUpdateUrl = 'update-tel.json';
