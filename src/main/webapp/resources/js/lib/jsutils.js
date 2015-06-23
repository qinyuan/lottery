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
        var $document = $(window);
        var key = 'scroll-status-record_' + location.pathname.replace(/\..*$/g, '');
        var value = $.cookie(key);
        if (value) {
            document.documentElement.scrollTop = value;
            $document.scrollTop(value);
        }
        $document.scroll(function () {
            $.cookie(key, $document.scrollTop());
        });
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
    }
};

/**
 * query plugins
 */
jQuery.fn.dataOptions = function () {
    var dataOptions = {};
    var dataOptionsString = this.attr('data-options');
    if (dataOptionsString == null) {
        return dataOptions;
    }

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
    return dataOptions;
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
};

jQuery.fn.getParentByTagName = function (tagName) {
    if (!tagName) {
        return null;
    }

    var parent = this.parent();
    while (true) {
        if (parent.size() == 0 || parent.is('body') || parent.is('html')) {
            return null;
        }
        if (parent.is(tagName)) {
            return parent;
        }
        parent = parent.parent();
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
