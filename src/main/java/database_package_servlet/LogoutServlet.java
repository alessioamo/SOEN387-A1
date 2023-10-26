package database_package_servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

public class LogoutServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try(PrintWriter out = response.getWriter()) {
			System.out.println("in logout servlet " + request.getSession().getAttribute("auth"));
			if (request.getSession().getAttribute("auth") != null) {
				//this means user is logged in
				request.getSession().removeAttribute("auth");
				response.sendRedirect("login.jsp");
			}
			else {
				response.sendRedirect("index.jsp");
				System.out.println("REDIRECT");
			}
		}
	}

}
