<%@ page import="database_package_model.*" %>

<nav class="navbar navbar-expand-lg navbar-light bg-light">
	<div class="container">
		<a class="navbar-brand" href="index.jsp">JShoP</a>
		<!-- <button class="navbar-toggler" type="button" data-toggle="collapse"
			data-target="#navbarSupportedContent" aria-controls="navbarSupportedContent"
			aria-expanded="false" aria-label="Toggle navigation">
			<span class="navbar-toggler-icon"></span>
		</button>  -->

		<div class="" id="navbarSupportedContent">
			<ul class="navbar-nav ml-auto">
				<li class="nav-item active"><a class="nav-link"
					href="index.jsp">Home</a></li>
				<li class="nav-item active"><a class="nav-link" href="cart.jsp">Cart
						<c:if test="${ cart_list.size() > 0 }">
							<span class="badge bg-danger px-1">${ cart_list.size() }</span>
						</c:if>
				</a></li>
				<li class="nav-item active"><a class="nav-link"
					href="products.jsp">Products</a></li>

				<%
				// Note: Auth says it cannot be resolved to a variable but the navbar is included in every jsp page after the auth variable has been initialized so this error is incorrect
				if (auth != null) {
					// Auth.getId() will be 0 if the user is not logged in (they are in anonymous mode or as we called it "temp")
					if (auth.getId() != 0) {%>
						<li class="nav-item active"><a class="nav-link" href="orders.jsp">Orders</a></li>
						<li class="nav-item active"><a class="nav-link" href="log-out">Logout</a></li>
						<li class="nav-item active"><a class="nav-link" href="user.jsp">User</a></li>
				<%	} else {%>
						<li class="nav-item active"><a class="nav-link" href="log-out">Login</a></li>
				<%	}
				} else { %>
				<li class="nav-item active"><a class="nav-link"
					href="login.jsp">Login</a></li>
				<%}
				%>
				<% 
				   User user = (User) request.getSession().getAttribute("auth");
				   if (user != null && user.isAdmin()) {
				%>
				<li class="nav-item active"><a class="nav-link" href="admin-toolkit.jsp">Admin Toolkit</a></li>
				<%}%>

			</ul>
		</div>
	</div>
</nav>