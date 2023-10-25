package database_package_servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.ArrayList;

import database_package_connection.databaseConnection;
import database_package_dao.ProductDao;
import database_package_model.Product;
import database_package_model.User;
import database_package_model.Cart;

/**
 * Servlet implementation class AddToCartServlet
 */
public class GetCartServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.setContentType("text/html; charset=UTF-8");
		PrintWriter out = response.getWriter();
		HttpSession session = request.getSession();
		try {
			if (session.getAttribute("cart_list") == null) {
				System.out.println(false);
			} else {
				ProductDao pd = new ProductDao(databaseConnection.getConnection());
				
				User user;
				Cart cart;
				if (request.getSession().getAttribute ("auth") != null) {
					user = (User) request.getSession().getAttribute("auth");
					cart = user.getCart();
					ArrayList<Product> cartProducts = pd.getCart(user);
					System.out.println(cart.toString());
				}
				response.sendRedirect("products.jsp");
			}
		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//System.out.println(session.getAttribute("cart-list"));
		
	}
}
