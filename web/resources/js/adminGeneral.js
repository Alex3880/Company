$(document).ready(function () {

    showDash();
});

function showDash() {
    $('#dashboard').show();
    $('#customers').hide();
    $('#projects').hide();
    $('#employers').hide();
}

function showCus() {
    $('#dashboard').hide();
    $('#customers').show();
    $('#projects').hide();
    $('#idcus').hide();
    $('#editcus').hide();
    $('#employers').hide();
    AjaxTableShow();
}

function showProj() {
    $('#dashboard').hide();
    $('#customers').hide();
    $('#projects').show();
    $('#employers').hide();
    $('#idproj').hide();
    $('#editproj').hide();
    AjaxTableShowProj();
    AjaxShowCustomersForProject();
    AjaxShowManagersForProject();
}

function showEmp() {
    $('#dashboard').hide();
    $('#customers').hide();
    $('#projects').hide();
    $('#employers').show();
    $('#idEmp').hide();
    $('#editemp').hide();
    AjaxTableShowEmployee();
    AjaxShowSkillsForEmployee();
}

function toDelete(urlForm, idd) {
    $.ajax({
        url: urlForm,
        type: "POST",
        data: ({id: idd}),
        success: function (data) {
            $('#ans').html(data);
            AjaxTableShow();
            AjaxTableShowProj();
            AjaxTableShowEmployee();
        },
        error: function (response) {
            $('#err').html('Error while sending request');
        }
    })
}