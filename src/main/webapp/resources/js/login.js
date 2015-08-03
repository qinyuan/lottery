(function () {
    $('div.login').focusFirstTextInput();
    $('#loginSubmit').click(function (e) {
        var $username = $('#username');
        if ($username.trimVal() == '') {
            alert('用户名不能为空！');
            $username.focusOrSelect();
            e.preventDefault();
            return false;
        }

        var $password = $('#password');
        if ($password.trimVal() == '') {
            alert('密码不能为空！');
            $password.focusOrSelect();
            e.preventDefault();
            return false;
        }

        return true;
    });
    if ($.param('login_error')) {
        $('form div.error-info').twinkle(4);
    }
})();