;
(function () {
    function getPosterDivByRowIndex(rowIndex) {
        return $('div.posters div.poster').eq(parseInt(rowIndex) - 1);
    }

    function getImageIdByPosterDiv($posterDiv) {
        var imageId = $posterDiv.find('img').attr('usemap').replace('#indexMap', '0');
        return parseInt(imageId);
    }

    function changePoster($posterDiv, indexImage) {
        $posterDiv.css('min-height', $posterDiv.height() + 'px');
        var $backgroundDiv = $posterDiv.find('div.background');
        $backgroundDiv.hide().css('background-image', 'url("' + indexImage['backPath'] + '")');
        $backgroundDiv.find('img').attr('src', indexImage['path'])
            .attr('usemap', '#indexMap' + indexImage['id']);
        $backgroundDiv.fadeIn(1000, function () {
            $posterDiv.css('min-height', 0);
        });

        $posterDiv.find('div.dots div.dot').removeClass('selected').eq(indexImage['index']).addClass('selected');
    }

    function switchToNextPoster(rowIndex) {
        var indexImages = indexImageGroups[rowIndex];
        var $posterDiv = getPosterDivByRowIndex(rowIndex);

        var currentImageId = getImageIdByPosterDiv($posterDiv);
        for (var i = 0, len = indexImages.length; i < len; i++) {
            var indexImage = indexImages[i];
            if (indexImage['id'] == currentImageId) {
                if (i == len - 1) {
                    changePoster($posterDiv, indexImages[0]);
                } else {
                    changePoster($posterDiv, indexImages[i + 1]);
                }
            }
        }
    }

    var indexImageGroups = window['indexImageGroups'];
    var cycleInterval = window['cycleInterval'] * 1000;
    var cycleLock = {};
    setInterval(function () {
        for (var rowIndex in indexImageGroups) {
            if (!indexImageGroups.hasOwnProperty(rowIndex) || !(typeof rowIndex == 'string')
                || !rowIndex.match(/^\d+$/) || cycleLock[rowIndex] != null) {
                continue;
            }
            switchToNextPoster(rowIndex);
        }
    }, cycleInterval);

    $('div.dots div.dot').hover(function () {
        var $this = $(this);
        var $posterDiv = $this.getParentByTagNameAndClass('div', 'poster');
        var rowIndex = $posterDiv.dataOptions('rowIndex');
        var index = $this.dataOptions('index');
        cycleLock[rowIndex] = {'ts': new Date().getTime()};

        if (!$this.hasClass('selected')) {
            var indexImages = indexImageGroups[rowIndex];
            changePoster($posterDiv, indexImages[parseInt(index)]);
        }
    }, function () {
        var rowIndex = $(this).getParentByTagNameAndClass('div', 'poster').dataOptions('rowIndex');
        setTimeout(function () {
            if (cycleLock[rowIndex] == null) {
                return;
            }
            var ts = cycleLock[rowIndex]['ts'];
            if (new Date().getTime() - ts > cycleInterval) {
                cycleLock[rowIndex] = null;
            }
        }, cycleInterval);
    });
    $('div.dots').each(function () {
        var $this = $(this);
        var width = $this.width();
        if (width > 0) {
            $this.css('margin-left', '-' + (width / 2) + 'px').removeClass('unload');
        }
    });
})();