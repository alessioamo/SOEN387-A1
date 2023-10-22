package database_package_servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;

import database_package_connection.databaseConnection;
import database_package_dao.BusinessFunctions;
import database_package_dao.ProductDao;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class CreateProductServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.sendRedirect("createProduct.jsp");
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.setContentType("text/html;charset=UTF-8");
		try (PrintWriter out = response.getWriter()) {
			String productName = request.getParameter("product-name");
			String productSku = request.getParameter("product-sku");
			String productDescription = request.getParameter("product-description");
			String productCategory = request.getParameter("product-category");
			String productPrice = request.getParameter("product-price");
			String productQuantity = request.getParameter("product-quantity");
			String productVendor = request.getParameter("product-vendor");
			String productSlug = request.getParameter("product-slug");
			//System.out.print(productName + productSku);
			try {
				//BusinessFunctions bf = new BusinessFunctions(databaseConnection.getConnection());
				//bf.CreateProduct(productSku, productName);
				ProductDao pd = new ProductDao(databaseConnection.getConnection());
				pd.createProduct(productSku, productName, productDescription, productCategory,productPrice, productQuantity,
						productVendor, productSlug);
				out.print(true);
				response.sendRedirect("products.jsp");

			} catch (ClassNotFoundException | SQLException e) {
				e.printStackTrace();
			}

		}
	}
}
