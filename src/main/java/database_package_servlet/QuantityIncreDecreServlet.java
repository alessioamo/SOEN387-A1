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
import database_package_model.*;

/**
 * Servlet implementation class QuantityIncreDecreServlet
 */
public class QuantityIncreDecreServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html;charset=UTF-8");
		try(PrintWriter out = response.getWriter();) {
			String action = request.getParameter("action");
			String sku = request.getParameter("sku");
			HttpSession session = request.getSession();
			ProductDao pd =new ProductDao(databaseConnection.getConnection());
			BusinessFunctions bf = new BusinessFunctions(databaseConnection.getConnection());
			
			User user;
			Cart cart;
			Product product;
			
			if (request.getSession().getAttribute ("auth") != null && action != null) {
				user = (User) request.getSession().getAttribute("auth");
				cart = user.getCart();
				product = bf.getProduct(sku);
				ArrayList<Product> newCartList = cart.getCartProducts();
				if (action.equals("incre")) {
					for (Product p:newCartList) {
						if (p.getSku().equals(sku)) {
							p.setQuantity(p.getQuantity()+1);
							cart.setCartProducts(newCartList);
							session.setAttribute("cart_list", cart.getCartProducts());
							pd.updateQuantity(sku, product.getQuantity());
							break;
						}
					}
					response.sendRedirect("cart.jsp");
				}
				if (action.equals("decre")) {
					for (Product p:newCartList) {
						if (p.getSku().equals(sku) && p.getQuantity() > 1) {
							p.setQuantity(p.getQuantity()-1);
							cart.setCartProducts(newCartList);
							session.setAttribute("cart_list", cart.getCartProducts());
							pd.updateQuantity(sku, product.getQuantity());
							break;
						}
					}
					response.sendRedirect("cart.jsp");
				}
			} else {
				response.setStatus(HttpServletResponse.SC_FOUND);
				response.sendRedirect("login.jsp?status=" + HttpServletResponse.SC_FOUND);
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
