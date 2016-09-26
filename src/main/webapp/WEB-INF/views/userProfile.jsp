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
<body background="http://picview.info/download/20150530/soft-light-color-line-shape-2880x1800.jpg">
<h1>Welcome!</h1>
<table><tr><td>
<h3>Let's plan a trip!</h3>
<form action="createTrip" method="get">
	<input type = "hidden" name= "userID" value = "${cookie.userID.value}"/>
  	<input type="text" name ="tripName" size="30" name="tripName"/>
  <button  type="submit">Click here to create trip!</button>
 
  </form>
  <br/>
 </form>
</td><td>
<c:if test="${cookie.username.value != null}">
	logged in as: ${cookie.username.value} </br>
	<a href="logout" align ="right" >Logout</a>
</c:if>
<c:if test="${cookie.username.value == null}">
<a href="login" align ="right" >Login</a><br/>
<a href="createaccount" align ="right" >Register</a></td>
</td></tr>
</form>
</c:if>		
</table>
<h3>Here are your current trips!</h3>
<a href="home">Go Home</a>
  <table>
		<c:forEach var="trip" items="${Profile.savedtrips}">
		<form:form commandName="tripsearch" method= "POST">
		<tr>
			<td><c:out value ="${trip.tripName}"/></td>
			<td><c:out value ="${trip.tripID}" /></td>
			<td><form:input type ="hidden" path="tripID" value ="10"/></td>
			<td><form:input type ="hidden" path="tripName" value ="${trip.tripName}"/></td>
			<td><input type="submit" value="View/Modify Trip"/></td>
		</tr>
		</form:form>
		</c:forEach>
		
		
</table>
</body>
</html>