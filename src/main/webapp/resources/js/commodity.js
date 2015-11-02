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
            var $clock = this.$remainingTime.find('div.clock');
            var digitClock = JSUtils.digitClock360($clock, this.getRemainingTimeString());

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
            var $details = $('div.main-body div.details').adjustMinHeightToHeight().empty();
            this.clearCommodityMap();
            //alert('aaa');
            var self = this;
            $.post('commodity-images.json', {
                id: imageId
            }, function (data) {
                for (var i = 0, len = data.length; i < len; i++) {
                    var imageWrapper = data[i];
                    var commodityMaps = imageWrapper['commodityMaps'];
                    self.loadCommodityMap(i, commodityMaps);

                    var detailImage = imageWrapper['image']['path'];
                    var backImage = imageWrapper['image']['backPath'];
                    var $detail = $('<div class="detail"><img/></div>');
                    $detail.setBackgroundImage(backImage);
                    $detail.find('img').attr('src', detailImage).attr('usemap', '#commodityMap' + i).hide();

                    $detail.appendTo($details);
                }
                JSUtils.patchMapAreaBug();

                $details.find('img').fadeIn(500, function () {
                    participantCount.update();
                    remainingTime.init();
                });
                $details.css('min-height', '0px');
                //$details.css('min-height', null);
            })
        },
        displaySize: 6,
        startIndex: 0,
        endIndex: 5,
        clearCommodityMap: function () {
            $('#commodityMaps').empty();
        },
        loadCommodityMap: function (index, commodityMaps) {
            var mapHtml = JSUtils.handlebars('mapTemplate', {'commodityMaps': commodityMaps});
            var $html = $('<map name="commodityMap' + index + '" id="commodityMap' + index + '">' + mapHtml + '</map>');
            $html.appendTo($('#commodityMaps'));
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
        showSubscribe: false,
        showAdditionalLotteryResult: function (options) {
            var success = true;
            if (options['noTelInvalidLot']) {
                this.$tel.show();
                success = false;
            } else {
                this.$tel.hide();
            }
            if (parseInt(options['liveness']) < parseInt(options['minLivenessToParticipate'])) {
                this.$insufficientLivness.show();
                this.$insufficientLivness.find('span.min-liveness-to-participate').text(options['minLivenessToParticipate']);
                this.$insufficientLivness.find('a').attr('href', 'setting.html?index=5&commodityId=' + getSelectedCommodityId());
                success = false;
            } else {
                this.$insufficientLivness.hide();
            }
            if (success) {
                this.$lotSuccess.show();
            } else {
                this.$lotSuccess.hide();
            }
        },
        show: function (options) {
            // title
            this.$div.find('div.title div.text span.text').text('抽奖详情：0元抽 ' + options['commodity']['name']);

            // commodity and activity
            var $image = this.$div.find('div.activity div.image').empty();
            var image = $('<img/>').attr('src', options['commodity']['snapshot']).appendTo($image);
            adjustImage(image.get(0), 110, 70);
            this.$div.find('div.activity div.description').html(options['activityDescription']);

            if (options['detail'] == 'activityExpire') {
                this.$activityExpire.show();
                this.$lot.hide();
            } else {
                this.$lot.show();
                if (options['success']) {
                    this.$createNumber.show();
                    this.$serialNumber.hide();
                } else {
                    var serialNumber = options['serialNumbers'][0];
                    this.showAdditionalLotteryResult(options);
                    this.$serialNumber.show().find('span.number').text(serialNumber);
                    this.$createNumber.hide();
                }
                this.$activityExpire.hide();
            }

            // share url
            var $share = this.$div.find('div.bottom div.share div.list ul');
            $share.find('a.sina').attr('href', options['sinaWeiboShareUrl']);
            $share.find('a.qq').attr('href', options['qqShareUrl']);
            $share.find('a.qzone').attr('href', options['qzoneShareUrl']);

            // show float panel
            JSUtils.showTransparentBackground(1);
            this.$div.fadeIn(300);
            if (!window['isMobileUserAgent']) {
                JSUtils.scrollToVerticalCenter(this.$div);
            }
        },
        init: function () {
            this.$lot = this.$div.find('div.body div.lot');
            this.$activityExpire = this.$div.find('div.body div.activity-expire');
            var self = this;
            setCloseIconEvent(this.$div, function () {
                self.$div.fadeOut(300, function () {
                    JSUtils.hideTransparentBackground();
                });
            });
            this.$serialNumber = this.$lot.find('div.serial-number');
            this.$lotSuccess = this.$lot.find('div.success');
            this.$tel = this.$lot.find('div.tel');
            this.$insufficientLivness = this.$lot.find('div.insufficient-liveness');
            this.$createNumber = this.$lot.find('button.create-number').click(function () {
                $.post('do-lottery-action.json', {commodityId: getSelectedCommodityId()}, function (data) {
                    if (data.success) {
                        var serialNumber = data['serialNumbers'][0];
                        self.$createNumber.hide();
                        self.$serialNumber.show();
                        JSUtils.changingNumber(self.$serialNumber.find('span.number'), 1000, serialNumber);
                        self.showAdditionalLotteryResult(data);
                    } else {
                        alert(data.detail);
                    }
                });
            });
            var timer = setInterval(function () {
                var init = false;
                $('map area').each(function () {
                    init = true;
                    if (this.href == "javascript:void(showLotteryRule())") {
                        var ruleHref = self.$div.find('div.body div.bottom div.rule a').attr('href');
                        if (ruleHref) {
                            this.href = self.$div.find('div.body div.bottom div.rule a').attr('href');
                        } else {
                            this.href = "javascript:void(0)";
                        }
                    }
                });
                if (init) {
                    clearInterval(timer);
                }
            }, 100);

            return this;
        }
    }).init();

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
        showSubscribe: false,
        show: function (options) {
            if (options['detail'] == 'activityExpire') {
                this.$activityExpire.show();
                this.$lot.hide();
                this.$remainingTime.hide();
            } else {
                // remaining time
                this.updateRemainingTime();
            }

            // title
            this.$div.find('div.title div.text span.text').text('秒杀详情：0元秒 ' + options['commodity']['name']);

            subscribe.setEmail(options['email']);
            this.showSubscribe = !options['receiveMail'];

            // commodity and activity
            var $image = this.$div.find('div.activity div.image').empty();
            var image = $('<img/>').attr('src', options['commodity']['snapshot']).appendTo($image);
            adjustImage(image.get(0), 110, 70);
            this.$div.find('div.activity div.description').html(options['activityDescription']);

            // share url
            var $share = this.$div.find('div.bottom div.share div.list ul');
            $share.find('a.sina').attr('href', options['sinaWeiboShareUrl']);
            $share.find('a.qq').attr('href', options['qqShareUrl']);
            $share.find('a.qzone').attr('href', options['qzoneShareUrl']);

            // show float panel
            JSUtils.showTransparentBackground(1);
            this.$div.fadeIn(300);
            if (!window['isMobileUserAgent']) {
                JSUtils.scrollToVerticalCenter(this.$div);
            }
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
            this.$activityExpire = this.$div.find('div.body div.activity-expire');

            var self = this;
            setCloseIconEvent(this.$div, function () {
                self.$div.fadeOut(300, function () {
                    JSUtils.hideTransparentBackground();
                });
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
var getLotteryLot, getSeckillLot/*, showLotteryRule*/;
