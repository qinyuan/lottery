;
(function () {
    $('#systemEditLink').addClass('emphasize');

    var imageForm = JSUtils.buildFloatPanel({
        $floatPanel: $('#addImageForm'),
        doSubmit: function () {
            this.$floatPanel.get(0).submit();
        },
        validateInput: function () {
            return JSUtils.validateUploadFile('image', '前景图不能为空');
        },
        setValues: function (id, pageIndex, image, backImage) {
            this.$floatPanel.setInputValue('id', id);
            this.$floatPanel.setInputValue('pageIndex', pageIndex);
            this.$floatPanel.setInputValue('image', image);
            this.$floatPanel.setInputValue('backImage', backImage);
            return this;
        }
    });

    $('table img.edit').click(function () {
        var $this = $(this);
        var $tr = $this.getParentByTagName('tr');
        var id = $tr.data('id');
        var $imageAnchor = $tr.find('td.image a');
        var image = $imageAnchor.size() > 0 ? $imageAnchor.attr('href') : '';
        var $backImageAnchor = $tr.find('td.back-image a');
        var backImage = $backImageAnchor.size() > 0 ? $backImageAnchor.attr('href') : '';
        var pageIndex = $this.getParentByTagNameAndClass('div', 'sub-index').dataOptions('index');
        if (id) {
            imageForm.setValues(id, pageIndex, image, backImage).show();
        }
    });

    $('button.add-image').click(function () {
        var pageIndex = $(this).parent().dataOptions('index');
        imageForm.setValues(null, pageIndex, null, null).show(pageIndex);
    });

    $('table td.action img.delete').click(function () {
        var $tr = $(this).getParentByTagName('tr');
        var id = $tr.data('id');
        if (confirm('确定删除？')) {
            $.post('admin-sub-index-image-delete.json', {id: id}, JSUtils.normalAjaxCallback);
        }
    });
    $('table td.action img.rank-up').click(function () {
        var $tr = $(this).getParentByTagName('tr');
        if (!$tr.prev().is('tr')) {
            return;
        }

        var id = $tr.data('id');
        $.post('admin-sub-index-image-rank-up.json', {id: id}, JSUtils.normalAjaxCallback);
    });
    $('table td.action img.rank-down').click(function () {
        var $tr = $(this).getParentByTagName('tr');
        if (!$tr.next().is('tr')) {
            return;
        }

        var id = $tr.data('id');
        $.post('admin-sub-index-image-rank-down.json', {id: id}, JSUtils.normalAjaxCallback);
    });
})();