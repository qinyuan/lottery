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
        $posterDiv.hide();
        $posterDiv.css('background-image', 'url("' + indexImage['backPath'] + '")');
        $posterDiv.find('img').attr('src', indexImage['path'])
            .attr('usemap', '#indexMap' + indexImage['id']);
        $posterDiv.fadeIn(1000);
    }

    var indexImageGroups = window['indexImageGroups'];
    setInterval(function () {
        for (var rowIndex in indexImageGroups) {
            if (!(typeof rowIndex == 'string') || !rowIndex.match(/^\d+$/)) {
                continue;
            }

            var indexImages = indexImageGroups[rowIndex];
            var $div = getPosterDivByRowIndex(rowIndex);
            var currentImageId = getImageIdByPosterDiv($div);

            for (var i = 0, len = indexImages.length; i < len; i++) {
                var indexImage = indexImages[i];
                if (indexImage['id'] == currentImageId) {
                    if (i == len - 1) {
                        changePoster($div, indexImages[0]);
                    } else {
                        changePoster($div, indexImages[i + 1]);
                    }
                }
            }
        }
    }, window['cycleInterval'] * 1000);
})();