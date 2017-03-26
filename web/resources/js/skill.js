function skillsForTasks() {
    jQuery.ajax({
        url: 'skills',
        type: "GET",
        dataType: "json",
        success: function (response) {
            var list = response;
            var l = response.length;
            var str = '';
            for (var i = 0; i < l; i++) {
                str += '<option value=' + list[i].id + '>' + list[i].skillName + '</option>';
            }
            $('#selectSkill').html(str);
        },
        error: function (response) {
            console.log('error with skills');
        }
    });
}