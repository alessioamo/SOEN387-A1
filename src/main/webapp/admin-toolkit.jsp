<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ page import="database_package_model.*"%>
<%@ page import="database_package_dao.*"%>
<%@ page import="database_package_connection.*"%>
<%@ page import="java.text.DecimalFormat"%>
<%@ page import="java.util.ArrayList"%>
<%
User auth = (User) request.getSession().getAttribute("auth");
if (auth != null){
	request.setAttribute("auth", auth);
	if (!auth.isAdmin()){
		response.setStatus(HttpServletResponse.SC_FORBIDDEN);
		response.sendRedirect("index.jsp?status="+ 403);
		//request.getRequestDispatcher("index.jsp").forward(request, response);
		//response.sendRedirect("index.jsp");
	}
	// temp refers to the anonymous user (not logged in)
}else if (auth == null || auth.getUsername() == "temp") {
	//response.sendRedirect("login.jsp");
	response.setStatus(HttpServletResponse.SC_FOUND);
	response.sendRedirect("login.jsp?status="+ 302);
	//request.getRequestDispatcher("login.jsp").forward(request, response);
}


ArrayList<Product> cart_list = (ArrayList<Product>) session.getAttribute("cart-list");
if (cart_list != null) {
	request.setAttribute("cart_list", cart_list);
}

BusinessFunctions bf = new BusinessFunctions(databaseConnection.getConnection());

DecimalFormat dcf = new DecimalFormat("#.00");
request.setAttribute("dcf", dcf);
%>
<!DOCTYPE html>
<html>
<head>
<title>Admin Toolkit</title>
<%@include file="includes/head.jsp"%>
</head>
<body>
	<%@include file="includes/navbar.jsp"%>

	<div class="container">
		<div class="card w-50 mx-auto my-5">
			<div class="card w-50 mx-auto my-5">
				<div class="card-header text-center">Create Product</div>
				<div class="card-body">
					<form action="create-product" method="post">
						<div class="form-group">
							<label class="">SKU</label> <input type="text"
								class="form-control" name="product-sku"
								placeholder="Product SKU" required>
						</div>

						<div class="form-group">
							<label class="">Name</label> <input type="text"
								class="form-control" name="product-name"
								placeholder="Product Name" required>
						</div>

						<div class="form-group">
							<label class="">Description</label> <input type="text"
								class="form-control" name="product-description"
								placeholder="Product Description">
						</div>

						<div class="form-group">
							<label class="">Category</label> <input type="text"
								class="form-control" name="product-category"
								placeholder="Product Category">
						</div>

						<div class="form-group">
							<label class="">price</label> <input type="number" step="0.01"
								class="form-control" name="product-price"
								placeholder="Product price">
						</div>

						<div class="form-group">
							<label class="">Image name</label> <input type="text"
								class="form-control" name="product-image"
								placeholder="example.jpg">
						</div>

						<div class="form-group">
							<label class="">Vendor</label> <input type="text"
								class="form-control" name="product-vendor"
								placeholder="Product Vendor">
						</div>

						<div class="form-group">
							<label class="">URL Slug</label> <input type="text"
								class="form-control" name="product-slug"
								placeholder="Product's URL Slug">
						</div>

						<div class="text-center">
							<button type="submit" class="btn btn-primary">Create</button>
						</div>
					</form>
				</div>
			</div>
			<%
			String productSku = (request.getParameter("productSku") != null) ? request.getParameter("productSku") : "";
			Product selectedProduct = bf.getProduct(productSku);
			String nameAttribute = (selectedProduct!=null && selectedProduct.getName() != null) ? selectedProduct.getName() : "";
			String descriptionAttribute = (selectedProduct!=null && selectedProduct.getDescription() != null) ? selectedProduct.getDescription() : "";
			String categoryAttribute = (selectedProduct!=null && selectedProduct.getCategory() != null) ? selectedProduct.getCategory() : "";
			Double priceAttribute = (selectedProduct != null) ? selectedProduct.getPrice() : null;
			String imageAttribute = (selectedProduct!=null && selectedProduct.getImage() != null) ? selectedProduct.getImage() : "";
			String vendorAttribute = (selectedProduct!=null && selectedProduct.getVendor() != null) ? selectedProduct.getVendor() : "";
			String urlSlugAttribute = (selectedProduct!=null && selectedProduct.getUrlSlug() != null) ? selectedProduct.getUrlSlug() : "";
			%>
			<div class="card w-50 mx-auto my-5" id="update-product-section">
				<div class="card-header text-center">Update Product</div>
				<div class="card-body">
					<form action="update-product" method="post">
						<div class="form-group">
							<label class="">SKU of the product you wish to update</label> <input type="text"
								class="form-control" name="product-sku"
								placeholder="Product SKU" value="<%=productSku%>" required>
						</div>

						<div class="form-group">
							<label class="">Name</label> <input type="text"
								class="form-control" name="product-name"
								placeholder="Product Name" value="<%=nameAttribute%>">
						</div>

						<div class="form-group">
							<label class="">Description</label> <input type="text"
								class="form-control" name="product-description"
								placeholder="Product Description"
								value="<%=descriptionAttribute%>">
						</div>

						<div class="form-group">
							<label class="">Category</label> <input type="text"
								class="form-control" name="product-category"
								placeholder="Product Category" value="<%=categoryAttribute%>">
						</div>

						<div class="form-group">
							<label class="">price</label> <input type="number" step="0.01"
								class="form-control" name="product-price"
								placeholder="Product price"
								value="<%= (priceAttribute != null)? dcf.format(priceAttribute) : null %>">
						</div>

						<div class="form-group">
							<label class="">Vendor</label> <input type="text"
								class="form-control" name="product-vendor"
								placeholder="Product Vendor" value="<%=vendorAttribute%>">
						</div>
						
						<div class="form-group"> 
							<label class="">Image</label> <input type="text"
								class="form-control" name="product-image"
								placeholder="Example.jpg"
								value="<%=imageAttribute%>">
						</div>

						<div class="form-group">
							<label class="">URL Slug</label> <input type="text"
								class="form-control" name="product-slug"
								placeholder="Product's URL Slug" value="<%=urlSlugAttribute%>">
						</div>

						<div class="text-center">
							<button type="submit" class="btn btn-primary">Update</button>
						</div>
					</form>
				</div>
			</div>
			<div class="card w-50 mx-auto my-5">
				<div class="card-header text-center">Get Cart</div>
				<div class="card-body">
					<form action="get-cart" method="get">
						<div class="text-center">
							<button type="submit" class="btn btn-primary">Get Cart</button>
						</div>
					</form>
				</div>
			</div>
			<div class="card w-50 mx-auto my-5">
				<div class="card-header text-center">Download Product Catalog</div>
				<div class="card-body">
					<form action="download-products" method="get">
						<div class="text-center">
							<button type="submit" class="btn btn-primary">Download</button>
						</div>
					</form>
				</div>
			</div>
			<div class="card w-50 mx-auto my-5">
				<div class="card-header text-center">Products List</div>
				<div class="card-body">
					<form action="product-list" method="get">
						<div class="text-center">
							<button type="submit" class="btn btn-primary">See Products list</button>
						</div>
					</form>
				</div>
			</div>
		</div>
	</div>

	<%@include file="includes/footer.jsp"%>
</body>
</html>