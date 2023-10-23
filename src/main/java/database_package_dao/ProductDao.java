package database_package_dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import database_package_model.Cart;
import database_package_model.Product;

public class ProductDao {
	private Connection con;
	private String query;
	private PreparedStatement pst;
	private ResultSet rs;

	public ProductDao(Connection con) {
		this.con = con;
	}

	public List<Product> getAllProducts() {
		List<Product> products = new ArrayList<Product>();

		try {
			query = "select * from products";
			pst = this.con.prepareStatement(query);
			rs = pst.executeQuery();
			while (rs.next()) {
				Product row = new Product();
				row.setId(rs.getInt("id"));
				row.setName(rs.getString("name"));
				row.setCategory(rs.getString("category"));
				row.setPrice(rs.getDouble("price"));
				row.setImage(rs.getString("image"));
				row.setDescription(rs.getString("description"));
				row.setVendor(rs.getString("vendor"));
				row.setUrlSlug(rs.getString("urlSlug"));
				row.setSku(rs.getString("sku"));

				products.add(row);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return products;
	}

	public ArrayList<Cart> getCart(ArrayList<Cart> cartList) {
		try {
			// after we confirm there are items in our cart list, we will proceed
			if (cartList.size() > 0) {
				return cartList;
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
		} finally {
			return (new ArrayList<Cart>());
		}
	}

	public Product getProduct(String sku) {
		List<Product> products = getAllProducts();

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
		List<Product> products = getAllProducts();

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

	public List<Cart> getCartProducts(ArrayList<Cart> cartList) {
		List<Cart> products = new ArrayList<Cart>();

		try {
			// after we confirm there are items in our cart list, we will proceed
			if (cartList.size() > 0) {
				for (Cart item : cartList) {
					query = "select * from products where id=?";
					pst = this.con.prepareStatement(query);
					pst.setInt(1, item.getId());
					rs = pst.executeQuery();
					while (rs.next()) {
						Cart row = new Cart();
						row.setId(rs.getInt("id"));
						row.setName(rs.getString("name"));
						row.setCategory(rs.getString("category"));
						row.setPrice(rs.getDouble("price") * item.getQuantity());
						row.setQuantity(item.getQuantity());
						row.setImage(rs.getString("image"));
						products.add(row);
					}
				}
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
		}

		return products;

	}

	public double getTotalCartPrice(ArrayList<Cart> cartList) {
		double sum = 0;

		try {
			if (cartList.size() > 0) {
				for (Cart item : cartList) {
					query = "select price from products where id=?";
					pst = this.con.prepareStatement(query);
					pst.setInt(1, item.getId());
					rs = pst.executeQuery();

					while (rs.next()) {
						sum += rs.getDouble("price") * item.getQuantity();
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return sum;
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

	public void createProduct(String sku, String name, String description, String category, String price,
			String quantity, String vendor, String slug) {
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
					updateName(sku, name);
				}
				if (!description.isBlank()) {
					updateDescription(sku, description);
				}
				if (!category.isBlank()) {
					updateCategory(sku, category);
				}
				if (!price.isBlank()) {
					updatePrice(sku, Double.parseDouble(price));
				}
				if (!quantity.isBlank()) {
					updateQuantity(sku, Integer.parseInt(quantity));
				}
				if (!vendor.isBlank()) {
					updateVendor(sku, vendor);
				}
				if (!slug.isBlank()) {
					updateSlug(sku, slug);
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

	public void updateProduct(String sku, String name, String description, String category, String price,
			String quantity, String vendor, String slug) {
		System.out.println(price);
		if (!name.isBlank()) {
			updateName(sku, name);
		}
		if (!description.isBlank()) {
			updateDescription(sku, description);
		}
		if (!category.isBlank()) {
			updateCategory(sku, category);
		}
		if (!price.isBlank()) {
			updatePrice(sku, Double.parseDouble(price));
		}
		if (!quantity.isBlank()) {
			updateQuantity(sku, Integer.parseInt(quantity));
		}
		if (!vendor.isBlank()) {
			updateVendor(sku, vendor);
		}
		if (!slug.isBlank()) {
			try {
				query = "SELECT * FROM products WHERE urlSlug=?";
				pst = this.con.prepareStatement(query);
				pst.setString(1, slug);
				rs = pst.executeQuery();
				if (!rs.next()) {
					updateSlug(sku, slug);
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

	public void updateName(String sku, String name) {
		try {
			query = "UPDATE products SET name = ? WHERE sku=?;";
			pst = this.con.prepareStatement(query);
			pst.setString(1, name);
			pst.setString(2, sku);
			pst.executeUpdate();
			pst.close();
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println(e.getMessage());
		}
	}

	public void updateDescription(String sku, String description) {
		try {
			query = "UPDATE products SET description = ? WHERE sku=?;";
			pst = this.con.prepareStatement(query);
			pst.setString(1, description);
			pst.setString(2, sku);
			pst.executeUpdate();
			pst.close();
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println(e.getMessage());
		}
	}

	public void updateCategory(String sku, String category) {
		try {
			query = "UPDATE products SET category = ? WHERE sku=?;";
			pst = this.con.prepareStatement(query);
			pst.setString(1, category);
			pst.setString(2, sku);
			pst.executeUpdate();
			pst.close();
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println(e.getMessage());
		}
	}

	public void updatePrice(String sku, double price) {
		try {
			query = "UPDATE products SET price = ? WHERE sku=?;";
			pst = this.con.prepareStatement(query);
			pst.setDouble(1, price);
			pst.setString(2, sku);
			pst.executeUpdate();
			pst.close();
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println(e.getMessage());
		}
	}

	public void updateQuantity(String sku, int quantity) {
		try {
			query = "UPDATE products SET quantity = ? WHERE sku=?;";
			pst = this.con.prepareStatement(query);
			pst.setInt(1, quantity);
			pst.setString(2, sku);
			pst.executeUpdate();
			pst.close();
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println(e.getMessage());
		}
	}

	public void updateVendor(String sku, String vendor) {
		try {
			query = "UPDATE products SET vendor = ? WHERE sku=?;";
			pst = this.con.prepareStatement(query);
			pst.setString(1, vendor);
			pst.setString(2, sku);
			pst.executeUpdate();
			pst.close();
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println(e.getMessage());
		}
	}

	public void updateSlug(String sku, String slug) {
		try {
			query = "SELECT * FROM products WHERE urlSlug=?;";
			pst = this.con.prepareStatement(query);
			pst.setString(1, slug);
			rs = pst.executeQuery();

			if (rs.next()) {
				query = "UPDATE products SET urlSlug = ? WHERE sku=?;";
				pst = this.con.prepareStatement(query);
				pst.setString(1, slug);
				pst.setString(2, sku);
				pst.executeUpdate();
				pst.close();
			} else {
				pst.close();
				// Throw Exception here
				System.out.println(false);
			}

		} catch (Exception e) {
			e.printStackTrace();
			System.out.println(e.getMessage());
		}
	}

	// public void updateImg(String sku, String img) {

	// }
}
