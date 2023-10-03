<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ page import="database_package_model.*" %>
    <%
    User auth = (User) request.getSession().getAttribute("auth");
    if (auth != null) {
    	request.setAttribute("auth", auth);
    }
    %>
<!DOCTYPE html>
<html>
<head>
	<title>Orders</title>
	<%@include file="includes/head.jsp" %>
</head>
<body>
	<%@include file="includes/navbar.jsp" %>
	
	<h1>Hello World!</h1>
	
	<%@include file="includes/footer.jsp" %>
</body>
</html>