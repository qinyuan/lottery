(function () {
    var usernameInput = ({
        $form: $('#usernameInputForm'),
        get$Username: function () {
            return this.$form.getInputByName('resetPasswordUsername');
        },
        get$IdentityCode: function () {
            return this.$form.getInputByName('identityCode');
        },
        init: function () {
            this.$form.setDefaultButtonById('usernameInputSubmit');
            var self = this;
            $('#usernameInputSubmit').click(function () {
                if (self.get$Username().trimVal() == '') {
                    alert('请输入帐户名！');
                    self.get$Username().focusOrSelect();
                    return;
                }

                if (self.get$IdentityCode().trimVal().length != 4) {
                    alert('请输入验证码！');
                    self.get$IdentityCode().focusOrSelect();
                    return;
                }

                $.post('send-reset-password-mail.json', self.$form.serialize(), function (data) {
                    if (data.success) {
                        self.$form.hide();
                        resetPasswordResult.show(data['mail']);
                    } else {
                        alert(data.detail);
                        self.$form.find('img.identity-code').trigger('click');
                    }
                });
            });
            return this;
        }
    }).init();
    usernameInput.$form.focusFirstTextInput();

    var resetPasswordResult = ({
        $div: $('div.main-body div.reset-password-result'),
        email: null,
        show: function (email) {
            this.email = email;
            email = JSUtils.makeEmailSecret(email);
            this.$div.find('span.mail').text(email);
            this.$div.find('a.toLoginPage').attr('href', JSUtils.getEmailLoginPage(email));
            this.$div.fadeIn(300);
        },
        hide: function () {
            this.$div.hide();
        },
        init: function () {
            var self = this;
            this.$div.find('a.resend').click(function () {
                //var email = self.$div.find('span.mail:first').text();
                $.post('resend-reset-password-mail.json', {'email': self.email}, function (data) {
                    if (data.success) {
                        self.$div.find('span.resend-success').showForAWhile(1500);
                    } else {
                        self.$div.find('span.resend-fail').text(data.detail).showForAWhile(1500);
                    }
                });
            });
            return this;
        }
    }).init();
    //resetPasswordResult.show('testme@qq.com');
})();