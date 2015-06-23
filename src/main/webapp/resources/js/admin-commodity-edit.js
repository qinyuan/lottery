(function () {
    $('form input[type=text]:first').focus();
    $('#commodityEditLink').addClass('emphasize');
    $('form button[name=ok]').click(function (e) {
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
})();
