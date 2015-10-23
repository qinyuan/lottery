/*(function () {
 $('div.main-body div.completed-link a.to-login').click(function () {
 window['switchToLogin'](function () {
 location.href = 'index.html';
 });
 });

 $('div.main-body div.re-register a').click(function () {
 $('#registerNavigationLink').trigger('click');
 });

 var validation = ({
 _get$Validation: function ($target) {
 var $validation = $target.next();
 if ($validation.size() == 0 || !$validation.is('span')) {
 $validation = $('<span><span class="validate"></span><span class="error-info"></span></span>');
 $validation.appendTo($target.getParentByTagNameAndClass('span', 'right'));
 }
 return $validation;
 },
 showSuccessIcon: function ($target, callback) {
 var $validation = this._get$Validation($target);
 $validation.find('span.validate').removeClass('fail').addClass('success');
 $validation.find('span.error-info').text('');
 $validation.show();
 typeof(callback) == 'function' && callback();
 },
 showErrorInfo: function ($target, content) {
 var $validation = this._get$Validation($target);
 $validation.find('span.validate').removeClass('success').addClass('fail');
 $validation.find('span.error-info').html(content);
 $validation.twinkle(3);
 },
 init: function () {
 return this;
 }
 }).init();

 $('#submitButton').click(function (e) {
 e.preventDefault();

 validateUsername(function () {
 validatePassword(function () {
 validatePassword2(function () {
 validateTel(function () {
 $.post('register-complete-user-info.json', $form.serialize(), function (data) {
 if (data.success) {
 location.reload();
 } else {
 alert(data.detail);
 }
 });
 })
 });
 });
 });
 });

 var $form = $('#userInfo');
 var $username = $form.getInputByName('username').blur(function () {
 validateUsername();
 });
 var $password = $form.getInputByName('password').blur(function () {
 validatePassword();
 });
 var $password2 = $form.getInputByName('password2').blur(function () {
 validatePassword2();
 });
 var $tel = $form.getInputByName('tel').blur(function () {
 validateTel();
 });

 setNormalInputBlurEvent($form.getInputByName('realName'));
 setNormalInputBlurEvent($form.find('select[name=gender]'));
 setNormalInputBlurEvent($form.find('select[name=birthdayDay]'));
 setNormalInputBlurEvent($form.find('select[name=constellation]'));
 setNormalInputBlurEvent($form.getInputByName('hometown'));
 setNormalInputBlurEvent($form.getInputByName('residence'));

 function validateUsername(successCallback) {
 var username = $username.val();
 if ($.trim(username) == '') {
 validation.showErrorInfo($username, '用户名不能为空！');
 } else if (JSUtils.validateTel(username)) {
 validation.showErrorInfo($username, '用户名不能为手机号！');
 } else if (username.indexOf('@') >= 0) {
 validation.showErrorInfo($username, '用户名不能包含"@"字符！');
 } else {
 $.get('validate-username.json', {'username': username, 'withoutLink': true}, function (data) {
 if (data.success) {
 validation.showSuccessIcon($username, successCallback);
 } else {
 validation.showErrorInfo($username, data.detail);
 }
 });
 }
 }

 function validatePassword(successCallback) {
 var password = $password.val();
 if ($.trim(password) == '') {
 validation.showErrorInfo($password, '密码不能为空！');
 } else if (password.length < 6) {
 validation.showErrorInfo($password, '密码不能少于6位字符！');
 } else if (password.indexOf(' ') >= 0) {
 validation.showErrorInfo($password, '密码不能包含空格！');
 } else {
 validation.showSuccessIcon($password, successCallback);
 }
 }

 function validatePassword2(successCallback) {
 var password = $password.val();
 var password2 = $password2.val();
 if ($.trim(password2) == '') {
 validation.showErrorInfo($password2, '确认密码不能为空！');
 } else if (password != password2) {
 validation.showErrorInfo($password2, '两次输入的密码需要一致！');
 } else {
 validation.showSuccessIcon($password2, successCallback);
 }
 }

 function validateTel(successCallback) {
 var tel = $tel.val();
 if (tel.length == 0) {
 validation.showSuccessIcon($tel, successCallback);
 } else if (JSUtils.validateTel(tel)) {
 $.post('validate-tel-without-login.json', {tel: tel}, function (data) {
 if (data.success) {
 validation.showSuccessIcon($tel, successCallback);
 } else {
 var text = data.detail;
 if (text.indexOf('被使用') > 0) {
 text += '<a style="margin-left:10px;" href="' + window['telValidateDescriptionPage']
 + '" target="_blank">验证</a>';
 }
 validation.showErrorInfo($tel, text);
 }
 });
 } else {
 validation.showErrorInfo($tel, '号码必须为11位数字');
 }
 }

 function setNormalInputBlurEvent($target) {
 $target.blur(function () {
 validation.showSuccessIcon($(this));
 });
 }
 })();
 $('div.main-body div.right').focusFirstTextInput();*/
;
(function () {
    var $form = $('div.main-body div.form').focusFirstTextInput();
    $form.setDefaultButtonByClass('ok');
    var $submit = $form.find('div.submit button.ok').click(function (e) {
        if (updateSubmitStatus()) {
            $.post('register-complete-user-info.json', {
                serialKey: $.url.param('serial'),
                username: $username.val(),
                password: $password.val()
            }, function (data) {
                if (data.success) {
                    showSuccessInfo();
                } else {
                    alert(data.detail);
                }
            });
            return true;
        } else {
            e.preventDefault();
            return false;
        }
    });
    var $username = $form.getInputByName('username').blur(function () {
        validateUsername();
    });
    var $password = $form.getInputByName('password').blur(function () {
        validatePassword();
    });
    var $password2 = $form.getInputByName('password2').blur(function () {
        validatePassword2();
    });
    var $checkbox = $form.find('div.subscribe input[type=checkbox]').click(function () {
        updateSubmitStatus();
    });

    function showSuccessInfo() {
        var $registerSuccess = $('div.main-body div.register-success');
        var remainSeconds = 5;
        var $remain = $registerSuccess.find('span.remain').text(remainSeconds);
        $form.hide();
        $registerSuccess.show();
        setInterval(function () {
            remainSeconds--;
            $remain.text(remainSeconds);
            if (remainSeconds == 0) {
                location.href = 'index.html';
            }
        }, 1000);
    }

    function validateUsername(callback) {
        var username = $username.val();
        if ($.trim(username) == '') {
            showError($username, '用户名不能为空！');
        } else if (JSUtils.validateTel(username)) {
            showError($username, '用户名不能为手机号！');
        } else if (username.indexOf('@') >= 0) {
            showError($username, '用户名不能包含"@"字符！');
        } else {
            $.get('validate-username.json', {'username': username, 'withoutLink': true}, function (data) {
                if (data.success) {
                    showValid($username, callback);
                } else {
                    showError($username, data.detail);
                }
            });
        }
    }

    function validatePassword(callback) {
        var password = $password.val();
        if ($.trim(password) == '') {
            showError($password, '密码不能为空！');
        } else if (password.length < 6) {
            showError($password, '密码不能少于6位字符！');
        } else if (password.indexOf(' ') >= 0) {
            showError($password, '密码不能包含空格！');
        } else {
            showValid($password, callback);
        }
    }

    function validatePassword2(callback) {
        var password = $password.val();
        var password2 = $password2.val();
        if ($.trim(password2) == '') {
            showError($password2, '确认密码不能为空！');
        } else if (password != password2) {
            showError($password2, '两次输入的密码需要一致！');
        } else {
            showValid($password2, callback);
        }
    }

    function validateSubscribe() {
        return $checkbox.get(0).checked;
    }

    function updateSubmitStatus() {
        var inputSize = $form.find('div.input').size();
        var validInputSize = $form.find('span.valid').filter(function () {
            return $(this).css('display') != 'none';
        }).size();

        if (validateSubscribe() && validInputSize >= inputSize) {
            $submit.attr('disabled', false);
            return true;
        } else {
            $submit.attr('disabled', true);
            return false;
        }
    }

    function showValid($input, callback) {
        var $parent = $input.parent();
        $parent.find('span.valid').show();
        $parent.find('span.invalid').hide();
        typeof(callback) == 'function' && callback();
    }

    function showError($input, info) {
        var $parent = $input.parent();
        var $invalid = $parent.find('span.invalid').show();
        $invalid.find('span.text').text(info);
        $parent.find('span.valid').hide();
    }
})();
(function () {
    // code about exception
    $('div.main-body div.re-register').click(function () {
        showRegisterForm();
    });
    $('div.main-body div.completed-link a.to-login').click(function () {
        showRegisterForm();
    });
})();
