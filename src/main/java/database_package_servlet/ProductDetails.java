package database_package_servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.util.List;

import database_package_connection.databaseConnection;
import database_package_dao.BusinessFunctions;
import database_package_model.Product;

/**
 * Servlet implementation class ProductDetails
 */
public class ProductDetails extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String pathInfo = request.getPathInfo(); // This will give you "/SLUGNAME"

	    if (pathInfo != null && pathInfo.length() > 1) {
	        String slugName = pathInfo.substring(1);
	        
	        BusinessFunctions bf;
	        
			try(PrintWriter out = response.getWriter()) {
				bf = new BusinessFunctions(databaseConnection.getConnection());
				Product product = bf.getProductBySlug(slugName);
				
				DecimalFormat dcf = new DecimalFormat("#.00");
			    request.setAttribute("dcf", dcf);

		        String htmlContent = "<html><head><title>" + product.getName() + "</title>"
		        		+ "<style>.product {\r\n"
		        		+ "      max-width: 50%;\r\n"
		        		+ "      border: 1px solid #ddd;\r\n"
		        		+ "      padding: 20px;\r\n"
		        		+ "      margin: 0 auto;\r\n"
		        		+ "      text-align: center;\r\n"
		        		+ "      position: relative;\r\n"
		        		+ "    }\r\n"
		        		+ "\r\n"
		        		+ "/* Style for the back button */\r\n"
		        		+ "    .back-button {\r\n"
		        		+ "      position: absolute;\r\n"
		        		+ "      top: 10px;\r\n"
		        		+ "      left: 10px;\r\n"
		        		+ "      width: 10%;\r\n"
		        		+ "      height: 10%;\r\n"
		        		+ "      font-size: 22px;\r\n"
		        		+ "      background-color: red;\r\n"
		        		+ "      border-radius: 5px;\r\n"
		        		+ "    }\r\n"
		        		+ "\r\n"
		        		+ "    /* Style for the product image */\r\n"
		        		+ "    .product img {\r\n"
		        		+ "      width: 18rem;\r\n"
		        		+ "      height: 20rem;\r\n"
		        		+ "      margin-bottom: 10px;\r\n"
		        		+ "    }\r\n"
		        		+ "\r\n"
		        		+ "    /* Style for the product name */\r\n"
		        		+ "    .product .name {\r\n"
		        		+ "      font-size: 30px;\r\n"
		        		+ "      font-weight: bold;\r\n"
		        		+ "    }\r\n"
		        		+ "\r\n"
		        		+ "    /* Style for the product category */\r\n"
		        		+ "    .product .category {\r\n"
		        		+ "      font-size: 20px;\r\n"
		        		+ "      color: #666;\r\n"
		        		+ "    }\r\n"
		        		+ "\r\n"
		        		+ "    /* Style for the product description */\r\n"
		        		+ "    .product .description {\r\n"
		        		+ "      font-size: 16px;\r\n"
		        		+ "      margin-top: 10px;\r\n"
		        		+ "    }\r\n"
		        		+ "\r\n"
		        		+ "    /* Style for the product price */\r\n"
		        		+ "    .product .price {\r\n"
		        		+ "      font-size: 20px;\r\n"
		        		+ "      font-weight: bold;\r\n"
		        		+ "      margin-top: 10px;\r\n"
		        		+ "    }\r\n"
		        		+ "\r\n"
		        		+ "    /* Style for the product vendor */\r\n"
		        		+ "    .product .vendor {\r\n"
		        		+ "      font-size: 14px;\r\n"
		        		+ "      color: #333;\r\n"
		        		+ "    }</style>"
		        		+ "</head><body>"
		        		+ "<div class=\"product\">"
		        		+ "<button class=\"back-button\" onclick=\"goBack()\">Back</button>"
		        		+ "<div class=\"name\">" + product.getName() + "</div><br>"
        				+ "<img class=\"image\" src=\"https://github.com/alessioamo/SOEN387-A1/blob/main/src/main/webapp/product-images/" + product.getImage() + "?raw=true\"><br>"
		        		+ "<div class=\"category\">Category: " + product.getCategory() + "</div><br>"
		        		+ "<div class=\"description\">" + product.getDescription() + "</div><br>"
        				+ "<div class=\"price\">$" + dcf.format(product.getPrice()) + "</div><br>"
		        		+ "<div class=\"vendor\">Vendor: " + product.getVendor() + "</div>"
		        		// TODO - add to cart button on product page + "<a href=\"add-to-cart?id=" + product.getId() + "\" class=\"btn btn-dark\">Add to Cart</a>"
		        		+ "</div>"
		        		+ "<script>\r\n"
		        		+ "    function goBack() {\r\n"
		        		+ "      window.history.back();\r\n"
		        		+ "    }\r\n"
		        		+ "  </script>"
		        		+ "</body></html>";
		        
				response.setContentType("text/html; charset=UTF-8");

		        // Write the HTML content to the response
		        response.getWriter().write(htmlContent);
				
				
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
	    } else {
	        // Handle cases where no SLUGNAME is provided.
	    }
	}

}
