(function () {
    var $copyLinkInput = $('div.main-body div.left > div.copy input[type=text]');
    var $copySuccess = $('div.main-body div.left > div.copy span.info');
    $('div.main-body div.left > div.copy button').zclip({
        //path: "js/ZeroClipboard.swf",
        path: "resources/js/lib/jquery-zclip/ZeroClipboard.swf",
        copy: function () {
            return $copyLinkInput.val();
        },
        afterCopy: function () {
            $copySuccess.fadeIn(300);
            setTimeout(function () {
                $copySuccess.fadeOut(1000);
            }, 2000);
        }
    });
    $('div.main-body div.right div.body table tbody td').hover(function () {
        var $this = $(this);
        if (!$this.hasClass('action')) {
            $this.parent().addClass('hover');
        }
    }, function () {
        var $this = $(this);
        if (!$this.hasClass('action')) {
            $this.parent().removeClass('hover');
        }
    }).click(function () {
        var $this = $(this);
        if ($this.hasClass('action')) {
            return;
        }

        var $parent = $this.parent();
        if ($parent.hasClass('selected')) {
            $parent.removeClass('selected');
        } else {
            $parent.parent().find('tr.selected').removeClass('selected');
            $parent.addClass('selected');
        }
    })/*.find('img.add-liveness').click(function (e) {
     var $tr = $(this).getParentByTagName('tr');
     var userId = $tr.data('id');
     $.post('add-liveness-by-share.json', {
     'userId': userId,
     'activityId': window['activityId']
     }, function (data) {
     if (data.success) {
     var livenessToAdd = parseInt(data['livenessToAdd']);
     JSUtils.createBubble(e, '+' + livenessToAdd, '#ff0000');
     var $liveness = $tr.find('td.liveness');
     var oldLiveness = parseInt($liveness.text());
     var newLiveness = oldLiveness + livenessToAdd;
     $liveness.text(newLiveness);
     } else {
     alert(data.detail);
     }
     });
     })*/;
})();