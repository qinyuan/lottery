(function () {
    var $supportDiv = $('div.main-body div.support-button');
    $supportDiv.find('button').click(function (e) {
        var activityId = $.url.param('activityId');
        var serial = $.url.param('serial');
        var medium = $.url.param('medium');
        $.post('add-liveness.json', {
            activityId: activityId,
            serial: serial,
            medium: medium
        }, function (data) {
            if (data.success) {
                showAddLivenessSuccessInfo(e, data['livenessToAdd']);
                redirect();
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
        $supportDiv.find('button').attr('disabled', true).addClass('mediumTransparent');
        JSUtils.createBubble(event, '+' + liveness, '#ff0000');
    }
})();