;
(function () {
    // codes about send email
    function getSelectedUserCount() {
        return $userSelectCheckboxes.filter(function () {
            return this.checked;
        }).size();
    }

    function updateButtonStatus() {
        $openMailForm.attr('disabled', getSelectedUserCount() == 0);
    }

    var $openMailForm = $('#openMailForm');
    $openMailForm.click(function () {
        mailForm.show();
    });

    var mailForm = ({
        $form: $('#mailForm'),
        $cancelMail: $('#cancelMail'),
        $submitMail: $('#submitMail'),
        editor: CKEDITOR.replace('mailContent', {}),
        hide: function () {
            this.$form.hide();
            JSUtils.hideTransparentBackground();
        },
        show: function () {
            JSUtils.showTransparentBackground(1);
            this.$form.fadeIn(200).focusFirstTextInput();
            JSUtils.scrollToVerticalCenter(this.$form);
            if (this.getSelectedMailAccountCount() == 0) {
                this.get$MailAccounts().first().trigger('click');
            }
        },
        get$Subject: function () {
            return this.$form.getInputByName('subject');
        },
        get$MailAccounts: function () {
            return this.$form.find('td.mail-account button');
        },
        get$SelectedMailAccounts: function () {
            return this.$form.find('td.mail-account input[name=mailAccountIds]');
        },
        getSelectedMailAccountCount: function () {
            return this.get$SelectedMailAccounts().size();
        },
        getContent: function () {
            return    this.editor.getData();
        },
        validate: function () {
            if (this.getSelectedMailAccountCount() == 0) {
                alert('必须至少选择一个发件箱帐号');
                return false;
            }

            if (this.get$Subject().trimVal() == '') {
                alert('邮件标题不能为空');
                this.get$Subject().focusOrSelect();
                return false;
            }
            if ($.trim(this.getContent()) == '') {
                alert('邮件正文不能为空');
                this.editor.focus();
                return false;
            }
            return true;
        },
        init: function () {
            this.$form.setDefaultButton('submitMail');
            var self = this;
            this.$cancelMail.click(function () {
                self.hide();
            });
            this.$submitMail.click(function () {
                if (self.validate()) {
                    var userIds = [];
                    $userSelectCheckboxes.each(function () {
                        if (this.checked) {
                            userIds.push(parseInt(this.value));
                        }
                    });
                    var mailAccountIds = [];
                    self.get$SelectedMailAccounts().each(function () {
                        mailAccountIds.push(parseInt(this.value));
                    });

                    self.$submitMail.text('邮件发送中...');
                    JSUtils.postArrayParams('admin-user-list-send-mail.json', {
                        'mailAccountIds': mailAccountIds,
                        'userIds': userIds,
                        'subject': self.get$Subject().val(),
                        'content': self.getContent()
                    }, function (data) {
                        if (data.success) {
                            location.reload();
                        } else {
                            self.$submitMail.text('确定');
                            alert(data.detail);
                        }
                    });
                }
            });
            this.get$MailAccounts().click(function () {
                var $this = $(this);
                if ($this.hasClass('selected')) {
                    $this.removeClass('selected');
                    while ($this.next().is('input')) {
                        $this.next().remove();
                    }
                } else {
                    $this.addClass('selected');
                    var input = '<input type="hidden" name="mailAccountIds" value="' + $this.dataOptions('id') + '"/>';
                    $this.after(input);
                }
            });
            return this;
        }
    }).init();

    var $userSelectCheckboxes = $('table input.select-user');
    $userSelectCheckboxes.click(function () {
        updateButtonStatus();
        if (getSelectedUserCount() == 0) {
            $selectAll.get(0).checked = false;
        } else if (getSelectedUserCount() == $userSelectCheckboxes.size()) {
            $selectAll.get(0).checked = true;
        }
    });

    var $selectAll = $('table thead th > input.select-all');
    $selectAll.click(function () {
        var checked = this.checked;
        $userSelectCheckboxes.each(function () {
            this.checked = checked;
        });
        updateButtonStatus();
    });
})();
(function () {
    // codes about filter
    var filterPanel = {
        getDistinctItems: function (alias, callback) {
            var href = JSUtils.updateUrlParam('alias', alias);
            href = 'admin-user-list-distinct-values.json?' + href.replace(/^.*\?/, '');
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
                    //var text = item.hasOwnProperty('text') ? item['text'] : '(空白)';
                    var text = item['text'];
                    html += '<div><input type="checkbox" value="' + text + '"';
                    if (item.checked) {
                        html += ' checked';
                    }
                    html += '/><span class="checkbox-label">' + text + '</span></div>'
                }

                // select or deselect all
                html += '<div><a href="javascript:void(0)" onclick="return selectAllValues(this);">全选</a>';
                html += '&nbsp;<a href="javascript:void(0)" onclick="return unSelectAllValues(this);">全不选</a></div>';

                // submit or cancel
                html += '<div><button type="button" class="btn btn-success btn-xs" onclick="return submitFilterValues(this);">确定</button>';
                html += '&nbsp;<button type="button" class="btn btn-default btn-xs" onclick="return cancelFilterValues(this);">取消</button></div>';

                html += '</div>';

                var $html = $(html).appendTo($parent.find('>div'));
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
    cancelFilterValues = function (target) {
        $(target).getParentByTagNameAndClass('div', 'filter').find('>button').trigger('blur');
    };
    submitFilterValues = function (target) {
        var $filter = $(target).getParentByTagNameAndClass('div', 'filter');
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
            $.post('admin-user-list-filter-remove.json', {
                filterField: filterField
            }, JSUtils.normalAjaxCallback);
        } else {
            JSUtils.postArrayParams('admin-user-list-filter.json', {
                filterField: filterField,
                filterValues: filterValues
            }, JSUtils.normalAjaxCallback);
        }
    };

    $('#statisticLink').addClass('emphasize');
})();
function selectAllValues(target) {
    $(target).parent().parent().find('input[type=checkbox]').each(function () {
        this.checked = true;
    });
}

function unSelectAllValues(target) {
    $(target).parent().parent().find('input[type=checkbox]').each(function () {
        this.checked = false;
    });
}

var submitFilterValues, cancelFilterValues;
