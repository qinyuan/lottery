(function () {
    var $supportDiv = $('div.main-body div.support-button');
    $supportDiv.find('button').click(function (e) {
        e.preventDefault();
        var activityId = $.url.param('activityId');
        var serial = $.url.param('serial');
        var medium = $.url.param('medium');
        var $self = $(this);
        $.post('add-liveness.json', {
            activityId: activityId,
            serial: serial,
            medium: medium
        }, function (data) {
            if (data.success) {
                showAddLivenessSuccessInfo(e, data['livenessToAdd']);
                //redirect();
            } else if (data.detail == 'noLogin') {
                showLoginForm(function () {
                    hideLoginForm();
                    JSUtils.hideTransparentBackground();
                    $self.trigger('click');
                });
            } else {
                alert(data.detail);
            }
        });
    });

    function redirect() {
        var redirectUrl = $.url.param('redirectUrl');
        if (redirectUrl) {
            location.href = decodeURIComponent(redirectUrl);
        }
    }

    function showAddLivenessSuccessInfo(event, liveness) {
        $supportDiv.find('div.text').hide();
        $supportDiv.find('div.text.success').show();
        var $button = $supportDiv.find('button');
        $button.after('<a href="index.html" class="blue">返回首页</a>');
        $button.remove();
        JSUtils.createBubble(event, '+' + liveness, '#ff0000');
    }
})();