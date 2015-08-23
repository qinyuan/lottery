;
(function () {
    var addEditForm = ({
        $form: $('#addEditForm'),
        $okButton: $('#addEditOk'),
        $cancelButton: $('#addEditCancel'),
        get$Id: function () {
            return this.$form.getInputByName('id');
        },
        get$Username: function () {
            return this.$form.getInputByName('username');
        },
        get$TelPrefix: function () {
            return this.$form.getInputByName('telPrefix');
        },
        get$TelSuffix: function () {
            return this.$form.getInputByName('telSuffix');
        },
        get$MailPrefix: function () {
            return this.$form.getInputByName('mailPrefix');
        },
        get$MailSuffix: function () {
            return this.$form.getInputByName('mailSuffix');
        },
        hide: function () {
            this.$form.fadeOut(200, function () {
                JSUtils.hideTransparentBackground();
            });
        },
        validate: function () {
            if (this.get$Username().trimVal() == '') {
                alert('用户名不能为空！');
                this.get$Username().focusOrSelect();
                return false;
            }

            if (this.get$TelPrefix().trimVal() == '') {
                alert('手机号前两位不能为空！');
                this.get$TelPrefix().focusOrSelect();
                return false;
            }
            if (!this.get$TelPrefix().val().match(/^\d{2}$/)) {
                alert('手机号前两位必须为两位数字！');
                this.get$TelPrefix().focusOrSelect();
                return false;
            }

            if (this.get$TelSuffix().trimVal() == '') {
                alert('手机号后四位不能为空！');
                this.get$TelSuffix().focusOrSelect();
                return false;
            }
            if (!this.get$TelSuffix().val().match(/^\d{4}$/)) {
                alert('手机号后四位必须为四位数字！');
                this.get$TelSuffix().focusOrSelect();
                return false;
            }

            if (this.get$MailPrefix().trimVal() == '') {
                alert('邮箱前两字符不能为空！');
                this.get$MailPrefix().focusOrSelect();
                return false;
            }
            if (!this.get$MailPrefix().val().match(/^\w{2}$/)) {
                alert('邮箱前两字符格式错误！');
                this.get$MailPrefix().focusOrSelect();
                return false;
            }

            if (this.get$MailSuffix().trimVal() == '') {
                alert('邮箱后缀不能为空！');
                this.get$MailSuffix().focusOrSelect();
                return false;
            }
            if (!this.get$MailSuffix().val().match(/^@\w+\.\w+.*$/)) {
                alert("邮箱后缀的正确格式为'@**.**'");
                this.get$MailSuffix().focusOrSelect();
                return false;
            }
            return true;
        },
        show: function (id, username, tel, mail) {
            this.get$Id().val(id);
            this.get$Username().val(username);

            var telPrefix, telSuffix;
            if (tel && tel.indexOf('*') > 0) {
                var telSplitArray = tel.split(/\*+/);
                telPrefix = telSplitArray[0];
                telSuffix = telSplitArray[1];
            }
            this.get$TelPrefix().val(telPrefix);
            this.get$TelSuffix().val(telSuffix);

            var mailPrefix, mailSuffix;
            if (mail && mail.indexOf('*') > 0) {
                var mailSplitArray = mail.split(/\*+/);
                mailPrefix = mailSplitArray[0];
                mailSuffix = mailSplitArray[1];
            }
            this.get$MailPrefix().val(mailPrefix);
            this.get$MailSuffix().val(mailSuffix);

            JSUtils.showTransparentBackground(1);
            this.$form.fadeIn(200).focusFirstTextInput();
        },
        init: function () {
            this.$form.setDefaultButtonById('addEditOk');

            var self = this;
            this.$cancelButton.click(function () {
                self.hide();
            });
            this.$okButton.click(function (e) {
                e.preventDefault();
                if (self.validate()) {
                    $.post('admin-virtual-user-add-update.json', self.$form.serialize(), JSUtils.normalAjaxCallback);
                }
                return false;
            });
            return this;
        }
    }).init();
    $('#addVirtualUser').click(function () {
        addEditForm.show();
    });
    $('div.users div.user > div.edit').click(function () {
        var $parent = $(this).getParentByTagNameAndClass('div', 'user');
        var id = $parent.dataOptions('id');
        var username = $parent.find('div.username').text();
        var tel = $parent.find('div.tel').text();
        var mail = $parent.find('div.mail').text();
        addEditForm.show(id, username, tel, mail);
    });
    $('div.users div.user > div.delete').click(function () {
        var id = $(this).getParentByTagNameAndClass('div', 'user').dataOptions('id');
        $.post('admin-virtual-user-delete.json', {'id': id}, JSUtils.normalAjaxCallback);
    });
})();
