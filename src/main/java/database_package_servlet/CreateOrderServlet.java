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
import database_package_dao.InvalidSkuException;
import database_package_model.Cart;
import database_package_model.Product;
import database_package_model.User;

/**
 * Servlet implementation class CreateOrderServlet
 */
public class CreateOrderServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public CreateOrderServlet() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.setContentType("text/html;charset=UTF-8");
		HttpSession session = request.getSession();
		try (PrintWriter out = response.getWriter()) {
			User user;
			Cart cart;
			String shippingAddress = request.getParameter("shipping-address");
			if (request.getSession().getAttribute("auth") != null) {
				user = (User) request.getSession().getAttribute("auth");
				
				// We must check if user is logged in, any id not equal to 0 means they are. If not logged in, redirect them to login
				if (user.getId() != 0) {
					System.out.println("User is logged in.");
					cart = user.getCart();
					BusinessFunctions bf = new BusinessFunctions(databaseConnection.getConnection());
					bf.createOrder(user, shippingAddress);
					session.setAttribute("orders_list", bf.getOrders(user));
					response.sendRedirect("orders.jsp");
				}
				else {
					System.out.println("User is not logged in, redirecting to login.");
					String loginFailedMessage = "User is not authenticated, please provide or create a new authentication key.";
				    response.sendRedirect("login.jsp?loginFailedMessage=" + loginFailedMessage);
				}
			} else {
				
			}
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.setContentType("text/html;charset=UTF-8");
		HttpSession session = request.getSession();
		try (PrintWriter out = response.getWriter()) {
			User user;
			Cart cart;
			String shippingAddress = request.getParameter("shipping-address");
			if (request.getSession().getAttribute("auth") != null) {
				user = (User) request.getSession().getAttribute("auth");
				
				// We must check if user is logged in, any id not equal to 0 means they are. If not logged in, redirect them to login
				if (user.getId() != 0) {
					System.out.println("User is logged in.");
					cart = user.getCart();
					if (cart.toString() != "") {
						BusinessFunctions bf = new BusinessFunctions(databaseConnection.getConnection());
						bf.createOrder(user, shippingAddress);
						session.setAttribute("orders_list", bf.getOrders(user));
						session.setAttribute("cart_list", user.getCart().getCartProducts());
						response.sendRedirect("orders.jsp");
					}
					else {
						System.out.println("Cart is empty, don't add order.");
						String cartIsEmptyMessage = "Cart is empty, please add a product to place an order.";
					    response.sendRedirect("cart.jsp?cartIsEmptyMessage=" + cartIsEmptyMessage);
					}
				}
				else {
					System.out.println("User is not logged in, redirecting to login.");
					String loginFailedMessage = "User is not authenticated, please provide or create a new authentication key.";
				    response.sendRedirect("login.jsp?loginFailedMessage=" + loginFailedMessage);
				}
			}
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
