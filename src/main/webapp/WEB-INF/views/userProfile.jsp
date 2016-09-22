<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>User Profile</title>
</head>
<body>
<h1>Welcome!</h1>
<h2>You have successfully logged in!</h2>
<h3>Let's plan a trip!</h3>
<form action="createTrip" method="get">
	<input type = "hidden" name= "userID" value = "${cookie.userID.value}"/>
  	<input type="text" name ="tripName" size="30" name="tripName"/>
  <td><button type="submit">Click here to create trip!</button></td>
  </form>
  <br/>
 </form>

<h3>Here are your current trips!</h3>

 <form:form action="savedtrips" commandName="savedtrips" method="GET">
 <td><button type="submit">View/Modify Trip</button></td>
  </div>
  </form:form>
  <table>
		<c:forEach var="trip" items="${Profile.savedtrips}">
		<form action = "<c:url value="addEvent" />">
		<tr>
			<td><c:out value ="${savedtrips.tripName}" />	</td>
			<td><c:out value ="${savedtrips.tripID}" /></td>
			<td><input type ="hidden" name ="tripID" value = "${savedtrips.tripID}"></td>
			<td><button type="submit">Modify this Trip</button></td>
		</tr>
		</form>
		</c:forEach>

</table>
</body>
</html>