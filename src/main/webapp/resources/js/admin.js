;
(function () {
    // codes about mail
    var emailAddEditForm = ({
        $form: $('#emailAddEditForm'),
        get$Host: function () {
            return this.$form.getInputByName('mailHost');
        },
        get$Username: function () {
            return this.$form.getInputByName('mailUsername');
        },
        get$Password: function () {
            return this.$form.getInputByName('mailPassword');
        },
        show: function (id, host, username, password) {
            JSUtils.showTransparentBackground(1);
            this.$form.setInputValue('id', id)
                .setInputValue('mailHost', host)
                .setInputValue('mailUsername', username)
                .setInputValue('mailPassword', password);
            this.$form.show().focusFirstTextInput();
        },
        hide: function () {
            this.$form.hide();
            JSUtils.hideTransparentBackground();
        },
        validateInput: function () {
            if (this.get$Host().trimVal() == '') {
                alert('发件箱服务器地址不能为空！');
                this.get$Host().focusOrSelect();
                return false;
            }
            if (!JSUtils.validateEmail(this.get$Username().val())) {
                alert('发件箱用户名必须为有效的邮件地址！');
                this.get$Username().focusOrSelect();
                return false;
            }
            if (this.get$Password().trimVal() == '') {
                alert('发件箱密码不能为空！');
                this.get$Password().focusOrSelect();
                return false;
            }
            return true;
        },
        init: function () {
            this.$form.setDefaultButton('addEmailSubmit');
            var self = this;
            $('#addEmailSubmit').click(function (e) {
                e.preventDefault();
                if (self.validateInput()) {
                    $.post('admin-add-edit-email.json', self.$form.serialize(), JSUtils.normalAjaxCallback);
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
        emailAddEditForm.show();
    });
    var $emailListDiv = $('#emailList');
    $emailListDiv.find('div.close-image').click(function () {
        var id = $(this).parent().dataOptions('id');
        $.post('admin-delete-mail.json', {'id': id}, JSUtils.normalAjaxCallback);
    });
    $emailListDiv.find('div.edit-image,div.username').click(function () {
        var $parent = $(this).parent();
        var id = $parent.dataOptions('id');
        var host = $parent.find('input.host').val();
        var password = $parent.find('input.password').val();
        var username = $parent.find('div.username').text();
        emailAddEditForm.show(id, host, username, password);
    });
})();
(function () {
    // codes about activate and reset mail account
    if (window['currentActivateMailAccountId']) {
        $('div.activate-mail-select li a').each(function () {
            var $this = $(this);
            if ($this.dataOptions('id') == window['currentActivateMailAccountId']) {
                $this.trigger('click');
                return false;
            }
            return true;
        });
    }
    if (window['currentResetPasswordMailAccountId']) {
        $('div.activate-mail-select li a').each(function () {
            var $this = $(this);
            if ($this.dataOptions('id') == window['currentResetPasswordMailAccountId']) {
                $this.trigger('click');
                return false;
            }
            return true;
        });
    }
})();
(function () {
    JSUtils.recordScrollStatus();
    $('#submitButton').click(function (e) {
        if ((!JSUtils.validateUploadFile('indexHeaderLeftLogo', '左图标未设置'))
            || (!JSUtils.validateUploadFile('indexHeaderRightLogo', '右图标未设置'))
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
