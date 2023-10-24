package database_package_servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.List;

import database_package_connection.databaseConnection;
import database_package_dao.ProductDao;
import database_package_model.Product;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class GetProductListServlet extends HttpServlet {

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.setContentType("text/html;charset=UTF-8");
		PrintWriter out = response.getWriter();

		out.println("<html>");
		out.println("<head><title>Product List</title></head>");
		out.println("<body>");
		out.println("<h1>Product List</h1>");
		out.println("<table border='1'>");
		out.println(
				"<tr><th>Product ID</th><th>Product Name</th><th>SKU</th><th>Price</th><th>Vendor</th><th>Category</th><th>Image</th><th>URL Slug</th><th>Description</th></tr>");

		try {
			ProductDao pd = new ProductDao(databaseConnection.getConnection());
			List<Product> products = pd.getAllProducts();
			for (Product p : products) {
				out.println(
						"<tr><td>" + p.getId() + "</td><td>" + p.getName() + "</td><td>" + p.getSku() + "</td><td>" + p.getPrice() + "</td><td>" + p.getVendor() + "</td><td>" + p.getCategory() + "</td><td>" + p.getImage() + "</td><td>" + p.getUrlSlug() + "</td><td>" + p.getDescription()+ "</td></tr>");
			}
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		out.println("</table>");
		out.println("</body>");
		out.println("</html>");
	}
}
