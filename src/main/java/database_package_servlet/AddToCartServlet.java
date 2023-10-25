package database_package_servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import database_package_model.User;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.ArrayList;

import database_package_connection.databaseConnection;
import database_package_dao.BusinessFunctions;
import database_package_dao.ProductDao;
import database_package_model.Cart;
import database_package_model.Product;

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
			ProductDao pd = new ProductDao(databaseConnection.getConnection());
			String sku = request.getParameter("sku");
			User user;
			Cart cart;
			Product product;
			if (request.getParameter("auth") != null) {
				user = (User) request.getSession().getAttribute("auth");
				cart = user.getCart();
				product = bf.getProduct(sku);
				if (product.getQuantity() == 0) {
					product.setQuantity(1);
					ArrayList<Product> newCartList = cart.getCartProducts();
					newCartList.add(product);
					cart.setCartProducts(newCartList);
					session.setAttribute("cart-list", cart.getCartProducts());
					response.sendRedirect("products.jsp");
				} else {
					product.setQuantity(product.getQuantity() + 1);
					
				}
				pd.updateQuantity(sku, product.getQuantity());
			}
			response.sendRedirect("products.jsp");

			

		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
