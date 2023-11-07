<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ page import="java.util.ArrayList" %>
<%@ page import="java.text.DecimalFormat" %>
<%@ page import="database_package_model.*" %>
    <%
    DecimalFormat dcf = new DecimalFormat("#.00");
    request.setAttribute("dcf", dcf);
    
	User auth = (User) request.getSession().getAttribute("auth");
    System.out.print("Auth is " + auth);
    if (auth == null || auth.getUsername() == null) {
        // User is not logged in; redirect to login.jsp
        response.sendRedirect("login.jsp");
    }
    
    ArrayList<Product> cart_list = (ArrayList<Product>) session.getAttribute("cart-list");
    if (cart_list != null) {
    	request.setAttribute("cart_list", cart_list);
    }
    
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
					<th scope="colr">Date </th>
					<th scope="colr">Shipping Address</th>
					<th scope="colr">Tracking Number</th>
					<th scope="colr">Products</th>
				</tr>
			</thead>
			<tbody>
			<!-- TODO - Not implemented yet  -->
			<%
			// if the user is an admin, show all orders. else, show only those belonging to the logged in user
			if (user != null && "admin".equals(user.getUsername())) {
				if (orders != null) {
					for (Order o:orders) {%>
						<tr><%= o.getOrderId %></tr>
						<tr><%= o.getDate %></tr>
						<tr><%= o.getShippingAddress %></tr>
						<tr><%= o.getTrackingNumber %></tr>
						<tr><%= //o.getProducts %></tr>
					<%}
				}
			}
			else {
				if (orders != null) {
					for (Order o:orders) {
						if (/*TODO if statement to see if id of the user order is the same as the user id who is logged in*/) {%>
							<tr><%= o.getOrderId %></tr>
							<tr><%= o.getDate %></tr>
							<tr><%= o.getShippingAddress %></tr>
							<tr><%= o.getTrackingNumber %></tr>
							<tr><%= o.getDate %></tr>
					<%	}
					}
				}	
			}
			%>
			</tbody>
		</table>
	</div>
	
	<%@include file="includes/footer.jsp" %>
</body>
</html>