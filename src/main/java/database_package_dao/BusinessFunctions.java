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
		int orderId = 0;
		Cart cart = user.getCart();
		StringBuilder productsInCart =  new StringBuilder("[\n");
		for (Product p : cart.getCartProducts()) {
			productsInCart.append("{\n");
			productsInCart.append("\"id\":" + p.getId() + ",\n");
			productsInCart.append("\"name\":\"" + p.getName() + "\",\n");
			productsInCart.append("\"quantity\":" + p.getQuantity() + ",\n");
			productsInCart.append("\"vendor\":\"" + p.getVendor() + "\",\n");
			productsInCart.append("\"sku\":\"" + p.getSku() + "\"\n");
			productsInCart.append("},\n");
		}
		if (productsInCart.length()>2) {
			productsInCart.deleteCharAt(productsInCart.length() - 2); // Remove the trailing comma
		}
		productsInCart.append("]");
		int userId = user.getId();
		Order order = new Order(orderId, shippingAddress, 0, productsInCart.toString(),
				userId);
	}
}
