;
(function () {
    $('div.main-body div.link-list div.content div.close-image').click(function () {
        $.post('admin-image-map-delete.json', {
            id: $(this).parent().dataOptions()['id'],
            relateType: window['relateType']
        }, JSUtils.normalAjaxCallback);
    });
    $('div.main-body div.link-list div.content div.edit-image').click(function () {
        var $parent = $(this).parent();
        var id = $parent.dataOptions()['id'];
        var href = $parent.find('input[name=href]').val();
        var comment = $.trim($parent.find('div.comment').text());
        showLinkInput(id, href, comment);
    });
    $('div.main-body div.link-list div.content div.comment').click(function () {
        var $this = $(this);
        var id = $this.parent().dataOptions()['id'];

        if ($this.hasClass('selected')) {
            $this.removeClass('selected');
            demoRectangle.hide(id);
        } else {
            var maps = window['imageMaps'];
            for (var i = 0, len = maps.length; i < len; i++) {
                var map = maps[i];
                if (map['id'] == id) {
                    demoRectangle.show(id, map['xStart'], map['yStart'], map['xEnd'], map['yEnd']);
                    $this.addClass('selected');
                    break;
                }
            }
        }
    });

    var demoRectangle = {
        show: function (id, x1, y1, x2, y2) {
            $('<div class="demoRectangle" id="demoRectangle' + id + '"></div>')
                .appendTo('div.main-body div.image').css({
                    'left': x1,
                    'top': y1,
                    'width': x2 - x1,
                    'height': y2 - y1,
                    'display': 'block'
                });
        },
        hide: function (id) {
            $('#demoRectangle' + id).remove();
        }
    };

    var rectangle = {
        _xStart: null,
        _yStart: null,
        _xEnd: null,
        _yEnd: null,
        _lock: false,
        $rectangle: $('#rectangle'),
        lock: function () {
            this._lock = true;
        },
        start: function (x, y) {
            this._xStart = x;
            this._yStart = y;
            this._xEnd = null;
            this._yEnd = null;
        },
        startByEvent: function (e) {
            var offset = JSUtils.getOffsetByEvent(e);
            this.start(offset.offsetX, offset.offsetY);
        },
        end: function (x, y) {
            this._xEnd = x;
            this._yEnd = y;

            return this._draw();
        },
        endByEvent: function (e) {
            if (this._lock) {
                return false;
            }
            var offset = JSUtils.getOffsetByEvent(e);
            return this.end(offset.offsetX, offset.offsetY);
        },
        getCoordinate: function () {
            return {
                x1: Math.min(this._xStart, this._xEnd),
                y1: Math.min(this._yStart, this._yEnd),
                x2: Math.max(this._xStart, this._xEnd),
                y2: Math.max(this._yStart, this._yEnd)
            };
        },
        _draw: function () {
            if (this._xStart == null || this._yStart == null
                || this._xEnd == null || this._yEnd == null) {
                return false;
            }

            if (this._xStart == this._xEnd || this._yStart == this._yEnd) {
                return false;
            }

            if (this._xStart < 0) {
                this._xStart = 0;
            }
            if (this._yStart < 0) {
                this._yStart = 0;
            }
            if (this._xEnd < 0) {
                this._xEnd = 0;
            }
            if (this._yEnd < 0) {
                this._yEnd = 0;
            }

            var coordinate = this.getCoordinate();
            this.$rectangle.css({
                'left': coordinate.x1,
                'top': coordinate.y1,
                'width': coordinate.x2 - coordinate.x1,
                'height': coordinate.y2 - coordinate.y1,
                'display': 'block'
            });
            return true;
        },
        hide: function () {
            this._xStart = null;
            this._yStart = null;
            this._xEnd = null;
            this._yEnd = null;
            this.$rectangle.css({
                'left': 0,
                'top': 0,
                'width': 0,
                'height': 0,
                'display': 'none'
            });
            this._lock = false;
        }
    };

    var $linkInputDiv = $('#linkInputDiv').setDefaultButton('addSubmit');
    $linkInputDiv.get$Id = function () {
        return $('#mapId');
    };
    $linkInputDiv.get$Href = function () {
        return $('#mapHref');
    };
    $linkInputDiv.get$Comment = function () {
        return $('#mapComment');
    };
    var $buildInHrefCheckboxes = $linkInputDiv.find('div.build-in-href input[type=checkbox]');
    $buildInHrefCheckboxes.unCheckAll = function () {
        $buildInHrefCheckboxes.each(function () {
            this.checked = false;
        });
    };
    $buildInHrefCheckboxes.getCheckedInstance = function () {
        return $buildInHrefCheckboxes.filter(function () {
            return this.checked;
        });
    };
    $buildInHrefCheckboxes.click(function () {
        if (this.checked) {
            $buildInHrefCheckboxes.unCheckAll();
            this.checked = true;
            $linkInputDiv.get$Href().attr('disabled', true);
            $linkInputDiv.get$Comment().focusOrSelect();
        } else {
            $linkInputDiv.get$Href().attr('disabled', false).focusOrSelect();
        }
    });

    function showLinkInput(id, href, comment) {
        JSUtils.showTransparentBackground(5);
        $buildInHrefCheckboxes.getCheckedInstance().trigger('click');
        $linkInputDiv.get$Id().val(id);
        $linkInputDiv.get$Comment().val(comment);
        $linkInputDiv.get$Href().val(href);
        $buildInHrefCheckboxes.each(function () {
            if ($(this).prev().val() == href) {
                $linkInputDiv.get$Href().val('');
                $(this).trigger('click');
                return false;
            }
        });
        $linkInputDiv.fadeIn(250).focusFirstTextInput();
    }

    function hideLinkInput() {
        $linkInputDiv.fadeOut(250, function () {
            JSUtils.hideTransparentBackground();
        });
    }

    $('div.main-body div.image').mousedown(function (e) {
        rectangle.startByEvent(e);
    }).mouseup(function (e) {
        if (rectangle.endByEvent(e)) {
            rectangle.lock();
            showLinkInput();
        } else {
            rectangle.hide();
        }
    }).mouseout(function (e) {
        if (rectangle.endByEvent(e)) {
            rectangle.lock();
            showLinkInput();
        }
    }).mousemove(function (e) {
        rectangle.endByEvent(e);
    });

    $('#addCancel').click(function () {
        rectangle.hide();
        hideLinkInput();
    });
    $('#addSubmit').click(function () {
        var href;
        if ($linkInputDiv.get$Href().attr('disabled')) {
            var $checkedCheckbox = $buildInHrefCheckboxes.getCheckedInstance();
            href = $checkedCheckbox.prev().val();
        } else {
            href = $linkInputDiv.get$Href().trimVal();
            if (href == '') {
                alert('目标链接不能为空');
                $linkInputDiv.get$Href().focusOrSelect();
                return;
            }
        }

        var comment = $linkInputDiv.get$Comment().trimVal();
        if (comment == '') {
            alert('备注不能为空');
            $linkInputDiv.get$Comment().focusOrSelect();
            return;
        }

        var id = $linkInputDiv.get$Id().trimVal();
        if (id) {
            $.post('admin-image-map-edit.json', {
                'id': id,
                'href': href,
                'comment': comment,
                'relateType': window['relateType']
            }, JSUtils.normalAjaxCallback);
        } else {
            var relateId = $.url.param('id');
            var coordinate = rectangle.getCoordinate();
            $.post('admin-image-map-add.json', {
                'relateId': relateId,
                'xStart': coordinate.x1,
                'yStart': coordinate.y1,
                'xEnd': coordinate.x2,
                'yEnd': coordinate.y2,
                'href': href,
                'comment': comment,
                'relateType': window['relateType']
            }, JSUtils.normalAjaxCallback);
        }
    });

    /*
     * adjust image-cover
     */
    setTimeout(function () {
        var imageWidth = JSUtils.getImageWidth($('div.main-body div.image img'));
        if (imageWidth < 1000) {
            $('div.main-body div.image').css({
                'width': imageWidth,
                'margin-left': 'auto',
                'margin-right': 'auto'
            })
        }
    }, 1000);
})();