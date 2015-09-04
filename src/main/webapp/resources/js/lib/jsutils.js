/**
 * This file create some useful function for javascript
 */
var JSUtils = {
    /**
     * notice that we can not use new Date("2012-01-01") in IE8,
     * so we use this function to ensure compatibility to IE8
     * @param arg date string or timestamp
     * @returns {Date}
     */
    newDate: function (arg) {
        if (this.isNumber(arg)) {
            return new Date(arg);
        }

        var dateArr = arg.split('-');
        var year = parseInt(dateArr[0]);
        var month = parseInt(dateArr[1] - 1);
        var day = parseInt(dateArr[2]);
        return new Date(year, month, day);
    },
    remainingTimeRecorder: function (remainingSeconds) {
        var startTimestamp = new Date().getTime();
        return {
            _startTimestamp: startTimestamp,
            getRemainingSeconds: function () {
                return remainingSeconds + parseInt((this._startTimestamp - new Date().getTime()) / 1000);
            },
            getRemainingTime: function () {
                function pad(value) {
                    return value < 10 ? '0' + value : '' + value;
                }

                var secondsInDay = 3600 * 24;
                var seconds = this.getRemainingSeconds();
                if (seconds <= 0) {
                    seconds = 0;
                }
                var days = parseInt(seconds / secondsInDay);
                seconds -= days * secondsInDay;
                var hours = parseInt(seconds / 3600);
                seconds -= hours * 3600;
                var minutes = parseInt(seconds / 60);
                seconds -= minutes * 60;

                return {
                    'days': pad(days),
                    'hours': pad(hours),
                    'minutes': pad(minutes),
                    'seconds': pad(seconds)
                };
            },
            getRemainingTimeString: function () {
                var time = this.getRemainingTime();
                return time.days + time.hours + time.minutes + time.seconds;
            }
        };
    },
    getWindowHeight: function () {
        return $(window).height();
    },
    getImageHeight: function ($img) {
        var img = $img.get(0);
        if (img.height > 0) {
            return img.height;
        } else {
            img = new Image();
            img.src = $img.attr('src');
            return img.height;
        }
    },
    getImageWidth: function ($img) {
        var img = $img.get(0);
        if (img.width > 0) {
            return img.width;
        } else {
            img = new Image();
            img.src = $img.attr('src');
            return img.width;
        }
    },
    isArrayContains: function (array, value) {
        for (var i = 0, len = array.length; i < len; i++) {
            if (array[i] === value) {
                return true;
            }
        }
        return false;
    },
    splitArray: function (array, groupSize) {
        var result = [], group;
        for (var i = 0, len = array.length; i < len; i++) {
            if (i % groupSize == 0) {
                group = [];
                result.push(group);
            }
            group.push(array[i]);
        }
        return result;
    },
    copyArray: function (array) {
        var arr = [];
        for (var i = 0, len = array.length; i < len; i++) {
            arr.push(array[i]);
        }
        return arr;
    },
    removeArrayItem: function (array, index) {
        if (array == null || array.length <= index) {
            return;
        }

        array.splice(index, 1);
    },
    isString: function (arg) {
        return (typeof arg) == 'string';
    },
    isNumber: function (arg) {
        return (typeof arg) == 'number';
    },
    isNumberString: function (arg) {
        if (!this.isString(arg)) {
            return false;
        }
        return arg.match(/^\d+(\.\d+)?$/) != null;
    },
    getUserAgent: function () {
        return navigator['userAgent'];
    },
    isFirefox: function () {
        return this.getUserAgent().indexOf('Firefox') > -1;
    },
    isIE: function () {
        return this.getUserAgent().indexOf('MSIE') > -1;
    },
    isChrome: function () {
        return this.getUserAgent().indexOf('Chrome') > -1;
    },
    getCurrentTime: function () {
        var date = new Date();
        var year = date.getFullYear();
        var month = date.getMonth() + 1;
        var day = date.getDate();
        var hour = date.getHours();
        var minute = date.getMinutes();
        var second = date.getSeconds();
        return year + "-" + month + "-" + day + " " + hour + ":" + minute + ":" + second;
    },
    recordScrollStatus: function () {
        var $window = $(window);
        var key = 'scroll-status-record_' + location.pathname.replace(/\..*$/g, '');
        var value = $.cookie(key);
        if (value) {
            document.documentElement.scrollTop = value;
            $window.scrollTop(value);
        }
        $window.scroll(function () {
            $.cookie(key, $window.scrollTop());
        });
    },
    /**
     * scroll certain html element to vertical center
     * @param $element
     */
    scrollToVerticalCenter: function ($element) {
        var top = (JSUtils.getWindowHeight() - $element.height()) / 2;
        if (top < 0) {
            top = 0;
        }
        top = top + $(window).scrollTop();
        $element.css({'top': top, 'position': 'absolute', 'margin-top': 0});
    },
    /**
     * In firefox, offsetX and offsetY is undefined, so we use this function to
     * ensure compatibility to firefox
     * @param e event object
     * @returns {{offsetX: number, offsetY: number}}
     */
    getOffsetByEvent: function (e) {
        if (e.offsetX !== undefined && e.offsetY !== undefined) {
            return {
                offsetX: e.offsetX,
                offsetY: e.offsetY
            };
        }

        function getPageCoord(element) {
            var coord = {x: 0, y: 0};
            while (element) {
                coord.x += element.offsetLeft;
                coord.y += element.offsetTop;
                element = element.offsetParent;
            }
            return coord;
        }

        var target = e.target;
        if (target.offsetLeft == undefined) {
            target = target.parentNode;
        }
        var pageCoord = getPageCoord(target);
        var eventCoord =
        {
            x: window.pageXOffset + e.clientX,
            y: window.pageYOffset + e.clientY
        };
        return {
            offsetX: eventCoord.x - pageCoord.x,
            offsetY: eventCoord.y - pageCoord.y
        };
    },
    getChineseStringLength: function (chineseString) {
        var len = 0;
        for (var i = 0; i < chineseString.length; i++) {
            if (chineseString.charCodeAt(i) > 127) {
                len += 2;
            } else {
                len++;
            }
        }
        return len;
    },
    getChineseSubString: function (chineseString, len) {
        if (this.getChineseStringLength(chineseString) <= len) {
            return chineseString;
        }
        var lenCount = 0, str = '';
        for (var i = 0; i < chineseString.length; i++) {
            if (chineseString.charCodeAt(i) > 127) {
                lenCount += 2;
            } else {
                lenCount++;
            }
            if (lenCount > len - 3) {
                str = str + "...";
                break;
            }
            str = str + chineseString[i];
        }
        return str;
    },
    limitTextLength: function ($element, len) {
        var text = $element.text();
        $element.attr('title', text);
        if (this.getChineseStringLength(text) > len) {
            $element.text(this.getChineseSubString(text, len));
        }
    },
    validateEmail: function (email) {
        var pattern = /^[a-zA-Z0-9_\\-\\.]+@[a-zA-Z0-9_-]+(.[a-zA-Z0-9_-]+)+$/;
        return email && email.match(pattern) != null;
    },
    validateTel: function (tel) {
        var pattern = /^\d{11}$/;
        return tel && tel.match(pattern) != null;
    },
    getEmailLoginPage: function (emailAccount) {
        return 'http://mail.' + emailAccount.replace(/^.*\@/, '');
    },
    handlebars: function (templateId, data) {
        var source = $("#" + templateId).html();
        var template = Handlebars.compile(source);
        if (!data) {
            data = {};
        }
        return template(data);
    },
    _getPromptDiv: function (zIndex, callback) {
        var id = 'bootstrapStylePrompt';
        var $div = $('#' + id);
        if ($div.size() == 0) {
            var html = '<div>';
            html += '<div class="title"></div>';
            html += '<div class="content"><input type="text" class="form-control"/></div>';
            html += '<div class="submit">';
            html += '<button class="btn btn-success ok" type="button">确定</button>';
            html += '<button class="btn btn-default cancel" type="button">取消</button>';
            html += '</div>';
            html += '</div>';
            $div = $(html).css({
                'padding': '20px',
                'min-width': '400px',
                'margin-left': '-200px'
            });
            $div.find('>div').css({'margin': '5px 0'});
            $div.find('div.title').css({'font-size': '12pt', 'font-weight': 'bold', 'padding-left': '2px'});
            $div.find('div.submit').css({'margin-top': '20px', 'text-align': 'center'})
                .find('button').css('margin-right', '10px');
            var self = this;
            $div.find('button.cancel').unbind('click').click(function () {
                self.hidePrompt();
            });
            $div.setDefaultButtonByClass('ok');
            $div.addClass('float-panel').attr('id', id).appendTo('body');
        } else {
            $div.find('button.ok').text('确定');
        }
        $div.find('button.ok').unbind('click').click(function () {
            var input = $div.find('div.content input').val();
            if (callback) {
                callback(input, $div);
            }
        });
        if (zIndex) {
            $div.css('z-index', zIndex);
        }
        return $div;
    },
    hidePrompt: function (remainTransparentBackground) {
        var self = this;
        this._getPromptDiv(10).fadeOut(200, function () {
            if (!remainTransparentBackground) {
                self.hideTransparentBackground();
            }
        });
    },
    showPrompt: function (title, defaultValue, callback) {
        this.showTransparentBackground(10);
        var $prompt = this._getPromptDiv(11, callback);
        JSUtils.scrollToVerticalCenter($prompt);
        $prompt.find('div.title').text(title);
        $prompt.fadeIn(200).find('div.content input').val(defaultValue).focusOrSelect();
    },
    focusPrompt: function () {
        $('#bootstrapStylePrompt').find('input').focusOrSelect();
    },
    _getTransparentBackgroundDiv: function (zIndex) {
        var $transparentBackground = $('#transparentBackground');
        if ($transparentBackground.size() == 0) {
            // if transparent background hasn't created, create it
            var style = {
                'position': 'fixed',
                'width': '100%',
                'height': '100%',
                'top': 0,
                'left': 0,
                'display': 'none',
                'background-color': '#000000',
                opacity: 0.4,
                filter: 'alpha(opacity=40)'
            };
            if (zIndex) {
                style['z-index'] = zIndex;
            }
            return $('<div></div>').attr('id', 'transparentBackground').css(style).appendTo('body');
        } else {
            // if transparent background is already created, just return it
            if (zIndex) {
                $transparentBackground.css('z-index', zIndex);
            }
            return $transparentBackground;
        }
    },
    showTransparentBackground: function (zIndex) {
        this._getTransparentBackgroundDiv(zIndex).show();
    },
    hideTransparentBackground: function () {
        this._getTransparentBackgroundDiv().hide();
    },
    normalAjaxCallback: function (data) {
        if (data.success) {
            location.reload();
        } else {
            alert(data.detail);
        }
    },
    postArrayParams: function (url, param, callback) {
        $.ajax({
            url: url,
            type: "post",
            data: param,
            async: false,
            dataType: "json",
            cache: false,
            error: function (data) {
            },
            beforeSend: function (XMLHttpRequest) {
            },
            complete: function (XMLHttpRequest, textStatus) {
            },
            success: callback
        });
    },
    /**
     * validate is upload file is set
     *
     * @param id the id of upload file
     * @param errorInfo information to show if upload file is not set
     * @returns {boolean} if upload file is set, return true, otherwise return false
     */
    validateUploadFile: function (id, errorInfo) {
        var $url = $('form input[name=' + id + ']');
        var $file = $('form input[name=' + id + 'File]');

        if ($url.trimVal() == '' && $file.trimVal() == '') {
            alert(errorInfo);
            $url.focusOrSelect();
            return false;
        } else {
            return true;
        }
    },
    /**
     * scroll certain element to top,
     *
     * if no element is given, scroll all screen to top
     * @param $targetElement element to scroll to top
     */
    scrollTop: function ($targetElement, speed) {
        if (speed == null) {
            speed = 250;
        }
        var offset = $targetElement ? $targetElement.offset().top : 0;
        if (JSUtils.isFirefox() || JSUtils.isIE()) {
            document.documentElement.scrollTop = offset;
        } else {
            $('body').animate({scrollTop: offset}, speed);
        }
    },
    isEnterKeyCode: function (keyCode) {
        return keyCode == 13;
    },
    isDateString: function (dateString) {
        return dateString != null &&
            dateString.match(/^\d{4}-\d{1,2}-\d{1,2}$/) != null;
    },
    isDateTimeString: function (dateTimeString) {
        return dateTimeString != null &&
            dateTimeString.match(/^\d{4}-\d{1,2}-\d{1,2} \d{1,2}:\d{1,2}:\d{1,2}$/) != null;
    },
    isDateOrDateTimeString: function (str) {
        return this.isDateString(str) || this.isDateTimeString(str);
    },
    /**
     * set events and initial values of bootstrap style select form
     * @param $div
     * @param initValue
     */
    loadSelectFormEventsAndValue: function ($div, initValue) {
        this.loadSelectFormEvents($div);
        this.loadSelectFormValue($div, initValue);
    },
    loadSelectFormEvents: function ($div) {
        $div.find('ul.dropdown-menu a').click(function () {
            var $this = $(this);
            var id = $this.dataOptions('id');
            var text = $this.text();
            var $parent = $this.getParentByTagNameAndClass('div', 'dropdown');
            $parent.find('button').html(text + ' <span class="caret"></span>');
            $parent.find('input[type=hidden]:first').val(id);
        });
    },
    loadSelectFormValue: function ($div, initValue) {
        if (initValue) {
            $div.find('li a').each(function () {
                var $this = $(this);
                if ($this.dataOptions('id') == initValue) {
                    $this.trigger('click');
                    return false;
                }
                return true;
            });
        }
    },
    loadTableFilterEvents: function (urlToGetValues, urlToAddFilter, urlToRemoveFilter) {
        var filterPanel = {
            getDistinctItems: function (alias, callback) {
                var href = JSUtils.updateUrlParam('alias', alias);
                href = urlToGetValues + '?' + href.replace(/^.*\?/, '');
                $.get(href, function (data) {
                    callback(data);
                });
            },
            clear: function ($parent) {
                $parent.find('div').remove();
            },
            build: function ($parent) {
                function setSelectRowCss($html) {
                    $html.css({
                        'width': '100%',
                        'padding': '4px'
                    }).filter(function () {
                        var $this = $(this);
                        return $this.find('button').size() == 0 && $this.find('a').size() == 0;
                    }).css({'cursor': 'pointer'}).hover(function () {
                        $(this).css('background-color', '#cccccc');
                    }, function () {
                        $(this).css('background-color', '#ffffff');
                    });
                }

                function adjustPanelWidth($filterDiv) {
                    var maxWidth = 0;
                    $filterDiv.find('>div >div').each(function () {
                        maxWidth = Math.max(maxWidth, JSUtils.getChineseStringLength($(this).text()));
                    });
                    $filterDiv.css('width', 10 + maxWidth * 9);
                }

                var html = '<div class="filter-menu">';
                html += '<div class="rank"><div class="asc">升序</div><div class="desc">降序</div>';
                html += '</div>';
                var $html = $(html).appendTo($parent);
                $html.css({
                    'position': 'absolute',
                    'background-color': '#ffffff',
                    'top': '100%',
                    'right': 0,
                    'min-width': '100px',
                    'width': 'auto',
                    'border': '1px solid #cccccc',
                    'cursor': 'default',
                    'text-align': 'left',
                    'z-index': 1
                });
                $html.find('div.rank').css({
                    'border-bottom': '1px solid #cccccc'
                }).find('>div').click(function () {
                    var $this = $(this);
                    var $th = $this.getParentByTagName('th');
                    location.href = JSUtils.updateUrlParam({
                        'orderField': $th.attr('class'),
                        'orderType': $this.attr('class')
                    });
                });
                setSelectRowCss($html.find('>div >div'));

                this.getDistinctItems($parent.getParentByTagName('th').attr('class'), function (distinctItems) {
                    var html = '<div class="filter-items">';

                    // checkboxes
                    for (var i = 0, len = distinctItems.length; i < len; i++) {
                        var item = distinctItems[i];
                        var text = item['text'];
                        html += '<div><input type="checkbox" value="' + text + '"';
                        if (item.checked) {
                            html += ' checked';
                        }
                        html += '/><span class="checkbox-label">' + text + '</span></div>'
                    }

                    // select or deselect all
                    html += '<div><a class="selectAll" href="javascript:void(0)">全选</a>';
                    html += '&nbsp;<a class="unSelectAll" href="javascript:void(0)">全不选</a></div>';

                    // submit or cancel
                    html += '<div><button type="button" class="btn btn-success btn-xs ok">确定</button>';
                    html += '&nbsp;<button type="button" class="btn btn-default btn-xs cancel">取消</button></div>';

                    html += '</div>';

                    var $html = $(html).appendTo($parent.find('>div'));
                    $html.find('a.selectAll').click(function () {
                        $(this).parent().parent().find('input[type=checkbox]').each(function () {
                            this.checked = true;
                        });
                    });
                    $html.find('a.unSelectAll').click(function () {
                        $(this).parent().parent().find('input[type=checkbox]').each(function () {
                            this.checked = false;
                        });
                    });
                    $html.find('button.ok').click(function () {
                        var $filter = $(this).getParentByTagNameAndClass('div', 'filter');
                        var filterField = $filter.parent().attr('class');
                        var filterValues = [];
                        var allChecked = true;
                        $filter.find('input[type=checkbox]').each(function () {
                            if (this.checked) {
                                filterValues.push($(this).val());
                            } else {
                                allChecked = false;
                            }
                        });

                        if (allChecked) {
                            $.post(urlToRemoveFilter, {
                                filterField: filterField
                            }, JSUtils.normalAjaxCallback);
                        } else {
                            JSUtils.postArrayParams(urlToAddFilter, {
                                filterField: filterField,
                                filterValues: filterValues
                            }, JSUtils.normalAjaxCallback);
                        }
                    });
                    $html.find('button.cancel').click(function () {
                        $(this).getParentByTagNameAndClass('div', 'filter').find('>button').trigger('blur');
                    });

                    setSelectRowCss($html.find('>div'));
                    $html.click(function () {
                        var $filterButton = $(this).getParentByTagNameAndClass('div', 'filter').find('>button');
                        $filterButton.attr('prevent-blur', 'true');
                    });
                    $html.find('input').css({'vertical-align': '-10%', 'margin-right': '3px'});
                    $html.find('span.checkbox-label').click(function () {
                        $(this).parent().find('input[type=checkbox]').trigger('click');
                    });
                    adjustPanelWidth($parent.find('>div'));
                });
            }
        };

        $('table div.filter button.filter').click(function () {
            var $parent = $(this).parent();
            if ($parent.find('div').size() > 0) {
                filterPanel.clear($parent);
            } else {
                filterPanel.build($parent);
            }
        }).blur(function () {
            var $this = $(this);
            var $parent = $this.parent();
            setTimeout(function () {
                if ($this.attr('prevent-blur')) {
                    $this.attr('prevent-blur', null);
                    $this.focus();
                } else {
                    filterPanel.clear($parent);
                }
            }, 200);
        });
    },
    /**
     * update certain parameter of current url, then return the new url
     */
    updateUrlParam: function (params) {
        function updateParamByUrl(url, key, value) {
            if (url.indexOf('?') < 0) {
                return url + '?' + key + '=' + value;
            }

            var stringArray = url.split('?');
            url = stringArray[0];
            stringArray = stringArray[1].split('&');
            for (var i = 0, len = stringArray.length; i < len; i++) {
                if (stringArray[i].indexOf(key + '=') == 0) {
                    stringArray[i] = key + '=' + value;
                    break;
                }
                if (i == len - 1) {
                    stringArray.push(key + '=' + value);
                }
            }
            return url + '?' + stringArray.join('&');
        }

        if (typeof(params) == 'string') {
            return updateParamByUrl(location.href, params, arguments[1]);
        } else if (typeof(params) == 'object') {
            var href = location.href;
            for (var key in params) {
                if (params.hasOwnProperty(key)) {
                    href = updateParamByUrl(href, key, params[key]);
                }
            }
            return href;
        } else {
            return location.href;
        }
    },
    /**
     * Get the constellation of given day
     */
    getConstellation: function (month, day) {
        function isBetweenDate(startMonth, startDay, endMonth, endDay) {
            return date >= new Date(2012, startMonth - 1, startDay) &&
                date <= new Date(2012, endMonth - 1, endDay);
        }

        var date = new Date(2012, month - 1, day);
        if (isBetweenDate(3, 21, 4, 19)) {
            return '白羊座';
        } else if (isBetweenDate(4, 20, 5, 20)) {
            return '金牛座';
        } else if (isBetweenDate(5, 21, 6, 21)) {
            return '双子座';
        } else if (isBetweenDate(6, 22, 7, 22)) {
            return '巨蟹座';
        } else if (isBetweenDate(7, 23, 8, 22)) {
            return '狮子座';
        } else if (isBetweenDate(8, 23, 9, 22)) {
            return '处女座';
        } else if (isBetweenDate(9, 23, 10, 23)) {
            return '天秤座';
        } else if (isBetweenDate(10, 24, 11, 22)) {
            return '天蝎座';
        } else if (isBetweenDate(11, 23, 12, 21)) {
            return '射手座';
        } else if (isBetweenDate(12, 22, 12, 31) || isBetweenDate(1, 1, 1, 19)) {
            return '摩羯座';
        } else if (isBetweenDate(1, 20, 2, 18)) {
            return '水瓶座';
        } else if (isBetweenDate(2, 19, 3, 20)) {
            return '双鱼座';
        }
    },
    /**
     * useful keys of options include:
     * $floatPanel, beforeShow, doSubmit, validateInput, postInit
     * @param options
     * @returns {{show: show, init: init}}
     */
    buildFloatPanel: function (options) {
        var utils = this;
        var floatPanel = ({
            get$OkButton: function () {
                return this.$floatPanel.find('button.ok');
            },
            get$CancelButton: function () {
                return this.$floatPanel.find('button.cancel');
            },
            show: function () {
                if (this['beforeShow']) {
                    this['beforeShow'](arguments);
                }
                utils.showTransparentBackground(1);
                utils.scrollToVerticalCenter(this.$floatPanel.fadeIn(200).focusFirstTextInput());
            },
            init: function () {
                this.$floatPanel.setDefaultButtonByClass('ok');
                var self = this;
                this.get$OkButton().click(function (e) {
                    e.preventDefault();
                    if (!self['doSubmit']) {
                        console.log('no submit');
                        return false;
                    }
                    if (self['validateInput']) {
                        if (self['validateInput']()) {
                            self['doSubmit']();
                        }
                    } else {
                        self['doSubmit']();
                    }
                    return false;
                });
                this.get$CancelButton().click(function (e) {
                    self.$floatPanel.fadeOut(200, function () {
                        utils.hideTransparentBackground();
                    });
                    e.preventDefault();
                });
                if (this['postInit']) {
                    this['postInit']();
                }
                return this;
            }
        });
        return this.extendsObject(floatPanel, options).init();
    },
    /**
     * method to extends certain object
     * @param object object to extend
     * @param properties properties that will be added to object
     */
    extendsObject: function (object, properties) {
        for (var key in properties) {
            if (properties.hasOwnProperty(key)) {
                object[key] = properties[key];
            }
        }
        return object;
    },
    /**
     * Patch of bug of firefox.
     * In firefox, if we use "javascript:void(XXX)" in map area,
     * problem will happen
     */
    patchMapAreaBug: function () {
        if (JSUtils.isFirefox()) {
            $('map area').filter(function () {
                return this.href != null && this.href.match(/^javascript:void\(/);
            }).click(function (e) {
                e.preventDefault();
                var code = this.href.substring("javascript:void(".length, this.href.length - 1);
                if (code.match(/\(\)$/)) {
                    code = code.replace(/\(\)$/, '');
                    window[code]();
                }
            });
        }
    }
};

/**
 * query plugins
 */
jQuery.fn.dataOptions = function (key, newValue) {
    var dataOptions = {};
    var dataOptionsString = this.attr('data-options');
    if (dataOptionsString != null) {
        dataOptionsString = $.trim(dataOptionsString);
        var dataOptionsArray = dataOptionsString.split(',');
        for (var i = 0, len = dataOptionsArray.length; i < len; i++) {
            var keyValuePair = dataOptionsArray[i].split(':');
            if (keyValuePair.length == 1) {
                continue;
            }

            var value = keyValuePair[1];
            if (value == '') {
                value = null;
            }
            value = $.trim(value);
            dataOptions[keyValuePair[0]] = value;
        }
    }
    if (newValue == null) {
        if (key == null) {
            return dataOptions;
        } else {
            return dataOptions[key];
        }
    } else {
        dataOptions[key] = newValue;
        dataOptionsString = '';
        for (var keyInOptions in dataOptions) {
            if (!dataOptions.hasOwnProperty(keyInOptions)) {
                continue;
            }
            if (dataOptionsString != null) {
                dataOptionsString += ',';
            }
            dataOptionsString += keyInOptions + ':' + dataOptions[keyInOptions];
        }
        this.attr('data-options', dataOptionsString);
        return this;
    }
    //return dataOptions;
};

jQuery.fn.parseIntegerInId = function () {
    var id = this.attr('id');
    if (id) {
        return parseInt(id.replace(/\D/g, ''));
    } else {
        return null;
    }
};

/**
 * In Chinese input method, keyup event is invalid.
 * in this case, we can use this method to catch value change event
 * @param callback method to call after value change
 */
jQuery.fn.monitorValue = function (callback) {
    var time = null;
    var self = this;
    var oldValue = self.val();
    this.focus(function () {
        if (time) {
            clearInterval(time);
        }
        oldValue = self.val();
        time = setInterval(function () {
            if (self.val() != oldValue) {
                oldValue = self.val();
                callback(oldValue);
            }
        }, 200);
    });
    this.blur(function () {
        if (time) {
            clearInterval(time);
            time = null;
        }
    });
    return this;
};

jQuery.fn.focusOrSelect = function () {
    var value = this.val();
    if (value != null && value != '') {
        this.select();
    } else {
        this.focus();
    }
    return this;
};

jQuery.fn.getParentByTagName = function (tagName) {
    if (!tagName) {
        return null;
    }

    var $parent = this.parent();
    while (true) {
        if ($parent.size() == 0 || $parent.is('body') || $parent.is('html')) {
            return null;
        }
        if ($parent.is(tagName)) {
            return $parent;
        }
        $parent = $parent.parent();
    }
};

jQuery.fn.getParentByTagNameAndClass = function (tagName, style) {
    var $parent = this.getParentByTagName(tagName);
    while (true) {
        if ($parent == null) {
            return null;
        }
        if ($parent.hasClass(style)) {
            return $parent;
        }
        $parent = $parent.getParentByTagName(tagName);
    }
};

/**
 * move an element before its previous element
 */
jQuery.fn.moveToPrev = function () {
    var $prev = this.prev();
    if ($prev.size() > 0) {
        this.insertBefore($prev);
    }
};

/**
 * move a element after its next element
 */
jQuery.fn.moveToNext = function () {
    var $next = this.next();
    if ($next.size() > 0) {
        this.insertAfter($next);
    }
};

jQuery.fn.trimVal = function () {
    return $.trim(this.val());
};

jQuery.fn.trimText = function () {
    return $.trim(this.text());
};

jQuery.fn.trimText = function () {
    return $.trim(this.text());
};

jQuery.fn.getInputByName = function (name) {
    return this.find('input[name=' + name + ']');
};

jQuery.fn.getButtonByName = function (name) {
    return this.find('button[name=' + name + ']');
};

jQuery.fn.setInputValue = function (inputName, inputValue) {
    var $target = this.getInputByName(inputName);
    var type = $target.attr('type');
    if (type == 'text' || type == 'password' || type == 'hidden') {
        $target.val(inputValue);
    } else if (type == 'checkbox' || type == 'radio') {
        $target.each(function () {
            this.checked = inputValue;
        });
    }
    return this;
};

jQuery.fn.getVisible$Input = function () {
    return this.find('input[type=text],input[type=password]');
};

jQuery.fn.setDefaultButtonByJQueryElement = function ($element) {
    this.getVisible$Input().keydown(function (e) {
        if (JSUtils.isEnterKeyCode(e.keyCode) && !$element.attr('disabled')) {
            $element.trigger('click');
        }
    });
    return this;
};

jQuery.fn.setDefaultButtonByClass = function (elementClass) {
    return this.setDefaultButtonByJQueryElement(this.find('button.' + elementClass));
};

jQuery.fn.setDefaultButtonById = function (elementId) {
    return this.setDefaultButtonByJQueryElement($('#' + elementId));
};

jQuery.fn.scrollToTop = function () {
    JSUtils.scrollTop(this);
    return this;
};

jQuery.fn.focusFirstTextInput = function () {
    this.getVisible$Input().first().focusOrSelect();
    return this;
};

jQuery.fn.twinkle = function (times) {
    if (times == null || times == undefined) {
        times = 2;
    }
    this.show();
    for (var i = 0; i < times; i++) {
        this.fadeOut(100).fadeIn(100);
    }
};

jQuery.fn.onAnimationEnd = function (callback) {
    this.one('webkitAnimationEnd oanimationend msAnimationEnd animationend', callback);
};

/**
 * show an element for several seconds, then hide it again
 * @param milliSeconds how many milliSeconds to show element
 */
jQuery.fn.showForAWhile = function (milliSeconds) {
    this.show();
    var self = this;
    setTimeout(function () {
        self.fadeOut(500);
    }, milliSeconds)
};

jQuery.fn.setBackgroundImage = function (backgroundImage) {
    if (backgroundImage == null || $.trim(backgroundImage) == '') {
        this.css('background-image', 'none');
    } else {
        this.css('background-image', 'url("' + backgroundImage + '")');
    }
};
