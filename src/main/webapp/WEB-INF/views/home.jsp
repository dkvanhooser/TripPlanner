<!DOCTYPE html>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%--@ page import="com.grandcircus.planit.FetchURLData"--%>
<%@ page session="false" %>
<%@ page import="java.io.*,java.util.*"%>
<%@ page import="javax.servlet.http.*,javax.servlet.*" %>
<%@ page import="com.grandcircus.planit.DAO"%>  
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/sql" prefix="sql"%>

<html>
<head>
<meta charset="utf-8">
	<title>PlanIT</title>
</head>
<body = background="http://picview.info/download/20150530/soft-light-color-line-shape-2880x1800.jpg">
<table border="0" width="100%">
<tr>
<td><h1 align = "center">
	Welcome to PlanIt! 
</h1></td>

 
<c:if test="${cookie.username.value != null}">
	<td>logged in as: ${cookie.username.value} </br>
	<a href="userProfile" align ="right"><input type="button" value="Profile"/></a></br>
	<a href="logout" align ="right"><input type="button" value="Logout"/></td>
</c:if>
<c:if test="${cookie.username.value == null}">
<td><a href="login" align ="right"><input type="button" value="Login"/></a><br/>
<a href="createaccount" align ="right"><input type="button" value="Register"/></td>

</form>
</c:if>

</tr>
</table>
<table>
<tr><td></td><td align = "right">Trip</td><td>date</td></tr>
<tr><td>Search</td><td align = "center">Start</td><td align = "center">End</td></tr>
<tr>
<form action = "<c:url value="search" />">
<td><input type="text"  name = "search" size="30"/></td>
<td><input type="date" name = "dateFrom" size="30"/></td>
<td><input type="date" name ="dateTo" size="30"/></td>
<td><input type = "submit" value = "Search"></td>
</form>
</tr>
</table>

</body>
</html>
