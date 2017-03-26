function showListTasks(idSprint) {
    jQuery.ajax({
        url: 'tasksBySprintId',
        type: "GET",
        data: {idSprint: idSprint},
        dataType: "json",
        success: function (response) {
            var list = response;
            var l = response.length;
            var str = '<h2>Tasks</h2><table>';

            str += '<tr>' +
                '<th>ID</th>' +
                '<th>Name</th>' +
                '<th>Estimate(hours)</th>' +
                '<th>Condition</th>' +
                '<th>Dependency task</th>' +
                '<th>subTask of</th>' +
                '<th>Skill</th>' +
                '<th>Employers</th>' +
                '<th>delete</th>' +
                '<th>edit</th>' +
                '</tr>';
            for (var i = 0; i < l; i++) {
                str += '<tr>';
                for (var j = 0; j < 7; j++) {
                    str += '<td>' + list[i][j] + '</td>';
                }
                str += '<td id=empsForTask'+list[i][0]+'>-not set-</td>';
                str += '<th><button id=' + list[i][0] + ' onclick=toDelete("deleteTask",' + list[i][0] + ')>delete</button></th>';
                str += '<th><button id=' + list[i][0] + ' onclick=editTask(' + list[i][0] + ')>edit</button></th>';
                str += '</tr>';
                empsForTask(list[i][0]);
            }
            str += '</table>';
            $('#showListTask').html(str);
        },
        error: function (response) {
            $('#showListTask').html('<h2>Empty list of tasks</h2>');
        }
    });
}

function manageTask(url) {
    var tempSprint = $('#sprintId').val();
    jQuery.ajax({
        url: url,
        type: "POST",
        data: jQuery("#taskInfo").serialize(),
        success: function (response) {
            $('.afterClear').val('');
            $('#addTask').show();
            $('#editTask').hide();
            $('#errTask').html(response);
            showListTasks(tempSprint);
            tasksForEmpTaskSet(tempSprint);
        },
        error: function (response) {
            $('#errTask').html('Error while sending request');
        }
    });
}

function editTask(idd) {
    jQuery.ajax({
        url: 'task',
        type: "GET",
        data: ({id: idd}),
        dataType: "json",
        success: function (response) {
            $('#idTask').val(response.id);
            $('#nameTask').val(response.name);
            $('#estimate').val(response.idEstimate);
            $('#dependencyTask').val(response.dependencyTask);
            $('#subtask').val(response.isSubtask);
            $('#selectSkill').val(response.idSkill);
            $('#addTask').hide();
            $('#editTask').show();
        },
        error: function (response) {
        }
    });
}

function tasksForEmpTaskSet(idSprint) {
    jQuery.ajax({
        url: 'tasksBySprintId',
        type: "GET",
        data: {idSprint: idSprint},
        dataType: "json",
        success: function (response) {
            var list = response;
            var l = response.length;
            var str = '<option disabled selected>select, please</option>';
            for (var i = 0; i < l; i++) {
                str += '<option value='+list[i][0]+'>'+list[i][1]+'</option>';
            }
            $('#selectTaskForSet').html(str);
        },
        error: function (response) {
            $('#selectTaskForSet').html('');
        }
    });
}

function employeersForEmpTaskSet(){
    var idTask = $('#selectTaskForSet').val();
    jQuery.ajax({
        url: 'employeersByIdSkill',
        type: "GET",
        data: {idTask: idTask},
        dataType: "json",
        success: function (response) {
            var list = response;
            var l = response.length;
            var str = '';
            for (var i = 0; i < l; i++) {
                str += '<option value='+list[i].id+'>'+list[i].lastName+'</option>';
            }
            $('#selectEmployeeForSet').html(str);
        },
        error: function (response) {
            $('#selectEmployeeForSet').html('<option>not exist</option>');
        }
    });
}

function setTaskForEmp() {
    var emp = $('#selectEmployeeForSet').val();
    var task = $('#selectTaskForSet').val();
    var idSprint = $('#sprintId').val();
    jQuery.ajax({
        url: 'setEmpTask',
        type: "POST",
        data: {idEmp: emp,idTask:task},
        dataType: "json",
        success: function (response) {
            $('#errTask').val(response);
            showListTasks(idSprint);
            console.log('succ');
        },
        error: function (response) {
            showListTasks(idSprint);
        }
    });

}