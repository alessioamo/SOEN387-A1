<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ page import="database_package_model.*" %>
<!DOCTYPE html>
<html>
<head>
	<title>Create Product</title>
	<%@include file="includes/head.jsp" %>
</head>
<body>
	
	<div class="container">
		<div class="card w-50 mx-auto my-5">
			<div class="card w-50 mx-auto my-5">
				<div class="card-header text-center">Create Product</div>
				<div class="card-body">
					<form action="create-product" method="post">
						<div class="form-group">
							<label class="">Name</label>
							<input type="text" class="form-control" name="product-name" placeholder="Product Name" required>
						</div>
						
						<div class="form-group">
							<label class="">SKU</label>
							<input type="text" class="form-control" name="product-sku" placeholder="Product SKU" required>
						</div>
						
						<div class="text-center">
							<button type="submit" class="btn btn-primary">Create</button>
						</div>
					</form>
				</div>
			</div>
			<div class="card w-50 mx-auto my-5">
				<div class="card-header text-center">Update Product</div>
				<div class="card-body">
					<form action="update-product" method="post">
						<div class="form-group">
							<label class="">SKU</label>
							<input type="text" class="form-control" name="product-sku" placeholder="Product SKU" required>
						</div>
						
						<div class="form-group">
							<label class="">Name</label>
							<input type="text" class="form-control" name="product-name" placeholder="Product Name">
						</div>
						
						<div class="form-group">
							<label class="">Description</label>
							<input type="text" class="form-control" name="product-description" placeholder="Product Description">
						</div>
						
						<div class="text-center">
							<button type="submit" class="btn btn-primary">Create</button>
						</div>
					</form>
				</div>
			</div>
		</div>
	</div>
	
	<%@include file="includes/footer.jsp" %>
</body>
</html>