package database_package_servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;

import database_package_connection.databaseConnection;
import database_package_dao.UserDao;
import database_package_model.User;

/**
 * Servlet implementation class ChangePassword
 */
public class ChangePassword extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.setContentType("text/html;charset=UTF-8");

		try {
			PrintWriter out = response.getWriter();
			String newPassword = request.getParameter("new-password");
			UserDao udao = new UserDao(databaseConnection.getConnection());
			User user;
			user = (User) request.getSession().getAttribute("auth");
			if (udao.SetPasscode(user, newPassword)) {
				response.sendRedirect("user.jsp");
			}else {
				String passcodeFailedMessage = "Could not set new passcode. Passcode already exists for another user or password is too short (minimum 4 characters).";
				response.sendRedirect("user.jsp?passcodeFailedMessage=" + passcodeFailedMessage);
			}
			
		
		}catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
	}

}
