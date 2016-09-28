<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ page session="false" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Login Page</title>
</head>
<body background="http://picview.info/download/20150530/soft-light-color-line-shape-2880x1800.jpg">
<form:form commandName="loginForm" method="post">
<td>Username:<form:input type="text" path="username" size="30"/></td>
<td>Password:<form:input path="password" type="password" size="30"/></td>
<td><input type="submit" value="Login!"/></td>
</form:form>
<a href="createaccount" align ="right"><input type="button" value="Register New Account"/></a>
</body>
</html>