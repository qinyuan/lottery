/**
 * This file create some useful function for javascript
 */
var JSUtils = {
    /**
     * notice that we can use new Date("2012-01-01") in IE8,
     * so we use this function to ensure compatibility to IE8
     * @param arg date string or timestamp
     * @returns {Date}
     */
    newDate: function
        (arg) {
        if (this.isNumber(arg)) {
            return new Date(arg);
        }

        var dateArr = arg.split('-');
        var year = parseInt(dateArr[0]);
        var month = parseInt(dateArr[1] - 1);
        var day = parseInt(dateArr[2]);
        return new Date(year, month, day);
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
    validateEmail: function (email) {
        var pattern = /^([a-zA-Z0-9_\\-\\.])+@([a-zA-Z0-9_-])+(.[a-zA-Z0-9_-])+$/;
        return email && email.match(pattern) != null;
    },
    handlebars: function (templateId, data) {
        var source = $("#" + templateId).html();
        var template = Handlebars.compile(source);
        if (!data) {
            data = {};
        }
        return template(data);
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
                opacity: 0.7,
                filter: 'alpha(opacity=70)'
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
     * update certain parameter of current url, then return the new url
     * @param paramKey key of parameter to update
     * @param paramValue value of parameter to update
     */
    updateUrlParam: function (paramKey, paramValue) {
        var url = location.href;
        if (url.indexOf('?') < 0) {
            return url + '?' + paramKey + '=' + paramValue;
        }

        var stringArray = url.split('?');
        url = stringArray[0];
        stringArray = stringArray[1].split('&');
        for (var i = 0, len = stringArray.length; i < len; i++) {
            if (stringArray[i].indexOf(paramKey + '=') == 0) {
                stringArray[i] = paramKey + '=' + paramValue;
                break;
            }
            if (i == len - 1) {
                stringArray.push(paramKey + '=' + paramValue);
            }
        }
        return url + '?' + stringArray.join('&');
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
};

jQuery.fn.setDefaultButton = function (elementId) {
    this.find('input[type=text]').keydown(function (e) {
        if (JSUtils.isEnterKeyCode(e.keyCode)) {
            $('#' + elementId).trigger('click');
        }
    });
    return this;
};

jQuery.fn.scrollToTop = function () {
    JSUtils.scrollTop(this);
    return this;
};

jQuery.fn.focusFirstTextInput = function () {
    this.find('input[type=text]:first').focusOrSelect();
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
