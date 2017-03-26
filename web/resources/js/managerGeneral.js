$(document).ready(function () {

    showDash();
    $('#idTask').hide();
    $('#sprintId').hide();
});

function showDash() {
    $('#dashboard').show();
    $('#projects').hide();
    $('#manageProject').hide();
}

function showManage(id) {
    $('#dashboard').hide();
    $('#projects').hide();
    $('#manageProject').show();
    AjaxProjectsShowByManagerToForm(id)
    $('#showListSprint').hide();
    $('#formSprint').hide();
    $('#idSprint').hide();
    $('#editSprint').hide();
    $('#projectId').hide();
    $('#manageTask').hide();
}

function showProj(id) {
    $('#dashboard').hide();
    $('#manageProject').hide();
    $('#projects').show();
    AjaxProjectsShowByManager(id);
}

function toDelete(urlForm, idd) {
    $.ajax({
        url: urlForm,
        type: "POST",
        data: ({id: idd}),
        success: function (data) {
            $('#errSprint').html(data);
            AjaxTableShowSprintByProject();
            showListTasks($('#sprintId').val());

        },
        error: function (response) {
            $('#errSprint').html('Error while sending request');
        }
    })
}

function showToManage() {
    AjaxTableShowSprintByProject();
    var tempId = $('#selProj').val();
    $('#projectId').val(tempId);
    $('#showListSprint').show();
    $('#formSprint').show();
    $('#editTask').hide();

}

function showTasks(idSprint) {
    showListTasks(idSprint);
    skillsForTasks();
    conditionsForTasks();
    $('#sprintId').val(idSprint);
    $('#manageTask').show();
    tasksForEmpTaskSet(idSprint);
    $('#selectEmployeeForSet').html('');


}