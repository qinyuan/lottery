;(function () {
    // code about birthday select
    function disableSelect($select) {
        $select.attr('disabled', true).css('background-color', '#eeeeee');
    }

    function enableSelect($select) {
        $select.attr('disabled', false).css('background-color', '#ffffff');
    }

    var $year = $('#birthdayYear');
    var $month = $('#birthdayMonth');
    var $day = $('#birthdayDay');
    if ($year.val() == '') {
        disableSelect($month);
        disableSelect($day);
    }
    $year.change(function () {
        if ($year.val() != '') {
            enableSelect($month);
        } else {
            disableSelect($month);
        }
    });
    $month.change(function () {
        if ($month.val() != '' && $year.val() != '') {
            enableSelect($day);
            var dayOfMonth = new Date($year.val(), $month.val(), 0).getDate();
            var $options = $day.find('option');
            var maxDayOfOptions = $options.eq(0).text() == '' ? $options.size() - 1 : $options.size();
            var i;
            if (maxDayOfOptions > dayOfMonth) {
                for (i = 0; i < maxDayOfOptions - dayOfMonth; i++) {
                    $day.find('option:last').remove();
                }
            } else {
                for (i = maxDayOfOptions + 1; i <= dayOfMonth; i++) {
                    $day.append('<option value="' + i + '">' + i + '</option>');
                }
            }
            updateConstellation();
        } else {
            disableSelect($day);
        }
    });
    $day.change(function () {
        updateConstellation();
    });
    function updateConstellation() {
        if ($year.val() != '' && $month.val() != '' && $day.val() != '') {
            var month = parseInt($month.val());
            var day = parseInt($day.val());
            var constellation = JSUtils.getConstellation(month, day);
            if (constellation) {
                $('#constellationSelect').val(constellation);
            }
        }
    }
})();
