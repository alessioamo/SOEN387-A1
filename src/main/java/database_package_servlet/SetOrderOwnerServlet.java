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

import database_package_connection.databaseConnection;
import database_package_dao.BusinessFunctions;
import database_package_dao.UserDao;
import database_package_model.Cart;
import database_package_model.User;

/**
 * Servlet implementation class ChangePermission
 */
public class SetOrderOwnerServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html; charset=UTF-8");

		try (PrintWriter out = response.getWriter()) {

			HttpSession session = request.getSession();
			BusinessFunctions bf = new BusinessFunctions(databaseConnection.getConnection());
			String orderId = request.getParameter("orderId");
			User currentUser = (User) session.getAttribute("auth");
			
		    bf.setOrderOwner(Integer.parseInt(orderId), currentUser.getId());
			
			
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
