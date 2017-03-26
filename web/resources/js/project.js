function AjaxFormProject(url) {
    jQuery.ajax({
        url: url,
        type: "POST",
        data: jQuery("#formProj").serialize(),
        success: function (response) {
            $('.afterClear').val('');
            $('#addproj').show();
            $('#editproj').hide();
            $('#errproj').html(response);
            AjaxTableShowProj();
        },
        error: function (response) {
            $('#errproj').html('Error while sending request');
        }
    });
}

function AjaxTableShowProj() {
    jQuery.ajax({
        url: 'projects',
        type: "GET",
        dataType: "json",
        success: function (response) {
            var list = response;
            var l = response.length;
            var str = '<h2>Projects</h2><table>';
            str += '<tr>' +
                '<th>ID</th>' +
                '<th>Name</th>' +
                '<th>ID Customer</th>' +
                '<th>Begin date</th>' +
                '<th>End date</th>' +
                '<th>ID Manager</th>' +
                '<th>delete</th>' +
                '<th>edit</th>' +
                '</tr>';
            for (var i = 0; i < l; i++) {
                str += '<tr>';
                str += '<td>' + list[i].id + '</td>';
                str += '<td>' + list[i].name + '</td>';
                str += '<td>' + list[i].idCustomer + '</td>';
                str += '<td>' + list[i].dateBegin + '</td>';
                str += '<td>' + list[i].dateEnd + '</td>';
                str += '<td>' + list[i].idManager + '</td>';
                str += '<th><button id=' + list[i].id + ' onclick=toDelete("deleteProj",' + list[i].id + ')>delete</button></th>';
                str += '<th><button id=' + list[i].id + ' onclick=AjaxProjectShow(' + list[i].id + ')>edit</button></th>';
                str += '</tr>';
            }
            str += '</table>';
            $('#showListProj').html(str);
        },
        error: function (response) {
            $('#showListProj').html('<h2>Empty list</h2>');
        }
    });
}

function AjaxShowCustomersForProject() {
    jQuery.ajax({
        url: 'customers',
        type: "GET",
        dataType: "json",
        success: function (response) {
            var list = response;
            var l = response.length;
            var str = '<select id=cusid name=cusid>';
            for (var i = 0; i < l; i++) {
                str += '<option value=' + list[i].id + '>' + list[i].name + '</option>';
            }
            str += '</select>';
            $('#selectCustomer').html(str);
        },
        error: function (response) {
            $('#selectCustomer').html('<h2>No Customers</h2>');
        }
    });
}

function AjaxShowManagersForProject() {
    jQuery.ajax({
        url: 'managers',
        type: "GET",
        dataType: "json",
        success: function (response) {
            var list = response;
            var l = response.length;
            var str = '<select id=managid name=managid>';
            for (var i = 0; i < l; i++) {
                str += '<option value=' + list[i].id + '>' + list[i].lastName + '</option>';
            }
            str += '</select>';
            $('#selectManager').html(str);
        },
        error: function (response) {
            $('#selectManager').html('<h2>No Managers</h2>');
        }
    });
}

function AjaxProjectShow(idd) {
    jQuery.ajax({
        url: 'project',
        type: "GET",
        data: ({id: idd}),
        dataType: "json",
        success: function (response) {
            var customer = response;
            $('#idproj').val(response.id);
            $('#nameproj').val(response.name);
            $('#cusid').val(response.idCustomer);
            $('#managid').val(response.idManager);
            $('#datebeginproj').val(response.dateBegin);
            $('#dateendproj').val(response.dateEnd);
            $('#addproj').hide();
            $('#editproj').show();
        },
        error: function (response) {
        }
    });
}

function AjaxProjectsShowByManager(idManager) {
    jQuery.ajax({
        url: 'projectsByManagerId',
        type: "GET",
        data:{idManager :idManager},
        dataType: "json",
        success: function (response) {
            var list = response;
            var l = response.length;
            var str = '<h2>My projects</h2><table>';
            str += '<tr>' +
                '<th>ID</th>' +
                '<th>Name</th>' +
                '<th>ID Customer</th>' +
                '<th>Begin date</th>' +
                '<th>End date</th>' +
                '</tr>';
            for (var i = 0; i < l; i++) {
                str += '<tr>';
                str += '<td>' + list[i].id + '</td>';
                str += '<td>' + list[i].name + '</td>';
                str += '<td>' + list[i].idCustomer + '</td>';
                str += '<td>' + list[i].dateBegin + '</td>';
                str += '<td>' + list[i].dateEnd + '</td>';
                str += '</tr>';
            }
            str += '</table>';
            $('#showListProj').html(str);
        },
        error: function (response) {
            $('#showListProj').html('<h2>Empty list</h2>');
        }
    });
}

function AjaxProjectsShowByManagerToForm(idManager) {
    jQuery.ajax({
        url: 'projectsByManagerId',
        type: "GET",
        data:{idManager :idManager},
        dataType: "json",
        success: function (response) {
            var list = response;
            var l = response.length;
            var str = '<h3>Choose project to manage: </h3><select id=selProj name=projId>';
            for (var i = 0; i < l; i++) {
                str += '<option value=' + list[i].id + '>' + list[i].name + '</option>';
            }
            str += '</select>';
            $('#selectProject').html(str);
        },
        error: function (response) {
            $('#selectManager').html('<h2>No Projects</h2>');
        }
    });
}

function showProjectsByCustomer() {
    jQuery.ajax({
        url: 'projectsByCustomerId',
        type: "GET",
        dataType: "json",
        success: function (response) {
            var list = response;
            var l = response.length;
            var str = '<h2>My Projects (choose one to show)</h2><table class=selectableTable>';
            str += '<tr>' +
                '<th>Name</th>' +
                '<th>Date begin</th>' +
                '<th>Date end</th>' +
                '</tr>';
            for (var i = 0; i < l; i++) {
                str += '<tr onclick=drawChart('+list[i].id+')>';
                str += '<td>' + list[i].name + '</td>';
                str += '<td>' + list[i].dateBegin + '</td>';
                str += '<td>' + list[i].dateEnd + '</td>';
                str += '</tr>';
            }
            str += '</table>';
            $('#showListByCustomer').html(str);
        },
        error: function (response) {
            $('#showListByCustomer').html('<h2>Empty list</h2>');
        }
    });
}