;
(function () {
    var participantCount = ({
        update: function () {
            var commodity = getSelectedCommodity();
            var $parent = this.$div.getParentByTagNameAndClass('div', 'right');
            var self = this;
            //var $participantCountDiv = this.$div;
            if (commodity.inLottery) {
                $.post('lottery-participant-count.json', {
                    commodityId: commodity.id
                }, function (data) {
                    if (data['participantCount']) {
                        self.$div.find("span.participant-count").text(data['participantCount']);
                        self.$div.show();
                        $parent.addClass('two-rows');
                    } else {
                        self.$div.hide();
                        $parent.removeClass('two-rows');
                    }
                });
            } else {
                self.$div.hide();
                $parent.removeClass('two-rows');
            }
        },
        hide: function () {
            this.$div.hide();
        },
        init: function () {
            this.$div = $('div.main-body > div.summary div.participant-count');
            var self = this;
            setInterval(function () {
                self.update();
            }, 3000); // refresh each three seconds
            return this;
        }
    }).init();

    var remainingTime = {
        $remainingTime: $('div.main-body div.summary div.remaining-time'),
        getRemainingTimeString: function () {
            return this._remainingTimeRecorder == null ? null : this._remainingTimeRecorder.getRemainingTimeString();
        },
        getRemainingTimeObject: function () {
            return this._remainingTimeRecorder.getRemainingTime();
        },
        _stopRemainingTimeDigitClockUpdateTimer: function () {
            if (this._updateTimer) {
                clearInterval(this._updateTimer);
            }
        },
        updateRemainingTimeDigitClock: function () {
            this.$remainingTime.show();
            var $click = this.$remainingTime.find('div.clock');
            var digitClock = JSUtils.digitClock($click, {
                'backgroundImage': 'resources/css/images/commodity/digit.png',
                'initValue': this.getRemainingTimeString()
            });

            var self = this;
            this._stopRemainingTimeDigitClockUpdateTimer();
            this._updateTimer = setInterval(function () {
                digitClock.to(self.getRemainingTimeString());
            }, 1000);
        },
        hide: function () {
            this.$remainingTime.hide();
        },
        init: function () {
            var self = this;

            function clear() {
                self._stopRemainingTimeDigitClockUpdateTimer();
                self._remainingTimeRecorder = null;
                self.hide();
            }

            var commodity = getSelectedCommodity();
            var params = {'commodityId': commodity.id};
            if (commodity.inLottery) {
                params['activityType'] = 'lottery';
            } else if (commodity.inSeckill) {
                params['activityType'] = 'seckill';
            } else {
                clear();
                return;
            }

            $.post('activity-remaining-seconds.json', params, function (data) {
                if (data['seconds']) {
                    self._remainingTimeRecorder = JSUtils.remainingTimeRecorder(data['seconds']);
                    self.updateRemainingTimeDigitClock();
                } else {
                    clear();
                }
            });
            return this;
        }
    };

    var snapshots = ({
        $divs: $('div.main-body div.snapshots div.snapshot'),
        loadDetail: function (imageId) {
            remainingTime.hide();
            participantCount.hide();
            var $detail = $('div.main-body div.detail');
            var $img = $detail.find('img');
            $img.hide();

            var self = this;
            $.post('commodity-info.json', {
                id: imageId
            }, function (data) {
                var commodity = data['commodity'];
                $detail.setBackgroundImage(commodity['backImage']);
                $img.attr('src', commodity['detailImage']);
                $img.fadeIn(500, function () {
                    participantCount.update();
                    remainingTime.init();
                });
                self.loadCommodityMap(data['commodityMaps']);
            })
        },
        displaySize: 6,
        startIndex: 0,
        endIndex: 5,
        loadCommodityMap: function (commodityMaps) {
            var mapHtml = JSUtils.handlebars('mapTemplate', {'commodityMaps': commodityMaps});
            $('#commodityMap').html(mapHtml);
            JSUtils.patchMapAreaBug();
        },
        $prevIcon: $('div.main-body div.snapshots div.prev'),
        $nextIcon: $('div.main-body div.snapshots div.next'),
        showPrevInstances: function () {
            var oldStartIndex = this.startIndex;
            var oldEndIndex = this.endIndex;

            this.$nextIcon.show();

            var newStartIndex = oldStartIndex - this.displaySize;
            if (newStartIndex <= 0) {
                newStartIndex = 0;
                this.$prevIcon.hide();
            }
            var newEndIndex = newStartIndex + this.displaySize - 1;

            this.startIndex = newStartIndex;
            this.endIndex = newEndIndex;

            var i = 0;
            while (oldEndIndex - i > newEndIndex) {
                this.$divs.eq(oldEndIndex - i).hide(500);
                this.$divs.eq(oldStartIndex - i - 1).show(500);
                i++;
            }
        },
        showNextInstances: function () {
            var oldStartIndex = this.startIndex;
            var oldEndIndex = this.endIndex;

            this.$prevIcon.show();

            var newEndIndex = oldEndIndex + this.displaySize;
            var snapshotCount = this.$divs.size();
            if (newEndIndex >= snapshotCount - 1) {
                newEndIndex = snapshotCount - 1;
                this.$nextIcon.hide();
            }
            var newStartIndex = newEndIndex - this.displaySize + 1;

            this.startIndex = newStartIndex;
            this.endIndex = newEndIndex;

            var i = 0;
            while (oldStartIndex + i < newStartIndex) {
                this.$divs.eq(oldStartIndex + i).hide(500);
                this.$divs.eq(oldEndIndex + i + 1).show(500);
                i++;
            }
        },
        init: function () {
            var selectedId = window['selectedCommodityId'];
            var idFromHash = JSUtils.getUrlHash('id');
            if (JSUtils.isNumberString(idFromHash)) {
                selectedId = parseInt(idFromHash);
            }

            var snapshotCount = this.$divs.size();

            // show snapshot beside selected snapshot
            this.startIndex = 0;
            this.endIndex = snapshotCount - 1;
            for (var i = 0; i < snapshotCount; i++) {
                var $snapshot = this.$divs.eq(i);
                if ($snapshot.dataOptions('id') == selectedId) {
                    $snapshot.addClass('selected');
                    var startIndex = parseInt(i / this.displaySize) * this.displaySize;
                    var endIndex = startIndex + this.displaySize - 1;
                    while (endIndex >= snapshotCount) {
                        endIndex--;
                        startIndex--;
                    }
                    this.startIndex = startIndex;
                    this.endIndex = endIndex;
                    for (var j = startIndex; j <= endIndex && j < snapshotCount; j++) {
                        this.$divs.eq(j).show();
                    }
                    break;
                }
            }
            if (this.startIndex > 0) {
                this.$prevIcon.show();
            }
            if (this.endIndex < snapshotCount - 1) {
                this.$nextIcon.show();
            }
            this.loadDetail(selectedId);

            var self = this;

            // click events
            this.$divs.click(function () {
                self.$divs.removeClass('selected');
                var $this = $(this);
                $this.addClass('selected');
                var id = $this.dataOptions('id');
                location.href = JSUtils.updateUrlHash('id', id);
                self.loadDetail(id);
            });
            this.$prevIcon.click(function () {
                self.showPrevInstances();
            });
            this.$nextIcon.click(function () {
                self.showNextInstances();
            });

            //this.loadCommodityMap(window['commodityMaps']);
            return this;
        }
    }).init();

    function setFloatPanelUsername($floatPanel, username) {
        $floatPanel.find('div.title div.text span.username').text(username);
    }

    function setCloseIconEvent($floatPanel, event) {
        $floatPanel.find('div.title div.close-icon').click(event);
    }

    var noPrivilege = ({
        $div: $('#noPrivilegePrompt'),
        show: function (username, toLoginCallback) {
            JSUtils.showTransparentBackground(1);
            setFloatPanelUsername(this.$div, username);
            this.$div.fadeIn(300);

            var self = this;
            this.$div.find('a.toLogin').click(function () {
                self.$div.hide();
                showLoginForm(toLoginCallback);
            });
        },
        init: function () {
            var self = this;
            setCloseIconEvent(self.$div, function () {
                JSUtils.hideTransparentBackground();
                self.$div.hide();
            });
            return this;
        }
    }).init();

    var $exceptionPrompt = $('#exceptionPrompt');
    setCloseIconEvent($exceptionPrompt, function () {
        JSUtils.hideTransparentBackground();
        $exceptionPrompt.hide();
    });
    function showExceptionPrompt(username, info) {
        JSUtils.showTransparentBackground(1);
        setFloatPanelUsername($exceptionPrompt, username);
        $exceptionPrompt.fadeIn(300).find('div.body div.info').text(info);
    }

    var lotteryResult = ({
        $div: $('#lotteryResult'),
        get$RemindMeCheckbox: function () {
            return this.$div.find('div.body div.remind-me input');
        },
        get$TelModifyAnchor: function () {
            return this.$div.find('div.body div.lot div.tel div.modify a');
        },
        get$ValidateError: function () {
            return this.$div.find('div.body div.lot div.tel div.validate-error');
        },
        get$TelInput: function () {
            return this.$div.find('div.body div.lot div.tel input');
        },
        get$InsufficientLivnessDiv: function () {
            return this.$div.find('div.body div.insufficient-liveness');
        },
        get$ActivityExpire: function () {
            return this.$div.find('div.body div.activity-expire');
        },
        show: function (options) {
            // title
            this.$div.find('div.title div.text span.text').text('抽奖详情：0元抽 ' + options['commodity']['name']);

            // remind me
            this.get$RemindMeCheckbox().get(0).checked = options['receiveMail'];

            // commodity and activity
            var $image = this.$div.find('div.activity div.image img');
            $image.attr('src', options['commodity']['snapshot']);
            adjustImage($image.get(0), 110, 70);
            this.$div.find('div.activity div.description').html(options['activityDescription']);

            if (options['detail'] == 'activityExpire') {
                this.get$ActivityExpire().show();
                this.$lot.hide();
                this.get$InsufficientLivnessDiv().hide();
            } else {
                // serial number
                var serialNumbers = options['serialNumbers'];
                if (serialNumbers.length > 0) {
                    this.$div.find('div.lot div.serial span.serial').text(serialNumbers[0]);

                    // tel
                    var tel = options['tel'];
                    this.$div.find('div.body div.lot div.tel input').val(tel).dataOptions('tel', tel);

                    // liveness
                    this.$div.find('div.lot div.liveness span.liveness').text(options['liveness']);

                    this.$lot.show();
                    this.get$InsufficientLivnessDiv().hide();
                } else {
                    this.$lot.hide();
                    var $insufficientLiveness = this.get$InsufficientLivnessDiv();
                    $insufficientLiveness.find('span.min-liveness-to-participate')
                        .text(options['minLivenessToParticipate']);
                    $insufficientLiveness.find('span.liveness').text(options['liveness']);
                    $insufficientLiveness.show();
                }
                this.get$ActivityExpire().hide();
            }

            // share url
            var $share = this.$div.find('div.bottom div.share div.list ul');
            $share.find('a.sina').attr('href', options['sinaWeiboShareUrl']);
            $share.find('a.qq').attr('href', options['qqShareUrl']);
            $share.find('a.qzone').attr('href', options['qzoneShareUrl']);

            // show float panel
            JSUtils.showTransparentBackground(1);
            this.$div.fadeIn(300);
            JSUtils.scrollToVerticalCenter(this.$div);
        },
        get$Buttons: function () {
            return  this.$div.find('div.body div.lot div.tel div.buttons');
        },
        get$SubmitButtons: function () {
            return this.get$Buttons().find('div.submit');
        },
        get$ConflictButtons: function () {
            return this.get$Buttons().find('div.conflict');
        },
        get$OkButton: function () {
            return this.get$SubmitButtons().find('button.ok');
        },
        get$CancelButton: function () {
            return this.get$SubmitButtons().find('button.cancel');
        },
        get$ClearButton: function () {
            return this.get$ConflictButtons().find('button.clear');
        },
        showValidateError: function (info) {
            this.get$TelInput().addClass('error');
            this.get$ValidateError().text(info).show();
        },
        hideValidateError: function () {
            this.get$TelInput().removeClass('error');
            this.get$ValidateError().text('').hide();
            this.get$Buttons().hide();
        },
        init: function () {
            this.$lot = this.$div.find('div.body div.lot');
            var self = this;
            setCloseIconEvent(this.$div, function () {
                JSUtils.hideTransparentBackground();
                self.$div.hide();
            });
            this.get$RemindMeCheckbox().click(function () {
                var $this = $(this);
                var checked = $this.get(0).checked;
                var $remindMeDiv = $this.getParentByTagNameAndClass('div', 'remind-me');
                var self = this;
                $.post('update-receive-mail.json', {'receiveMail': checked}, function (data) {
                    if (data['success']) {
                        $remindMeDiv.find('span.update-success').showForAWhile(2000);
                    } else {
                        $remindMeDiv.find('span.update-fail').text(data['detail']).showForAWhile(2000);
                        self.checked = !checked;
                    }
                });
            });
            this.get$TelModifyAnchor().click(function () {
                self.get$TelInput().val('').focusOrSelect();
            });
            this.get$TelInput().monitorValue(function (tel) {
                if (tel.length < 11) {
                    self.hideValidateError();
                    return;
                }

                if (!JSUtils.validateTel(tel)) {
                    self.get$Buttons().hide();
                    self.showValidateError('号码必须是11位数字');
                    return;
                }

                if (tel == self.get$TelInput().dataOptions('tel')) {
                    self.showValidateError('新号码与原号码相同');
                    return;
                }

                $.post('tel-validate.json', {'tel': tel}, function (data) {
                    if (data['success']) {
                        self.get$SubmitButtons().show();
                        self.get$ConflictButtons().hide();
                        self.get$Buttons().show();
                    } else {
                        var errorInfo = data['detail'];
                        self.showValidateError(errorInfo);
                        if (errorInfo.indexOf('被使用') >= 0) {
                            self.get$ConflictButtons().show();
                            self.get$SubmitButtons().hide();
                            self.get$Buttons().show();
                        }
                    }
                });
            }).focus(function () {
                self.get$TelModifyAnchor().parent().hide();
            }).blur(function () {
                if (self.get$ValidateError().css('display') == 'none' || self.get$ValidateError().text() == '') {
                    self.get$TelModifyAnchor().parent().show();
                }
            });
            this.get$CancelButton().click(function () {
                self.get$Buttons().hide();
                self.get$TelInput().val(self.get$TelInput().dataOptions('tel'));
            });
            this.get$ClearButton().click(function () {
                self.get$TelModifyAnchor().trigger('click');
                self.hideValidateError();
            });
            this.get$OkButton().click(function () {
                var tel = self.get$TelInput().val();
                $.post('update-tel.json', {'tel': tel}, function (data) {
                    if (data['success']) {
                        self.get$Buttons().hide();
                    } else {
                        alert(data);
                    }
                });
            });
            this.$div.find('div.body div.bottom div.rule a').click(function () {
                lotteryRule.show(self.$div.attr('id'));
            });

            return this;
        }
    }).init();

    var lotteryRule = ({
        $div: $('#lotteryRule'),
        $parent: null,
        show: function (parentId) {
            JSUtils.showTransparentBackground(1);
            this.$div.fadeIn(200);
            JSUtils.scrollToVerticalCenter(this.$div);
            if (parentId) {
                this.$parent = $('#' + parentId);
                this.$parent.hide();
            }
        },
        hide: function () {
            this.$div.hide();
            if (this.$parent) {
                this.$parent.show();
                this.$parent = null;
            } else {
                JSUtils.hideTransparentBackground();
            }
        },
        init: function () {
            var self = this;
            this.$div.find('div.close-icon').click(function () {
                self.hide();
            });
            this.$div.find('div.button button').click(function () {
                self.hide();
            });
            return this;
        }
    }).init();
    showLotteryRule = function (parentId) {
        lotteryRule.show(parentId);
    };

    getLotteryLot = function () {
        var toLoginCallback = function () {
            hideLoginForm();
            getLotteryLot();
        };
        $.post('take-lottery.json', {
            'commodityId': getSelectedCommodityId()
        }, function (data) {
            if (data.success || data.detail == 'activityExpire' || data.detail == 'alreadyAttended') {
                lotteryResult.show(data);
            } else if (data.detail == 'noLottery') {
                showExceptionPrompt(data.username, '本商品暂时没有抽奖，敬请关注其他商品的抽奖！');
            } else if (data.detail == 'noLogin') {
                JSUtils.showTransparentBackground(1);
                showLoginForm(toLoginCallback);
            } else if (data.detail == 'noPrivilege') {
                noPrivilege.show(data.username, toLoginCallback);
            } else {
                alert(data.detail);
            }
        });
    };

    var seckillResult = ({
        $div: $('#seckillResult'),
        clearRemainingTimeUpdater: function () {
            if (this.remainingTimeUpdaters) {
                for (var i = 0, len = this.remainingTimeUpdaters.length; i < len; i++) {
                    clearInterval(this.remainingTimeUpdaters[i]);
                }
            }
            this.remainingTimeUpdaters = [];
        },
        get$ActivityExpire: function () {
            return this.$div.find('div.body div.activity-expire');
        },
        updateRemainingTime: function () {
            var self = this;
            this.$remainingTime.show();

            updateHTML();
            this.clearRemainingTimeUpdater();
            this.remainingTimeUpdaters.push(setInterval(function () {
                updateHTML();
            }, 1000));

            function updateHTML() {
                var time = remainingTime.getRemainingTimeObject();
                if (parseInt(time.days) > 0) {
                    self.$remainingDay.text(time.days + '天');
                } else {
                    self.$remainingDay.text('');
                }
                self.$remainingHour.text(time.hours);
                self.$remainingMinute.text(time.minutes);
                self.$remainingSecond.text(time.seconds);
            }
        },
        show: function (options) {
            if (options['detail'] == 'activityExpire') {
                this.get$ActivityExpire().show();
                this.$lot.hide();
                this.$remainingTime.hide();
            } else {
                // remaining time
                this.updateRemainingTime();
            }

            // title
            this.$div.find('div.title div.text span.text').text('秒杀详情：0元秒 ' + options['commodity']['name']);

            // commodity and activity
            var $image = this.$div.find('div.activity div.image img');
            $image.attr('src', options['commodity']['snapshot']);
            adjustImage($image.get(0), 110, 70);
            this.$div.find('div.activity div.description').html(options['activityDescription']);

            // share url
            var $share = this.$div.find('div.bottom div.share div.list ul');
            $share.find('a.sina').attr('href', options['sinaWeiboShareUrl']);
            $share.find('a.qq').attr('href', options['qqShareUrl']);
            $share.find('a.qzone').attr('href', options['qzoneShareUrl']);

            // show float panel
            JSUtils.showTransparentBackground(1);
            this.$div.fadeIn(300);
            JSUtils.scrollToVerticalCenter(this.$div);
        },
        _rolloverPoker: function ($poker) {
            var $front = $poker.find('img.front');
            var $back = $poker.find('img.back');
            $front.addClass('positive').onAnimationEnd(function () {
                $(this).css('z-index', 1).removeClass('positive');
            });
            $back.addClass('negative').onAnimationEnd(function () {
                $(this).css('z-index', 2).removeClass('negative');
            });

            setTimeout(function () {
                $front.addClass('negative').onAnimationEnd(function () {
                    $(this).css('z-index', 2).removeClass('negative');
                });
                $back.addClass('positive').onAnimationEnd(function () {
                    $(this).css('z-index', 1).removeClass('positive');
                });
            }, 700);
        },
        init: function () {
            this.$remainingTime = this.$div.find('div.body div.remaining-time');
            this.$remainingDay = this.$remainingTime.find('span.day');
            this.$remainingHour = this.$remainingTime.find('span.hour');
            this.$remainingMinute = this.$remainingTime.find('span.minute');
            this.$remainingSecond = this.$remainingTime.find('span.second');
            this.$lot = this.$div.find('div.body div.lot');
            this.$successResult = this.$lot.find('div.success.result');
            this.$notStartResult = this.$lot.find('div.not-start.result');
            this.$overResult = this.$lot.find('div.over.result');
            this.$unknownResult = this.$lot.find('div.unknown.result');

            var self = this;
            setCloseIconEvent(this.$div, function () {
                JSUtils.hideTransparentBackground();
                self.$div.hide();
                self.clearRemainingTimeUpdater();
            });
            this.$div.find('div.body div.lot div.pokers div.poker img.front').click(function () {
                var $this = $(this);

                // when image is on rollover, just exists
                if ($this.hasClass('negative') || $this.hasClass('positive')) {
                    return;
                }

                self.$successResult.hide();
                self.$notStartResult.hide();
                self.$overResult.hide();
                self.$unknownResult.hide();

                if (self.$remainingDay.text() == '' && parseInt(self.$remainingHour.text()) == 0
                    && parseInt(self.$remainingMinute.text()) == 0 && parseInt(self.$remainingSecond.text()) == 0) {
                    $.post('do-seckill-action.json', {'commodityId': getSelectedCommodityId()}, function (data) {
                        if (data.success) {
                            self.$successResult.twinkle(4);
                        } else if (data.detail == 'over') {
                            self.$overResult.twinkle(4);
                        } else if (data.detail == 'notStart') {
                            self.$notStartResult.twinkle(4);
                        } else if (data.detail == 'noLogin') {
                            alert('请重新登录');
                            location.reload();
                        } else {
                            self.$unknownResult.twinkle(4);
                        }
                    });
                } else {
                    self._rolloverPoker($this.getParentByTagNameAndClass('div', 'poker'));
                }
            });
            return this;
        }
    }).init();

    getSeckillLot = function () {
        var toLoginCallback = function () {
            hideLoginForm();
            getSeckillLot();
        };
        $.post('take-seckill.json', {
            'commodityId': getSelectedCommodityId()
        }, function (data) {
            if (data.success || data.detail == 'activityExpire') {
                seckillResult.show(data);
            } else if (data.detail == 'noSeckill') {
                showExceptionPrompt(data.username, '本商品暂时没有秒杀，敬请关注其他商品的秒杀！');
            } else if (data.detail == 'noLogin') {
                JSUtils.showTransparentBackground(1);
                showLoginForm(toLoginCallback);
            } else if (data.detail == 'noPrivilege') {
                noPrivilege.show(data.username, toLoginCallback);
            } else {
                alert(data.detail);
            }
        });
    };

    function getSelectedCommodityId() {
        return getSelectedCommodity()['id'];
    }

    function getSelectedCommodity() {
        var $selectedSnapshot = $('div.main-body div.snapshots div.snapshot.selected');
        var id = $selectedSnapshot.dataOptions('id');
        return {
            'id': id,
            'inLottery': $selectedSnapshot.find('div.in-lottery-icon').size() > 0,
            'inSeckill': $selectedSnapshot.find('div.in-seckill-icon').size() > 0
        }
    }
})();
var getLotteryLot, getSeckillLot, showLotteryRule;

