<!DOCTYPE html>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ page session="false" %>

<html>
<head>
<meta charset="utf-8">
	<title>PlanIT</title>
</head>
<body = background="http://picview.info/download/20150530/soft-light-color-line-shape-2880x1800.jpg">
<table border="0" width="100%">
<tr>
<td><h1 align = "center">
	Welcome to PlanIT! 
</h1></td>
<td><a href="<c:url value="login" />" align ="right" >Login</a><br/>
<a href="<c:url value="createaccount" />" align ="right" >Register</a></td>
</tr>
</table>
<table>
<tr><td></td><td align = "right">Trip</td><td>date</td></tr>
<tr><td>Search</td><td align = "center">Start</td><td align = "center">End</td></tr>
<tr>
<form:form action="login" commandName="searchForm">
<td><form:input path="search" type="text" size="30"/></td>
<td><form:input path="date" type="date" size="30"/></td>
<td><form:input path="date" type="date" size="30"/></td>
<td><input type="submit" value="search"/></td>
</form:form>
</table>
</body>
</html>
