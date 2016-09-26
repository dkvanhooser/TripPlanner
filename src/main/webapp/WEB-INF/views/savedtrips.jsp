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
<body background="http://picview.info/download/20150530/soft-light-color-line-shape-2880x1800.jpg">
<h1 align = "center">Saved Trip</h1>
<table><tr><td></td><td>

<c:if test="${cookie.username.value != null}">
	logged in as: ${cookie.username.value} </br>
	<a href="userProfile" align ="right" >Profile</a></br>
	<a href="logout" align ="right" >Logout</a>
</c:if>
<c:if test="${cookie.username.value == null}">
<a href="login" align ="right" >Login</a><br/>
<a href="createaccount" align ="right" >Register</a></td>
</td></tr>
</form>
</c:if>		
<tr><td>
<table>
		<c:forEach var="event" items="${listevents.events}">
		
		<tr>
			<td><c:out value ="${event.name}" /></td>
			<td><a href="<c:out value ="${event.url}" />">Click Here to View it on Ticketmaster!</a>	</td>
			<td><c:out value ="${event.dateTime}" />	</td>
			<td><c:out value ="${event.info}" /></td>
			<input type ="hidden" name ="eventId" value = "${event.id}"></input>
			</td>
		</tr>
		</form>
		</c:forEach>

</table>
<a href="home">Go Home</a>

</table>
</td></tr>
</table>
</body>
</html>