;
(function () {
    // codes about lottery activity form
    var descriptionEditor = ({
        $floatPanel: $('#descriptionEditor'),
        show: function () {
            lotteryActivity.$floatPanel.hide();
            this.$floatPanel.fadeIn(300);
            JSUtils.scrollToVerticalCenter(this.$floatPanel);

            var description = lotteryActivity.getDescriptionHtml();
            this.editor.setData(description);

            var self = this;
            setTimeout(function () {
                self.editor.focus();
            }, 200);
        },
        hide: function () {
            this.$floatPanel.hide();
            lotteryActivity.$floatPanel.fadeIn(300);
        },
        editor: null,
        init: function () {
            var self = this;
            this.$floatPanel.find('button.ok').click(function () {
                var data = $.trim(self.editor.getData());
                if (data.indexOf('<p>') == 0 && data.lastIndexOf('</p>') == data.length - 4) {
                    data = data.substring(3, data.length - 4);
                }
                lotteryActivity.setDescriptionHtml(data);
                self.hide();
            });
            this.$floatPanel.find('button.cancel').click(function () {
                self.hide();
            });
            this.editor = CKEDITOR.replace('descriptionInput');
            return this;
        }
    }).init();

    var lotteryActivity = JSUtils.buildFloatPanel({
        $floatPanel: $('#lotteryActivityForm'),
        get$LivenessRow: function () {
            return this.$floatPanel.find('tr.liveness');
        },
        get$DualColoredBallTerm: function () {
            return this.$floatPanel.getInputByName('dualColoredBallTerm');
        },
        get$Term: function () {
            return this.$floatPanel.getInputByName('term');
        },
        get$AutoStartTime: function () {
            return this.$floatPanel.getInputByName('autoStartTime');
        },
        get$StartTime: function () {
            return this.$floatPanel.getInputByName('startTime');
        },
        get$ExpectEndTime: function () {
            return this.$floatPanel.getInputByName('expectEndTime');
        },
        get$ContinuousSerialLimit: function () {
            return this.$floatPanel.getInputByName('continuousSerialLimit');
        },
        get$VirtualLiveness: function () {
            return this.$floatPanel.getInputByName('virtualLiveness');
        },
        get$VirtualLivenessUsers: function () {
            return this.$floatPanel.getInputByName('virtualLivenessUsers');
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
        _bindDescriptionEvent: function () {
            this.get$Description().find('a:last').unbind('click').click(function () {
                descriptionEditor.show();
            });
        },
        getDescriptionHtml: function () {
            return this.$floatPanel.getInputByName('description').val();
        },
        setDescriptionHtml: function (html) {
            var $html = $('<div>' + html + '</div>');
            $html.append('<a href="javascript:void(0)">编辑</a>');
            this.get$Description().empty().html($html.html());
            this.$floatPanel.getInputByName('description').val(html);
            this._bindDescriptionEvent();
        },
        get$Id: function () {
            return this.$floatPanel.find('input[name=id]');
        },
        get$MinLivenessToParticipate: function () {
            return this.$floatPanel.find('input[name=minLivenessToParticipate]');
        },
        validateDualColoredBall: function () {
            var dualColoredBallTerm = this.get$DualColoredBallTerm().val();
            if (dualColoredBallTerm.length != 7
                || !(dualColoredBallTerm.match(/^20\d{5}$/) || dualColoredBallTerm.match(/^19\d{5}$/))) {
                alert('双色球期数应为19或20开头的7位数字！');
                this.get$DualColoredBallTerm().focusOrSelect();
                return false;
            }
            return true;
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
            if (!this.get$AutoStartTime().get(0).checked) {
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
            }
            return true;
        },
        validateExpectEndTime: function () {
            var expectEndTime = this.get$ExpectEndTime().trimVal();
            if (expectEndTime == '') {
                alert("预期结束时间未填写！");
                this.get$ExpectEndTime().focusOrSelect();
                return false;
            } else if (!JSUtils.isDateOrDateTimeString(expectEndTime)) {
                alert("预期结束时间格式错误！");
                this.get$ExpectEndTime().focusOrSelect();
                return false;
            }
            return true;
        },
        validateContinuousSerialLimit: function () {
            var continuousSerialLimit = this.get$ContinuousSerialLimit().val();
            if (continuousSerialLimit != '' && !JSUtils.isNumberString(continuousSerialLimit)) {
                alert('抽奖号最大连接个数只能为数字格式！');
                this.get$ContinuousSerialLimit().focusOrSelect();
                return false;
            }
            return true;
        },
        validateLiveness: function () {
            if (this.get$LivenessRow().css('display') != 'none') {
                var virtualLiveness = this.get$VirtualLiveness().val();
                if (virtualLiveness != '') {
                    if (!JSUtils.isNumberString(virtualLiveness)) {
                        alert('最大爱心数只能为数字格式！');
                        this.get$VirtualLiveness().focusOrSelect();
                        return false;
                    }
                    var virtualLivenessUsers = this.get$VirtualLivenessUsers().trimVal();
                    if (virtualLivenessUsers == '') {
                        alert('如果填写了最大爱心，则最大爱心用户必须填写！');
                        this.get$VirtualLivenessUsers().focusOrSelect();
                        return false;
                    }
                }
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
        validateMinLivenessToParticipate: function () {
            var minLivenessToParticipate = this.get$MinLivenessToParticipate().val();
            if (minLivenessToParticipate != '' && !JSUtils.isNumberString(minLivenessToParticipate)) {
                alert('最少需要的爱心数只能为数字格式！');
                this.get$MinLivenessToParticipate().focusOrSelect();
                return false;
            }
            return true;
        },
        validateInput: function () {
            return this.validateTerm() && this.validateStartTime() && this.validateDualColoredBall()
                && this.validateExpectEndTime() && this.validateContinuousSerialLimit()
                && this.validateLiveness() && this.validateParticipantCount()
                && this.validateMinLivenessToParticipate();
        },
        beforeShow: function (args) {
            var displayLiveness = args[0];
            if (displayLiveness) {
                this.get$LivenessRow().show();
            } else {
                this.get$LivenessRow().hide();
            }
            this._bindDescriptionEvent();
        },
        updateExpectEndDateByDualColoredBallTerm: function () {
            var self = this;
            $.post('dual-colored-ball-query-date.json', {
                'fullTermNumber': self.get$DualColoredBallTerm().val()
            }, function (data) {
                if (data['date']) {
                    self.get$ExpectEndTime().val(data['date']);
                }
            });
        },
        postInit: function () {
            var self = this;
            this.get$AutoStartTime().click(function () {
                var checked = this.checked;
                if (checked) {
                    self.get$StartTime().attr('disabled', true);
                } else {
                    self.get$StartTime().attr('disabled', false).focusOrSelect();
                }
            });
            if (this.get$DualColoredBallTerm().trimVal() != '') {
                this.updateExpectEndDateByDualColoredBallTerm();
            }
            this.get$DualColoredBallTerm().blur(function () {
                if (self.validateDualColoredBall()) {
                    self.updateExpectEndDateByDualColoredBallTerm();
                } else {
                    setTimeout(function () {
                        // if user blur by pressing tab key, we must focus after several milliseconds
                        self.get$DualColoredBallTerm().focusOrSelect();
                    }, 100);
                }
            });
        },
        doSubmit: function () {
            var self = this;
            $.post('admin-lottery-activity-add-edit.json', this.$floatPanel.serialize(), function (data) {
                if (!data.success) {
                    alert(data.detail);
                    return;
                }
                if (self.$floatPanel.getInputByName('id').val()) {
                    // if edit, just reload
                    location.reload();
                } else {
                    // if add, go to first page
                    location.href = 'admin-lottery-activity.html';
                }
            });
        }
    });

    $('#addLotteryActivityButton').click(function () {
        lotteryActivity.$floatPanel.setInputValue('id', null);
        lotteryActivity.show();
    });
    JSUtils.loadSelectFormEvents(lotteryActivity.get$CommoditySelect());
    $('table.normal img.edit').click(function () {
        lotteryActivity.show(true);

        var $tr = $(this).getParentByTagName('tr');
        lotteryActivity.get$Id().val($tr.dataOptions('id'));
        lotteryActivity.get$Term().val($tr.find('td.term').text()).focusOrSelect();
        lotteryActivity.get$StartTime().attr('disabled', false).val($tr.find('td.start-time').text());
        lotteryActivity.get$AutoStartTime().get(0).checked = false;
        var $expectEndTimeTd = $tr.find('td.expect-end-time');
        lotteryActivity.get$ExpectEndTime().val($expectEndTimeTd.text());
        lotteryActivity.get$DualColoredBallTerm().val($expectEndTimeTd.dataOptions('dualColoredBallTerm'));
        lotteryActivity.get$VirtualLiveness().val($tr.find('td.virtual-liveness').text());
        lotteryActivity.get$VirtualLivenessUsers().val($tr.find('td.virtual-liveness-users').text());
        lotteryActivity.get$ContinuousSerialLimit().val($tr.find('td.continuous-serial-limit').text());
        lotteryActivity.get$ExpectParticipantCount().val($tr.find('td.expect-participant-count').text());
        lotteryActivity.setDescriptionHtml($tr.find('input.description').val());
        lotteryActivity.get$MinLivenessToParticipate().val($tr.find('input.min-liveness-to-participate').val());

        JSUtils.loadSelectFormValue(lotteryActivity.get$CommoditySelect(), $tr.find('td.commodity').dataOptions('commodityId'));
    });
})();
var stopUrl = 'admin-lottery-activity-stop.json';
var announcementUpdateUrl = 'admin-lottery-activity-update-announcement.json';
var deleteUrl = 'admin-lottery-activity-delete.json';
