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
	Search
</h1></td>
<td><a href="<c:url value="login" />" align ="right" >Login</a><br/>
<a href="<c:url value="createaccount" />" align ="right" >Register</a></td>
</tr>
</table>
<table>
<tr><td></td><td align = "right">Trip</td><td>date</td></tr>
<tr><td>Search</td><td align = "center">Start</td><td align = "center">End</td></tr>
<tr>
<form action = "<c:url value="search" />">
<td><input type="text" path="search" name = "search" size="30"/></td>
<td><input path="dateFrom" type="date" name = "dateFrom" size="30"/></td>
<td><input path="dateTo" type="date" name ="dateTo" size="30"/></td>
<td><input type = "submit" value = "Search"></td>
</form>
</tr>
</table>
<table>
		<c:forEach var="event" items="${events.eventList}">
		<tr>
			<td><c:out value ="${event.name}" /></td>
			<td><c:out value ="${event.url}" />	</td>
			<td><c:out value ="${event.dateTime}" />	</td>
			<td><c:out value ="${event.info}" /></td>
		</tr>
		</c:forEach>

</table>
</body>
</html>