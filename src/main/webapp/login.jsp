<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ page import="java.util.ArrayList" %>
<%@ page import="database_package_model.*" %>
    <%
    User auth = (User) request.getSession().getAttribute("auth");
    if (auth != null) {
    	request.setAttribute("auth", auth);
    	response.sendRedirect("index.jsp");
    }
    
    ArrayList<Product> cart_list = (ArrayList<Product>) session.getAttribute("cart-list");
    if (cart_list != null) {
    	request.setAttribute("cart_list", cart_list);
    }
    %>
<!DOCTYPE html>
<html>
<head>
	<title>Login Page</title>
	<%@include file="includes/head.jsp" %>
	
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
	<%@include file="includes/navbar.jsp" %>
	 <%
        // Retrieve the status code that was set in the sender.jsp
        int statusCode = response.getStatus();
	 	if (statusCode == 302){
    %>
    <p>HTTP Status Code: <%= statusCode %></p>
    <p>Admin Only page: Please Log in to your staff account to view the Admin Toolkit</p>
    <%} %>
	<div class="container">
		<div class="card w-50 mx-auto my-5">
			<div class="card-header text-center">Admin Login</div>
			<div class="card-body">
				<form action="user-login" method="post">
					<div class="form-group">
						<label class="">Username (admin for admin)</label>
						<input type="username" class="form-control" name="login-username" placeholder="Username" required>
					</div>

					<div class="form-group">
						<label class="">Password</label>
						<input type="password" class="form-control" name="login-password" placeholder="Password" required>
					</div>
					<div class="text-center">
						<button type="submit" class="btn btn-primary">Login</button>
					</div>
				</form>
			</div>
		</div>
	</div>
	
	<%@include file="includes/footer.jsp" %>
</body>
</html>