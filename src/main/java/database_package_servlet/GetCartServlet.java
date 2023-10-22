package database_package_servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

import database_package_model.Cart;

/**
 * Servlet implementation class AddToCartServlet
 */
public class GetCartServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		System.out.println("HI");
		response.setContentType("text/plain; charset=UTF-8");
		//PrintWriter out = response.getWriter();
		ArrayList<Cart> cart_list = (ArrayList<Cart>) request.getSession().getAttribute("cart-list");
		//String cart_products = "";
		for (Cart cart : cart_list) {
			System.out.println(cart);
		}
		response.sendRedirect("products.jsp");
	}
}
