;
(function () {
    var $form = $('div.form form');
    $form.setDefaultButtonByClass('ok');

    var $prompt = $form.find('div.prompt');
    var $inputDiv = $form.find('div.input');
    $inputDiv.find('img').click(function () {
        var $input = $('<input type="text" name="usernames" class="form-control" placeholder="请输入新的邮箱名"/>');
        $input.keydown(function (e) {
            var $this = $(this);
            if (e.keyCode == 9 && $this.next().is('img')) {
                e.preventDefault();
                $this.next().trigger('click');
                setTimeout(function () {
                    $this.next().focus();
                }, 200);
            }
        });

        $input.insertBefore($(this)).focus();
        if ($prompt.css('display') == 'none') {
            $prompt.show(500);
        }
    }).trigger('click');

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

        var $this = $(this);
        $this.text('正在处理...');
        setTimeout(function () {
            JSUtils.postArrayParams('admin-batch-register-submit.json', {emails: emails}, function (data) {
                $this.text('确定');
                if (data.success) {
                    alert('批量注册成功，注册邮件已经发送到对应的邮箱');
                } else if (data.detail == 'invalid') {
                    var info = "";
                    if (data['invalidEmails']) {
                        info += "存在无效邮箱：\n" + data['invalidEmails'];
                    }
                    if (data['registeredEmails']) {
                        if (info.length > 0) {
                            info += "\n";
                        }
                        info += "如下邮箱已经注册\n" + data['registeredEmails'];
                    }
                    alert(info);
                } else {
                    alert(data.detail);
                }
            });
        }, 100); // delay to let button text change
    });

    function get$Inputs() {
        return $inputDiv.find('input');
    }
})();
$('#statisticLink').addClass('emphasize');
