function AjaxFormEmployee(url) {
    jQuery.ajax({
        url: url,
        type: "POST",
        data: jQuery("#formEmp").serialize(),
        success: function (response) {
            $('.afterClear').val('');
            $('#addemp').show();
            $('#editemp').hide();
            $('#errEmp').html(response);
            AjaxTableShowEmployee();
        },
        error: function (response) {
            $('#errEmp').html('Error while sending request');
        }
    });
}

function AjaxTableShowEmployee() {
    jQuery.ajax({
        url: 'employers', //Адрес подгружаемой страницы
        type: "GET", //Тип запроса
        dataType: "json", //Тип данных
        success: function (response) { //Если все нормально
            var list = response;
            var l = response.length;
            var str = '<h2>Employers</h2><table>';
            str += '<tr>' +
                '<th>ID</th>' +
                '<th>Name</th>' +
                '<th>Login</th>' +
                '<th>Password</th>' +
                '<th>Last Name</th>' +
                '<th>ID Skill</th>' +
                '<th>delete</th>' +
                '<th>edit</th>' +
                '</tr>';
            for (var i = 0; i < l; i++) {
                str += '<tr>';
                str += '<td>' + list[i].id + '</td>';
                str += '<td>' + list[i].name + '</td>';
                str += '<td>' + list[i].login + '</td>';
                str += '<td>' + list[i].pass + '</td>';
                str += '<td>' + list[i].lastName + '</td>';
                str += '<td>' + list[i].idSkill + '</td>';
                str += '<th><button id=' + list[i].id + ' onclick=toDelete("deleteEmployee",' + list[i].id + ')>delete</button></th>';
                str += '<th><button id=' + list[i].id + ' onclick=AjaxEmployeeShow(' + list[i].id + ')>edit</button></th>';
                str += '</tr>';
            }
            str += '</table>';
            $('#showListEmp').html(str);
        },
        error: function (response) {
            $('#showListEmp').html('<h2>Empty list</h2>');
        }
    });
}


function AjaxShowSkillsForEmployee() {
    jQuery.ajax({
        url: 'skills', //Адрес подгружаемой страницы
        type: "GET", //Тип запроса
        dataType: "json", //Тип данных
        success: function (response) { //Если все нормально
            var list = response;
            var l = response.length;
            var str = '<select id=skillId name=skillId>';
            for (var i = 0; i < l; i++) {
                str += '<option value=' + list[i].id + '>' + list[i].skillName + '</option>';
            }
            str += '</select>';
            $('#selectSkill').html(str);
        },
        error: function (response) { //Если ошибка
            $('#selectSkill').html('<h2>No Skills</h2>');
        }
    });
}

function AjaxEmployeeShow(idd) {
    jQuery.ajax({
        url: 'employee',
        type: "GET",
        data: ({id: idd}),
        dataType: "json",
        success: function (response) {
            var customer = response;
            $('#idEmp').val(response.id);
            $('#nameEmp').val(response.name);
            $('#loginEmp').val(response.login);
            $('#lastNameEmp').val(response.lastName);
            $('#passEmp').val(response.pass);
            $('#skillId').val(response.idSkill);
            $('#addemp').hide();
            $('#editemp').show();
        },
        error: function (response) { //Если ошибка
        }
    });
}

function empsForTask(idTask) {
    jQuery.ajax({
        url: 'employeeByTaskId',
        type: "GET",
        data: ({idTask: idTask}),
        dataType: "json",
        success: function (response) {
            var list = response;
            var len = list.length;
            var str = '';
            for(var i = 0; i<len;i++){
                str+=list[i].lastName+'<br/>';
            }
            $('#empsForTask'+idTask).html(str);
        },
        error: function (response) {
        }
    });
}

