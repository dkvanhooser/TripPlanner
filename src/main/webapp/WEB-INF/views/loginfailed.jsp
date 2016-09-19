<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Login Failed</title>
</head>
<body>

<h1>Invalid Username or Password. Please try again or return home.</h1>

<form action="login" method="GET">
    <input type="submit" value="Try Logging in again!" 
         name="Submit"/>         
</form>
<form action="home" method="GET">
    <input type="submit" value="Return to home page!" 
         name="Submit"/>         
</form>
<a href="home">Flee now or die in vane.</a>



</body>
</html>