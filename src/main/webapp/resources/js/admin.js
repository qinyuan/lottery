;
(function () {
    // codes about mail
    var emailAddEditForm = ({
        $form: $('#emailAddEditForm'),
        /**
         * available options: id, type, host, username, password, user, domainName, apiKey
         * @param options
         */
        show: function (options) {
            if (options == null) {
                options = {};
            }
            JSUtils.showTransparentBackground(1);
            this.$form.setInputValue('id', options['id']);
            this.$form.show();
            if (options['id'] && options['type'] == 'SimpleMailAccount') {
                this.$form.setInputValue('host', options['host'])
                    .setInputValue('username', options['username'])
                    .setInputValue('password', options['password']);
                if (options['username']) {
                    this.$form.getInputByName('username').attr('disabled', true);
                } else {
                    this.$form.getInputByName('username').attr('disabled', false);
                }

                this.$simpleMail.removeClass('disable').find('input[type=radio]').attr('disabled', false).trigger('click');
                this.$sendCloud.addClass('disable').find('input[type=radio]').attr('disabled', true);
            } else if (options['id'] && options['type'] == 'SendCloudAccount') {
                this.$form.setInputValue('user', options['user'])
                    .setInputValue('domainName', options['domainName'])
                    .setInputValue('apiKey', options['apiKey']);

                this.$sendCloud.removeClass('disable').find('input[type=radio]').attr('disabled', false).trigger('click');
                this.$simpleMail.addClass('disable').find('input[type=radio]').attr('disabled', true);
            } else {
                this.$simpleMail.removeClass('disable').find('input[type=radio]').attr('disabled', false).trigger('click');
                this.$sendCloud.removeClass('disable').find('input[type=radio]').attr('disabled', false);
            }
        },
        hide: function () {
            this.$form.hide();
            JSUtils.hideTransparentBackground();
        },
        validateInput: function () {
            if (this.getType() == 'SimpleMailAccount') {
                if (this.$host.trimVal() == '') {
                    alert('发件箱服务器地址不能为空！');
                    this.$host.focusOrSelect();
                    return false;
                } else if (!JSUtils.validateEmail(this.$username.val())) {
                    alert('发件箱用户名必须为有效的邮件地址！');
                    this.$username.focusOrSelect();
                    return false;
                } else if (this.$password.trimVal() == '') {
                    alert('发件箱密码不能为空！');
                    this.$password.focusOrSelect();
                    return false;
                }
                return true;
            } else if (this.getType() == 'SendCloudAccount') {
                var user = this.$user.val();
                if ($.trim(user) == '') {
                    alert('sendcloud用户名不能为空！');
                    this.$user.focusOrSelect();
                    return false;
                } else if (user.indexOf('@') >= 0) {
                    alert('sendcloud用户名不能包含"@"字符！');
                    this.$user.focusOrSelect();
                    return false;
                }

                var domainName = this.$domainName.val();
                if (domainName == '') {
                    alert('sendcloud域名不能为空！');
                    this.$domainName.focusOrSelect();
                    return false;
                } else if (domainName.indexOf('@') >= 0) {
                    alert('sendcloud域名不能包含"@"字符！');
                    this.$domainName.focusOrSelect();
                    return false;
                }

                if (this.$apiKey.trimVal() == '') {
                    alert('apiKey不能为空！');
                    this.$apiKey.focusOrSelect();
                    return false;
                }
                return true;
            } else {
                return false;
            }

        },
        getType: function () {
            return this.$form.find('div.type input[type=radio]').filter(function () {
                return this.checked;
            }).val();
        },
        init: function () {
            var self = this;
            this.$form.setDefaultButtonById('addEmailSubmit');

            this.$host = this.$form.getInputByName('host');
            this.$username = this.$form.getInputByName('username');
            this.$password = this.$form.getInputByName('password');
            this.$user = this.$form.getInputByName('user');
            this.$domainName = this.$form.getInputByName('domainName');
            this.$apiKey = this.$form.getInputByName('apiKey');

            this.$simpleMailTable = this.$form.find('table.simple-mail');
            this.$sendCloudTable = this.$form.find('table.send-cloud');

            this.$simpleMail = this.$form.find('span.simple-mail');
            this.$simpleMail.find('input[type=radio]').click(function () {
                self.$sendCloudTable.hide();
                self.$simpleMailTable.show().focusFirstTextInput();
            });

            this.$sendCloud = this.$form.find('span.send-cloud');
            this.$sendCloud.find('input[type=radio]').click(function () {
                self.$simpleMailTable.hide();
                self.$sendCloudTable.show().focusFirstTextInput();
            });

            $('#addEmailSubmit').click(function (e) {
                e.preventDefault();
                if (self.validateInput()) {
                    $.post('admin-add-edit-mail-account.json', self.$form.serialize(), JSUtils.normalAjaxCallback);
                }
                return false;
            });
            $('#addEmailCancel').click(function () {
                self.hide();
            });
            return this;
        }
    }).init();
    $('#addEmailButton').click(function () {
        emailAddEditForm.show({});
    });
    var $emailListDiv = $('#emailList');
    $emailListDiv.find('div.close-image').click(function () {
        var id = $(this).parent().dataOptions('id');
        $.post('admin-delete-mail.json', {'id': id}, JSUtils.normalAjaxCallback);
    });
    $emailListDiv.find('div.edit-image,div.username').click(function () {
        var $parent = $(this).parent();
        var id = $parent.dataOptions('id');
        var type = $parent.find('input.type').val();
        $.post('admin-query-mail-account.json', {id: id}, function (data) {
            data['id'] = id;
            data['type'] = type;
            emailAddEditForm.show(data);
        });
    });
})();
(function () {
    // codes about mail account
    //JSUtils.loadSelectFormEventsAndValue($('div.activate-mail-select'), window['currentActivateMailAccountId']);
    JSUtils.loadSelectFormEventsAndValue($('div.register-mail-select'), window['currentRegisterMailAccountId']);
    JSUtils.loadSelectFormEventsAndValue($('div.reset-password-mail-select'), window['currentResetPasswordMailAccountId']);
    JSUtils.loadSelectFormEventsAndValue($('div.reset-email-mail-select'), window['currentResetEmailMailAccountId']);
})();
(function () {
    JSUtils.recordScrollStatus();
    $('#submitButton').click(function (e) {
        if ((!JSUtils.validateUploadFile('indexHeaderLeftLogo', '左图标未设置'))
            /*|| (!JSUtils.validateUploadFile('indexHeaderRightLogo', '右图标未设置'))*/
            || (!JSUtils.validateUploadFile('indexHeaderSlogan', '右侧宣传图片未设置'))) {
            e.preventDefault();
            return false;
        } else {
            return true;
        }
    });
    $('#addLink').click(function () {
        var $linkTable = $('#linkTable');
        $linkTable.append(JSUtils.handlebars("link-template"));
        $linkTable.find('tr:last').find('input:first').focus();
    });
    $('#systemEditLink').addClass('emphasize');
})();
function rankUpLink(target) {
    $(target).getParentByTagName('tr').moveToPrev();
}
function rankDownLink(target) {
    $(target).getParentByTagName('tr').moveToNext();
}
function deleteLink(target) {
    $(target).getParentByTagName('tr').remove();
}
