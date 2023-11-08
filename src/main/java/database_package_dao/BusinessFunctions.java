package database_package_dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import database_package_connection.databaseConnection;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import database_package_model.*;

public class BusinessFunctions {
	private Connection con;
	private String query;
	private PreparedStatement pst;
	private ResultSet rs;
	private ProductDao pd;
	private OrderDao od;

	public BusinessFunctions(Connection con) {
		this.con = con;
		pd = new ProductDao(con);
	}

	public void createProduct(String sku, String name, String description, String category, String price, String image,
			String vendor, String slug) throws InvalidSkuException{
		try {
			query = "SELECT * FROM products WHERE sku=?";
			pst = this.con.prepareStatement(query);
			pst.setString(1, sku);
			rs = pst.executeQuery();
			if (!rs.next()) {
				query = "INSERT INTO products (name, sku) VALUES (?, ?)";
				pst = this.con.prepareStatement(query);
				pst.setString(1, name);
				pst.setString(2, sku);
				pst.executeUpdate();
				pst.close();
				if (!name.isBlank()) {
					pd.updateName(sku, name);
				}
				if (!description.isBlank()) {
					pd.updateDescription(sku, description);
				}
				if (!category.isBlank()) {
					pd.updateCategory(sku, category);
				}
				if (!price.isBlank()) {
					pd.updatePrice(sku, Double.parseDouble(price));
				}
				if (!image.isBlank()) {
					pd.updateImage(sku, image);
				}
				if (!vendor.isBlank()) {
					pd.updateVendor(sku, vendor);
				}
				if (!slug.isBlank()) {
					pd.updateSlug(sku, slug);
				}
			} else {
				pst.close();
				throw new InvalidSkuException("SKU = "+sku+" is already assigned to another product.");
			}
		}catch (SQLException e) {
			System.out.println(e);
		}
	}

	public void updateProduct(String sku, String name, String description, String category, String price, String image,
			String vendor, String slug) throws InvalidSlugException{
		if (!name.isBlank()) {
			pd.updateName(sku, name);
		}
		if (!description.isBlank()) {
			pd.updateDescription(sku, description);
		}
		if (!category.isBlank()) {
			pd.updateCategory(sku, category);
		}
		if (!price.isBlank()) {
			pd.updatePrice(sku, Double.parseDouble(price));
		}
		if (!image.isBlank()) {
			pd.updateImage(sku, image);
		}
		if (!vendor.isBlank()) {
			pd.updateVendor(sku, vendor);
		}
		if (!slug.isBlank()) {
			pd.updateSlug(sku, slug);
		}
	}
	public void addProductToCart(User user, String sku) {
		Cart cart = user.getCart();
		int newQuantity = 0;
		ArrayList<Product> newCartList = cart.getCartProducts();
		if (cart.findInCart(sku)) {
			for (Product p : newCartList) {
				if (p.getSku().equals(sku)) {
					newQuantity = p.getQuantity() + 1;
					p.setQuantity(newQuantity);
					break;
				}
			}
		} else {
			Product product= getProduct(sku);
			product.setQuantity(1);
			newCartList.add(product);
		}
		cart.setCartProducts(newCartList);
		pd.updateQuantity(sku,newQuantity);
	}
	
	public void removeProductFromCart(User user, String sku) {
		Cart cart = user.getCart();
		ArrayList<Product> newCartList = cart.getCartProducts();
		for (Product p : newCartList) {
			if (p.getSku().equals(sku)) {
				newCartList.remove(p);
				break;
			}
		}
		cart.setCartProducts(newCartList);
		pd.updateQuantity(sku, 0);
	}

	public Product getProduct(String sku) {
		List<Product> products = pd.getAllProducts();
		if (sku.length()>0) {
			try {
				// Find the product with the matching slug
				for (Product product : products) {
					if (product.getSku().equals(sku)) {
						return product;
					}
				}
				throw new InvalidSkuException("SKU = "+sku+" is not assigned to any product");
			}catch (InvalidSkuException ise) {
				System.out.println(ise);
			}catch (Exception e) {
				e.printStackTrace();
			}
			return null;
		}
		return null;
	}

	public Product getProductBySlug(String slug) {
		List<Product> products = pd.getAllProducts();

		try {
			// Find the product with the matching slug
			for (Product product : products) {
				if (product.getUrlSlug().equals(slug)) {
					return product;
				}
			}
			throw new InvalidSlugException("URL Slug = "+slug+" is not assigned to any product.");
		} catch (InvalidSlugException ise) {
			System.out.println(ise);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	
	public String downloadProductCatalog() {
		try {
			query = "SELECT * FROM products";
			pst = this.con.prepareStatement(query);
			rs = pst.executeQuery();

			StringBuilder json = new StringBuilder("[\n");
			while (rs.next()) {
				json.append("{\n");
				json.append("\"id\":" + rs.getInt("id") + ",\n");
				json.append("\"name\":\"" + rs.getString("name") + "\",\n");
				json.append("\"category\":\"" + rs.getString("category") + "\",\n");
				json.append("\"price\":" + rs.getDouble("price") + ",\n");
				json.append("\"quantity\":" + rs.getInt("quantity") + ",\n");
				json.append("\"image\":\"" + rs.getString("image") + "\",\n");
				json.append("\"description\":\"" + rs.getString("description") + "\",\n");
				json.append("\"vendor\":\"" + rs.getString("vendor") + "\",\n");
				json.append("\"urlSlug\":\"" + rs.getString("urlSlug") + "\",\n");
				json.append("\"sku\":\"" + rs.getString("sku") + "\"\n");
				json.append("},\n");
			}
			pst.close();
			if (json.length()>2) {
				json.deleteCharAt(json.length() - 2); // Remove the trailing comma
			}
			json.append("]");

			return json.toString();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	public void createOrder(User user, String shippingAddress) {
		System.out.println("in createorder() bf");
		int orderId = 0;
		double totalCost = 0;
		Cart cart = user.getCart();
		od = new OrderDao(this.con);
		StringBuilder productsInCart =  new StringBuilder("[\n");
		for (Product p : cart.getCartProducts()) {
			productsInCart.append("{\n");
			productsInCart.append("\"id\":" + p.getId() + ",\n");
			productsInCart.append("\"name\":\"" + p.getName() + "\",\n");
			productsInCart.append("\"quantity\":" + p.getQuantity() + ",\n");
			productsInCart.append("\"vendor\":\"" + p.getVendor() + "\",\n");
			productsInCart.append("\"sku\":\"" + p.getSku() + "\",\n");
			productsInCart.append("\"price\":\"" + p.getPrice() + "\"\n");
			productsInCart.append("},\n");
			
			totalCost += (p.getPrice() * p.getQuantity());
		}
		System.out.println("total: " + totalCost);
		
		if (productsInCart.length()>2) {
			productsInCart.deleteCharAt(productsInCart.length() - 2); // Remove the trailing comma
		}
		productsInCart.append("]");
		int userId = user.getId();
		Order order = new Order(orderId, shippingAddress, 0, productsInCart.toString(), totalCost,
				userId);
		od.newOrder(order);
		System.out.println("done createorder() bf");
	}
	
	public List<Order> getOrders(User user) {
		System.out.println("In getOrders");
		List<Order> orders = new ArrayList<Order>();

		try {
			query = "select * from orders where userId = ?";
			pst = this.con.prepareStatement(query);
			pst.setInt(1, user.getId());
			rs = pst.executeQuery();
			while (rs.next()) {
				Order row = new Order();
				row.setOrderId(rs.getInt("orderId"));
				row.setShippingAddress(rs.getString("shippingAddress"));
				row.setTrackingNumber(rs.getInt("trackingNumber"));
				row.setDatePlaced(rs.getString("datePlaced"));
				row.setProductsInCart(rs.getString("productsInCart"));
				row.setTotalCost(rs.getDouble("totalCost"));
				row.setUserId(rs.getInt("userId"));

				orders.add(row);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println("Returning orders: " + orders);
		return orders;
	}
	
	public List<Order> getAllOrders() {
		System.out.println("In getAllOrders");
		List<Order> orders = new ArrayList<Order>();

		try {
			query = "select * from orders";
			pst = this.con.prepareStatement(query);
			rs = pst.executeQuery();
			while (rs.next()) {
				Order row = new Order();
				row.setOrderId(rs.getInt("orderId"));
				row.setShippingAddress(rs.getString("shippingAddress"));
				row.setTrackingNumber(rs.getInt("trackingNumber"));
				row.setDatePlaced(rs.getString("datePlaced"));
				row.setProductsInCart(rs.getString("productsInCart"));
				row.setTotalCost(rs.getDouble("totalCost"));
				row.setUserId(rs.getInt("userId"));

				orders.add(row);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println("Returning all orders: " + orders);
		return orders;
	}
	
	public Order getOrderById(String orderId) {
		List<Order> orders = getAllOrders();

		try {
			// Find the product with the matching slug
			for (Order order : orders) {
				String orderIdAsString = Integer.toString(order.getOrderId());
				if (orderIdAsString.equals(orderId)) {
					return order;
				}
			}
			// TODO - create a new exception for this order
			throw new InvalidSlugException("Order Id = "+orderId+" is not a valid id.");
		} catch (InvalidSlugException ise) {
			System.out.println(ise);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public void shipOrder(String orderId, String trackingNumber) {
		Order order;
        order = getOrderById(orderId);
        int orderIdAsInt = Integer.parseInt(orderId);
        order.setTrackingNumber(orderIdAsInt);
        
        int trackingNumberAsInt = getTrackingNumber(trackingNumber);
        
        query = "UPDATE orders SET trackingNumber = ? WHERE orderId = ?";
        try {
        	System.out.println("In try: " + query);
			pst = this.con.prepareStatement(query);
			pst.setInt(1, trackingNumberAsInt);
	        pst.setInt(2, orderIdAsInt);
	        pst.executeUpdate();
	        pst.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public int getTrackingNumber(String trackingNumber) {
		String result = "00000" + trackingNumber;
		result = result.substring(result.length() - 5);
		return Integer.parseInt(result);
	}
	
	public String getTrackingNumber(int trackingNumber) {
		String result = "00000" + Integer.toString(trackingNumber);
		result = result.substring(result.length() - 5);
		return result;
	}
}
