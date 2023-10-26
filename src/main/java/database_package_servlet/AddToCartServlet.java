package database_package_servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.ArrayList;

import database_package_connection.databaseConnection;
import database_package_dao.BusinessFunctions;
import database_package_dao.ProductDao;
import database_package_model.Cart;
import database_package_model.Product;
import database_package_model.User;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

/**
 * Servlet implementation class AddToCartServlet
 */
public class AddToCartServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.setContentType("text/html; charset=UTF-8");

		try (PrintWriter out = response.getWriter()) {
			HttpSession session = request.getSession();
			BusinessFunctions bf = new BusinessFunctions(databaseConnection.getConnection());
			String sku = request.getParameter("sku");
			User user;
			Cart cart;
			System.out.println("ttt " + request.getSession().getAttribute("auth"));
			user = (User) request.getSession().getAttribute("auth");
			if (request.getSession().getAttribute("auth") != null && user.getUsername() != "temp") {
				System.out.println("Test: " + request.getSession().getAttribute("auth"));
				bf.addProductToCart(user, sku);
				cart = user.getCart();
				session.setAttribute("cart_list", cart.getCartProducts());
				response.sendRedirect("products.jsp");
			} else {
				user = new User();
				session.setAttribute("auth", user);
				System.out.println("auth " + request.getSession().getAttribute("auth"));;
				
				bf.addProductToCart(user, sku);
				cart = user.getCart();
				session.setAttribute("cart_list", cart.getCartProducts());
				
				response.sendRedirect("products.jsp");
				
				//response.setStatus(HttpServletResponse.SC_FOUND);
				//response.sendRedirect("login.jsp?status=" + HttpServletResponse.SC_FOUND);
			}
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
