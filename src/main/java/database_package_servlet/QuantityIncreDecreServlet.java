package database_package_servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
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
			int id = Integer.parseInt(request.getParameter("sku"));
			
			ArrayList<Product> cart_list = (ArrayList<Product>) request.getSession().getAttribute("cart-list");
			
			if (action != null && id >= 1) {
				if (action.equals("incre")) {
					for (Product p:cart_list) {
						if (p.getId() == id) {
							int quantity = p.getQuantity();
							quantity++;
							p.setQuantity(quantity);
							response.sendRedirect("cart.jsp");
						}
					}
				}
				if (action.equals("decre")) {
					for (Product p:cart_list) {
						if (p.getId() == id && p.getQuantity() > 1) {
							int quantity = p.getQuantity();
							quantity--;
							p.setQuantity(quantity);
							break;
						}
					}
					response.sendRedirect("cart.jsp");
				}
			} else {
				response.sendRedirect("cart.jsp");
			}
		}
	}

}
