(function () {
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

        $parent.empty();
        buildClockHtml(options).appendTo($parent);
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

    var jsutilsDigitPath = JSUtils.getLastLoadScriptPath();
    /**
     * 360 style digit clock
     */
    window.JSUtils.digitClock360 = function ($parent, initValue) {
        $parent.empty();
        var backgroundImage = jsutilsDigitPath.replace(/\/[^/]+$/, '/digits.png');
        var $clock = buildClockHtml({
            numberWidth: 18,
            numberHeight: 26,
            numberSize: 14,
            backgroundImage: backgroundImage
        });
        $clock.find('div.number').empty().css('background-image', 'url("' + backgroundImage + '")');
        $clock.appendTo($parent);

        return ({
            _numberDivs: $parent.find('div.number'),
            _valueSize: 8,
            _setBackgroundPosition: function ($element, y) {
                // notice that firefox doesn't support background-position-y
                $element.css({
                    'background-position': '0 ' + y + 'px',
                    'background-position-y': y + 'px'
                });
                return $element;
            },
            setValue: function (index, value) {
                var y = (parseInt(value) * -26 * 6);
                this._setBackgroundPosition(this._numberDivs.eq(index), y).dataOptions('value', value);
            },
            transformToValue: function (index, value) {
                var i = 6;
                var self = this;
                self._numberDivs.eq(index).dataOptions('value', value);

                changeNumber();
                function changeNumber() {
                    i--;
                    self._setBackgroundPosition(self._numberDivs.eq(index), ((parseInt(value) * 6 + i) * -26));
                    if (i > 0) {
                        setTimeout(function () {
                            changeNumber()
                        }, 50);
                    }
                }
            },
            to: function (value) {
                var first = true;
                for (var i = 0; i < Math.min(value.length, this._valueSize); i++) {
                    var n = value[i];
                    if (n != this._numberDivs.eq(i).dataOptions('value')) {
                        if (first) {
                            first = false;
                            this.transformToValue(i, value[i]);
                        } else {
                            this.setValue(i, value[i]);
                        }
                    }
                }
            },
            _init: function () {
                for (var i = 0; i < Math.min(initValue.length, this._valueSize); i++) {
                    this.setValue(i, initValue[i]);
                }
                return this;
            }
        })._init();
    };

    /**
     * require options: numberWidth, numberHeight, backgroundImage, numberSize
     * optional options: initValue
     * @param options configuration
     * @returns {*|jQuery|HTMLElement}
     */
    function buildClockHtml(options) {
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
        var $numbers = $html.find('div.number').css({
            'width': options.numberWidth + 'px'
        });
        if (options['initValue']) {
            $numbers.each(function (index) {
                $(this).find('> div:first').text(options['initValue'][index]);
            });
        }
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
})();
