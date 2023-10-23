<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ page import="java.util.List" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="java.text.DecimalFormat" %>
<%@ page import="database_package_connection.*" %>
<%@ page import="database_package_dao.ProductDao" %>
<%@ page import="database_package_model.*" %>
    <%
    DecimalFormat dcf = new DecimalFormat("#.00");
    request.setAttribute("dcf", dcf);
    
    User auth = (User) request.getSession().getAttribute("auth");
    if (auth != null) {
    	request.setAttribute("auth", auth);
    }
    
    ProductDao pd = new ProductDao(databaseConnection.getConnection());
    List<Product> products = pd.getAllProducts();
    
    ArrayList<Cart> cart_list = (ArrayList<Cart>) session.getAttribute("cart-list");
    if (cart_list != null) {
    	request.setAttribute("cart_list", cart_list);
    }
    
    %>
<!DOCTYPE html>
<html>
<head>
	<title>Products</title>
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
						<a href="<%= request.getContextPath() %>/products/<%= p.getUrlSlug() %>"><img src="product-images/<%= p.getImage() %>" class="card-img-top" style="width:18rem;height:20rem;" alt="..."></a>
						<div class="card-body">
							<a href="<%= request.getContextPath() %>/products/<%= p.getUrlSlug() %>"><h5 class="card-title" style="color:black;text-decoration:none;"><%= p.getName() %></h5></a>
							<h6 class="price">Price: $<%= dcf.format(p.getPrice()) %></h6>
							<h6 class="category">Category: <%= p.getCategory() %></h6>
							<div class="mt-3 d-flex justify-content-between">
								<a href="add-to-cart?id=<%= p.getId() %>" class="btn btn-dark">Add to Cart</a>
								<% 
								if (user != null && "admin".equals(user.getUsername())) {
								%>
								<a href="createProduct.jsp?productId=<%= p.getId() %>#update-product-section" class="btn btn-primary">Edit</a>
								<%}%>
								
								<!-- Might need a buy now feature in the future <a href="#" class="btn btn-primary">Buy Now</a>  -->
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