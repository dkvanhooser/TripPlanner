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
<h1>Welcome ${userForm.username}!!</h1>
<h2>You have successfully logged in!</h2>
<h3>Let's plan a trip!</h3>
<form:form action="home" commandName="newtrip" method="GET">
<div class="buttons">
  <td><input type="submit" name="NewTrip" value="Create a New Trip" /></td>
  </div>
 </form:form>
 <form:form action="savedtrips" commandName="savedtrips" method="GET">
 <div class="buttons">
  <td><input type="submit" name="SavedTrips" value="My Saved Trips"/></td>
  </div>
  </form:form>
</body>
</html>