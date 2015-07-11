;
(function () {
    // code about commodity image
    var snapshotDisplaySize = 6;
    var $snapshots = $('div.body div.snapshots div.snapshot');
    var snapshotCount = $snapshots.size();
    var selectedId = window['selectedCommodityId'];

    function loadDetailImageById(id) {
        var $img = $('div.body div.detail img');
        $img.hide();
        $.post('commodity-info.json', {
            id: id
        }, function (data) {
            $img.attr('src', data['commodity']['detailImage']);
            $img.fadeIn(500);
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
    // code about lottery
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
        $telInputForm.show().focusFirstTextInput();
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
        $noPrivilegePrompt.show();
    }

    var $lotteryExceptionPrompt = $('#lotteryExceptionPrompt');
    setCloseIconEvent($lotteryExceptionPrompt, function () {
        JSUtils.hideTransparentBackground();
        $lotteryExceptionPrompt.hide();
    });
    function showLotteryExceptionPrompt(username, info) {
        JSUtils.showTransparentBackground(1);
        setFloatPanelUsername($lotteryExceptionPrompt, username);
        $lotteryExceptionPrompt.show().find('div.body div.info').text(info);
    }

    getLotteryLot = function () {
        var $selectedSnapshot = $('div.body div.snapshots div.snapshot.selected');
        var commodityId = $selectedSnapshot.dataOptions('id');
        $.post('take-lottery.json', {
            'commodityId': commodityId
        }, function (data) {
            if (data.success) {
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

                } else {
                    alert(data.detail);
                }
            }
        });
    };
})();
var getLotteryLot;
