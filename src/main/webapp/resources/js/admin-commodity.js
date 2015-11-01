;
(function () {
    // code to set commodity visibility
    $('td.visible div.switch').on('switch-change', function (e, data) {
        var visible = data.value;
        var $this = $(this);
        $.post('admin-commodity-update-visible.json', {
            id: $this.getParentByTagName('tr').dataOptions('id'),
            visible: visible
        }, function (data) {
            if (!data.success) {
                alert('数据更新失败，错误原因：' + data.detail);
                location.reload();
            }
        });
    });
})();
(function () {
    // codes about deleting/ranking commodity
    $('table img.delete').click(function () {
        var id = $(this).getParentByTagName('tr').dataOptions('id');
        if (confirm('确定删除？')) {
            $.post('admin-commodity-delete.json', {id: id}, JSUtils.normalAjaxCallback);
        }
    });
    $('table img.rank-up').click(function () {
        var id = $(this).getParentByTagName('tr').dataOptions('id');
        $.post('admin-commodity-rank-up.json', {id: id}, JSUtils.normalAjaxCallback);
    });
    $('table img.rank-down').click(function () {
        var id = $(this).getParentByTagName('tr').dataOptions('id');
        $.post('admin-commodity-rank-down.json', {id: id}, JSUtils.normalAjaxCallback);
    });
})();
(function () {
    // codes about adding/editing commodity
    var commodityForm = JSUtils.buildFloatPanel({
        $floatPanel:  $('#commodityForm'),
        beforeShow: function(){
            decreaseSwitchZIndex();
        },
        doSubmit: function(){
            this.$floatPanel.get(0).submit();
        },
        postInit: function(){
            var self = this;
            this.$addDetail = $('button.add-detail').click(function(){
                var html = JSUtils.handlebars("detailImageTemplate");
                var $html = $(html);
                $html.find('div.delete img').click(function(){
                    $(this).getParentByTagNameAndClass('div', 'delete').parent().remove();
                });
                $html.find('input').attr('id', null);
                $html.insertBefore(self.$addDetail).focusFirstTextInput();
            });
            this.$name= this.$floatPanel.getInputByName('name');
            this.$price = this.$floatPanel.getInputByName('price');
            setTimeout(function(){
                self.$addDetail.trigger('click') ;
            }, 500);
        },
        validateInput: function(){
            if (this.$name.trimVal() == '') {
                alert('商品名不能为空');
                this.$name.focusOrSelect();
                return false;
            }

            var priceValue = this.$price.val();
            if (!JSUtils.isNumberString(priceValue) || parseFloat(priceValue) < 0) {
                alert('价格必须为不小于零的数字');
                this.$price.focusOrSelect();
                return false;
            }

            return JSUtils.validateUploadFile('snapshot', '商品缩略图未设置');
        }
    });

    $('div.form div.add button').click(function(){
        commodityForm.show();
    }).trigger('click');

    function decreaseSwitchZIndex(){
        $('.switch-mini').css('z-index', 1);
    }

    /*
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

    $('table img.edit').click(function () {
        var $tr = $(this).getParentByTagName('tr');
        var id = $tr.dataOptions()['id'];
        var name = $tr.find('td.name').trimText();
        var price = $tr.find('td.price').trimText();
        var own = $tr.find('td.own').trimText() == '是';
        var snapshot = $tr.find('td.snapshot img').attr('src');
        var detailImage = $tr.find('td.detailImage img').attr('src');
        var backImage = $tr.find('td.backImage img').attr('src');

        $form.setInputValue('id', id);
        $form.setInputValue('name', name);
        $form.setInputValue('price', price);
        $form.setInputValue('own', own);
        $form.setInputValue('snapshot', snapshot);
        $form.setInputValue('snapshotFile', null);
        $form.setInputValue('detailImage', detailImage);
        $form.setInputValue('detailImageFile', null);
        $form.setInputValue('backImage', backImage);
        $form.setInputValue('backImageFile', null);
        if ($.url.param('pageNumber')) {
            $form.setInputValue('pageNumber', $.url.param('pageNumber'));
        }

        $okButton.addClass('btn-success').removeClass('btn-primary').text('提交修改');
        $cancelButton.show();
        $form.focusFirstTextInput().scrollToTop();
    });
    $cancelButton.click(function () {
        $okButton.addClass('btn-primary').removeClass('btn-success').text('添加商品');
        $form.setInputValue('id', null);
        $form.setInputValue('pageNumber', null);
        $cancelButton.hide();
        $form.focusFirstTextInput();
    });*/
})();
(function () {
    // code about editing index
    $('div.form table td.index img').click(editImageClickEvent);

    function editImageClickEvent() {
        var $div = $(this).getParentByTagName("div");
        var rankIndex = $div.dataOptions('rankIndex');

        $div.empty();

        var $input = $('<input type="text" class="form-control" value="' + rankIndex + '"/>');
        $input.appendTo($div);

        var $button = $('<button class="btn btn-success btn-xs">确定</button>');
        $button.appendTo($div);

        $input.focusOrSelect().blur(function () {
            var $this = $(this);
            var newRankIndex = $this.val();
            var $div = $this.getParentByTagName('div');
            var oldRankIndex = $div.dataOptions('rankIndex');
            if (!validateRankIndex(newRankIndex) || newRankIndex == oldRankIndex) {
                recover($div);
            } else {
                var id = $div.getParentByTagName('tr').dataOptions('id');
                $.post('admin-commodity-rank-to.json', {'id': id, 'rankIndex': newRankIndex}, function (data) {
                    if (data.success) {
                        location.reload();
                    } else {
                        alert(data.detail);
                        recover($div);
                    }
                });
            }
        });
    }

    function recover($div) {
        var rankIndex = $div.dataOptions('rankIndex');
        $div.empty().html(rankIndex + ' <img title="编辑" class="link" src="resources/css/images/note_edit.png">');
        $div.find('img').click(editImageClickEvent);
    }

    function validateRankIndex(rankIndex) {
        if (JSUtils.isIntegerString(rankIndex)) {
            return true;
        } else {
            alert('序号必须为整数格式');
            return false;
        }
    }
})();
JSUtils.recordScrollStatus();
$('#commoditySeckillLink').addClass('emphasize');
