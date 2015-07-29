;
(function () {
    // code about participant count
    function updateParticipantCount(commodityId) {
        $.post('participant-count.json', {
            commodityId: commodityId
        }, function (data) {
            if (data['participantCount']) {
                var $participantCountDiv = $('div.body > div.detail > div.participant-count');
                $participantCountDiv.find("span.participant-count").text(data['participantCount']);
                $participantCountDiv.show();
            }
        });
    }

    updateParticipantCount(window['selectedCommodityId']);
    setInterval(function () {
        updateParticipantCount(getSelectedCommodityId());
    }, 2000); // refresh each two seconds

    // code about commodity image
    var snapshotDisplaySize = 6;
    var $snapshots = $('div.body div.snapshots div.snapshot');
    var snapshotCount = $snapshots.size();
    var selectedId = window['selectedCommodityId'];

    function loadDetailImageById(id) {
        $('div.body > div.detail > div.participant-count').hide();
        var $img = $('div.body div.detail img');
        $img.hide();
        $.post('commodity-info.json', {
            id: id
        }, function (data) {
            $img.attr('src', data['commodity']['detailImage']);
            $img.fadeIn(500, function () {
                updateParticipantCount(getSelectedCommodityId());
            });
            loadCommodityMap(data['commodityMaps']);
        })
    }

    function getVisibleSnapshots() {
        return $snapshots.filter(function () {
            return $(this).css('display') == 'block';
        });
    }

    function loadCommodityMap(commodityMaps) {
        var mapHtml = JSUtils.handlebars('mapTemplate', {'commodityMaps': commodityMaps});
        $('#commodityMap').html(mapHtml);
    }

    var $prevArrow = $('div.body div.snapshots div.prev');
    var $nextArrow = $('div.body div.snapshots div.next');
    if (snapshotCount > snapshotDisplaySize) {
        $prevArrow.click(function () {
            var $visibleSnapshots = getVisibleSnapshots();
            var $firstVisibleSnapshot = $visibleSnapshots.first();
            var $lastVisibleSnapshot = $visibleSnapshots.last();

            for (var i = 0; i < snapshotDisplaySize; i++) {
                $firstVisibleSnapshot = $firstVisibleSnapshot.prev();
                if ($firstVisibleSnapshot.size() > 0 && $firstVisibleSnapshot.hasClass('snapshot')) {
                    $lastVisibleSnapshot.hide(500);
                    $lastVisibleSnapshot = $lastVisibleSnapshot.prev();
                    $firstVisibleSnapshot.show(500);
                    if (i == 0) {
                        $nextArrow.show();
                    }
                } else {
                    if (i > 0) {
                        $prevArrow.hide();
                    }
                    break;
                }
            }
        }).show();
        $nextArrow.click(function () {
            var $visibleSnapshots = getVisibleSnapshots();
            var $firstVisibleSnapshot = $visibleSnapshots.first();
            var $lastVisibleSnapshot = $visibleSnapshots.last();

            for (var i = 0; i < snapshotDisplaySize; i++) {
                $lastVisibleSnapshot = $lastVisibleSnapshot.next();
                if ($lastVisibleSnapshot.size() > 0 && $lastVisibleSnapshot.hasClass('snapshot')) {
                    $firstVisibleSnapshot.hide(500);
                    $firstVisibleSnapshot = $firstVisibleSnapshot.next();
                    $lastVisibleSnapshot.show(500);
                    if (i == 0) {
                        $prevArrow.show();
                    }
                } else {
                    if (i > 0) {
                        $nextArrow.hide();
                    }
                    break;
                }
            }
        }).show();
    }

    // show snapshot beside selected snapshot
    for (var i = 0; i < snapshotCount; i++) {
        var $snapshot = $snapshots.eq(i);
        if ($snapshot.dataOptions('id') == selectedId) {
            $snapshot.addClass('selected');
            var startIndex = parseInt(i / snapshotDisplaySize) * snapshotDisplaySize;
            var endIndex = startIndex + 5;
            while (endIndex >= snapshotCount) {
                endIndex--;
                startIndex--;
            }
            for (var j = startIndex; j <= endIndex && j < snapshotCount; j++) {
                $snapshots.eq(j).show();
            }
            break;
        }
    }
    $snapshots.click(function () {
        $snapshots.removeClass('selected');
        var $this = $(this);
        $this.addClass('selected');
        var id = $this.dataOptions('id');
        loadDetailImageById(id);
    });

    loadCommodityMap(window['commodityMaps']);
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
        var tel = $tel.val();
        if (!tel.match(/^\d{11}$/)) {
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
                this.$parent = $('#'+parentId);
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
    var $selectedSnapshot = $('div.body div.snapshots div.snapshot.selected');
    return $selectedSnapshot.dataOptions('id');
}
