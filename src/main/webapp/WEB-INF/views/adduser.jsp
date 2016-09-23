<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ page import="com.grandcircus.planit.User"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Add User</title>
</head  background="http://picview.info/download/20150530/soft-light-color-line-shape-2880x1800.jpg">
<body>
<form:form action="form" method="post" modelAttribute="addUser">
            <table>
                <tr>
                    <td><form:label path="username">Username</form:label></td>
                    <td><form:input path="username"/></td>
                    <td>${nameError}</td>
                </tr>
                <tr>
                    <td><form:label path="password">Password</form:label></td>
                    <td><form:input path="password"/></td>
                    <td>${passwordError}</td>
                </tr>
                 <tr>
                    <td><form:label path="email">E-mail</form:label></td>
                    <td><form:input path="email"/></td>
                    <td>${emailError}</td>
                </tr>
                <tr>
                    <td><button type="submit">Submit</button></td>
                </tr>
            </table>
        </form:form>
</body>
</html>