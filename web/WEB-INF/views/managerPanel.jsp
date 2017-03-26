<%@ taglib prefix="sp" uri="http://www.springframework.org/tags" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags/form" %>
<html>
<head>
    <title>Manager Panel</title>
    <link href="<c:url value="/resources/css/general.css" />" rel="stylesheet">
    <script src="<c:url value="/resources/js/jquery-3.0.0.min.js"/> "></script>

    <script src="<c:url value="/resources/js/managerGeneral.js"/> "></script>
    <script src="<c:url value="/resources/js/project.js"/> "></script>
    <script src="<c:url value="/resources/js/sprint.js"/> "></script>
    <script src="<c:url value="/resources/js/task.js"/> "></script>
    <script src="<c:url value="/resources/js/employers.js"/> "></script>
    <script src="<c:url value="/resources/js/condition.js"/> "></script>
    <script src="<c:url value="/resources/js/skill.js"/> "></script>
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
            <li><a href="#" onclick="showProj(${sessionScope.id})">Projects</a></li>
            <li><a href="#" onclick="showManage(${sessionScope.id})">Manage Project</a></li>
        </ul>

    </div>
    <div id="content">
        <div id="dashboard"></div>
        <div id="projects">
            <div id="showListProj"></div>
        </div>
        <div id="manageProject">
            <form id="selectProject">
            </form>
            <br/>
            <button id="chooseProject" onclick="showToManage()">manage</button>
            <br/>
            <div id="manageSprint">
                <div id="showListSprint"></div>
                <div id="formSprint">
                    <h2>Add/Edit sprint</h2>
                    <form id="sprintInfo">
                        <table>
                            <tr>
                                <th>Name :</th>
                                <th>
                                    <input id="idSprint" class="afterClear" name="idSprint" type="text"/>
                                    <input id="projectId"  name="projectId" type="text"/>
                                    <input id="nameSprint" class="afterClear" name="nameSprint" type="text" placeholder="admin"/>
                                </th>
                            </tr>
                            <tr>
                                <th>Previous sprint :</th>
                                <th>
                                    <input id="prevSprint" class="afterClear" name="prevSprint" type="text"
                                           placeholder="1234"/>
                                </th>
                            </tr>
                            <tr>
                                <th>
                                    <input id="addSprint" type="button" onclick="AjaxFormSprint('addSprint')"
                                           value="add"/>
                                    <input id="editSprint" type="button" onclick="AjaxFormSprint('editSprint')"
                                           value="edit"/>
                                </th>
                                <th>

                                </th>
                            </tr>
                        </table>
                    </form>
                    <pre id="errSprint"></pre>
                </div>
            </div>
            <div id="manageTask">
                <div id="showListTask"></div>
                <div id="formTask" style="float: left; width: 300px">
                    <h2>Add/Edit task</h2>
                    <form id="taskInfo">
                        <table>
                            <tr>
                                <th>Name :</th>
                                <th>
                                    <input id="idTask" class="afterClear" name="idTask" type="text"/>
                                    <input id="sprintId"  name="sprintId" type="text"/>
                                    <input id="nameTask" class="afterClear" name="nameTask" type="text" placeholder="name"/>
                                </th>
                            </tr>
                            <tr>
                                <th>Estimate (hours) :</th>
                                <th>
                                    <input id="estimate" class="afterClear" name="estimate" type="text"
                                           placeholder="hours"/>
                                </th>
                            </tr>
                            <tr style="display: none">
                                <th>Condition :</th>
                                <th>
                                    <select id="selectCondition" name="selectCondition"></select>
                                </th>
                            </tr>
                            <tr>
                                <th>Dependency task :</th>
                                <th>
                                    <input id="dependencyTask" class="afterClear" name="dependencyTask" type="text"
                                           placeholder="id"/>
                                </th>
                            </tr>
                            <tr>
                                <th>Subtask of :</th>
                                <th>
                                    <input id="subtask" class="afterClear" name="subtask" type="text"
                                           placeholder="id"/>
                                </th>
                            </tr>
                            <tr>
                                <th>Skill :</th>
                                <th>
                                    <select id="selectSkill" name="selectSkill"></select>
                                </th>
                            </tr>
                            <tr>
                                <th>
                                    <input id="addTask" type="button" onclick="manageTask('addTask')"
                                           value="add"/>
                                    <input id="editTask" type="button" onclick="manageTask('editTask')"
                                           value="edit"/>
                                </th>
                                <th>

                                </th>
                            </tr>
                        </table>
                    </form>
                    <pre id="errTask"></pre>
                </div>
                <div id="empTaskSet" style="padding-left: 30px">
                    <h2>Choose employee for task:</h2>
                    <span>Task:</span>
                    <select id="selectTaskForSet" onchange="employeersForEmpTaskSet()"></select>
                    <br/>
                    <span>Employee:</span>
                    <select id="selectEmployeeForSet"></select>
                    <br/>
                    <button onclick="setTaskForEmp()">set</button>
                </div>
            </div>
        </div>
    </div>

    <div id="footer">Footer
    </div>
</div>
</body>
</html>
