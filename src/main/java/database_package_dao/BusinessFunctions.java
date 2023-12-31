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
		user.getCart().setCartProducts(newCartList);
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
		user.getCart().setCartProducts(newCartList);
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
			e.printStackTrace();
		}
		return null;
	}
	
	public void clearCart(User user) {
		Cart cart = user.getCart();
		for (Product p : cart.getCartProducts()) {
			pd.updateQuantity(p.getSku(), 0);
		}
		cart.setCartProducts(new ArrayList<Product>());
		user.setCart(cart);	
	}
	
	public void SetProductQuantityInCart(User user, String sku, int quantity) {
		Cart cart = user.getCart();
		ArrayList<Product> newCartList = cart.getCartProducts();
		boolean isFound = false;
		for (Product p : newCartList) {
			if (p.getSku().equals(sku)) {
				p.setQuantity(quantity);
				pd.updateQuantity(sku, quantity);
				isFound = true;
				user.getCart().setCartProducts(newCartList);
				break;
			}
			
		}
	}

	public void createOrder(User user, String shippingAddress) {
		System.out.println("start createorder() bf");
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
		
		if (productsInCart.length()>2) {
			productsInCart.deleteCharAt(productsInCart.length() - 2); // Remove the trailing comma
		}
		productsInCart.append("]");
		String productsInCartString = productsInCart.toString().replace("\\", "");

		int userId = user.getId();
		Order order = new Order(orderId, shippingAddress, 0, productsInCartString, totalCost,
				userId);
		od.newOrder(order);
		getOrder(user, 1);
		clearCart(user);
	}
	
	public List<Order> getOrders(User user) {
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
		return orders;
	}
	
	public Order getOrder(User user, int orderId) {
		Order order = new Order();

		try {
			query = "select * from orders where userId = ? and orderId = ?";
			pst = this.con.prepareStatement(query);
			pst.setInt(1, user.getId());
			pst.setInt(2, orderId);
			rs = pst.executeQuery();
			if (rs.next()) {
				order.setOrderId(rs.getInt("orderId"));
				order.setShippingAddress(rs.getString("shippingAddress"));
				order.setTrackingNumber(rs.getInt("trackingNumber"));
				order.setDatePlaced(rs.getString("datePlaced"));
				order.setProductsInCart(rs.getString("productsInCart"));
				order.setTotalCost(rs.getDouble("totalCost"));
				order.setUserId(rs.getInt("userId"));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println("getOrder(user orderid) returned this order: " + order);
		return order;
	}
	
	public List<Order> getAllOrders() {
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
			throw new InvalidOrderIdException("Order Id = "+orderId+" is not a valid id.");
		} catch (InvalidOrderIdException ioe) {
			System.out.println(ioe);
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
	
	public boolean setOrderOwner(int orderId, int userId){
		boolean isValidOrder = false;
		query = "select * from orders WHERE orderId = ?";
		try {
			pst = this.con.prepareStatement(query);
			pst.setInt(1, orderId);
			try (ResultSet rs = pst.executeQuery()) {
                // If a row is found, check if userId is 0
                if (rs.next()) {
                    int userIdFromDB = rs.getInt("userId");
                    if (userIdFromDB == 0) {
                    	System.out.println("Valid Unclaimed Order.");
                    	isValidOrder = true;
                    }
                    else {
                    	System.out.println("Invalid Order, Belongs To A User.");
                    	isValidOrder = false;
                    }
                }
            }
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if (isValidOrder) {
			Order order = getOrderById(Integer.toString(orderId));
	        order.setUserId(userId);
	        
	        query = "UPDATE orders SET userId = ? WHERE orderId = ?";
	        try {
				pst = this.con.prepareStatement(query);
				pst.setInt(1, userId);
		        pst.setInt(2, orderId);
		        pst.executeUpdate();
		        pst.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
	        return true;
		}
		
		else {
			System.out.println("Invalid Order ID. Belongs To Another User or Doesn't Exist.");
			return false;
		}
	}
	
	public int getMostRecentOrderId(User user) {
		int orderId = -1; // Default value if no order is found

        query = "SELECT MAX(orderId) AS max_id FROM Orders WHERE userId = ?";
        try {
			pst = this.con.prepareStatement(query);
			pst.setInt(1, user.getId());
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        
        try (ResultSet rs = pst.executeQuery()) {
            if (rs.next()) {
                orderId = rs.getInt("max_id");
            }
        } catch (SQLException e) {
        	e.printStackTrace();
        }

        return orderId;
	}
}
