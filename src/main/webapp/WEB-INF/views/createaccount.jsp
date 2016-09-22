<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
 <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
 <%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Create a new account</title>
</head>

<body>
<%-- <c:if test ="${usernameTaken.validation=='true'}"> --%>
<!-- 	<script type="text/javascript">alert("That username already exists");</script> -->
<%-- <c:otherwise> --%>

<%-- </c:otherwise> --%>
<%-- </c:if> --%>
<form:form action="adduser" commandName="addUser" method="POST">
<label class="fieldLabel">Username:
	
    <form:input type="text" path="username" name="username" size="40" maxlength="32" placeholder="username" />      
<!-- error form -->
	<form:errors path="username" cssClass="error"/>

  </label>
  <br>
  <label class="fieldLabel">Password:
    <form:input type="password" path="password" name="password" size="40" maxlength="12"/>      
  </label>
  <br>
   <label class="fieldLabel"> Email:
    <form:input type="text" path="email" name="email" size="40" maxlength="32" placeholder="someone@somewhere.com" />      
  </label>
  <br>
  <div class="buttons">
    <input type="submit" name="submit" onclick="validateForm()" value="Create Account" />
    <input type="reset" name="reset" value="Clear Form" />
  </div>
  </form:form>
</body>

</html>