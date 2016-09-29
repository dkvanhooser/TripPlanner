<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css" integrity="sha384-BVYiiSIFeK1dGmJRAkycuHAHRg32OmUcww7on3RYdg4Va+PmSTsz/K68vbdEjh4u" crossorigin="anonymous">
<link href="https://fonts.googleapis.com/css?family=Josefin+Sans" rel="stylesheet">
<link rel="stylesheet" type="text/css" href="resources/css/userProfile.css">
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>User Profile</title>
</head>
<body background="https://images5.alphacoders.com/374/374293.jpg">
<h1>Welcome!</h1>
<table><tr><td>
<h3>Let's plan a trip!</h3>
<form action="createTrip" method="get">
	<input type = "hidden" name= "userID" value = "${cookie.userid.value}"/>
  	<input type="text" size="30" name="tripName"/>
  <button  type="submit">Click here to create trip!</button>
 
  </form>
  <br/>
 </form>
</td><td>
<nav class="navbar navbar-default navbar-fixed-top">
  <div class="container-fluid">
    <!-- Brand and toggle get grouped for better mobile display -->
    <div class="navbar-header">
      <button type="button" class="navbar-toggle collapsed" data-toggle="collapse" data-target="#bs-example-navbar-collapse-1" aria-expanded="false">
		
      </button>
      <a class="navbar-brand" href="home">Home</a>
    </div>

    <!-- Collect the nav links, forms, and other content for toggling -->
    
    <div class="collapse navbar-collapse" id="bs-example-navbar-collapse-1">
    
<c:if test="${cookie.username.value == null}">
          <ul class="nav navbar-nav navbar-right">
        <li><a href="createaccount">Register</a></li>
      </ul>

      <ul class="nav navbar-nav navbar-right">
        <li><a href="login">Login</a></li>
      </ul>
      </c:if>
      <c:if test="${cookie.username.value != null}">

      <ul class="nav navbar-nav navbar-right">
        <li><a href="userProfile">Profile</a></li>
      </ul>

      <ul class="nav navbar-nav navbar-right">
        <li><a href="logout">Logout</a></li>
      </ul>
            <ul class="nav navbar-nav navbar-right">
        <li>	<p class="navbar-text">Logged in as: ${cookie.username.value}</p></li>
      </ul>
      </c:if>

    </div><!-- /.navbar-collapse -->
  </div><!-- /.container-fluid -->
</nav>
</table>
<h3>Here are your current trips!</h3>
<a href="home" align ="right"><input type="button" value="Home"/></a>
  <table>
  
		<c:forEach var="trip" items="${Profile.savedtrips}">
		<form:form commandName="tripsearch" method= "POST">
		<tr>
			<td><c:out value ="${trip.tripName}"/></td>
			<td><c:out value ="${trip.tripID}" /></td>
			<td><form:input type ="hidden" path="tripID" value ="${trip.tripID}"/></td>
			<td><form:input type ="hidden" path="tripName" value ="${trip.tripName}"/></td>
			<td><form:input type ="hidden" path="userID" value ="${trip.userID}"/></td>
			<td>${trip.userID}</td>
			<td><input type="submit" value="View/Modify Trip"/></td>
		</tr>
		</form:form>
		</c:forEach>
		
		
</table>
   <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js" integrity="sha384-Tc5IQib027qvyjSMfHjOMaLkfuWVxZxUPnCJA7l2mCWNIpG9mGCD8wGNIcPD7Txa" crossorigin="anonymous"></script> 

</body>
</html>