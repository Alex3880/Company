<%@ taglib prefix="sp" uri="http://www.springframework.org/tags" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags/form" %>
<html>
<head>
    <title>Admin Panel</title>
    <link href="<c:url value="/resources/css/general.css" />" rel="stylesheet">
    <script src="<c:url value="/resources/js/jquery-3.0.0.min.js"/> "></script>

    <script src="<c:url value="/resources/js/adminGeneral.js"/> "></script>
    <script src="<c:url value="/resources/js/customer.js"/> "></script>
    <script src="<c:url value="/resources/js/project.js"/> "></script>
    <script src="<c:url value="/resources/js/employers.js"/> "></script>
</head>
<body>
<div id="container">
    <div id="header">
        <h1>Admin</h1>
        <a id="exit" href="logout">exit</a>
    </div>


    <div id="menu">

        <ul>
            <li><a href="#" onclick="showDash()">Dashboard</a></li>
            <li><a href="#" onclick="showCus()">Customers</a></li>
            <li><a href="#" onclick="showProj()">Projects</a></li>
            <li><a href="#" onclick="showEmp()">Employers</a></li>
        </ul>

    </div>
    <div id="content">
        <div id="dashboard"></div>
        <div id="customers">
            <div id="showListCus"></div>
            <br/>

            <h2>Customers Redactor</h2>
            <form id="slick-add" method="post">
                <table>
                    <tr>
                        <th>Name :</th>
                        <th>
                            <input id="idcus" class="afterClear" name="id" type="password"/>
                            <input id="namecus" class="afterClear" name="name" type="text" placeholder="admin"/>
                        </th>
                    </tr>
                    <tr>
                        <th>Login :</th>
                        <th>
                            <input id="logincus" class="afterClear" name="login" type="email"
                                   placeholder="admin@example.com"/>
                        </th>
                    </tr>
                    <tr>
                        <th>Password :</th>
                        <th>
                            <input id="passcus" class="afterClear" name="password" type="password"
                                   placeholder="Password"/>
                        </th>
                    </tr>
                    <tr>
                        <th>
                            <input id="addcus" type="button" onclick="AjaxFormCustomer('slick-add','addCustomer')"
                                   value="add"/>
                            <input id="editcus" type="button" onclick="AjaxFormCustomer('slick-add','editCustomer')"
                                   value="edit"/>
                        </th>
                        <th>

                        </th>
                    </tr>
                </table>
                <pre id="err"></pre>
            </form>
        </div>
        <div id="projects">
            <div id="showListProj"></div>
            <br/>

            <h2>Project Redactor</h2>
            <form id="formProj" method="post">
                <table>
                    <tr>
                        <th>Name :</th>
                        <th>
                            <input id="idproj" class="afterClear" name="idproj" type="password"/>
                            <input id="nameproj" class="afterClear" name="nameproj" type="text" placeholder="name"/>
                        </th>
                    </tr>
                    <tr>
                        <th>Customer :</th>
                        <th id="selectCustomer">
                        </th>
                    </tr>
                    <tr>
                        <th>Begin date :</th>
                        <th>
                            <input id="datebeginproj" class="afterClear" name="datebeginproj" type="text"
                                   placeholder="yyyy-mm-dd"/>
                        </th>
                    </tr>
                    <tr>
                        <th>End date :</th>
                        <th>
                            <input id="dateendproj" class="afterClear" name="dateendproj" type="text"
                                   placeholder="yyyy-mm-dd"/>
                        </th>
                    </tr>
                    <tr>
                        <th>Manager :</th>
                        <th id="selectManager">
                        </th>
                    </tr>
                    <tr>
                        <th>
                            <input id="addproj" type="button" onclick="AjaxFormProject('addProject')"
                                   value="add"/>
                            <input id="editproj" type="button" onclick="AjaxFormProject('editProject')"
                                   value="edit"/>
                        </th>
                        <th>

                        </th>
                    </tr>
                </table>
                <pre id="errproj"></pre>
            </form>
        </div>
        <div id="employers">

            <div id="showListEmp"></div>
            <br/>

            <h2>Employee Redactor</h2>
            <form id="formEmp" method="post">
                <table>
                    <tr>
                        <th>Name :</th>
                        <th>
                            <input id="idEmp" class="afterClear" name="idEmp" type="password"/>
                            <input id="nameEmp" class="afterClear" name="nameEmp" type="text" placeholder="John"/>
                        </th>
                    </tr>
                    <tr>
                        <th>Login :</th>
                        <th>
                            <input id="loginEmp" class="afterClear" name="loginEmp" type="text"
                                   placeholder="admin@example.com"/>
                        </th>
                    </tr>
                    <tr>
                        <th>Password :</th>
                        <th>
                            <input id="passEmp" class="afterClear" name="passEmp" type="password"
                                   placeholder="password"/>
                        </th>
                    </tr>
                    <tr>
                        <th>Last Name :</th>
                        <th>
                            <input id="lastNameEmp" class="afterClear" name="lastNameEmp" type="text"
                                   placeholder="Johnson"/>
                        </th>
                    </tr>
                    <tr>
                        <th>Skill :</th>
                        <th id="selectSkill">
                        </th>
                    </tr>
                    <tr>
                        <th>
                            <input id="addemp" type="button" onclick="AjaxFormEmployee('addEmployee')"
                                   value="add"/>
                            <input id="editemp" type="button" onclick="AjaxFormEmployee('editEmployee')"
                                   value="edit"/>
                        </th>
                        <th>

                        </th>
                    </tr>
                </table>
                <pre id="errEmp"></pre>
            </form>

        </div>
    </div>

    <div id="footer">Footer
    </div>
</div>
</body>
</html>
