<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ page import="java.util.List"%>
<%@ page import="java.util.ArrayList"%>
<%@ page import="java.text.DecimalFormat"%>
<%@ page import="com.fasterxml.jackson.databind.JsonNode"%>
<%@ page import="com.fasterxml.jackson.databind.ObjectMapper"%>
<%@ page import="com.fasterxml.jackson.databind.node.ArrayNode"%>
<%@ page import="com.fasterxml.jackson.databind.node.ObjectNode"%>
<%@ page import="database_package_model.*"%>
<%@ page import="database_package_connection.*"%>
<%@ page import="database_package_dao.*"%>

<%
DecimalFormat dcf = new DecimalFormat("#.00");
request.setAttribute("dcf", dcf);
UserDao udao = new UserDao(databaseConnection.getConnection());

User auth = (User) request.getSession().getAttribute("auth");
if (auth == null || auth.getUsername() == "temp") {
	// User is not logged in; redirect to login.jsp
	response.sendRedirect("login.jsp");
}

if (auth != null && auth.getUsername() != "temp") {
	request.setAttribute("auth", auth);
} else {
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

ObjectMapper objectMapper = new ObjectMapper();
%>
<!DOCTYPE html>
<html>
<head>
<title>Orders</title>
<%@include file="includes/head.jsp"%>

<script>
	// Check if the URL contains the invalidTrackingNumber parameter
	const urlParams = new URLSearchParams(window.location.search);
	const invalidTrackingNumber = urlParams.get('invalidTrackingNumber');

	if (invalidTrackingNumber) {
		// Display an alert box with the login failed message
		alert(invalidTrackingNumber);
	}
</script>
</head>
<body>
	<%@include file="includes/navbar.jsp"%>

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
					<th scope="colr">Cost</th>
					<%
					if (user.isAdmin()) {
					%>
					<th scope="colr">User ID</th>
					<%}%>
					<th scope="colr">Details</th>
					<%
					if (user.isAdmin()) {
					%>
					<th scope="colr">Ship Order</th>
					<%}%>
				</tr>
			</thead>
			<tbody>
				<%
				System.out.println("orders in jsp is " + orders);
				if (orders != null) {
					if (user.isAdmin()) {
						for (Order o : allOrders) {
					System.out.println("o is " + o);
					JsonNode rootNode = null;
					if (o.getProductsInCart() != null) {
						rootNode = objectMapper.readTree(o.getProductsInCart());
					}
				%>
				<tr>
					<td><%=o.getOrderId()%></td>
					<td><%=o.getDatePlaced()%></td>
					<td><%=o.getShippingAddress()%></td>
					<%
					if (o.getTrackingNumber() != 0) {
					%>
					<td><%=bf.getTrackingNumber(o.getTrackingNumber())%></td>
					<%} else {%>
					<td>Not Shipped Yet</td>
					<%
					}
					%>
					<td><ul>
							<%
							if (rootNode != null && rootNode.isArray()) {
								ArrayNode updatedRootNode = objectMapper.createArrayNode();
								for (JsonNode jsonNode : rootNode) {
									String name = jsonNode.get("name").asText();
									int quantity = jsonNode.get("quantity").asInt();
									String prod = (name + " x" + quantity);
							%>
							<li><%=prod%></li>
							<%
							}
							}
							%>
						</ul></td>
					<td>$<%=dcf.format(o.getTotalCost())%></td>
					<td><%=o.getUserId()%></td>
					<td><a
						href="<%=request.getContextPath()%>/orders/<%=o.getOrderId()%>">View
							Order</a></td>
					<td>
						<%
						if (o.getTrackingNumber() == 0) {
						%>
						<form action="ShipOrderServlet" method="post">
							<input type="hidden" name="orderId" value="<%=o.getOrderId()%>">
							<input type="number" id="shipping-address" name="tracking-number"
								required placeholder="Enter Tracking number">
							<button type="submit" class="btn btn-primary">Ship</button>
						</form> <%} else {%> Shipped <%}%>

					</td>
				</tr>

				<%
				}
				} else {
				for (Order o : orders) {
				String trackingNumberAsString = bf.getTrackingNumber(o.getTrackingNumber());
				JsonNode rootNode = null;
				if (o.getProductsInCart() != null) {
					rootNode = objectMapper.readTree(o.getProductsInCart());
				}
				%>
				<tr>
					<td><%=o.getOrderId()%></td>
					<td><%=o.getDatePlaced()%></td>
					<td><%=o.getShippingAddress()%></td>
					<%
					if (o.getTrackingNumber() != 0) {
					%>
					<td><%=trackingNumberAsString%></td>
					<%} else {%>
					<td>Not Shipped Yet</td>
					<%
					}
					%>
					<td><ul>
							<%
							if (rootNode != null && rootNode.isArray()) {
								ArrayNode updatedRootNode = objectMapper.createArrayNode();
								for (JsonNode jsonNode : rootNode) {
									String name = jsonNode.get("name").asText();
									int quantity = jsonNode.get("quantity").asInt();
									String prod = (name + " x" + quantity);
							%>
							<li><%=prod%></li>
							<%
							}
							}
							%>

						</ul></td>
					<td>$<%=dcf.format(o.getTotalCost())%></td>
					<td><a
						href="<%=request.getContextPath()%>/orders/<%=o.getOrderId()%>">View
							Order</a></td>
				</tr>
				<%
				}
				}
				%>

				<%
				}
				%>
			</tbody>
		</table>
	</div>

	<%@include file="includes/footer.jsp"%>
</body>
</html>