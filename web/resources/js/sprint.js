function AjaxFormSprint(url) {
    jQuery.ajax({
        url: url,
        type: "POST",
        data: jQuery("#sprintInfo").serialize(),
        success: function (response) {
            $('.afterClear').val('');
            $('#addSprint').show();
            $('#editSprint').hide();
            $('#errSprint').html(response);
            AjaxTableShowSprintByProject();
        },
        error: function (response) {
            $('#errSprint').html('Error while sending request,\n check, please, entered values');
        }
    });
}

function AjaxTableShowSprintByProject() {
    jQuery.ajax({
        url: 'sprintsByProject', //Адрес подгружаемой страницы
        type: "GET", //Тип запроса
        data: jQuery("#selectProject").serialize(),
        dataType: "json", //Тип данных
        success: function (response) { //Если все нормально
            var list = response;
            var l = response.length;
            var str = '<h2>Sprints (choose one to manage)</h2><table class=selectableTable>';
            str += '<tr>' +
                '<th>ID</th>' +
                '<th>Name</th>' +
                '<th>Previous sprint</th>' +
                '<th>delete</th>' +
                '<th>edit</th>' +
                '</tr>';
            for (var i = 0; i < l; i++) {
                str += '<tr onclick=showTasks('+list[i].id+')>';
                str += '<td>' + list[i].id + '</td>';
                str += '<td>' + list[i].name + '</td>';
                str += '<td>' + list[i].prevSprint + '</td>';
                str += '<th><button id=' + list[i].id + ' onclick=toDelete("deleteSprint",' + list[i].id + ')>delete</button></th>';
                str += '<th><button id=' + list[i].id + ' onclick=AjaxSprintShow(' + list[i].id + ')>edit</button></th>';
                str += '</tr>';
            }
            str += '</table>';
            $('#showListSprint').html(str);
        },
        error: function (response) { //Если ошибка
            $('#showListSprint').html('<h2>Empty list</h2>');
        }
    });
}


function AjaxSprintShow(idd) {
    jQuery.ajax({
        url: 'sprint',
        type: "GET",
        data: ({id: idd}),
        dataType: "json",
        success: function (response) {
            var customer = response;
            $('#idSprint').val(response.id);
            $('#nameSprint').val(response.name);
            $('#projectId').val(response.idProject);
            $('#prevSprint').val(response.prevSprint);
            $('#addSprint').hide();
            $('#editSprint').show();
        },
        error: function (response) { //Если ошибка
        }
    });
}