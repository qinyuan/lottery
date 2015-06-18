;
(function () {
    var $addImageForm = $('#addImageForm').addClass('shadow');
    $('#indexEditLink').addClass('emphasize');
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
        showImageForm($(this).dataOptions()['id']);
    });

    function showImageForm(id, rowId, image, backImage) {
        $addImageForm.find('input[name=id]').val(id);
        $addImageForm.find('input[name=rowIndex]').val(rowId);
        $addImageForm.find('#image').val(image);
        $addImageForm.find('#backImage').val(backImage);
        $addImageForm.find('div.input input[type=file]').val(null);

        JSUtils.showTransparentBackground();
        $addImageForm.fadeIn(300);
    }
})();
