package database_package_servlet;

import jakarta.servlet.ServletException;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

import database_package_model.Cart;

/**
 * Servlet implementation class RemoveFromCartServlet
 */
public class RemoveFromCartServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html;charset=UTF-8");
	    try (PrintWriter out = response.getWriter()) {
	        String id = request.getParameter("id");
	        if (id != null) {
	            ArrayList<Cart> cart_list = (ArrayList<Cart>) request.getSession().getAttribute("cart-list");
	            if (cart_list != null) {
	                for (Cart c : cart_list) {
	                    if (c.getId() == Integer.parseInt(id)) {
	                        cart_list.remove(c);
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
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doDelete(request, response);
	}

}
