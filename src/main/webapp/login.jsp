<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ page import="java.util.ArrayList"%>
<%@ page import="database_package_model.*"%>
<%
User auth = (User) request.getSession().getAttribute("auth");

if (auth != null) {
	request.setAttribute("auth", auth);
}

ArrayList<Product> cart_list = (ArrayList<Product>) session.getAttribute("cart-list");
if (cart_list != null) {
	request.setAttribute("cart_list", cart_list);
}
%>
<!DOCTYPE html>
<html>
<head>
<title>Account Page</title>
<%@include file="includes/head.jsp"%>

<script>
	// Check if the URL contains the loginFailedMessage parameter
	const urlParams = new URLSearchParams(window.location.search);
	const loginFailedMessage = urlParams.get('loginFailedMessage');

	if (loginFailedMessage) {
		// Display an alert box with the login failed message
		alert(loginFailedMessage);
	}
</script>
</head>
<body>
	<%@include file="includes/navbar.jsp"%>
	<%
	String statusParam = request.getParameter("status");
	int statusCode;
	if (statusParam != null) {
		statusCode = Integer.parseInt(statusParam);
		if (statusCode == 302) {
	%>
	<p>
		HTTP Status Code:
		<%=statusCode%></p>
	<p>Not Logged in: Please Log in to your account to access this
		request.</p>
	<%
		}
	}
	%>
	<div class="container">
		<div class="card w-50 mx-auto my-5">
			<div class="card-header text-center">User Login</div>
			<div class="card-body">
				<form action="user-login" method="post">
					<div class="form-group">
						<label class="">Username (admin for admin)</label> <input
							type="username" class="form-control" name="login-username"
							placeholder="Username" required>
					</div>

					<div class="form-group">
						<label class="">Password</label> <input type="password"
							class="form-control" name="login-password" placeholder="Password"
							required>
					</div>
					<br>
					<div class="text-center">
						<button type="submit" class="btn btn-primary">Login</button>
					</div>
				</form>
			</div>
		</div>
		<div class="card w-50 mx-auto my-5">
			<div class="card-header text-center">Create Account</div>
			<div class="card-body">
			<!-- TODO: Make a create account servlet -->
				<form action="" method="post">
					<div class="form-group">
						<label class="">Username</label> <input
							type="username" class="form-control" name="login-username"
							placeholder="Username" required>
					</div>

					<div class="form-group">
						<label class="">Password</label> <input type="password"
							class="form-control" name="login-password" placeholder="Password"
							required>
					</div>
					<br>
					<div class="text-center">
						<button type="submit" class="btn btn-success">Create Account</button>
					</div>
				</form>
			</div>
		</div>
	</div>

	<%@include file="includes/footer.jsp"%>
</body>
</html>