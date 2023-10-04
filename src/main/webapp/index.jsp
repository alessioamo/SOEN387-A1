<%@ page import="database_package_connection.databaseConnection" %>
<%@ page import="database_package_model.*" %>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    <%
    User auth = (User) request.getSession().getAttribute("auth");
    if (auth != null) {
    	request.setAttribute("auth", auth);
    }
    
    %>
<!DOCTYPE html>
<html>
<head>
	<title>Main Page</title>
	<%@include file="includes/head.jsp" %>
</head>
<body>
	<%@include file="includes/navbar.jsp" %>
	
	<% out.print(databaseConnection.getConnection()); %>
	
	<%@include file="includes/footer.jsp" %>
</body>
</html>