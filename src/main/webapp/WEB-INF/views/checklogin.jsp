<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="java.io.*,java.util.*"%>
<%@ page import="javax.servlet.http.*,javax.servlet.*" %>
<%@ page import="com.grandcircus.planit.DAO"%>  
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/sql" prefix="sql"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Checking User Info...</title>
</head>

<body  background="http://picview.info/download/20150530/soft-light-color-line-shape-2880x1800.jpg">
Welcome back ${username }
<c:if test="${username != null }">
	<a href="home">Click here to go home!</a>
</c:if>
<%--
	String loginStatus = (String)session.getAttribute("loggedin");
	//if (loginStatus == null)
	if (loginStatus.equals("true"))
		out.println("You are logged in");
	else
		out.println("You are not really logged in.");
--%>

<jsp:useBean id="user" class="com.grandcircus.planit.User">  
</jsp:useBean>
<jsp:setProperty property="*" name="user"/>
<%  
//DAO.userAndPassValidator(user);
//if (i)  
//	out.print("Product successfully added");  
//else
//	out.println("Error; product not added");
%>
</body>
</html>