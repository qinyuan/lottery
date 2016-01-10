;
(function () {
    if (window['redirectUrl']) {
        location.href = window['redirectUrl'];
        return;
    }

    var participantCount = ({
        update: function () {
            var commodity = getSelectedCommodity();
            var $parent = this.$div.getParentByTagNameAndClass('div', 'right');
            var self = this;
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
        $details: $('div.main-body div.details'),
        imageAndMapsCache: [],
        loadCommodityImageAndMaps: function (data) {
            for (var i = 0, len = data.length; i < len; i++) {
                var imageWrapper = data[i];
                var commodityMaps = imageWrapper['commodityMaps'];
                this.loadCommodityMap(i, commodityMaps);

                var detailImage = imageWrapper['image']['path'];
                var backImage = imageWrapper['image']['backPath'];
                var $detail = $('<div class="detail"><img/></div>');
                $detail.setBackgroundImage(backImage);
                $detail.find('img').attr('src', detailImage).attr('usemap', '#commodityMap' + i).hide();

                $detail.appendTo(this.$details);
            }
            JSUtils.patchMapAreaBug();

            this.$details.find('img').fadeIn(500, function () {
                participantCount.update();
                remainingTime.init();
            });
            this.$details.css('min-height', '500px');
        },
        loadDetail: function (imageId) {
            remainingTime.hide();
            participantCount.hide();
            this.$details.adjustMinHeightToHeight().empty();
            this.clearCommodityMap();
            var self = this;
            if (this.imageAndMapsCache[imageId]) {
                this.loadCommodityImageAndMaps(this.imageAndMapsCache[imageId]);
            } else {
                $.post('commodity-images.json', {
                    id: imageId
                }, function (data) {
                    self.imageAndMapsCache[imageId] = data;
                    self.loadCommodityImageAndMaps(data);
                });
            }
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
                this.$divs.each(function () {
                    // ensure that idFromHash is in snapshot's ids
                    if (idFromHash == $(this).dataOptions('id')) {
                        selectedId = parseInt(idFromHash);
                        return false;
                    }
                });
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

            setInterval(function () {
                self.$divs.each(function () {
                    var $div = $(this);
                    if ($div.css('display') != 'none') {
                        var id = $div.dataOptions('id');
                        if (!self.imageAndMapsCache[id]) {
                            $.post('commodity-images.json', {
                                id: id
                            }, function (data) {
                                self.imageAndMapsCache[id] = data;
                            });
                        }
                    }
                });
            }, 60000);   // load cache each minute

            return this;
        }
    }).init();

    function setCloseIconEvent($floatPanel, event) {
        $floatPanel.find('div.title div.close-icon').click(event);
    }

    var noPrivilege = ({
        $div: $('#noPrivilegePrompt'),
        show: function (toLoginCallback) {
            JSUtils.showTransparentBackground(1);
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

    var exceptionPrompt = ({
        $div: $('#exceptionPrompt'),
        show: function (info) {
            JSUtils.showTransparentBackground(1);
            this.$div.fadeIn(300).find('div.body div.info').text(info);
        },
        init: function () {
            var self = this;
            setCloseIconEvent(this.$div, function () {
                JSUtils.hideTransparentBackground();
                self.$div.hide();
            });

            return this;
        }
    }).init();

    var noTelPrompt = ({
        $div: $('#noTelPrompt'),
        show: function (options) {
            this.$username.text(options['username']);
            this.$noTelLiveness.text(options['noTelLiveness']);
            this.$maxTelModificationTimes.text(options['maxTelModificationTimes']);

            JSUtils.showTransparentBackground(1);
            this.$div.fadeIn(300);
        },
        init: function () {
            this.$username = this.$div.find('span.username');
            this.$noTelLiveness = this.$div.find('span.no-tel-liveness');
            this.$maxTelModificationTimes = this.$div.find('span.max-tel-modification-times');
            var self = this;
            setCloseIconEvent(this.$div, function () {
                JSUtils.hideTransparentBackground();
                self.$div.hide();
            });

            return this;
        }
    }).init();

    var lotteryResult = ({
        $div: $('#lotteryResult'),
        showSubscribe: false,
        showManualSerialNumberCreator: function () {
            this.hideConcealDivs();
            this.$manualSerialNumberCreator.fadeIn(250);
        },
        getManualSerialNumber: function () {
            var serialNumbers = [];
            this.$manualSerialNumberCreator.find('select').each(function () {
                serialNumbers.push($(this).val());
            });
            for (var i = 0, len = serialNumbers.length; i < len - 1; i++) {
                for (var j = i + 1; j < len; j++) {
                    if (serialNumbers[i] == serialNumbers[j]) {
                        alert('选取的多不抽奖必须各不相同');
                        return null;
                    }
                }
            }
            return parseInt(serialNumbers.join(''));
        },
        hideConcealDivs: function () {
            this.$div.find('div.conceal').hide();
        },
        showSerialNumber: function (data, changingBeforeShow) {
            var serialNumber = data['serialNumbers'][0];
            this.hideConcealDivs();
            var serialNumberComponents = [
                serialNumber.substr(0, 2), serialNumber.substr(2, 2), serialNumber.substr(4, 2)];

            this.$serialNumber.find('span.number span').each(function (index) {
                if (changingBeforeShow) {
                    JSUtils.changingNumber($(this), 1000, serialNumberComponents[index]);
                } else {
                    $(this).text(serialNumberComponents[index]);
                }
            });

            this.$serialNumber.show();
            this.$lot.show();

            var self = this;
            setTimeout(function () {
                self.showSameLotUsers(serialNumber, data['sameLotUsers']);
            }, 1000);
        },
        showSameLotUsers: function (serialNumber, sameLotUsers) {
            this.hideConcealDivs();
            var currentUser = $('body div.header div.right-navigation a.username').html();
            var $tbody = this.$sameLotUsers.find('div.data tbody').empty();
            for (var i = 0; i < sameLotUsers.length; i++) {
                var user = sameLotUsers[i];
                var html = user.username == currentUser ? '<tr class="current">' : '<tr>';
                html += '<td>' + user.username + '</td>';
                html += '<td>' + serialNumber + '</td>';
                html += '<td class="liveness">' + user['liveness'] + '</td>';
                html += '</tr>';
                $tbody.append(html);
            }
            this.$sameLotUsers.find('div.more-liveness a')
                .attr('href', 'share.html?commodityId=' + getSelectedCommodityId());
            this.$sameLotUsers.show();
        },
        show: function (options) {
            console.log(options);
            // title
            this.$div.find('div.title div.text span.text').text('抽奖详情：0元抽 ' + options['commodity']['name']);

            // commodity and activity
            var $image = this.$div.find('div.activity div.image').empty();
            var image = $('<img/>').attr('src', options['commodity']['snapshot']).appendTo($image);
            adjustImage(image.get(0), 110, 70);
            this.$div.find('div.activity div.description').html(options['activityDescription']);

            this.hideConcealDivs();
            if (options['detail'] == 'activityExpire') {
                this.$activityExpire.show();
            } else if (options['detail'] == 'activityClosed') {
                this.$activityClosed.show();
            } else {
                if (options['success']) {
                    this.$createNumber.show();
                } else {
                    this.showSerialNumber(options);
                }
            }

            // share url
            var $share = this.$div.find('div.bottom div.share div.list ul');
            $share.find('a.sina').attr('href', options['sinaWeiboShareUrl']);
            $share.find('a.qq').attr('href', options['qqShareUrl']);
            $share.find('a.qzone').attr('href', options['qzoneShareUrl']);

            // show float panel
            JSUtils.showTransparentBackground(1);
            this.$div.fadeIn(300);
        },
        init: function () {
            this.$lot = this.$div.find('div.body div.lot');
            this.$activityExpire = this.$div.find('div.body div.activity-expire');
            this.$activityClosed = this.$div.find('div.body div.activity-closed');
            var self = this;
            setCloseIconEvent(this.$div, function () {
                self.$div.fadeOut(300, function () {
                    JSUtils.hideTransparentBackground();
                });
            });
            this.$serialNumber = this.$lot.find('div.serial-number');
            this.$createNumber = this.$div.find('div.body div.create-number');
            this.$createNumber.find('button.auto').click(function () {
                $.post('do-lottery-action.json', {commodityId: getSelectedCommodityId()}, function (data) {
                    if (data.success) {
                        self.showSerialNumber(data, true);
                    } else {
                        alert(data.detail);
                    }
                });
            });
            this.$manualSerialNumberCreator = this.$div.find('div.body div.manual-number-creator');
            this.$manualSerialNumberCreator.find('button').click(function () {
                var serialNumber = self.getManualSerialNumber();
                if (serialNumber) {
                    $.post('do-lottery-action.json', {
                        commodityId: getSelectedCommodityId(),
                        serialNumber: self.getManualSerialNumber()
                    }, function (data) {
                        if (data.success) {
                            self.showSerialNumber(data);
                        } else {
                            alert(data.detail);
                        }
                    });
                }
            });
            this.$createNumber.find('button.manual').click(function () {
                self.showManualSerialNumberCreator();
            });
            this.$sameLotUsers = this.$div.find('div.body div.same-lot-users');
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
            if (data.success || data.detail == 'activityClosed' || data.detail == 'activityExpire'
                || data.detail == 'alreadyAttended') {
                lotteryResult.show(data);
            } else if (data.detail == 'noTel') {
                noTelPrompt.show(data);
            } else if (data.detail == 'noLottery') {
                exceptionPrompt.show('本商品暂时没有抽奖，敬请关注其他商品的抽奖！');
            } else if (data.detail == 'noLogin') {
                JSUtils.showTransparentBackground(1);
                showLoginForm(toLoginCallback);
            } else if (data.detail == 'noPrivilege') {
                noPrivilege.show(toLoginCallback);
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
                exceptionPrompt.show('本商品暂时没有秒杀，敬请关注其他商品的秒杀！');
            } else if (data.detail == 'noLogin') {
                JSUtils.showTransparentBackground(1);
                showLoginForm(toLoginCallback);
            } else if (data.detail == 'noPrivilege') {
                noPrivilege.show(toLoginCallback);
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
var getLotteryLot, getSeckillLot;
