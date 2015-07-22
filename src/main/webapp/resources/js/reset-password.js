;
(function () {
    var $remainingRelocateSeconds = $('#remainingRelocateSeconds');
    if ($remainingRelocateSeconds.size() > 0) {
        setInterval(function () {
            var seconds = parseInt($remainingRelocateSeconds.text());
            if (seconds > 1) {
                $remainingRelocateSeconds.text(seconds - 1);
            } else {
                location.href = $remainingRelocateSeconds.getParentByTagName('a').attr('href');
            }
        }, 1000);
        return;
    }

    var passwordInput = ({
        $form: $('#passwordInputForm'),
        hide: function () {
            this.$form.hide();
        },
        get$Password: function () {
            return this.$form.getInputByName('password');
        },
        get$Password2: function () {
            return this.$form.getInputByName('password2');
        },
        init: function () {
            this.$form.setDefaultButton('resetPasswordSubmit');
            var self = this;
            $('#resetPasswordSubmit').click(function (e) {
                e.preventDefault();
                if (self.get$Password().trimVal() == '') {
                    alert('密码不能为空！');
                    self.get$Password().focusOrSelect();
                    return false;
                }
                if (self.get$Password().val().length < 6) {
                    alert('密码不能少于6个字符！');
                    self.get$Password().focusOrSelect();
                    return false;
                }
                if (self.get$Password().val() != self.get$Password2().val()) {
                    alert('两次输入的密码不相等！');
                    self.get$Password2().focusOrSelect();
                    return false;
                }

                $.post('update-password-submit.json', self.$form.serialize(), function (data) {
                    if (data.success) {
                        self.hide();
                        showResetPasswordReset();
                    } else {
                        alert(data['detail']);
                    }
                });
                return false;
            });
            return this;
        }
    }).init();
    passwordInput.$form.focusFirstTextInput();

    function showResetPasswordReset() {
        $('div.main-body div.reset-password-result').fadeIn(200);
    }
})();