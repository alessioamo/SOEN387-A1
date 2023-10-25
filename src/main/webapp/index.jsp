<%@ page import="database_package_connection.*" %>
<%@ page import="database_package_model.*" %>
<%@ page import="java.util.ArrayList" %>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    <%
    User auth = (User) request.getSession().getAttribute("auth");
    if (auth != null) {
    	request.setAttribute("auth", auth);
    }
    
    ArrayList<Product> cart_list = (ArrayList<Product>) session.getAttribute("cart-list");
    if (cart_list != null) {
    	request.setAttribute("cart_list", cart_list);
    }
    
    %>
<!DOCTYPE html>
<html>
<head>
	<title>Home Page</title>
	<%@include file="includes/head.jsp" %>
	<link rel="stylesheet" href="style.css">
</head>
<body>
	<%@include file="includes/navbar.jsp" %>
	<%
		String statusParam = request.getParameter("status");
		int statusCode;
		if (statusParam!=null){
			statusCode = Integer.parseInt(statusParam);
		 	if (statusCode == 403){
		 	    %>
		 	    <p>HTTP Status Code: <%= statusCode %></p>
		 	    <p>Admin Only page: Please Log in to your staff account to view the Admin Toolkit</p>
		 	<%} 
		}%>
	
	
	<!-- <% out.print(databaseConnection.getConnection()); %> -->
	
	<div class="intro">
		<h1 class="logo-header">
			<span class="logo">JS</span><span class="logo">ho</span><span class="logo">P</span>
		</h1>
	</div>
	
	<div class="welcome-message">
		<h4>Welcome to JShoP</h4>
		<h3>Powering your online shopping through JSP and servlets.</h3>
	</div>
	
	<script src="main.js"></script>
	
	<%@include file="includes/footer.jsp" %>
</body>
</html>