;
(function () {
    // codes about lottery activity form
    var lotteryActivity = buildAddEditFloatPanel({
        $floatPanel: $('#lotteryActivityForm'),
        get$LivenessRow: function () {
            return this.$floatPanel.find('tr.liveness');
        },
        get$DualColoredBallTerm: function () {
            return this.$floatPanel.getInputByName('dualColoredBallTerm');
        },
        get$AutoStartTime: function () {
            return this.$floatPanel.getInputByName('autoStartTime');
        },
        get$ExpectEndTime: function () {
            return this.$floatPanel.getInputByName('expectEndTime');
        },
        get$CloseTime: function () {
            return this.$floatPanel.getInputByName('closeTime');
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
        get$MinLivenessToParticipate: function () {
            return this.$floatPanel.find('input[name=minLivenessToParticipate]');
        },
        get$SerialNumberRange: function () {
            return this.$floatPanel.find('input[name=serialNumberRange]');
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
        validateMinLivenessToParticipate: function () {
            var minLivenessToParticipate = this.get$MinLivenessToParticipate().val();
            if (minLivenessToParticipate != '' && !JSUtils.isNumberString(minLivenessToParticipate)) {
                alert('最少需要的爱心数只能为数字格式！');
                this.get$MinLivenessToParticipate().focusOrSelect();
                return false;
            }
            return true;
        },
        validateSerialNumberRange: function () {
            var serialNumberRange = this.get$SerialNumberRange().val();
            if ($.trim(serialNumberRange) == '') {
                alert('抽奖号取值范围未填写！');
                this.get$SerialNumberRange().focusOrSelect();
                return false;
            }
            if (!serialNumberRange.match(/^\d+~\d+$/)) {
                alert('抽奖号取值范围格式错误，正确格式如"10~100000"');
                this.get$SerialNumberRange().focusOrSelect();
                return false;
            }
            return true;
        },
        updateExpectEndDateByDualColoredBallTerm: function () {
            var self = this;
            $.post('dual-colored-ball-query-date.json', {
                'fullTermNumber': self.get$DualColoredBallTerm().val()
            }, function (data) {
                if (data['date']) {
                    self.get$ExpectEndTime().val(data['date']);
                    self.get$CloseTime().val(data['date']);
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
        addOrEditUrl: 'admin-lottery-activity-add-edit.json'
    });

    $('#addLotteryActivityButton').click(function () {
        lotteryActivity.$floatPanel.setInputValue('id', null);
        lotteryActivity.get$LivenessRow().hide();
        lotteryActivity.show();
    });
    $('table.normal img.edit').click(function () {
        lotteryActivity.get$LivenessRow().show();
        lotteryActivity.show();

        var $tr = $(this).getParentByTagName('tr');
        lotteryActivity.get$Id().val($tr.dataOptions('id'));
        lotteryActivity.get$Term().val($tr.find('td.term').text()).focusOrSelect();
        lotteryActivity.get$StartTime().attr('disabled', false).val($tr.find('td.start-time').text());
        lotteryActivity.get$AutoStartTime().get(0).checked = false;
        var $expectEndTimeTd = $tr.find('td.expect-end-time');
        lotteryActivity.get$ExpectEndTime().val($expectEndTimeTd.text());
        lotteryActivity.get$DualColoredBallTerm().val($expectEndTimeTd.dataOptions('dualColoredBallTerm'));
        lotteryActivity.get$CloseTime().val($tr.find('input.close-time').val());
        lotteryActivity.get$VirtualLiveness().val($tr.find('td.virtual-liveness').text());
        lotteryActivity.get$VirtualLivenessUsers().val($tr.find('td.virtual-liveness-users').text());
        lotteryActivity.get$ContinuousSerialLimit().val($tr.find('td.continuous-serial-limit').text());
        lotteryActivity.get$ExpectParticipantCount().val($tr.find('td.expect-participant-count').text());
        lotteryActivity.setDescriptionHtml($tr.find('input.description').val());
        lotteryActivity.get$MinLivenessToParticipate().val($tr.find('input.min-liveness-to-participate').val());
        lotteryActivity.get$SerialNumberRange().val($tr.find('input.serial-number-range').val());

        JSUtils.loadSelectFormValue(lotteryActivity.get$CommoditySelect(), $tr.find('td.commodity')
            .dataOptions('commodityId'));
    });
})();
var stopUrl = 'admin-lottery-activity-stop.json';
var announcementUpdateUrl = 'admin-lottery-activity-update-announcement.json';
var deleteUrl = 'admin-lottery-activity-delete.json';
