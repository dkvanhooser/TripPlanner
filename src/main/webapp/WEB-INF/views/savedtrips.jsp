<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Saved trips</title>
</head>
<body>

saved trips page

  <table>
		<c:forEach var="savedtrip" items="${trip.tripList}">
		<form action = "<c:url value="addEvent" />">
		<tr>
			<td><c:out value ="${trip.tripID}" /></td>
			<td><c:out value ="${trip.tripName}" />	</td>
			<td><c:out value ="${trip.info}" /></td>
			<input type ="hidden" name ="eventId" value = "${trip.id}">
			</td>
			<td><button type="submit">Save this Trip</button></td>
		</tr>
		</form>
		</c:forEach>

</table>
</form>
</body>
</html>