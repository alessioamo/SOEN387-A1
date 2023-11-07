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
				cart = user.getCart();
				System.out.println(cart.toString());
			} else {
				System.out.println("Null User Shud prolly be exception");
			}
			BusinessFunctions bf = new BusinessFunctions(databaseConnection.getConnection());
			bf.createOrder(user, shippingAddress);
			response.sendRedirect("products.jsp");
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
		doGet(request, response);
	}

}
