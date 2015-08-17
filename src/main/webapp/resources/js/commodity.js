;
(function () {
    var participantCount = ({
        get$Div: function () {
            return $('div.main-body > div.detail > div.participant-count');
        },
        update: function (commodityId) {
            var self = this;
            $.post('participant-count.json', {
                commodityId: commodityId
            }, function (data) {
                var $participantCountDiv = self.get$Div();
                if (data['participantCount']) {
                    $participantCountDiv.find("span.participant-count").text(data['participantCount']);
                    $participantCountDiv.show();
                } else {
                    $participantCountDiv.hide();
                }
            });
        },
        hide: function () {
            this.get$Div().hide();
        },
        init: function () {
            //this.update(window['selectedCommodityId']);
            var self = this;
            setInterval(function () {
                self.update(getSelectedCommodityId());
            }, 3000); // refresh each three seconds
            return this;
        }
    }).init();

    var snapshots = ({
        $divs: $('div.main-body div.snapshots div.snapshot'),
        loadDetail: function (imageId) {
            //location.href = JSUtils.updateUrlParam('id', imageId);
            participantCount.get$Div().hide();
            var $detail = $('div.main-body div.detail');
            var $img = $detail.find('img');
            $img.hide();

            var self = this;
            $.post('commodity-info.json', {
                id: imageId
            }, function (data) {
                var commodity = data['commodity'];
                $detail.css('background-image', 'url("' + commodity['backImage'] + '")');
                $img.attr('src', commodity['detailImage']);
                $img.fadeIn(500, function () {
                    participantCount.update(getSelectedCommodityId());
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
})();
(function () {
    // codes about lottery
    function setFloatPanelUsername($floatPanel, username) {
        $floatPanel.find('div.title div.text span.username').text(username);
    }

    function setCloseIconEvent($floatPanel, event) {
        $floatPanel.find('div.title div.close-icon').click(event);
    }

    function afterLoginSuccess() {
        hideLoginForm();
        getLotteryLot();
    }

    var $telInputForm = $('#telInputForm');
    $telInputForm.get$OkButton = function () {
        return $telInputForm.find('button[name=ok]');
    };
    $telInputForm.get$OkButton().click(function (e) {
        e.preventDefault();

        var $tel = $telInputForm.getInputByName('tel');
        if (!JSUtils.validateTel($tel.val())) {
            alert('手机号码必须为11位数字');
            $tel.focusOrSelect();
            return false;
        }

        var $identityCode = $telInputForm.getInputByName('identityCode');
        var identityCode = $identityCode.val();
        if (!identityCode || identityCode.length != 4) {
            alert('请填写4个字符的验证码');
            $identityCode.focusOrSelect();
            return false;
        }

        $.post('update-tel.json', $telInputForm.serialize(), function (data) {
            if (data.success) {
                $telInputForm.hide();
                getLotteryLot();
            } else {
                alert(data.detail);
                $telInputForm.find('img.identity-code').trigger('click');
            }
        });

        return false;
    });
    setCloseIconEvent($telInputForm, function () {
        JSUtils.hideTransparentBackground();
        $telInputForm.hide();
        //location.reload();
    });
    function showTelInputForm(username) {
        JSUtils.showTransparentBackground(1);
        setFloatPanelUsername($telInputForm, username);
        $telInputForm.find('img.identity-code').trigger('click');
        $telInputForm.fadeIn(300).focusFirstTextInput();
    }

    var $noPrivilegePrompt = $('#noPrivilegePrompt');
    setCloseIconEvent($noPrivilegePrompt, function () {
        JSUtils.hideTransparentBackground();
        $noPrivilegePrompt.hide();
        //location.reload();
    });
    $noPrivilegePrompt.find('a.toLogin').click(function () {
        $noPrivilegePrompt.hide();
        showLoginForm(afterLoginSuccess);
    });
    function showNoPrivilegeForm(username) {
        JSUtils.showTransparentBackground(1);
        setFloatPanelUsername($noPrivilegePrompt, username);
        $noPrivilegePrompt.fadeIn(300);
    }

    var $lotteryExceptionPrompt = $('#lotteryExceptionPrompt');
    setCloseIconEvent($lotteryExceptionPrompt, function () {
        JSUtils.hideTransparentBackground();
        $lotteryExceptionPrompt.hide();
    });
    function showLotteryExceptionPrompt(username, info) {
        JSUtils.showTransparentBackground(1);
        setFloatPanelUsername($lotteryExceptionPrompt, username);
        $lotteryExceptionPrompt.fadeIn(300).find('div.body div.info').text(info);
    }

    var $lotteryResult = $('#lotteryResult');
    setCloseIconEvent($lotteryResult, function () {
        JSUtils.hideTransparentBackground();
        $lotteryResult.hide();
        $lotteryResult.clearDeadlineUpdater();
    });
    $lotteryResult.clearDeadlineUpdater = function () {
        if ($lotteryResult.deadlineUpdaters) {
            for (var i = 0, len = $lotteryResult.deadlineUpdaters.length; i < len; i++) {
                clearInterval($lotteryResult.deadlineUpdaters[i]);
            }
        }
        $lotteryResult.deadlineUpdaters = [];
    };
    $lotteryResult.updateDeadline = function (remainingSeconds) {
        var startTimestamp = new Date().getTime();
        var secondsInDay = 3600 * 24;
        var $deadline = $lotteryResult.find('div.body div.activity-info div.deadline');
        var $day = $deadline.find('span.day');
        var $hour = $deadline.find('span.hour');
        var $minute = $deadline.find('span.minute');
        var $second = $deadline.find('span.second');

        updateHTML();
        $lotteryResult.clearDeadlineUpdater();
        $lotteryResult.deadlineUpdaters.push(setInterval(function () {
            updateHTML();
        }, 1000));

        function updateHTML() {
            var seconds = remainingSeconds + parseInt((startTimestamp - new Date().getTime()) / 1000);
            if (seconds <= 0) {
                seconds = 0;
            }
            var days = parseInt(seconds / secondsInDay);
            seconds -= days * secondsInDay;
            var hours = parseInt(seconds / 3600);
            seconds -= hours * 3600;
            var minutes = parseInt(seconds / 60);
            seconds -= minutes * 60;

            if (hours < 10) {
                hours = '0' + hours;
            }
            if (minutes < 10) {
                minutes = '0' + minutes;
            }
            if (seconds < 10) {
                seconds = '0' + seconds;
            }

            $day.text(days);
            $hour.text(hours);
            $minute.text(minutes);
            $second.text(seconds);
        }
    };
    $lotteryResult.getSpreadDiv = function () {
        return  $lotteryResult.find('div.body div.prompt div.spread');
    };
    $('#takeLotteryAgain').click(function () {
        getLotteryLot();
    });
    function showLotteryResult(options) {
        $lotteryResult.updateDeadline(options['remainingSeconds']);
        setFloatPanelUsername($lotteryResult, options.username);
        $lotteryResult.find('div.body div.activity-info div.participant-count span')
            .text(options['participantCount']);

        // serial number
        var serialNumbers = options['serialNumbers'];
        if (serialNumbers) {
            var $numberList = $lotteryResult.find('div.body div.my-lottery div.number div.number-list').empty();
            for (var i = 0, len = serialNumbers.length; i < len; i++) {
                $numberList.append('<span>' + serialNumbers[i] + '</span>')
            }
        }

        // liveness
        $lotteryResult.find('div.body div.my-lottery span.my-liveness').text(options['liveness']);
        $lotteryResult.find('div.body div.my-lottery span.max-liveness').text(options['maxLiveness'])
            .attr('title', options['maxLivenessUsers']);

        // share url
        var $share = $lotteryResult.find('div.body div.share');
        $share.find('a.sina').attr('href', options['sinaWeiboShareUrl']);
        $share.find('a.qq').attr('href', options['qqShareUrl']);
        $share.find('a.qzone').attr('href', options['qzoneShareUrl']);

        // show float panel
        JSUtils.showTransparentBackground(1);
        if (options.success) {
            $lotteryResult.getSpreadDiv().hide();
            $lotteryResult.fadeIn(300);
        } else {
            $lotteryResult.getSpreadDiv().show();
            $lotteryResult.fadeIn(300, function () {
                $lotteryResult.getSpreadDiv().twinkle(4);
            });
        }
        JSUtils.scrollToVerticalCenter($lotteryResult);
    }

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
        /*var $selectedSnapshot = $('div.body div.snapshots div.snapshot.selected');
         var commodityId = $selectedSnapshot.dataOptions('id');*/
        $.post('take-lottery.json', {
            'commodityId': getSelectedCommodityId()
        }, function (data) {
            if (data.success) {
                showLotteryResult(data);
            } else {
                if (data.detail == 'noLottery') {
                    showLotteryExceptionPrompt(data.username, '本商品暂时没有抽奖，敬请关注其他商品的抽奖！');
                } else if (data.detail == 'noLogin') {
                    JSUtils.showTransparentBackground(1);
                    showLoginForm(afterLoginSuccess);
                } else if (data.detail == 'noPrivilege') {
                    showNoPrivilegeForm(data.username);
                } else if (data.detail == 'noTel') {
                    showTelInputForm(data.username);
                } else if (data.detail == 'activityExpire') {
                    showLotteryExceptionPrompt(data.username, '本期抽奖已结束，敬请关注下期抽奖！');
                } else if (data.detail == 'alreadyAttended') {
                    showLotteryResult(data);
                } else {
                    alert(data.detail);
                }
            }
        });
    };
})();
var getLotteryLot, showLotteryRule;
function getSelectedCommodityId() {
    var $selectedSnapshot = $('div.main-body div.snapshots div.snapshot.selected');
    return $selectedSnapshot.dataOptions('id');
}
