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
<body>
	<li><p><b>Username :</b>
   <%= request.getParameter("username")%>
   <br>
</p></li>

<jsp:useBean id="obj" class="com.grandcircus.planit.User">  
</jsp:useBean>  
<jsp:setProperty property="*" name="obj"/>
<%  
boolean i=DAO.userAndPassValidator(obj); 
if (i)  
	out.print("Product successfully added");  
else
	out.println("Error; product not added");
%>
</body>
</html>