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
				<li class="nav-item active"><a class="nav-link" href="index.jsp">Home</a></li>
				<li class="nav-item active"><a class="nav-link" href="cart.jsp">Cart
					<c:if test="${ cart_list.size() > 0 }">
			            <span class="badge bg-danger px-1">${ cart_list.size() }</span>
			        </c:if>
				</a></li>
				<li class="nav-item active"><a class="nav-link" href="products.jsp">Products</a></li>
				
				<%
				if (auth != null) {%>
					<li class="nav-item active"><a class="nav-link" href="orders.jsp">Orders</a></li>
					<li class="nav-item active"><a class="nav-link" href="log-out">Logout</a></li>
				<%} else { %>
					<li class="nav-item active"><a class="nav-link" href="login.jsp">Login</a></li>
				<%}
				%>
				
			</ul>
		</div>
	</div>
</nav>