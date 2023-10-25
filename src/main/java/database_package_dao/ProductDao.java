package database_package_dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import database_package_model.Cart;
import database_package_model.Product;
import database_package_model.User;

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

	public ArrayList<Product> getCart(User user) {
		return user.getCart().getCartProducts();
	}


	public ArrayList<Product> getCartProducts(ArrayList<Product> cartList) {
		ArrayList<Product> products = new ArrayList<Product>();

		try {
			// after we confirm there are items in our cart list, we will proceed
			if (cartList.size() > 0) {
				for (Product p : cartList) {
					query = "select * from products where id=?";
					pst = this.con.prepareStatement(query);
					pst.setInt(1, p.getId());
					rs = pst.executeQuery();
					while (rs.next()) {
						Product row = new Product();
						row.setId(rs.getInt("id"));
						row.setName(rs.getString("name"));
						row.setCategory(rs.getString("category"));
						row.setPrice(rs.getDouble("price") * p.getQuantity());
						row.setQuantity(p.getQuantity());
						row.setImage(rs.getString("image"));
						cartList.add(row);
					}
				}
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
		}

		return products;

	}

	public double getTotalCartPrice(ArrayList<Product> cartList) {
		double sum = 0;

		try {
			if (cartList.size() > 0) {
				for (Product item : cartList) {
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

	public void updateImage(String sku, String image) {
		try {
			query = "UPDATE products SET image = ? WHERE sku=?;";
			pst = this.con.prepareStatement(query);
			pst.setString(1, image);
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
	public void updateQuantity(String sku, int quantity) {
		try {
			query = "UPDATE products SET quantity = ? WHERE sku=?;";
			pst = this.con.prepareStatement(query);
			pst.setInt(1, quantity+1);
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
			query = "SELECT * FROM products WHERE urlSlug=?";
			pst = this.con.prepareStatement(query);
			pst.setString(1, slug);
			rs = pst.executeQuery();
			if (!rs.next()) {
				query = "UPDATE products SET urlSlug = ? WHERE sku=?;";
				pst = this.con.prepareStatement(query);
				pst.setString(1, slug);
				pst.setString(2, sku);
				pst.executeUpdate();
				pst.close();
			} else {
				pst.close();
				throw new InvalidSlugException("URL Slug = "+slug+" is already assigned to another product.");
			}
				
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println(e.getMessage());
		}
	}
}
