google.charts.load('current', {'packages': ['gantt']});
google.charts.setOnLoadCallback(drawChart);

function toMilliseconds(hours) {
    return hours * 60 * 60 * 1000;
}

function getSprintList(idProject) {
    var rowList = new Array();
    var list;
    var len;
    jQuery.ajax({
        url: 'sprintDataGraph',
        type: "GET",
        dataType: "json",
        data: {idProject:idProject},
        async: false,
        success: function (response) {
            list = response;
            len = list.length;
        },
        error: function () {
            return null;
        }
    });
    for (var i = 0; i < len; i++) {
        var row = new Array();
        row.push(list[i].id, list[i].name, list[i].name, null, null, toMilliseconds(list[i].fullEstimate), list[i].percent, list[i].dependencyId);
        rowList.push(row);
    }
    return rowList;
}

function drawChart(idProject) {
    var otherData = new google.visualization.DataTable();
    otherData.addColumn('string', 'Task ID');
    otherData.addColumn('string', 'Task Name');
    otherData.addColumn('string', 'Resource');
    otherData.addColumn('date', 'Start');
    otherData.addColumn('date', 'End');
    otherData.addColumn('number', 'Duration');
    otherData.addColumn('number', 'Percent Complete');
    otherData.addColumn('string', 'Dependencies');

    var dataList = getSprintList(idProject);
    otherData.addRows(dataList);

    var options = {
        height: 260,
        width: 800,
        gantt: {
        }
    };

    var chart = new google.visualization.Gantt(document.getElementById('chartSprint'));

    chart.draw(otherData, options);
}