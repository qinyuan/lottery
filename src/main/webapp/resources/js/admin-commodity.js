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
    // codes about deleting/ranking commodity/image
    $('div.form table td.action img.delete').click(function () {
        var id = $(this).getParentByTagName('tr').dataOptions('id');
        if (confirm('确定删除？')) {
            $.post('admin-commodity-delete.json', {id: id}, JSUtils.normalAjaxCallback);
        }
    });
    $('div.form table td.action img.rank-up').click(function () {
        var id = $(this).getParentByTagName('tr').dataOptions('id');
        $.post('admin-commodity-rank-up.json', {id: id}, JSUtils.normalAjaxCallback);
    });
    $('div.form table td.action img.rank-down').click(function () {
        var id = $(this).getParentByTagName('tr').dataOptions('id');
        $.post('admin-commodity-rank-down.json', {id: id}, JSUtils.normalAjaxCallback);
    });

    $('div.form table td.detail-and-back table.inner img.delete').click(function () {
        var id = $(this).getParentByTagName('tr').data('id');
        if (confirm('确定删除？')) {
            $.post('admin-commodity-image-delete.json', {id: id}, JSUtils.normalAjaxCallback);
        }
    });
    $('div.form table td.detail-and-back table.inner img.rank-up').click(function () {
        var id = $(this).getParentByTagName('tr').data('id');
        $.post('admin-commodity-image-rank-up.json', {id: id}, JSUtils.normalAjaxCallback);
    });
    $('div.form table td.detail-and-back table.inner img.rank-down').click(function () {
        var id = $(this).getParentByTagName('tr').data('id');
        $.post('admin-commodity-image-rank-down.json', {id: id}, JSUtils.normalAjaxCallback);
    });
})();
(function () {
    // codes about adding/editing commodity/image
    var commodityForm = JSUtils.buildFloatPanel({
        $floatPanel: $('#commodityForm'),
        beforeShow: function () {
            decreaseSwitchZIndex();
        },
        doSubmit: function () {
            this.$floatPanel.get(0).submit();
        },
        postInit: function () {
            this.$name = this.$floatPanel.getInputByName('name');
            this.$price = this.$floatPanel.getInputByName('price');
            this.$floatPanel.setInputValue('pageNumber', $.url.param('pageNumber'));
        },
        validateInput: function () {
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
        },
        setValues: function (id, name, price, snapshot) {
            this.$floatPanel.setInputValue('id', id);
            this.$floatPanel.setInputValue('name', name);
            this.$floatPanel.setInputValue('price', price);
            this.$floatPanel.setInputValue('snapshot', snapshot);
            return this;
        }
    });

    $('div.form div.add button').click(function () {
        commodityForm.setValues().show();
    });

    $('div.form table td.action img.edit').click(function () {
        var $tr = $(this).getParentByTagName('tr');
        var id = $tr.dataOptions('id');
        var name = $tr.find('td.name').trimText();
        var price = $tr.find('td.price').trimText();
        var snapshot = $tr.find('td.snapshot img').attr('src');

        commodityForm.setValues(id, name, price, snapshot).show();
    });

    var commodityImageForm = JSUtils.buildFloatPanel({
        $floatPanel: $('#commodityImageForm'),
        beforeShow: function () {
            decreaseSwitchZIndex();
        },
        doSubmit: function () {
            this.$floatPanel.get(0).submit();
        },
        postInit: function () {
            this.$floatPanel.setInputValue('pageNumber', $.url.param('pageNumber'));
        },
        validateInput: function () {
            return JSUtils.validateUploadFile('detailImage', '前景图不能为空');
        },
        setValues: function (id, commodityId, detailImage, backImage) {
            this.$floatPanel.setInputValue('id', id);
            this.$floatPanel.setInputValue('commodityId', commodityId);
            this.$floatPanel.setInputValue('detailImage', detailImage);
            this.$floatPanel.setInputValue('backImage', backImage);
            return this;
        }
    });

    $('div.form table td.detail-and-back img.add').click(function () {
        var commodityId = $(this).getParentByTagName('tr').dataOptions('id');
        if (commodityId) {
            commodityImageForm.setValues(null, commodityId).show();
        }
    });
    $('div.form table td.detail-and-back table.inner img.edit').click(function () {
        var $tr = $(this).getParentByTagName('tr');
        var id = $tr.data('id');
        var $detailImageAnchor = $tr.find('td.detail-image a');
        var detailImage = $detailImageAnchor.size() > 0 ? $detailImageAnchor.attr('href') : '';
        var $backImageAnchor = $tr.find('td.back-image a');
        var backImage = $backImageAnchor.size() > 0 ? $backImageAnchor.attr('href') : '';
        if (id) {
            commodityImageForm.setValues(id, null, detailImage, backImage).show();
        }
    });

    function decreaseSwitchZIndex() {
        $('.switch-mini').css('z-index', 1);
    }
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
