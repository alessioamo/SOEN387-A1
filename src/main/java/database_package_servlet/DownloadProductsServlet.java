package database_package_servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;

import database_package_connection.databaseConnection;
import database_package_dao.ProductDao;

/**
 * Servlet implementation class AddToCartServlet
 */
public class DownloadProductsServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.setContentType("application/json; charset=UTF-8");
		try {
			ProductDao pd = new ProductDao(databaseConnection.getConnection());
			String json = pd.downloadProductCatalog();
			
			response.setHeader("Content-Disposition", "attachment; filename=products.json");
			PrintWriter out = response.getWriter();
			out.print(json);
			
		}catch(ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
	}
}
