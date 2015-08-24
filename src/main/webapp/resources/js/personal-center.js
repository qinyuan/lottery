/*;
 (function () {
 // codes about changing password
 var $changePassword = $('#changePassword');
 $changePassword.click(function () {
 var $tr = $(this).getParentByTagName('tr');
 if ($tr.next().hasClass('change-password')) {
 $tr.next().focusFirstTextInput();
 return;
 }

 var $html = $(JSUtils.handlebars("changePasswordTemplate"));
 $html.insertAfter($tr).focusFirstTextInput();
 $html.find('button[name=ok]').click(function () {
 var $oldPassword = $html.getInputByName('oldPassword');
 var oldPassword = $oldPassword.val();
 if ($.trim(oldPassword) == '') {
 alert('现密码不能为空！');
 $oldPassword.focusOrSelect();
 return;
 }

 var $newPassword = $html.getInputByName('newPassword');
 var newPassword = $newPassword.val();
 if ($.trim(newPassword) == '') {
 alert('新密码不能为空！');
 $newPassword.focusOrSelect();
 return;
 }

 if (newPassword.length < 6) {
 alert('密码应为6~20个字符，区分大小写！');
 $newPassword.focusOrSelect();
 return;
 }

 var $newPassword2 = $html.getInputByName('newPassword2');
 var newPassword2 = $newPassword2.val();
 if (newPassword != newPassword2) {
 alert('两次输入的新密码不一致！');
 $newPassword2.focusOrSelect();
 return;
 }

 $.post('personal-center-update-password.json', $html.find('form').serialize(), function (data) {
 if (data.success) {
 $html.find('td').html('<h3>密码修改成功！</h3>');
 setTimeout(function () {
 hideAndClear($html);
 }, 1000);
 } else {
 alert(data.detail);
 }
 });
 });
 $html.find('button[name=cancel]').click(function () {
 hideAndClear($html);
 });
 $html.setDefaultButtonById('changePasswordSubmit');
 });
 $('#changePassword2').click(function () {
 $changePassword.trigger('click');
 JSUtils.scrollTop($changePassword);
 });
 })();
 (function () {
 // codes about changing tel
 $('#changeTel').click(function () {
 var $tr = $(this).getParentByTagName('tr');
 if ($tr.next().hasClass('change-tel')) {
 $tr.next().focusFirstTextInput();
 return;
 }

 var $html = $(JSUtils.handlebars("changeTelTemplate"));
 $html.insertAfter($tr).focusFirstTextInput();
 $html.find('button[name=ok]').click(function (e) {
 e.preventDefault();   // prevent normal submit

 var $tel = $html.getInputByName('tel');
 var tel = $tel.val();
 if ($.trim(tel) == '') {
 alert('手机号不能为空！');
 $tel.focusOrSelect();
 return false;
 }

 if (!JSUtils.validateTel(tel)) {
 alert('手机号必须为11位数字！');
 $tel.focusOrSelect();
 return false;
 }

 $.post('personal-center-update-tel.json', $html.find('form').serialize(), function (data) {
 if (data.success) {
 $html.getParentByTagName('table').find('span.tel').text(tel);
 $html.find('td').html('<h3>手机号修改成功！</h3>');
 setTimeout(function () {
 hideAndClear($html);
 }, 1000);
 } else {
 alert(data.detail);
 }
 });
 return false;
 });
 $html.find('button[name=cancel]').click(function () {
 hideAndClear($html);
 });
 $html.setDefaultButtonById('changeTelSubmit');
 });
 })();
 (function () {
 // codes about changing email
 $('#changeEmail').click(function () {
 var $tr = $(this).getParentByTagName('tr');
 if ($tr.next().hasClass('change-email')) {
 $tr.next().focusFirstTextInput();
 return;
 }

 var $html = $(JSUtils.handlebars("changeEmailTemplate"));
 $html.insertAfter($tr).focusFirstTextInput();
 $html.find('button[name=ok]').click(function (e) {
 e.preventDefault();   // prevent normal submit

 var $email = $html.getInputByName('email');
 var email = $email.val();
 if ($.trim(email) == '') {
 alert('邮箱地址不能为空！');
 $email.focusOrSelect();
 return false;
 }

 if (!JSUtils.validateEmail(email)) {
 alert('邮箱地址格式错误！');
 $email.focusOrSelect();
 return false;
 }

 var $this = $(this);
 var oldEmail = $this.getParentByTagName('table').find('span.email').text();
 if (oldEmail.toLowerCase() == email.toLowerCase()) {
 alert('新邮箱不能与原邮箱相同！');
 $email.focusOrSelect();
 return false;
 }

 $this.text('正在处理...');
 var url = 'personal-center-update-email.json';
 $.post(url, $html.find('form').serialize(), function (data) {
 if (data.success) {
 var loginPage = JSUtils.getEmailLoginPage(email);
 var info = JSUtils.handlebars('changeEmailResultTemplate', {
 loginPage: loginPage,
 email: email
 });
 $html.find('td').html(info).find('a.resend').click(function () {
 $.post(url, {email: email}, function (data) {
 if (data.success) {
 $html.find('span.resend-success').showForAWhile(3000);
 } else {
 $html.find('span.resend-fail').text(data.detail).showForAWhile(3000);
 }
 });
 });
 } else {
 alert(data.detail);
 $this.text('确定');
 }
 });
 return false;
 });
 $html.find('button[name=cancel]').click(function () {
 hideAndClear($html);
 });
 $html.setDefaultButtonById('changeTelSubmit');
 });
 })();
 function hideAndClear($element) {
 $element.hide(200, function () {
 $element.remove();
 });
 }*/
;
(function () {
    // code about birthday select
    function disableSelect($select) {
        $select.attr('disabled', true).css('background-color', '#eeeeee');
    }

    function enableSelect($select) {
        $select.attr('disabled', false).css('background-color', '#ffffff');
    }

    var $year = $('#birthdayYear');
    var $month = $('#birthdayMonth');
    var $day = $('#birthdayDay');
    if ($year.val() == '') {
        disableSelect($month);
        disableSelect($day);
    }
    $year.change(function () {
        if ($year.val() != '') {
            enableSelect($month);
        } else {
            disableSelect($month);
        }
    });
    $month.change(function () {
        if ($month.val() != '' && $year.val() != '') {
            enableSelect($day);
            var dayOfMonth = new Date($year.val(), $month.val(), 0).getDate();
            var $options = $day.find('option');
            var maxDayOfOptions = $options.eq(0).text() == '' ? $options.size() - 1 : $options.size();
            var i;
            if (maxDayOfOptions > dayOfMonth) {
                for (i = 0; i < maxDayOfOptions - dayOfMonth; i++) {
                    $day.find('option:last').remove();
                }
            } else {
                for (i = maxDayOfOptions + 1; i <= dayOfMonth; i++) {
                    $day.append('<option value="' + i + '">' + i + '</option>');
                }
            }
            updateConstellation();
        } else {
            disableSelect($day);
        }
    });
    $day.change(function () {
        updateConstellation();
    });
    function updateConstellation() {
        if ($year.val() != '' && $month.val() != '' && $day.val() != '') {
            var month = parseInt($month.val());
            var day = parseInt($day.val());
            var constellation = JSUtils.getConstellation(month, day);
            if (constellation) {
                $('#constellationSelect').val(constellation);
            }
        }
    }
})();
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
            $.post('personal-center-update-real-name.json', {'realName': input}, JSUtils.normalAjaxCallback);
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
            var url = 'personal-center-update-password.json';
            $.post(url, this.$floatPanel.serialize(), JSUtils.normalAjaxCallback);
        }
    });
    $('#editPassword').click(function () {
        password.show();
    });
})();
(function () {
    // code about editing email
    function sendResetEmail(email, successCallback, failCallback) {
        var updateUrl = 'personal-center-update-email.json';
        $.post(updateUrl, {'email': email}, function (data) {
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
            this.$floatPanel.find('div.submit button').click(function(){
                self.hide();
            });
            return this;
        }
    }).init();
})();
