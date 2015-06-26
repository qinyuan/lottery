var angularUtils = {
    _module: null,
    /**
     * Usage:
     * controller(controllerName, func)
     * or
     * controller(func)
     */
    controller: function () {
        if (!this._module) {
            this._module = angular.module('main', []);
        }
        var argSize = arguments.length;
        if (argSize == 1) {
            this._module.controller('ContentController', ['$scope', '$http', arguments[0]]);
        } else if (argSize >= 2) {
            this._module.controller(arguments[0], ['$scope', '$http', arguments[1]]);
        }
        return this;
    }
};

(function () {
    var errorInfo = $.url.param('errorInfo');
    if (errorInfo) {
        alert(errorInfo);
    }
    var $springLoginForm = $('#springLoginForm');
    $('#springLoginForm div.body div.rememberLogin span').click(function () {
        var checkBox = $(this).parent().find('input[type=checkbox]').get(0);
        checkBox.checked = !checkBox.checked;
    });
    $('#springLoginForm div.title div.close-icon').click(function () {
        $springLoginForm.hide();
        JSUtils.hideTransparentBackground();
    });
    $('#loginNavigationLink').click(function () {
        JSUtils.showTransparentBackground();
        $springLoginForm.fadeIn(500).focusFirstTextInput();
    });

})();
