if (!window.JSUtils) {
    window.JSUtils = {};
}

/**
 * Create digit clock.
 * Require options: backgroundImage, initValue
 * Optional options: numberWidth, numberHeight, numberSize
 *
 * This is an simple example:
 *
 * var digitClock = JSUtils.digitClock($('#digit'), {
 *    'backgroundImage': 'css/images/digit.png',
 *    'initValue': '08121542'
 * });
 * digitClock.to('08121543')
 * @param $parent parent element
 * @param options configuration options of digit clock
 */
window.JSUtils.digitClock = function ($parent, options) {
    // set default values
    if (!options.numberWidth) {
        options.numberWidth = 18;
    }
    if (!options.numberHeight) {
        options.numberHeight = 26;
    }
    if (!options.numberSize) {
        options.numberSize = '14';
    }

    function changeTimeElement($div, newValue) {
        var $subDirs = $div.find('> div');
        var $firstDiv = $subDirs.eq(0);
        var $secondDiv = $subDirs.eq(1);
        $secondDiv.text(newValue);
        $firstDiv.animate({'margin-top': '-' + options.numberHeight + 'px'}, 300, function () {
            $firstDiv.text(newValue);
            $firstDiv.css('margin-top', '0');
        });
    }

    function create$Html() {
        function buildUnit(unit) {
            return '<div class="unit">' + unit + '</div>';
        }

        var html = '<div class="digit-clock">';

        var number = '<div class="number"><div></div><div></div></div>';
        html += number + number + buildUnit('天');
        html += number + number + buildUnit('时');
        html += number + number + buildUnit('分');
        html += number + number + buildUnit('秒');

        html += '<div class="clear"></div>';
        html += '</div>';

        var $html = $(html);
        $html.find('div.number,div.unit').css({
            'float': 'left',
            'height': options.numberHeight + 'px',
            'overflow': 'hidden'
        });
        $html.find('div.number').css({
            'width': options.numberWidth + 'px'
        }).each(function (index) {
            $(this).find('> div:first').text(options['initValue'][index]);
        });
        $html.find('div.number > div').css({
            'width': '100%',
            'height': options.numberHeight + 'px',
            'background-image': 'url("' + options['backgroundImage'] + '")',
            'background-repeat': 'no-repeat',
            'font-size': options['numberSize'] + 'pt',
            'text-align': 'center',
            'line-height': options.numberHeight + 'px',
            'font-weight': 'bold',
            'color': '#ffffff'
        });
        $html.find('div.unit').css({
            'padding-top': (options.numberHeight - 18) + 'px',
            'margin': '0 3px'
        });
        $html.find('div.clear').css('clear', 'both');
        return $html;
    }

    $parent.empty();
    create$Html().appendTo($parent);
    return {
        _numberDivs: $parent.find('div.number'),
        _oldValue: options.initValue,
        to: function (value) {
            var length = Math.min(value.length, this._oldValue.length);
            for (var i = 0; i < length; i++) {
                if (value[i] != this._oldValue[i]) {
                    changeTimeElement(this._numberDivs.eq(i), value[i]);
                }
            }
            this._oldValue = value;
        }
    };
};
