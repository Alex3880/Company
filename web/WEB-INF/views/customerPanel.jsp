<%@ taglib prefix="sp" uri="http://www.springframework.org/tags" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags/form" %>
<html>
<head>
    <title>Admin Panel</title>
    <link href="<c:url value="/resources/css/general.css" />" rel="stylesheet">
    <script src="<c:url value="/resources/js/jquery-3.0.0.min.js"/> "></script>
    <script src="<c:url value="https://www.gstatic.com/charts/loader.js"/> "></script>
    <script src="<c:url value="/resources/js/graph.js"/> "></script>
    <script src="<c:url value="/resources/js/customerGeneral.js"/> "></script>
    <script src="<c:url value="/resources/js/project.js"/> "></script>
</head>
<body>
<div id="container">
    <div id="header">
        <h1>Hello ${sessionScope.name}!</h1>
        <a id="exit" href="logout">exit</a>
    </div>


    <div id="menu">

        <ul>
            <li><a href="#" onclick="showDash()">Dashboard</a></li>
        </ul>

    </div>
    <div id="content">
        <div id="dashboard">
            <div id="showListByCustomer"></div>
            <div id="chartSprint"></div>
        </div>

    </div>

    <div id="footer">Footer
    </div>
</div>
</body>
</html>
