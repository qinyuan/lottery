var simpleFloatPanel = {
    $parent: null,
    show: function (parentId) {
        JSUtils.showTransparentBackground(1);
        this.$div.fadeIn(200);
        if (parentId) {
            this.$parent = $('#' + parentId);
            this.$parent.hide();
        }
    },
    hide: function () {
        this.$div.hide();
        if (this.$parent) {
            this.$parent.show();
            this.$parent = null;
        } else {
            JSUtils.hideTransparentBackground();
        }
    },
    init: function () {
        var self = this;
        this.$div.find('div.close-icon').click(function () {
            self.hide();
        });
        JSUtils.isFunction(this.postInit) && this.postInit();
        return this;
    }
};
var subscribe = (JSUtils.cloneAndExtendObject(simpleFloatPanel, {
    $div: $('#subscribe'),
    setEmail: function (email) {
        this.$to.val(email);
    },
    getEmail: function () {
        return this.$to.val();
    },
    postInit: function () {
        this.$to = this.$div.getInputByName('to');
        var self = this;
        this.$div.find('div.button button[type=submit]').click(function () {
            if (JSUtils.validateEmail(self.getEmail())) {
                $.post('update-receive-mail.json', {'receiveMail': true});
            }
        });
    }
})).init();
