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
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css" integrity="sha384-BVYiiSIFeK1dGmJRAkycuHAHRg32OmUcww7on3RYdg4Va+PmSTsz/K68vbdEjh4u" crossorigin="anonymous">
<link href="https://fonts.googleapis.com/css?family=Josefin+Sans" rel="stylesheet">
<link rel="stylesheet" type="text/css" href="resources/css/main.css">
<meta charset="utf-8">
	<title>PlanIT</title>
</head>
<body = background="http://hdwallpaperbackgrounds.net/wp-content/uploads/2015/08/amazing-planet-hd-wallpapers.jpg">
<table border="0" width="100%">
<tr>
<td><h1 align = "center">
	Welcome to PlanIt! 
</h1></td>

 
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

</tr>
</table>
<table>
<tr><td></td><td align = "right">Trip</td><td>date</td></tr>
<tr><td>Search</td><td align = "center">Start</td><td align = "center">End</td></tr>
<tr>
<form action = "<c:url value="search" />">

<td><input type="text"  name = "search" size="30" placeholder="Search by City"/></td>
<td><input type="date" name = "dateFrom" size="30"/></td>
<td><input type="date" name ="dateTo" size="30"/></td>
<td><input type = "submit" value = "Search"></td>

</form>
</tr>
</table>
   <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js" integrity="sha384-Tc5IQib027qvyjSMfHjOMaLkfuWVxZxUPnCJA7l2mCWNIpG9mGCD8wGNIcPD7Txa" crossorigin="anonymous"></script> 

</body>
</html>
