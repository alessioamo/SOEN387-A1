package database_package_servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.text.DecimalFormat;

import database_package_connection.databaseConnection;
import database_package_dao.BusinessFunctions;
import database_package_model.Order;

/**
 * Servlet implementation class OrderDetails
 */
public class OrderDetails extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String pathInfo = request.getPathInfo(); // This will give you the order ID with a '/'

	    if (pathInfo != null && pathInfo.length() > 1) {
	    	// Removing the / before the ID
	        String idSlug = pathInfo.substring(1);
	        
	        BusinessFunctions bf;
	        
			try(PrintWriter out = response.getWriter()) {
				bf = new BusinessFunctions(databaseConnection.getConnection());
				Order order = bf.getOrderById(idSlug);
				
				DecimalFormat dcf = new DecimalFormat("#.00");
			    request.setAttribute("dcf", dcf);
			    
			    String trackingInfo;
			    if (order.getTrackingNumber() != 0) {
			    	trackingInfo = Integer.toString(order.getTrackingNumber());
			    }
			    else {
			    	trackingInfo = "Not Shipped Yet";
			    }


		        String htmlContent = "<html><head><title>Order " + order.getOrderId() + "</title>"
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
		        		+ "<div class=\"name\">Order ID: " + order.getOrderId() + "</div><br>"
		        		+ "<div class=\"category\">Date Placed: " + order.getDatePlaced() + "</div><br>"
		        		+ "<div class=\"description\">Shipping Address: " + order.getShippingAddress() + "</div><br>"
        				+ "<div class=\"description\">Tracking Number: " + trackingInfo + "</div><br>"
		        		+ "<div class=\"description\">Products: " + order.getProductsInCart() + "</div><br>"
        				+ "<div class=\"price\">Total Cost: $" + dcf.format(order.getTotalCost()) + "</div><br>"
        				+ "<div class=\"vendor\">Placed by User ID: " + order.getUserId() + "</div>"
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
	        // Handle cases where no id slug is provided.
	    }
	}

	

}
