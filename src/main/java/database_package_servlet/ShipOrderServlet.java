package database_package_servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import database_package_connection.databaseConnection;
import database_package_dao.BusinessFunctions;
import database_package_dao.InvalidSkuException;
import database_package_dao.UserDao;
import database_package_model.Order;
import database_package_model.User;

/**
 * Servlet implementation class ShipOrderServlet
 */
public class ShipOrderServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
     
	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try (PrintWriter out = response.getWriter()) {
			
			String orderId = request.getParameter("orderId");
	        System.out.println("Received orderId: " + orderId);
			
			BusinessFunctions bf = new BusinessFunctions(databaseConnection.getConnection());
			bf.shipOrder(orderId, orderId);
			
	        response.sendRedirect("orders.jsp");
	
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

}
