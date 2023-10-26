package database_package_servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;

import database_package_connection.databaseConnection;
import database_package_dao.UserDao;
import database_package_model.User;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class LoginServlet
 */
public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.sendRedirect("login.jsp");
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.setContentType("text/html;charset=UTF-8");
		try (PrintWriter out = response.getWriter()) {
			
			String username = request.getParameter("login-username");
			String password = request.getParameter("login-password");

			try {
				UserDao udao = new UserDao(databaseConnection.getConnection());
				User user = udao.userLogin(username, password);

				if (user != null) {
					request.getSession().setAttribute("auth", user);
					response.sendRedirect("index.jsp");
				} else {
					String loginFailedMessage = "User login failed, username or password is incorrect.";
				    response.sendRedirect("login.jsp?loginFailedMessage=" + loginFailedMessage);
					//out.print("User login failed");
				}
			} catch (ClassNotFoundException | SQLException e) {
				e.printStackTrace();
			}

		}
	}

}
