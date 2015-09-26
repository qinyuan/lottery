(function () {
    if ($('#announcementEditor').size() > 0) {
        // codes about announcement edit form
        var $announceImages = $('table.normal img.announce');
        var $announceEditForm = $('#announceEditForm');
        var $announceOkButton = $announceEditForm.getButtonByName('ok');
        var $announceCancelButton = $announceEditForm.getButtonByName('cancel');
        var $announceEditor = CKEDITOR.replace('announcementEditor', {'width': 650});

        function showAnnounceEditForm(id, winners, announcement) {
            JSUtils.showTransparentBackground(1);
            $announceEditForm.setInputValue('id', id);
            $announceEditForm.setInputValue('winners', winners);
            $announceEditor.setData(announcement);
            $announceEditForm.fadeIn(500).focusFirstTextInput();
            JSUtils.scrollToVerticalCenter($announceEditForm);
        }

        $announceImages.click(function () {
            var $tr = $(this).getParentByTagName('tr');
            showAnnounceEditForm(
                $tr.dataOptions('id'),
                $tr.find('td.winners').trimText(),
                $.trim($tr.find('td.announcement').html())
            );
        });
        $announceCancelButton.click(function () {
            $announceEditForm.fadeOut(300, function () {
                JSUtils.hideTransparentBackground();
            });
        });
        $announceOkButton.click(function (e) {
            e.preventDefault();
            var data = {
                id: $announceEditForm.find('input[name=id]').val(),
                winners: $announceEditForm.find('input[name=winners]').val(),
                announcement: $announceEditor.getData()
            };
            $.post(announcementUpdateUrl, data, JSUtils.normalAjaxCallback);
            return false;
        });
    }
})();
(function () {
    var $listType = $('div.list-type');

    $listType.find('input[type=radio]').click(function () {
        location.href = location.origin + location.pathname + '?listType=' + $(this).val();
    });

    $('table.normal img.stop').click(function () {
        if (confirm('确定强行结束该秒杀活动？')) {
            var activityId = $(this).getParentByTagName('tr').dataOptions('id');
            $.post(stopUrl, {'id': activityId}, JSUtils.normalAjaxCallback);
        }
    });
    $('table.normal img.delete').click(function () {
        if (confirm('确定删除？')) {
            var activityId = $(this).getParentByTagName('tr').dataOptions('id');
            $.post(deleteUrl, {'id': activityId}, JSUtils.normalAjaxCallback);
        }
    });
})();
function buildAddEditFloatPanel(options) {
    var descriptionEditor = ({
        $floatPanel: $('#descriptionEditor'),
        show: function () {
            activityFloatPanel.$floatPanel.hide();
            this.$floatPanel.fadeIn(300);
            JSUtils.scrollToVerticalCenter(this.$floatPanel);

            var description = activityFloatPanel.getDescriptionHtml();
            this.editor.setData(description);

            var self = this;
            setTimeout(function () {
                self.editor.focus();
            }, 200);
        },
        hide: function () {
            this.$floatPanel.hide();
            activityFloatPanel.$floatPanel.fadeIn(300);
        },
        editor: null,
        init: function () {
            var self = this;
            this.$floatPanel.find('button.ok').click(function () {
                var data = $.trim(self.editor.getData());
                /*if (data.indexOf('<p>') == 0 && data.lastIndexOf('</p>') == data.length - 4) {
                 data = data.substring(3, data.length - 4);
                 }*/
                activityFloatPanel.setDescriptionHtml(data);
                self.hide();
            });
            this.$floatPanel.find('button.cancel').click(function () {
                self.hide();
            });
            this.editor = CKEDITOR.replace('descriptionInput');
            return this;
        }
    }).init();

    var obj = {
        get$Term: function () {
            return this.$floatPanel.getInputByName('term');
        },
        get$StartTime: function () {
            return this.$floatPanel.getInputByName('startTime');
        },
        get$ExpectParticipantCount: function () {
            return this.$floatPanel.getInputByName('expectParticipantCount');
        },
        get$CommoditySelect: function () {
            return this.$floatPanel.find('div.commodity-select');
        },
        get$Description: function () {
            return this.$floatPanel.find('td.description div.description');
        },
        bindDescriptionEvent: function () {
            this.$floatPanel.find('a.edit-description').unbind('click').click(function () {
                descriptionEditor.show();
            });
            this.$floatPanel.find('td.content.description').unbind('click').click(function () {
                descriptionEditor.show();
            });
            /*this.get$Description().find('a:last').unbind('click').click(function () {
             descriptionEditor.show();
             });*/
        },
        getDescriptionHtml: function () {
            return this.$floatPanel.getInputByName('description').val();
        },
        setDescriptionHtml: function (html) {
            this.get$Description().empty().html(html);
            this.$floatPanel.getInputByName('description').val(html);
            this.bindDescriptionEvent();
        },
        get$Id: function () {
            return this.$floatPanel.find('input[name=id]');
        },
        validateTerm: function () {
            var $term = this.get$Term();
            if ($term.trimVal() == '') {
                alert('期数不能为空！');
                $term.focusOrSelect();
                return false;
            } else if (!JSUtils.isNumberString($term.val())) {
                alert('期数必须为数字格式！');
                $term.focusOrSelect();
                return false;
            }
            return true;
        },
        validateStartTime: function () {
            if (this.get$AutoStartTime && this.get$AutoStartTime().get(0).checked) {
                return true;
            }

            var startTime = this.get$StartTime().trimVal();
            if (startTime == '') {
                alert('开始时间未填写！');
                this.get$StartTime().focusOrSelect();
                return false;
            } else if (!JSUtils.isDateOrDateTimeString(startTime)) {
                alert('开始时间格式错误！');
                this.get$StartTime().focusOrSelect();
                return false;
            }
            return true;
        },
        validateParticipantCount: function () {
            var expectParticipantCount = this.get$ExpectParticipantCount().val();
            if (expectParticipantCount != '' && !JSUtils.isNumberString(expectParticipantCount)) {
                alert('预期参与人数只能为数字格式！');
                this.get$ExpectParticipantCount().focusOrSelect();
                return false;
            }
            return true;
        },
        validateInput: function () {
            for (var key in this) {
                if (this.hasOwnProperty(key) && key.indexOf('validate') == 0 && key != 'validateInput') {
                    if (!this[key]()) {
                        return false;
                    }
                }
            }
            return true;
        },
        doSubmit: function () {
            var self = this;
            $.post(this.addOrEditUrl, this.$floatPanel.serialize(), function (data) {
                if (!data.success) {
                    alert(data.detail);
                    return;
                }
                if (self.$floatPanel.getInputByName('id').val()) {
                    // if edit, just reload
                    location.reload();
                } else {
                    // if add, go to first page
                    location.href = location.origin + location.pathname;
                }
            });
        }
    };

    var activityFloatPanel = JSUtils.buildFloatPanel(JSUtils.extendObject(obj, options));
    activityFloatPanel.bindDescriptionEvent();
    JSUtils.loadSelectFormEvents(activityFloatPanel.get$CommoditySelect());
    return activityFloatPanel;
}
$('#commoditySeckillLink').addClass('emphasize');
