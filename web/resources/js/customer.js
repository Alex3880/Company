function AjaxFormCustomer(form_id, url) {
    jQuery.ajax({
        url: url, //Адрес подгружаемой страницы
        type: "POST", //Тип запроса
        dataType: "html", //Тип данных
        data: jQuery("#" + form_id).serialize(),
        success: function (response) { //Если все нормально
            $('.afterClear').val('');
            $('#addcus').show();
            $('#editcus').hide();
            $('#err').html(response);
            AjaxTableShow();
        },
        error: function (response) { //Если ошибка
            $('#err').html('Error while sending request');
        }
    });
}

function AjaxTableShow() {
    jQuery.ajax({
        url: 'customers',
        type: "GET",
        dataType: "json",
        success: function (response) {
            var list = response;
            var l = response.length;
            var str = '<h2>Customers</h2><table>';
            str += '<tr>' +
                '<th>ID</th>' +
                '<th>Name</th>' +
                '<th>Login</th>' +
                '<th>Password</th>' +
                '<th>delete</th>' +
                '<th>edit</th>' +
                '</tr>';
            for (var i = 0; i < l; i++) {
                str += '<tr>';
                str += '<td>' + list[i].id + '</td>';
                str += '<td>' + list[i].name + '</td>';
                str += '<td>' + list[i].login + '</td>';
                str += '<td>' + list[i].pass + '</td>';
                str += '<th><button id=' + list[i].id + ' onclick=toDelete("deleteCus",' + list[i].id + ')>delete</button></th>';
                str += '<th><button id=' + list[i].id + ' onclick=AjaxCustomerShow(' + list[i].id + ')>edit</button></th>';
                str += '</tr>';
            }
            str += '</table>';
            $('#showListCus').html(str);
        },
        error: function (response) { //Если ошибка
            $('#showListCus').html('<h2>Empty list</h2>');
        }
    });
}

function AjaxCustomerShow(idd) {
    jQuery.ajax({
        url: 'customer', //Адрес подгружаемой страницы
        type: "GET", //Тип запроса
        data: ({id: idd}),
        dataType: "json", //Тип данных
        success: function (response) { //Если все нормально
            var customer = response;
            $('#idcus').val(response.id);
            $('#namecus').val(response.name);
            $('#logincus').val(response.login);
            $('#passcus').val(response.pass);
            $('#actioncus').val('edit');
            $('#addcus').hide();
            $('#editcus').show();
        },
        error: function (response) { //Если ошибка
        }
    });
}