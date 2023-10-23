package database_package_dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

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
			String vendor, String slug) {
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
				// Throw Exception here
				System.out.println(false);
			}
			pst.close();

		} catch (Exception e) {
			e.printStackTrace();
			System.out.println(e.getMessage());
		}
	}

	public void updateProduct(String sku, String name, String description, String category, String price, String image,
			String vendor, String slug) {
		System.out.println(price);
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
			try {
				query = "SELECT * FROM products WHERE urlSlug=?";
				pst = this.con.prepareStatement(query);
				pst.setString(1, slug);
				rs = pst.executeQuery();
				if (!rs.next()) {
					pd.updateSlug(sku, slug);
				} else {
					pst.close();
					// throw exception here
					System.out.println("FALSE");
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
	}

	public Product getProduct(String sku) {
		List<Product> products = pd.getAllProducts();

		try {
			// Find the product with the matching slug
			for (Product product : products) {
				if (product.getSku().equals(sku)) {
					return product;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
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
		} catch (Exception e) {
			e.printStackTrace();
		}

		// If the product with the given slug is not found, return null or throw an
		// exception
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
			json.deleteCharAt(json.length() - 2); // Remove the trailing comma
			json.append("]");

			return json.toString();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
}
