<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
 <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
 <%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>

<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css" integrity="sha384-BVYiiSIFeK1dGmJRAkycuHAHRg32OmUcww7on3RYdg4Va+PmSTsz/K68vbdEjh4u" crossorigin="anonymous">
<link rel="stylesheet" type="text/css" href="resources/css/main.css">

<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Create a new account</title>

<style>
.error{
color:red;
}
td{
padding:5px;
}


</style>
</head>
<body background="http://hdwallpaperbackgrounds.net/wp-content/uploads/2015/08/amazing-planet-hd-wallpapers.jpg">
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
<table>
<tr>
<form:form action="createaccount" commandName="addUser" method="POST">
	<td>Username:</td>
	
   <td> <form:input type="text" path="username" name="username" size="40" maxlength="32" placeholder="username" /></td>
	<td><form:errors path="username" cssClass="error"/></td>

  </tr>
  <tr>
  <td>Password:</td>
    <td><form:input type="password" path="password" name="password" size="40" maxlength="30"/></td>
   <td> <form:errors path="password" cssClass="error"/></td>
  
  <tr>
  <td>Email:</td>
  
  <td>  <form:input type="text" path="email" name="email" size="40" maxlength="32" />   </td>
   <td> <form:errors path="email" cssClass="error"/></td>
</tr>
  <tr>
<div class="buttons">
 <td> 
    <input type="submit" name="submit" value="Create Account" />
    <input type="reset" name="reset" value="Clear Form" /></td>
  </div>
  </tr>
  </form:form>
  </table>
   <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js" integrity="sha384-Tc5IQib027qvyjSMfHjOMaLkfuWVxZxUPnCJA7l2mCWNIpG9mGCD8wGNIcPD7Txa" crossorigin="anonymous"></script> 

</body>

</html>