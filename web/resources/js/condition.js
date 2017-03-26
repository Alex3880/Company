function conditionsForTasks() {
    jQuery.ajax({
        url: 'conditions',
        type: "GET",
        dataType: "json",
        success: function (response) {
            var list = response;
            var l = response.length;
            var str = '';
            for (var i = 0; i < l; i++) {
                str += '<option value=' + list[i].id + '>' + list[i].name + '</option>';
            }
            $('#selectCondition').html(str);
        },
        error: function (response) {
            console.log('error with conditions');
        }
    });
}