<%@ page import="database_package_connection.*" %>
<%@ page import="database_package_model.*" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="java.util.List" %>
<%@ page import="database_package_dao.*" %>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    <%
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
    
    UserDao ud = new UserDao(databaseConnection.getConnection());
    List<User> users = ud.getAllUsers();
    
    %>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>User Details</title>
	<%@include file="includes/head.jsp" %>
	
	<script>
		// Check if the URL contains the changePermissionsFailedMessage parameter
		const urlParams = new URLSearchParams(window.location.search);
		const changePermissionsFailedMessage = urlParams.get('changePermissionsFailedMessage');
		const passcodeFailedMessage = urlParams.get('passcodeFailedMessage');
		
		if (changePermissionsFailedMessage) {
			// Display an alert box with the login failed message
			alert(changePermissionsFailedMessage);
		}

		if (passcodeFailedMessage) {
			// Display an alert box with the login failed message
			alert(passcodeFailedMessage);
		}
	</script>

</head>
<body>
	<%@include file="includes/navbar.jsp"%>

	<div class="container">
		<div class="card w-50 mx-auto my-5">
			<div class="card-header text-center">Change Your Passcode</div>
			<div class="card-body">
				<form action="change-password" method="post">
					<div class="form-group">
						<label class="">New Passcode</label> <input
							type="username" class="form-control" name="new-password"
							placeholder="New Passcode" required>
					</div>
					<br>
					<div class="text-center">
						<button type="submit" class="btn btn-primary">Change Passcode</button>
					</div>
				</form>
			</div>
		</div>
		
		<table class="table table-light">
			<thead>
				<tr>
					<th scope="col">User ID</th>
					<th scope="col">Passcode</th>
					<%
					if (user.isAdmin()) {
					%>
					<th scope="col">Is Admin?</th>
					<th scope="col">Change Permission</th>
					<th scope="col">Claim Ownership</th>
					<%}%>
				</tr>
			</thead>
			<tbody>
			<%
			if (users != null) {
				if (user.isAdmin()) {
					for (User u:users) { %>
						<tr>
							<td><%= u.getId() %></td>
							<td><%= u.getPassword() %></td>
							<%
							if (user.isAdmin()) {
							%>
							<td><%= u.isAdmin() %></td>
							<td><form action="change-permission?userId=<%= u.getId() %>" method="post">
								<button type="submit" class="btn btn-sm btn-primary">Change</button>
							</form></td>
							<%} %>
						</tr>
					<% }
				} else {
					for (User u:users) { 
						if (u.getId() == auth.getId()) {%>
							<tr>
								<td><%= u.getId() %></td>
								<td><%= u.getPassword() %></td>
								<%
								if (user.isAdmin()) {
								%>
								<td><%= u.isAdmin() %></td>
								<td><form action="change-permission?userId=<%= u.getId() %>" method="post">
									<button type="submit" class="btn btn-sm btn-primary">Change</button>
								</form></td>
								<%} %>
							</tr>
				<% 		}
					}
				}
			}
			%>
			</tbody>
		</table>
	</div>
</body>
</html>