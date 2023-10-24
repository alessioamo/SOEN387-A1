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
    
    ArrayList<Cart> cart_list = (ArrayList<Cart>) session.getAttribute("cart-list");
    if (cart_list != null) {
    	request.setAttribute("cart_list", cart_list);
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
	<%
        // Retrieve the status code that was set in the sender.jsp
        int statusCode = response.getStatus();
	 	if (statusCode == 403){
    %>
    <p>HTTP Status Code: <%= statusCode %></p>
    <p>Admin Only page: Please Log in to your staff account to view the Admin Toolkit</p>
    <%} %>
	
	<% out.print(databaseConnection.getConnection()); %>
	
	<%@include file="includes/footer.jsp" %>
</body>
</html>