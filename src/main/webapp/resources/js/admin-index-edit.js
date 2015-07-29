;
(function () {
    var $addImageForm = $('#addImageForm').addClass('shadow');
    $('#systemEditLink').addClass('emphasize');
    $('#addImage').click(function () {
        showImageForm();
    });
    $('#addImageCancel').click(function () {
        $addImageForm.fadeOut(300);
        JSUtils.hideTransparentBackground();
    });
    $('div.form div.image-row button.add-cycle-image').click(function () {
        showImageForm(null, $(this).dataOptions()['rowIndex']);
    });

    $('div.form div.image-row div.image button.edit-image').click(function () {
        var $this = $(this);
        var id = $this.dataOptions()['id'];
        var $div = $this.parent();
        var image = $div.find('img').attr('src');
        var backImage = $div.find('input[type=hidden]').val();
        showImageForm(id, null, image, backImage);
    });

    $('div.form div.image-row div.image button.delete-image').click(function () {
        $.post('admin-index-delete-image.json', {
            id: $(this).dataOptions()['id']
        }, JSUtils.normalAjaxCallback);
    });

    $('#cycleIntervalInput').blur(function () {
        var $this = $(this);
        var $infoDiv = $this.parent().next();
        $.post('admin-index-image-cycle-interval.json', {
            'cycleInterval': $this.val()
        }, function (data) {
            if (data.success) {
                $infoDiv.text('时间间隔更新成功').css('color', '#61BB5E').show();
                setTimeout(function () {
                    $infoDiv.fadeOut(1000);
                }, 1000);
            } else {
                $infoDiv.text(data.detail).css('color', '#ff0000');
            }
        });
    });

    function showImageForm(id, rowIndex, image, backImage) {
        $addImageForm.find('input[name=id]').val(id);
        $addImageForm.find('input[name=rowIndex]').val(rowIndex);
        $addImageForm.find('#image').val(image);
        $addImageForm.find('#backImage').val(backImage);
        $addImageForm.find('div.input input[type=file]').val(null);

        JSUtils.showTransparentBackground(1);
        $addImageForm.fadeIn(300);
    }
})();
