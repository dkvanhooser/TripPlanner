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
					
<table>
		<c:forEach var="event" items="${listevents.events}">
		
		<tr>
			<td><c:out value ="${event.name}" /></td>
			<td><c:out value ="${event.url}" />	</td>
			<td><c:out value ="${event.dateTime}" />	</td>
			<td><c:out value ="${event.info}" /></td>
			<input type ="hidden" name ="eventId" value = "${event.id}"></input>
			</td>
		</tr>
		</form>
		</c:forEach>

</table>



</body>
</html>