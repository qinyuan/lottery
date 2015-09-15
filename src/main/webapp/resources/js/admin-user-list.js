;
(function () {
    // codes about send email or system info
    var $openMailForm = $('#openMailForm');
    $openMailForm.click(function () {
        mailForm.show();
    });

    var $openSystemInfoForm = $('#openSystemInfoForm');
    $openSystemInfoForm.click(function () {
        systemInfoForm.show();
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
            return this.editor.getData();
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
            return confirm("确定发送？");
        },
        init: function () {
            this.$form.setDefaultButtonById('submitMail');
            var self = this;
            this.$cancelMail.click(function () {
                self.hide();
            });
            this.$submitMail.click(function () {
                if (self.validate()) {
                    var mailAccountIds = [];
                    self.get$SelectedMailAccounts().each(function () {
                        mailAccountIds.push(parseInt(this.value));
                    });

                    self.$submitMail.text('邮件发送中...');
                    JSUtils.postArrayParams('admin-user-list-send-mail.json', {
                        'mailAccountIds': mailAccountIds,
                        'userIds': users.getIds(),
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
            $('#previewMailButton').click(function () {
                var subject = self.get$Subject().val();
                var content = self.getContent();
                var username = users.getSelectedNames()[0];
                mailPreview.show(username, subject, content);
            });
            return this;
        }
    }).init();

    var systemInfoForm = ({
        $form: $('#systemInfoForm'),
        $cancelSystemInfo: $('#cancelSystemInfo'),
        $submitSystemInfo: $('#submitSystemInfo'),
        editor: CKEDITOR.replace('systemInfoContent', {}),
        hide: function () {
            this.$form.hide();
            JSUtils.hideTransparentBackground();
        },
        show: function () {
            JSUtils.showTransparentBackground(1);
            this.$form.fadeIn(200);
            this.editor.focus();
            JSUtils.scrollToVerticalCenter(this.$form);
        },
        getContent: function () {
            return this.editor.getData();
        },
        validate: function () {
            if ($.trim(this.getContent()) == '') {
                alert('消息正文不能为空');
                this.editor.focus();
                return false;
            }
            return confirm("确定发送？");
        },
        init: function () {
            this.$form.setDefaultButtonById('submitSystemInfo');
            var self = this;
            this.$cancelSystemInfo.click(function () {
                self.hide();
            });
            this.$submitSystemInfo.click(function () {
                if (self.validate()) {
                    self.$submitSystemInfo.text('邮件发送中...');
                    JSUtils.postArrayParams('admin-user-list-send-system-info.json', {
                        'userIds': users.getIds(),
                        'content': self.getContent()
                    }, function (data) {
                        if (data.success) {
                            location.reload();
                        } else {
                            self.$submitSystemInfo.text('确定');
                            alert(data.detail);
                        }
                    });
                }
            });
            $('#previewSystemInfoButton').click(function () {
                var content = self.getContent();
                var username = users.getSelectedNames()[0];
                systemInfoPreview.show(username, content);
            });
            return this;
        }
    }).init();

    var users = ({
        $checkboxes: $('input.select-user'),
        $selectAll: $('input.select-all'),
        get$SelectedCheckboxes: function () {
            return this.$checkboxes.filter(function () {
                return this.checked;
            });
        },
        getIds: function () {
            var userIds = [];
            this.get$SelectedCheckboxes().each(function () {
                userIds.push(parseInt(this.value));
            });
            return userIds;
        },
        getSelectedNames: function () {
            var names = [];
            this.get$SelectedCheckboxes().each(function () {
                var $this = $(this), username;
                if ($this.hasClass('list-mode')) {
                    username = $this.getParentByTagNameAndClass('div', 'user').find('div.username').text();
                } else {
                    username = $this.getParentByTagName('tr').find('td.username').text();
                }
                names.push(username);
            });
            return names;
        },
        getSelectedCount: function () {
            return this.get$SelectedCheckboxes().size();
        },
        updateButtonStatus: function () {
            $openMailForm.attr('disabled', this.getSelectedCount() == 0);
            $openSystemInfoForm.attr('disabled', this.getSelectedCount() == 0);
        },
        init: function () {
            var self = this;
            this.$checkboxes.click(function () {
                self.updateButtonStatus();
                if (self.getSelectedCount() == 0) {
                    self.$selectAll.get(0).checked = false;
                } else if (self.getSelectedCount() == self.$checkboxes.size()) {
                    self.$selectAll.get(0).checked = true;
                }
            });
            this.$selectAll.click(function () {
                var checked = this.checked;
                self.$checkboxes.each(function () {
                    this.checked = checked;
                });
                self.updateButtonStatus();
            });

            return this;
        }
    }).init();

    var mailPreview = ({
        $div: $('#mailPreview'),
        show: function (username, subject, content) {
            // deal with placeholder
            subject = subject.replace("{{user}}", username);
            content = content.replace("{{user}}", username);

            JSUtils.showTransparentBackground(1);
            mailForm.$form.hide();
            this.$div.find('div.subject').text(subject);
            this.$div.find('div.content').html(content);
            this.$div.fadeIn(200);
            JSUtils.scrollToVerticalCenter(this.$div);
        },
        hide: function () {
            this.$div.hide();
            mailForm.$form.fadeIn(200);
        },
        init: function () {
            var self = this;
            $('#cancelMailPreview').click(function () {
                self.hide();
            });
            return this;
        }
    }).init();

    var systemInfoPreview = ({
        $div: $('#systemInfoPreview'),
        show: function (username, content) {
            // deal with placeholder
            content = content.replace("{{user}}", username);

            JSUtils.showTransparentBackground(1);
            systemInfoForm.$form.hide();
            this.$div.find('div.content').html(content);
            this.$div.fadeIn(200);
            JSUtils.scrollToVerticalCenter(this.$div);
        },
        hide: function () {
            this.$div.hide();
            systemInfoForm.$form.fadeIn(200);
        },
        init: function () {
            var self = this;
            $('#cancelSystemInfoPreview').click(function () {
                self.hide();
            });
            return this;
        }
    }).init();

    JSUtils.loadTableFilterEvents('admin-user-list-distinct-values.json', 'admin-user-list-filter.json',
        'admin-user-list-filter-remove.json');
})();
(function () {
    // code about display mode
    $('div.display-mode input').iCheck({
        checkboxClass: 'icheckbox_minimal',
        radioClass: 'iradio_minimal'
    }).on('ifChecked', function () {
        location.href = JSUtils.updateUrlParam('displayMode', this.value);
    });
})();
(function () {
    // code about list mode
    $('div.user-filter > div.content div.activity div.item div.icon').click(function () {
        var id = $(this).getParentByTagNameAndClass('div', 'item').dataOptions('id');
        var url = 'admin-user-list-delete-lottery-activity-filter.json';
        $.post(url, {filterLotteryActivityId: id}, JSUtils.normalAjaxCallback);
    });

    var $livenessFilter = $('div.user-filter div.content div.liveness').setDefaultButtonById('livenessFilterSubmit');
    var $livenessFilterButton = $livenessFilter.find('button').click(function () {
        var minLiveness = $livenessFilterInput.val();
        if (JSUtils.isNumberString(minLiveness)) {
            location.href = JSUtils.updateUrlParam('minLiveness', minLiveness);
        } else {
            alert('最小爱心值必须为数字格式');
        }
    });
    var $livenessFilterInput = $livenessFilter.find('input[type=text]').focus(function () {
        $livenessFilterButton.fadeIn(300);
    }).blur(function () {
        $livenessFilterButton.fadeOut(200);
    });

    var activityFilterForm = ({
        $form: $('#lotteryActivityFilterForm'),
        $cancelButton: $('#cancelLotteryActivityFilter'),
        $submitButton: $('#submitLotteryActivityFilter'),
        get$Activities: function () {
            return this.$form.find('div.activities div.activity');
        },
        show: function () {
            JSUtils.showTransparentBackground(1);
            this.$form.fadeIn(200);
            JSUtils.scrollToVerticalCenter(this.$form);
        },
        hide: function () {
            this.$form.fadeOut(200, function () {
                JSUtils.hideTransparentBackground();
            });
        },
        inputName: 'filterLotteryActivityIds',
        init: function () {
            var self = this;
            this.get$Activities().click(function () {
                var $this = $(this);
                if ($this.hasClass('selected')) {
                    $this.removeClass('selected');
                    $this.find('input[type=hidden]').remove();
                } else {
                    $this.addClass('selected');
                    var id = $this.dataOptions('id');
                    $this.append('<input type="hidden" name="' + self.inputName + '" value="' + id + '"/>');
                }
            });
            this.$cancelButton.click(function () {
                self.hide();
            });
            this.$submitButton.click(function () {
                if (self.$form.find('input[name=' + self.inputName + ']').size() == 0) {
                    alert('未选择任何一期活动');
                } else {
                    var url = 'admin-user-list-add-lottery-activity-filters.json';
                    $.post(url, self.$form.serialize(), JSUtils.normalAjaxCallback);
                }
            });
            return this;
        }
    }).init();

    $('div.user-filter > div.content div.activity button').click(function () {
        activityFilterForm.show();
    });

    $('div.user-list div.content div.list-body div.user > div').filter(function () {
        return !$(this).hasClass('edit');
    }).click(function (e) {
        if (!$(e.target).is('input')) {
            $(this).parent().find('input[type=checkbox]').trigger('click');
        }
    }).hover(function () {
        $(this).getParentByTagNameAndClass('div', 'user').find('>div').filter(function () {
            return !$(this).hasClass('edit');
        }).addClass('mouse-over');
    }, function () {
        $(this).getParentByTagNameAndClass('div', 'user').find('>div').filter(function () {
            return !$(this).hasClass('edit');
        }).removeClass('mouse-over');
    });
})();
(function () {
    // code about user filter
    $('div.user-list form.search div.icon img').click(function () {
        $(this).getParentByTagName('form').get(0).submit();
    });
    $('div.user-list form.search input').focusOrSelect();
})();
$('#statisticLink').addClass('emphasize');
