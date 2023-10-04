<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ page import="database_package_model.*" %>
    <%
    User auth = (User) request.getSession().getAttribute("auth");
    if (auth != null) {
    	request.setAttribute("auth", auth);
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
</head>
<body>
	<%@include file="includes/navbar.jsp" %>
	
	<div class="container">
		<div class="d-flex py-3">
			<h3>Total Price: $200</h3>
			<a class="mx-3 btn btn-primary" href="#">Check Out</a>
		</div>
		<table class="table table-light">
			<thead>
				<tr>
					<th scope="col">Name</th>
					<th scope="col">Category</th>
					<th scope="col">Price</th>
					<th scope="col">Buy Now</th>
					<th scope="col">Cancel</th>
				</tr>
			</thead>
			<tbody>
				<tr>
					<td>Book 1</td>
					<td>Books</td>
					<td>$10</td>
					<td>
						<form action="" method="post" class="form-inline">
							<input type="hidden" name="id" value="1" class="form-input">
							<div class="form-group d-flex justify-content-between">
								<a class="btn btn-sm btn-decre" href=""><i class="fas fa-minus-square"></i></a>
								<input type="text" name="quantity" class="form-control" value="1" readonly>
								<a class="btn btn-sm btn-incre" href=""><i class="fas fa-plus-square"></i></a>
							</div>
						</form>
					</td>
					<td>
						<a class="btn btn-sm btn-danger" href="">Remove</a>
					</td>
				</tr>
			</tbody>
		</table>
	</div>
	
	<%@include file="includes/footer.jsp" %>
</body>
</html>