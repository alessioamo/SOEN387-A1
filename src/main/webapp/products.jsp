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
    
    ArrayList<Product> cart_list = (ArrayList<Product>) session.getAttribute("cart-list");
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
		
		<div class="checkbox-sorter">
    <label style="margin-right:10px">
        <input type="checkbox" id="checkbox1" data-category="books"> Books
    </label>
    <label style="margin-right:10px">
        <input type="checkbox" id="checkbox2" data-category="electronics"> Electronics
    </label>
    <label style="margin-right:10px">
        <input type="checkbox" id="checkbox3" data-category="jewelry"> Jewelry
    </label>
    <label style="margin-right:10px">
        <input type="checkbox" id="checkbox4" data-category="sports"> Sports
    </label>
</div>
		
		<div id="row" class="row">
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
									<!-- old method of adding to cart <a href="add-to-cart?sku=<%= p.getSku() %>" class="btn btn-dark">Add to Cart</a> -->
									<form action="add-to-cart?sku=<%= p.getSku() %>" method="post">
										<button type="submit" class="btn btn-dark">Add to Cart</button>
									</form>
									
									<% 
									if (user != null && "admin".equals(user.getUsername())) {
									%>
									<a href="admin-toolkit.jsp?productSku=<%= p.getSku() %>#update-product-section" class="btn btn-primary">Edit</a>
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
	
	<script>
	// Function to filter products based on the selected categories
    function filterProducts() {
        const checkboxes = document.querySelectorAll('.checkbox-sorter input[type="checkbox"]');
        const selectedCategories = Array.from(checkboxes)
            .filter(checkbox => checkbox.checked)
            .map(checkbox => checkbox.getAttribute('data-category'));

        const products = document.querySelectorAll('#row .col-md-3');

        products.forEach(product => {
            const category = product.querySelector('.category').textContent.trim().split(' ')[1];
            
            if (selectedCategories.length === 0 || selectedCategories.includes(category)) {
                product.style.display = 'block'; // Show the product
            } else {
                product.style.display = 'none'; // Hide the product
            }
        });
    }

    // Add event listeners to checkboxes
    const checkboxes = document.querySelectorAll('.checkbox-sorter input[type="checkbox"]');
    checkboxes.forEach(checkbox => {
        checkbox.addEventListener('change', filterProducts);
    });

    // Initial filter when the page loads
    filterProducts();

    </script>
	
	<%@include file="includes/footer.jsp" %>
</body>
</html>