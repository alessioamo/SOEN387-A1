<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ page import="java.util.ArrayList" %>
<%@ page import="java.text.DecimalFormat" %>
<%@ page import="java.util.List" %>
<%@ page import="database_package_model.*" %>
<%@ page import="database_package_dao.*" %>
<%@ page import="database_package_connection.*" %>
    <%
    DecimalFormat dcf = new DecimalFormat("#.00");
    request.setAttribute("dcf", dcf);
    
    User auth = (User) request.getSession().getAttribute("auth");
	// temp refers to the anonymous user (not logged in)
    if (auth != null && auth.getUsername() != "temp") {
    	request.setAttribute("auth", auth);
    }
    else {
    	auth = new User();
    	auth.setUsername("temp");
    	session.setAttribute("auth", auth);
    }
    
    ArrayList<Product> cart_list = (ArrayList<Product>) session.getAttribute("cart_list");
    if (cart_list != null) {
    	ProductDao pDao = new ProductDao(databaseConnection.getConnection());
    	double total = pDao.getTotalCartPrice(cart_list);
    	request.setAttribute("total", total);
    }
    %>
<!DOCTYPE html>
<html>
<head>
	<title>Cart</title>
	<%@include file="includes/head.jsp" %>
	
	<style type="text/css">
		.table tbody td {
			vertical-align: middle;
		}
		.btn-incre, .btn-decre {
			box-shadow: none;
			font-size: 25px;
			color: green;
		}
	</style>
	
	<script>
	// Check if the URL contains the loginFailedMessage parameter
	const urlParams = new URLSearchParams(window.location.search);
	const cartIsEmptyMessage = urlParams.get('cartIsEmptyMessage');

	if (cartIsEmptyMessage) {
		// Display an alert box with the login failed message
		alert(cartIsEmptyMessage);
	}
	</script>
</head>
<body>
	<%@include file="includes/navbar.jsp" %>
	
	<div class="container">
		<div class="d-flex py-3">
			<h3>Total Price: $${ (total>0)?dcf.format(total):0 }</h3>
			<form action="CreateOrderServlet" method="post">
				<label for="shipping-address">Shipping Address:</label>
			    <input type="text" id="shipping-address" name="shipping-address" required>
			    <button type="submit" class="mx-3 btn btn-primary">Place Order</button>
			</form>
		</div>
		<table class="table table-light">
			<thead>
				<tr>
					<th scope="col">Image</th>
					<th scope="col">Name</th>
					<th scope="col">Category</th>
					<th scope="col">Price</th>
					<th scope="col">Quantity</th>
					<th scope="col">Cancel</th>
				</tr>
			</thead>
			<tbody>
			<%
			if (cart_list != null) {
				for (Product p:cart_list) { %>
					<tr>
					<td><img src="product-images/<%= p.getImage() %>" style="width:4rem;height:4rem;"></td>
					<td><%= p.getName() %></td>
					<td><%= p.getCategory() %></td>
					<td>$<%= dcf.format(p.getPrice()*p.getQuantity()) %></td>
					<td>
						<form action="" method="post" class="form-inline">
							<input type="hidden" name="sku" value="<%= p.getSku() %>" class="form-input">
							<div class="form-group d-flex justify-content-between">
								<a class="btn btn-sm btn-decre" href="quantity-incre-decre?action=decre&sku=<%= p.getSku() %>"><i class="fas fa-minus-square"></i></a>
								<input type="text" name="quantity" class="form-control" value="<%= p.getQuantity() %>" readonly>
								<a class="btn btn-sm btn-incre" href="quantity-incre-decre?action=incre&sku=<%= p.getSku() %>"><i class="fas fa-plus-square"></i></a>
							</div>
						</form>
					</td>
					<td><form action="remove-from-cart?sku=<%= p.getSku() %>" method="post">
							<button type="submit" class="btn btn-sm btn-danger">Remove</button>
						</form></td>
					<!-- old method of removing from cart <td><a class="btn btn-sm btn-danger" href="remove-from-cart?id=<%= p.getId() %>">Remove</a></td> -->
				</tr>
				<% }
			}
			%>
			</tbody>
		</table>
	</div>
	
	<%@include file="includes/footer.jsp" %>
</body>
</html>