<%@ taglib prefix="spring" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://www.springframework.org/tags" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
	<meta charset="utf-8">
	<title>LOG IN</title>
    <link href="<c:url value="/resources/css/login.css" />" rel="stylesheet">
</head>
<body>
	<spring:form id="slick-login" method="post"  modelAttribute="user" action="check-user">
		<label>Логин:</label><spring:input path="login"  type="text" name="username" class="placeholder" placeholder="admin@example.com"/>
		<spring:errors path="login" cssClass="error"/>
		<label>Пароль:</label><spring:input path="pass" type="password" name="password" class="placeholder" placeholder="Password"/>
		<spring:errors path="pass" cssClass="error"/>
		<span class="error">${message.toString()}</span>
		<spring:button type="submit">LOG IN</spring:button>
	</spring:form>
</body>
</html>