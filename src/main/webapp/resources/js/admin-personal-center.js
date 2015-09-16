var userId = $.url.param('id');
var realNameUpdateUrl = 'admin-personal-center-update-real-name.json?id=' + userId;
var passwordUpdateUrl = 'admin-personal-center-update-password.json?id=' + userId;
var emailUpdateUrl = 'admin-personal-center-update-email.json?id=' + userId;
var telUpdateUrl = 'admin-update-tel.json?id=' + userId;
$('#additionalInfoForm').attr('action', 'admin-personal-center-update-additional-info')
    .append('<input type="hidden" name="id" value="' + userId + '"/>');
$('div.main-body div.history.content div.title').text('该用户最近20次登录记录');
