<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ page import="java.util.List" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="java.text.DecimalFormat" %>
<%@ page import="database_package_model.*" %>
<%@ page import="database_package_connection.*" %>
<%@ page import="database_package_dao.*" %>

    <%
    DecimalFormat dcf = new DecimalFormat("#.00");
    request.setAttribute("dcf", dcf);
    
	User auth = (User) request.getSession().getAttribute("auth");
    if (auth == null || auth.getUsername() == "temp") {
        // User is not logged in; redirect to login.jsp
        response.sendRedirect("login.jsp");
    }
    
    if (auth != null && auth.getUsername() != "temp") {
    	request.setAttribute("auth", auth);
    }
    else {
    	auth = new User();
    	auth.setUsername("temp");
    	session.setAttribute("auth", auth);
    }
    
    ArrayList<Product> cart_list = (ArrayList<Product>) session.getAttribute("cart-list");
    if (cart_list != null) {
    	request.setAttribute("cart_list", cart_list);
    }
    
    BusinessFunctions bf = new BusinessFunctions(databaseConnection.getConnection());
    List<Order> orders = bf.getOrders(auth);
    List<Order> allOrders = bf.getAllOrders();
    
    //TODO add order list
    
    %>
<!DOCTYPE html>
<html>
<head>
	<title>Orders</title>
	<%@include file="includes/head.jsp" %>
</head>
<body>
	<%@include file="includes/navbar.jsp" %>
	
	<div class="container">
		<div class="card-header my-3">All Orders</div>
		<table class="table table-light">
			<thead>
				<tr>
					<th scope="colr">Order ID</th>
					<th scope="colr">Date Placed</th>
					<th scope="colr">Shipping Address</th>
					<th scope="colr">Tracking Number</th>
					<th scope="colr">Products</th>
					<% if (auth.getUsername() == "admin") {%>
						<th scope="colr">User ID</th>
					<%}%>
					
					
				</tr>
			</thead>
			<tbody>
			<!-- TODO - Not implemented yet  -->
			<% System.out.println("orders in jsp is " + orders);
				if (orders != null) {
					if (auth.getUsername() == "admin") {
						for (Order o:allOrders) {System.out.println("o is " + o);%>
						<tr>
							<td><%= o.getOrderId() %></td>
							<td><%= o.getDatePlaced() %></td>
							<td><%= o.getShippingAddress() %></td>
							<td><%= o.getTrackingNumber() %></td>
							<td>Temp</td>
							<td><%= o.getUserId() %></td>
						</tr>
					<%}
					}
					else {
						for (Order o:orders) {System.out.println("o is " + o);%>
						<tr>
							<td><%= o.getOrderId() %></td>
							<td><%= o.getDatePlaced() %></td>
							<td><%= o.getShippingAddress() %></td>
							<td><%= o.getTrackingNumber() %></td>
							<td>Temp</td>
						</tr>
					<%}	
					}
				}
			%>
			</tbody>
		</table>
	</div>
	
	<%@include file="includes/footer.jsp" %>
</body>
</html>