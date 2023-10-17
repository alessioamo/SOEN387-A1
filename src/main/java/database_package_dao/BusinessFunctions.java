/*
 * The expected business functions are:
• CreateProduct(sku, name): Create a new product, with the given name and SKU.
• UpdateProduct(sku, …): Update some of all the fields of a product with the given sku
• GetProduct(sku): Get all the information on a product by SKU
• GetProductBySlug(slug): Get all the information on a product by slug
• GetCart(user): Get the list of products in the cart associated with the user, if there is no cart
associated with the user, return the empty list.
• AddProductToCart(user, sku): Add a product to the cart associated with the user. If no cart is
associated with the user, create one first.
• RemoveProductFromCart(user, sku): Remove all instances of a product from the cart associated
with the user. If no cart is associated with the user, no operation is performed.
• DownloadProductCatalog(): Produce a file that contains a list of all the products in the store
which includes name, description, vendor, URL slug, SKU, and price
 */

package database_package_dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import database_package_model.*;

public class BusinessFunctions {
	private Connection con;
	private String query;
	private PreparedStatement pst;
	private ResultSet rs;

	public BusinessFunctions(Connection con) {
		this.con = con;
	}

	public Product CreateProduct(String sku, String name) {
		Product product = null;
		try {
			query = "INSERT INTO products (id, name, sku) VALUES (?, ?, ?)";
			pst = this.con.prepareStatement(query);
			pst.setInt(1, 22);
			pst.setString(2, name);
			pst.setString(3, sku);
			pst.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println(e.getMessage());
		}
		return product;
	}
	
	/*public Product GetProduct(String sku) {
		Product product = null;
		try {
			query = "select * from products where sku=?";
			pst = this.con.prepareStatement(query);
			pst.setString(1, sku);
			rs = pst.executeQuery();
			
		}
		catch (Exception e) {
			e.printStackTrace();
			System.out.println(e.getMessage());
		}
		return product;
	}*/
}
