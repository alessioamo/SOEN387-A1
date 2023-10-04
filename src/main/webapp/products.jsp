<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ page import="java.util.List" %>
<%@ page import="database_package_connection.databaseConnection" %>
<%@ page import="database_package_dao.ProductDao" %>
<%@ page import="database_package_model.*" %>
    <%
    User auth = (User) request.getSession().getAttribute("auth");
    if (auth != null) {
    	request.setAttribute("auth", auth);
    }
    
    ProductDao pd = new ProductDao(databaseConnection.getConnection());
    List<Product> products = pd.getAllProducts();
    
    %>
<!DOCTYPE html>
<html>
<head>
	<title>Cart</title>
	<%@include file="includes/head.jsp" %>
</head>
<body>
	<%@include file="includes/navbar.jsp" %>
	
	<div class="container">
		<div class="card-header my-3">All Products</div>
		<div class="row">
		<%
			if (!products.isEmpty()) {
				for (Product p:products) {
					//out.println(p.getCategory());%>
					
					<div class="col-md-3 my-3">
					<div class="card w-100" style="width: 18rem;">
						<img src="product-images/<%= p.getImage() %>" class="card-img-top" alt="...">
						<div class="card-body">
							<h5 class="card-title"><%= p.getName() %></h5>
							<h6 class="price">Price: $<%= p.getPrice() %></h6>
							<h6 class="category">Category: <%= p.getCategory() %></h6>
							<div class="mt-3 d-flex justify-content-between">
								<a href="#" class="btn btn-primary">Add to Cart</a>
								<a href="#" class="btn btn-primary">Buy Now</a>
							</div>
						</div>
					</div>
				</div>
				<%}
			}
		%>
		</div>
	</div>
	
	<%@include file="includes/footer.jsp" %>
</body>
</html>