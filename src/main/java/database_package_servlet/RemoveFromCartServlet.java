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
import database_package_dao.BusinessFunctions;
import database_package_dao.ProductDao;
import database_package_model.Cart;
import database_package_model.Product;
import database_package_model.User;

/**
 * Servlet implementation class RemoveFromCartServlet
 */
public class RemoveFromCartServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doDelete(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.setContentType("text/html; charset=UTF-8");

		try (PrintWriter out = response.getWriter()) {

			HttpSession session = request.getSession();
			BusinessFunctions bf = new BusinessFunctions(databaseConnection.getConnection());
			ProductDao pd = new ProductDao(databaseConnection.getConnection());
			String sku = request.getParameter("sku");
			User user;
			Cart cart;
			System.out.println("100");
			if (request.getSession().getAttribute("auth") != null) {
				user = (User) request.getSession().getAttribute("auth");
				cart = user.getCart();
				ArrayList<Product> newCartList = cart.getCartProducts();
				System.out.println("1");
				for (Product p : newCartList) {
					if (p.getSku().equals(sku)) {
						System.out.println("2");
						newCartList.remove(p);
						break;
					}
				}
				cart.setCartProducts(newCartList);
				session.setAttribute("cart_list", cart.getCartProducts());
				pd.updateQuantity(sku, 0);
				response.sendRedirect("cart.jsp");
			} else {
				System.out.println("3");
				// Throw exception here
				response.sendRedirect("products.jsp");
			}

		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		doDelete(request, response);
	}

}
