(function () {
    $('#commodityEditLink').addClass('emphasize');

    var $form = $('#commodityForm').focusFirstTextInput();
    var $okButton = $form.find('button[name=ok]');
    var $cancelButton = $form.find('button[name=cancel]');
    $okButton.click(function (e) {
        var $name = $('form input[name=name]');
        if ($name.trimVal() == '') {
            alert('商品名不能为空');
            $name.focusOrSelect();
            e.preventDefault();
            return false;
        }

        var $price = $('form input[name=price]');
        var priceValue = $price.val();
        if (!JSUtils.isNumberString(priceValue) || parseFloat(priceValue) < 0) {
            alert('价格必须为不小于零的数字')
            $price.focusOrSelect();
            e.preventDefault();
            return false;
        }

        if (!JSUtils.validateUploadFile('snapshot', '商品缩略图未设置')) {
            e.preventDefault();
            return false;
        }

        if (!JSUtils.validateUploadFile('detailImage', '商品详细图未设置')) {
            e.preventDefault();
            return false;
        }
    });
    $('img.delete').click(function () {
        var id = $(this).getParentByTagName('tr').dataOptions()['id'];
        if (confirm('确定删除？')) {
            $.post('admin-commodity-delete.json', {
                id: id
            }, JSUtils.normalAjaxCallback);
        }
    });

    $('img.edit').click(function () {
        var $tr = $(this).getParentByTagName('tr');
        var id = $tr.dataOptions()['id'];
        var name = $tr.find('td.name').trimText();
        var price = $tr.find('td.price').trimText();
        var own = $tr.find('td.own').trimText() == '是';
        var snapshot = $tr.find('td.snapshot img').attr('src');
        var detailImage = $tr.find('td.detailImage img').attr('src');

        $form.setInputValue('id', id);
        $form.setInputValue('name', name);
        $form.setInputValue('price', price);
        $form.setInputValue('own', own);
        $form.setInputValue('snapshot', snapshot);
        $form.setInputValue('snapshotFile', null);
        $form.setInputValue('detailImage', detailImage);
        $form.setInputValue('detailImageFile', null);

        $okButton.addClass('btn-success').removeClass('btn-primary').text('提交修改');
        $cancelButton.show();
        $form.focusFirstTextInput();
    });
    $cancelButton.click(function () {
        $okButton.addClass('btn-primary').removeClass('btn-success').text('添加商品');
        $form.setInputValue('id', null);
        $cancelButton.hide();
        $form.focusFirstTextInput();
    });
})();
