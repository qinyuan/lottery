;
(function () {
    var $form = $('div.form form');
    $form.setDefaultButtonByClass('ok');

    var insertInput = '<input type="text" name="usernames" class="form-control" placeholder="请输入新的邮箱名"/>';
    var $inputDiv = $form.find('div.input');
    $inputDiv.find('img').click(function () {
        $(insertInput).insertBefore($(this)).focus();
    });

    $('div.form form div.submit button.cancel').click(function () {
        if (confirm('确定消除添加的邮箱？')) {
            get$Inputs().remove();
        }
    });

    $form.find('button.ok').click(function (e) {
        e.preventDefault();

        var emails = [];
        var invalidEmails = [];
        get$Inputs().each(function () {
            var value = $(this).val();
            if ($.trim(value) == '') {
                return true;
            }

            if (JSUtils.validateEmail(value)) {
                emails.push(value);
            } else {
                invalidEmails.push(value);
            }
        });

        if (invalidEmails.length > 0) {
            alert('如下邮箱格式错误：\n' + invalidEmails.join(", "));
            return true;
        }

        if (emails.length == 0) {
            alert('至少填写一个有效的邮箱！');
        }

        $inputDiv.find('input');
    });

    function get$Inputs() {
        return $inputDiv.find('input');
    }
})();
$('#statisticLink').addClass('emphasize');
