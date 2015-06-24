;
(function () {
    $('div.main-body div.link-list div.content div.close-image').click(function () {
        $.post('admin-commodity-link-delete.json', {
            id: $(this).parent().dataOptions()['id']
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
            var maps = window['commodityMaps'];
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

    var $linkInputDiv = $('#linkInputDiv');

    function showLinkInput(id, href, comment) {
        JSUtils.showTransparentBackground(5);
        $('#mapId').val(id);
        $('#mapHref').val(href);
        $('#mapComment').val(comment);
        $linkInputDiv.fadeIn(250).find('input[type=text]').eq(0).focusOrSelect();
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
        var $mapHref = $('#mapHref');
        var href = $.trim($mapHref.val());
        if (href == '') {
            alert('目标链接不能为空');
            $mapHref.focusOrSelect();
            return;
        }

        var $mapComment = $('#mapComment');
        var comment = $.trim($mapComment.val());
        if (comment == '') {
            alert('备注不能为空');
            $mapComment.focusOrSelect();
            return;
        }

        var id = $('#mapId').val();
        if (id) {
            $.post('admin-commodity-link-edit.json', {
                'id': id,
                'href': href,
                'comment': comment
            }, JSUtils.normalAjaxCallback);
        } else {
            var commodityId = $.url.param('commodityId');
            var coordinate = rectangle.getCoordinate();
            $.post('admin-commodity-link-add.json', {
                'commodityId': commodityId,
                'xStart': coordinate.x1,
                'yStart': coordinate.y1,
                'xEnd': coordinate.x2,
                'yEnd': coordinate.y2,
                'href': href,
                'comment': comment
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